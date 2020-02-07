FROM openjdk:12.0.1-jdk
COPY /target /

CMD ["java","-jar","myapp-0.0.1.jar"]