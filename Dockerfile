FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic as builder

LABEL maintainer="Leandro Loureiro <leandroloureiro@pm.me>"

COPY src src
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw

RUN ./mvnw clean install


FROM adoptopenjdk:11.0.4_11-jre-hotspot-bionic

WORKDIR /root/

COPY --from=builder /target/mahabharata-gods-1.0-SNAPSHOT.jar mahabharata-gods.jar

RUN java -Xshare:dump

EXPOSE 8080

CMD ["java", "-XX:MaxDirectMemorySize=10M", "-XX:ReservedCodeCacheSize=48M", "-Xss1M", "-Xmx874088K", "-jar", "mahabharata-gods.jar"]