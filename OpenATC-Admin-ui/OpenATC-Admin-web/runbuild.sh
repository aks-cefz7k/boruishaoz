#!/bin/sh
npm install --registry=http://192.168.13.116/artifactory/api/npm/npm_mirror/
npm run build
docker build -t 192.168.13.110:5000/kedacom/kissedge:latest -f Dockerfile .
docker push  192.168.13.110:5000/kedacom/kissedge:latest
docker push  192.168.13.110:5000/kedacom/agent
ssh root@192.168.13.103 > /dev/null 2>&1 << remotessh
docker service update --force kiss-stack-16_agent
docker service update --force kiss-stack-16_kisswebedge
exit
remotessh
echo done!
