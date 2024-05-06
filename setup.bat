cd token-service
call gradlew build
docker build . --file Dockerfile --tag sheelprabhakar/token-service:latest
docker image push sheelprabhakar/token-service:latest

cd ..
cd shortener-service
call gradlew build
docker build . --file Dockerfile --tag sheelprabhakar/shortener-service:latest
docker image push sheelprabhakar/shortener-service:latest

cd ..
cd redirect-service
call gradlew build
docker build . --file Dockerfile --tag sheelprabhakar/redirect-service:latest
docker image push sheelprabhakar/redirect-service:latest

cd ..
reset-deployment.bat