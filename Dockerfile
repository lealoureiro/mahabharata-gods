FROM adoptopenjdk:11.0.5_10-jdk-hotspot-bionic as builder

LABEL maintainer="Leandro Loureiro <leandroloureiro@pm.me>"

COPY src src
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw

RUN ./mvnw clean install


FROM adoptopenjdk:11.0.5_10-jre-hotspot-bionic

WORKDIR /root/

COPY --from=builder /target/mahabharata-gods-1.0-SNAPSHOT.jar mahabharata-gods.jar

RUN ["java", "-XX:DumpLoadedClassList=classes.lst", "-jar", "mahabharata-gods.jar"]
RUN ["java", "-XX:+UseAppCDS", "-Xshare:dump", "-XX:DumpLoadedClassList=classes.lst", "-XX:SharedArchiveFile=app-cds.jsa", "--class-path", "mahabharata-gods.jar"]


EXPOSE 8080

CMD ["java","-Xshare:on", "-XX:SharedArchiveFile=app-cds.jsa", "-jar", "mahabharata-gods.jar"]
