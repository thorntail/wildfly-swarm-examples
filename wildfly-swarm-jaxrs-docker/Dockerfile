FROM java:openjdk-8-jdk

ADD target/SwarmDocker-swarm.jar /opt/SwarmDocker-swarm.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/SwarmDocker-swarm.jar"]
