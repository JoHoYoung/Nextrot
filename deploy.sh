# mvn package spring-boot:repackage -Dmaven.test.skip=true
# java -jar ./target/myapp-0.0.1.jar

/Users/HY/Downloads/apache-maven-3.6.3/bin/mvn package spring-boot:repackage -Dmaven.test.skip=true -Djava.net.preferIPv4Stack=true -P prod

docker rm -f nextrot
docker image rm nextrot
docker build -t nextrot .
docker tag nextrot 34.64.150.65:5000/nextrot:1.3
docker push 34.64.150.65:5000/nextrot:1.3