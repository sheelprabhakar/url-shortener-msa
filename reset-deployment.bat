kubectl delete -f redirect-service/redirect-service.yaml
kubectl apply -f redirect-service/redirect-service.yaml

kubectl delete -f shortener-service/shortener-service.yaml
kubectl apply -f shortener-service/shortener-service.yaml

kubectl delete -f token-service/token-service.yaml
kubectl apply -f token-service/token-service.yaml