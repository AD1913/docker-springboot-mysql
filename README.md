A simple Spring Boot application that polls files present in a particular directory, validates, process the file and stores them in the MYSQL database.

On processing the file, it is moved to another directory.

Dockerfile is written to execute the application as a container. It can also be run using mvn spring-boot commands but relevant database details need to be
updated inside the application.properties

docker-compose is being used to bring up application and the dependent database in a single go in the same docker network.

wait commands are being used for the application to wait till the database is completely up and running.


To run this application:

maven build has to be run first to create the required artifacts which are used in docker image

```$code
mvn clean install
```

```$code
docker image build -t products:TAG .
```

Once the new image is built, corresponding tag needs to be updated inside docker-compose.yml file
under products -> image:""

```$code
>docker-compose up -d
```

To build it without docker, following commands can be used but there should be a separate mysql database up and running. 
These details need to be updated in application.properties before executing the below commands:

```$code
> mvn clean install
```

To run it:

```$code
> mvn spring-boot:run
```

Once the application is up, json files can be placed under input/fileread folder. Processed files both successful and failure are moved to 
input/processed folder and the files which could not be processed are moved to input/error folders.


Sample files, drop-1.json, drop-2.json, drop-3.json are copied to /input folder for easy testing.
drop-1.json is duplicated as drop-4.json to test the negative functionality of excluding the already processed file.


Added the target folder in this repository which is generated post maven build just for reference