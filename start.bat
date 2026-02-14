@echo off
REM Start Spring Boot Application with IPv4 preference
set JAVA_OPTS=-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false

echo Starting GitHub Profile Analyzer with IPv4 DNS configuration...
call mvnw.cmd spring-boot:run -Dspring-boot.run.jvmArguments="%JAVA_OPTS%"
