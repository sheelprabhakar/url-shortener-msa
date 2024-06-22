REM Deploy Backing services
kubectl apply -f cd/backend/cassandra.yml
kubectl apply -f cd/backend/mysql-configmap.yml
kubectl apply -f cd/backend/mysql.yml
kubectl apply -f cd/backend/redis.yml

REM Deploy Ingress services
kubectl apply -f cd/ingress/nginx-ingress-deploy.yaml
kubectl apply -f cd/ingress/ingress.yaml
timeout 60 > NUL
REM Deploy Service Backends
kubectl delete -f redirect-service/redirect-service.yaml
kubectl apply -f redirect-service/redirect-service.yaml

kubectl delete -f shortener-service/shortener-service.yaml
kubectl apply -f shortener-service/shortener-service.yaml

kubectl delete -f token-service/token-service.yaml
kubectl apply -f token-service/token-service.yaml