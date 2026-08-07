# docker build -t docker-registry:5000/openatc/openatc-admin:latest .
# docker build -t docker-registry:5000/openatc/openatc-admin:1.20.1 .

docker push docker-registry:5000/openatc/openatc-admin:latest
docker push docker-registry:5000/openatc/openatc-admin:%maven.project.artifactId%

id
ssh root@192.168.13.103 > /dev/null 2>&1 << remotessh
docker service update --image docker-registry:5000/openatc/openatc-admin:latest openatc_openatc
exit
remotessh
echo done!