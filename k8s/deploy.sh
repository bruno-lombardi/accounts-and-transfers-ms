#!/usr/bin/env bash
echo "Building container image"
cd ../
./gradlew jibDockerBuild
echo "Pushing image to local repository"
cd k8s/
docker push 192.168.205.3:32000/accounts-and-transfers:latest
echo "Applying services using kubectl"
kubectl delete service accounts-and-transfers-service
kubectl apply -f ./accounts-and-transfers
kubectl expose deployment accounts-and-transfers --type=NodePort --name=accounts-and-transfers-service
echo "Wait for pods to start, and run start.sh script to see service available in http://localhost:8080"
pod=$(kubectl get pods | awk '{print $1}' | grep -e "accounts-and-transfers" | tail -n 1)
echo "Waiting for pod ${pod} to be running"
kubectl wait --for=condition=Ready pod/${pod}
kubectl port-forward svc/accounts-and-transfers-service 8080:8080