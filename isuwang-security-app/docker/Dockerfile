FROM docker.oa.isuwang.com:5000/system/java
VOLUME /tmp
ADD isuwang-security-app-1.0-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'
#ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -Djava.security.egd=file:/dev/./urandom -jar  /app.jar" ]