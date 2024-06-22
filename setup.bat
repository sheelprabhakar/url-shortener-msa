
cd token-service
break>src\main\resources\application.properties
echo spring.profiles.active=stage>> src\main\resources\application.properties
call gradlew build
docker build . --file Dockerfile --tag sheelprabhakar/token-service:latest
docker image push sheelprabhakar/token-service:latest
break>src\main\resources\application.properties
echo spring.profiles.active=dev>> src\main\resources\application.properties

cd ..
cd shortener-service
break>src\main\resources\application.properties
echo spring.profiles.active=stage>> src\main\resources\application.properties
call gradlew build
docker build . --file Dockerfile --tag sheelprabhakar/shortener-service:latest
docker image push sheelprabhakar/shortener-service:latest
break>src\main\resources\application.properties
echo spring.profiles.active=dev>> src\main\resources\application.properties

cd ..
cd redirect-service
break>src\main\resources\application.properties
echo spring.profiles.active=stage>> src\main\resources\application.properties
call gradlew build
docker build . --file Dockerfile --tag sheelprabhakar/redirect-service:latest
docker image push sheelprabhakar/redirect-service:latest
echo spring.profiles.active=dev>> src\main\resources\application.properties
break>src\main\resources\application.properties
echo spring.profiles.active=dev>> src\main\resources\application.properties

cd ..
reset-deployment.bat