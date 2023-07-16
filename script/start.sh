#!/bin/bash

JAR_FILE_PATH=$(ls /*.jar)
JAR_FILE_NAME=$(basename $JAR_FILE_PATH)

APP_LOG="/log/app$(date +%Y-%m-%d-%T).log"
ERROR_LOG="/log/error$(date +%Y-%m-%d-%T).log"
DEPLOY_LOG="/log/deploy$(date +%Y-%m-%d-%T).log"
TIME_NOW=$(date +%c)

echo "-------------------------------------------------------" >> $DEPLOY_LOG
echo "Start deployment on the development environment server." >> $DEPLOY_LOG

# dev profile로 jar 파일 실행
echo "$TIME_NOW > $JAR_FILE_NAME 파일 실행" >> $DEPLOY_LOG
nohup java -jar -Dspring.profiles.active=dev $JAR_FILE_NAME > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE_NAME)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
echo "-------------------------------------------------------" >> $DEPLOY_LOG

while true; do
  sleep 60
done