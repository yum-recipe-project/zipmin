FROM tomcat:10.1-jdk17-temurin

# 기본 ROOT 앱 제거
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Gradle로 생성된 war를 Tomcat webapps에 배치
ARG WAR_FILE=build/libs/*.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080