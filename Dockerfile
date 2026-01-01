FROM tomcat:10.1-jdk21-temurin

# 기존 ROOT 앱 제거
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# WAR 배포
ARG WAR_FILE=build/libs/zipmin.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080







# FROM eclipse-temurin:21-jdk

# JAR 복사
# ARG JAR_FILE=build/libs/zipmin.jar
# COPY ${JAR_FILE} app.jar

# 실행
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# EXPOSE 8080