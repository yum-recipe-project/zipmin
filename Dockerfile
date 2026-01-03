FROM tomcat:10.1-jdk21-temurin

# ROOT 제거
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# WAR 배포
ARG WAR_FILE=build/libs/zipmin.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080