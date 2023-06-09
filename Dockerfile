FROM openjdk:11-jdk-slim

ARG JAR_FILE_PATH=build/libs/online_auction_shop-1.0.jar

WORKDIR /opt/app

EXPOSE 8080

COPY ${JAR_FILE_PATH} /opt/app/online_auction_shop.jar

ENTRYPOINT ["java","-jar","online_auction_shop.jar"]