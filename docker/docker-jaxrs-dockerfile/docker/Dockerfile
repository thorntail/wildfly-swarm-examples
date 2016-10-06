FROM openjdk:jre-alpine

ADD example-docker-jaxrs-dockerfile-swarm.jar /opt/wildfly-swarm.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/wildfly-swarm.jar"]
