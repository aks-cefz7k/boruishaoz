docker push kiss-registry:5000/agent
docker images | grep 'kedacom' | awk '{print $3}' | xargs docker rmi --force
id
ssh root@192.168.13.103 > /dev/null 2>&1 << remotessh
exit
remotessh
echo done!