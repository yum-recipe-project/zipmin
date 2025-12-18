FROM tomcat:10.1-jdk21-temurin

# 기존 기본 앱 제거
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# RUN rm -rf /usr/local/tomcat/webapps/*

# WAR를 ROOT로 배포
# ARG WAR_FILE=build/libs/*.war
ARG WAR_FILE=build/libs/zipmin.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080