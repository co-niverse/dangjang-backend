#!/bin/bash

ROOT_PATH=/home/ubuntu/dangjang
JAR_FILE_PATH=$(ls $ROOT_PATH/build/libs/*SNAPSHOT.jar)
JAR_FILE_NAME=$(basename $JAR_FILE_PATH)

DEPLOY_LOG="$ROOT_PATH/deploy.log"
TIME_NOW=$(date +%c)

echo "-------------------------------------------------------" >> $DEPLOY_LOG
echo "Start deployment on the development environment server." >> $DEPLOY_LOG

# 애플리케이션 pid 확인
CURRENT_PID=$(pgrep -f $JAR_FILE_NAME)

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]
then
  echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료 " >> $DEPLOY_LOG
  sudo kill -15 $CURRENT_PID
fi