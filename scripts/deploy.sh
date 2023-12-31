#!/bin/bash
#ec2 step2 환경에서 실행될 스크립트

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=fr-springboot

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 앱 pid 확인"
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/| grep jar | grep -v "plain" | tail -n 1)

echo "> JAR_NAME: $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

source ~/.bashrc

echo "> $JAR_NAME 실행"

 # profile 활성화 부분은 내가 짠 코드라 개선 필요할 수 있음.
nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=real,oauth,real-db \
  $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &