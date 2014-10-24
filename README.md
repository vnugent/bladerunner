Blade Runner
===========

A simple cron-like housekeeping app that deletes Kubernetes objects older than x days


How to run
==========
From scratch
------------
```
mvn package
export KUBERNETES_API_ENDPOINT=http://<your kubernete api server>:8080/api/v1beta1/
java -jar target/bladerunner.jar
```

Read-to-go Docker image
-----------------------
```
docker pull vnguyen/bladerunner
docker run -d -e "KUBERNETES_API_ENDPOINT=http://<your kubernete api server>:8080/api/v1beta1/" vnguyen/bladerunner
```
