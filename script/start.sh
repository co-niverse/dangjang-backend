#!/bin/bash

ROOT_PATH=/home/ubuntu/dangjang
JAR_FILE_PATH=$(ls $ROOT_PATH/build/libs/*SNAPSHOT.jar)
JAR_FILE_NAME=$(basename $JAR_FILE_PATH)

APP_LOG="$ROOT_PATH/app.log"
ERROR_LOG="$ROOT_PATH/error.log"
DEPLOY_LOG="$ROOT_PATH/deploy.log"
TIME_NOW=$(date +%c)

echo "-------------------------------------------------------" >> $DEPLOY_LOG
echo "Start deployment on the development environment server." >> $DEPLOY_LOG

# jar 파일 이동
echo "$TIME_NOW > $JAR_FILE_NAME 파일 이동" >> $DEPLOY_LOG
mv $JAR_FILE_PATH $ROOT_PATH

# dev profile로 jar 파일 실행
echo "$TIME_NOW > $JAR_FILE_NAME 파일 실행" >> $DEPLOY_LOG
nohup java -jar -Dspring.profiles.active=dev $ROOT_PATH/$JAR_FILE_NAME > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE_NAME)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
echo "-------------------------------------------------------" >> $DEPLOY_LOG
