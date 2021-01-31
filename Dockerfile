From openjdk:8
COPY ./target/products-0.0.1-SNAPSHOT.jar products-0.0.1-SNAPSHOT.jar

RUN mkdir -p /input/processed && \
	mkdir -p /input/error

COPY ./input/*.json /input/ 

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD ["java","-jar","products-0.0.1-SNAPSHOT.jar"]