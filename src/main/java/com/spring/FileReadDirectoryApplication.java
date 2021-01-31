package com.spring;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.spring.filter.LastModifiedFileFilter;
import com.spring.processor.FileProcessor;

@SpringBootApplication
public class FileReadDirectoryApplication {
	private static final String DIRECTORY = "input/fileread/";

    public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(FileReadDirectoryApplication.class, args);

		//createFiles();
	}

	@Bean
	public IntegrationFlow processFileFlow() {
		return IntegrationFlows
				.from("fileInputChannel")
				.enrichHeaders(h -> h.headerExpression(FileHeaders.ORIGINAL_FILE, "payload"))
//				.transform(fileToStringTransformer())
//				.handle("fileProcessor", "process").get();
				.handle("fileProcessor", "process", e -> e.advice(advice())).get();
	}

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> fileReadingMessageSource() {
		CompositeFileListFilter<File> filters =new CompositeFileListFilter<>();
//		filters.addFilter(new SimplePatternFileListFilter("*.txt"));
		filters.addFilter(new LastModifiedFileFilter());

		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(new File(DIRECTORY));
		source.setFilter(filters);

		return source;
	}

	@Bean
	public FileToStringTransformer fileToStringTransformer() {
		return new FileToStringTransformer();
	}

	@Bean
	public FileProcessor fileProcessor() {
		return new FileProcessor();
	}
	
	@Bean
    public AbstractRequestHandlerAdvice advice() {
        return new AbstractRequestHandlerAdvice() {

            @Override
            protected Object doInvoke(ExecutionCallback callback, Object target, Message<?> message) throws Exception {
                File file = message.getHeaders().get(FileHeaders.ORIGINAL_FILE, File.class);
                System.out.println("file details"+message.getHeaders());
                System.out.println("file name is " + file.getName());
                try {
                    Object result = callback.execute();
                    File newFile = new File("input/processed/", file.getName());
                    System.out.println("new file is" + newFile);
                    file.renameTo(newFile);
                    System.out.println("File " + file.getName() + " renamed after processing ");
                    return result;
                }
                catch (Exception e) {
                	File newFile = new File("input/error/", file.getName());
                    System.out.println("new file is" + newFile);
                    file.renameTo(newFile);
                    System.out.println("File " + file.getName() + " renamed after failure");
                    throw e;
                }
            }
        };
    }

}
