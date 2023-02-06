From openjdk:8

ADD /target/manager-security-0.0.1-SNAPSHOT.jar manager-security-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","manager-security-0.0.1-SNAPSHOT.jar"]