# mvn package spring-boot:repackage -Dmaven.test.skip=true
# java -jar ./target/myapp-0.0.1.jar

/Users/HY/Downloads/apache-maven-3.6.3/bin/mvn package spring-boot:repackage -Dmaven.test.skip=true -Dspring.profiles.active=prod

docker rm -f nextrot
docker image rm nextrot
docker build -t nextrot .
docker tag nextrot 34.64.150.65:5000/nextrot:0.8
docker push 34.64.150.65:5000/nextrot:0.8

# docker run -p 8080:8080 -d --name groot groot:0.1

# docker run -p 8080:8080 -d --name nextrot 34.64.150.65:5000/nextrot