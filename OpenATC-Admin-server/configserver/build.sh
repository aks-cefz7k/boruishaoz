docker build -t docker-registry:5000/openatc/configserver:latest .
docker push docker-registry:5000/openatc/configserver:latest
docker build -t docker-registry:5000/openatc/configserver:2.0.0 .
docker push docker-registry:5000/openatc/configserver:2.0.0