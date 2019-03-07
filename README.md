# onevote-command

1. stop and reomve all docker container(skip this if this is your first run)
   -- docker stop $(docker ps -aq)
   -- docker rm $(docker ps -aq)
2. remove docker image
   -- docker rmi producer
3. mvn -U clean -Dkiptests
4. docker build -t producer:latest .





swagger:
http://localhost:8085/swagger-ui.html

health:
http://localhost:8085/actuator/health

