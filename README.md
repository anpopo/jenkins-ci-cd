# 도커, 젠킨스, 스프링부트 어플리케이션 ci

---
## 1. 도커 젠킨스 컨테이너 구동
``` shell
$ docker pull jenkins/jenkins
$ docker run -d -p 8088:8080 -p 50000:50000 --name jenkins-server --restart=on-failure jenkins/jenkins:2.375.2-lts-jdk11
$ docker exec -it -u root jenkins-server /bin/bash # 관리자 권한으로 실행
$ cat /var/jenkins_home/secrets/initialAdminPassword # 관리자 로그인용 최초 비밀번호
```

- jenkins 기본 jdk 설정 변경
  - jenkins 대시보드 접속 > jenkins 관리 > Global Tool Configuration > jdk 
    - Install automatically 체크 해제
    - name : openjdk-17-jdk
    - JAVA_HOME : /usr/lib/jvm/java-17-openjdk-amd64

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
 
## 3. 깃허브 웹훅 생성
- 깃허브 웹훅은 localhost 를 지원하지 않기 때문에 [ngrok](https://ngrok.com/) 과 같은 서비스를 이용해서 웹훅 테스트를 해야한다.
- github 의 레포지토리에서 setting > webhooks > add webhook 버튼을 클릭한다.
>
> Payload URL : https://<로컬 젠킨스 호스팅 주소>/github-webhook/\
> Content type : application/json
>

## 4. 젠킨스 크레덴셜 생성 및 젠킨스 파이프라인 아이템 생성
- jenkins dashboard (http://localhost:8088) 접속
- dashboard > jenkins 관리 > Credentials > System > Global credential > new credentials
>
> username : 깃 허브 유저 명\
> password : 깃 허브 토큰 명\
> id : 젠킨스에서 사용할 credential id
> 

- dashboard > new item > pipeline 으로 새로운 파이프라인 아이템을 생성
- 체크 항목
  - Github project : 본인의 원격 레포지토리 주소 (웹훅이 등록되어 있는 레포지토리)
  - Build Triggers > GitHub hook trigger for GITScm polling
  - Pipeline > Definition : Pipeline script from SCM
    - Repository URL : 원격 레포지토리 주소
    - credentials : 등록한 credential
    - branch : 선택
    - Script Path : Jenkinsfile (원격 레포지토리의 jenkinsfile 을 읽어 jenkins pipeline script 실행)


