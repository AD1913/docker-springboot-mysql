package com.spring.processor;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dao.ProductDao;
import com.spring.domain.Data;

public class FileProcessor {
	
	@Autowired
	ProductDao productDao;
	
    private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG = "%s received. Content: %s";

    public void process1(Message<String> msg) {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        String content = msg.getPayload();

        System.out.println(String.format(MSG, fileName, content));
    }
    
    public void process(Message<File> msg) {
//        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        File file = msg.getHeaders().get(FileHeaders.ORIGINAL_FILE, File.class);
        File content = msg.getPayload();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
			Data customer = objectMapper.readValue(content, Data.class);
			System.out.println(customer);
			System.out.println("Processing the file " + file.getName());
			if (!productDao.isFileProcessed(customer.getTransmissionsummary().getId()))
			{
			int qtySum = customer.getProducts().stream().mapToInt(o -> o.getQty()).sum();
			
			System.out.println("qtySum is " + qtySum);
			
			if (customer.getTransmissionsummary().getRecordcount() == 
					customer.getProducts().size() && 
					qtySum == customer.getTransmissionsummary().getQtysum()){
				
				
				productDao.insertProducts(customer);
				
				System.out.println("Completed processing the file " + file.getName());
				
				
			} else {
				System.out.println("Discarding file "+ file.getName() +", incorrect record count or quantities");
			}
			
			List<Map<String, Object>> summary = productDao.getProductSummary();
			
			for (Map<String, Object> row : summary) {
				String l3Category = (String) row.get("l3Category");
				String location = (String) row.get("location");
				BigDecimal qty = (BigDecimal) row.get("qty");
				
				System.out.println(l3Category + "-" + location + "-" + Integer.valueOf(qty.intValue()));
			}
			} else {
				System.out.println("Skiiping file "+ file.getName());
			}
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
