FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1
VOLUME /tmp
ARG EXTRACTED=target/extracted
COPY ${EXTRACTED}/dependencies/ ./
RUN true
COPY ${EXTRACTED}/spring-boot-loader/ ./
RUN true
COPY ${EXTRACTED}/snapshot-dependencies/ ./
RUN true
COPY ${EXTRACTED}/application/ ./
RUN true
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
