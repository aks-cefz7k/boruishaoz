docker build -t docker-registry:5000/openatc/admin:latest .
docker push docker-registry:5000/openatc/admin:latest
docker build -t docker-registry:5000/openatc/admin:1.20.1 .
docker push docker-registry:5000/openatc/admin:1.20.1