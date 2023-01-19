# 도커, 젠킨스, 스프링부트 어플리케이션 ci

---
## 1. 도커 젠킨스 컨테이너 구동
``` shell
$ docker pull jenkins/jenkins
$ docker run -d -p 8088:8080 -p 50000:50000 --name jenkins-server --restart=on-failure jenkins/jenkins:2.375.2-lts-jdk11
```
- 젠킨스는 자바 기반의 오픈소스로 jdk 를 내장하고 있다.
- 현재 jenkins:lts 이미지의 jdk 버전은 11 로 스프링 부트 3.0.1 의 기본 요구 사항인 17 버전으로 맞춰야 한다.

## 2. 젠킨스 컨테이너 jdk 변경 11 -> 17
``` shell
$ docker exec -it -u root jenkins-server /bin/bash # 관리자 권한으로 실행
$ apt-get update
$ apt-get install openjdk-17-jdk -y
$ export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 # 환경 변수 셋팅
$ export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
_=/usr/bin/env:/usr/lib/jvm/java-17-openjdk-amd64/bin # 패스 환경 변수 설정 변경
$ javac --version # javac 17.0.4
```
 

