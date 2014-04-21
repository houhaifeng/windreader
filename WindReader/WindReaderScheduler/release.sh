#!/bin/bash

##
# 使用release.sh, 将browser service替换onebox上的服务
##
EX_JVM_ARGS="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9994"
SCRIPT_DIR=`cd $(dirname $0); pwd -P`

service="readerschedule"

## clean和insatll项目 ##
echo " -- mvn clean & install"
if [ "$1" = "debug" ]; then
    mvn clean install -DexJvmArgs="$EX_JVM_ARGS"
else
    mvn clean install
fi

## 关闭supervisord, 删除上面的service代码 ##
echo " -- shut down service "$service 
ssh root@oneboxhost supervisorctl stop $service;rm -rf /opt/soft/$service;

## 复制target到onebox ##
echo " -- copy target files"
cp -fr $SCRIPT_DIR/target /opt/soft/$service

## 重新启动supervisor和browserservice ##
echo " -- restart service"
ssh root@oneboxhost sudo supervisorctl start $service

