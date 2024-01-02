#!/usr/bin/env bash

#실제 파일의 경로를 반환
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
#source = import
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
# 구동중인 앱 중에서 IDLE_PORT 에 해당하는 것의 pid 출력
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi