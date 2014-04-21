#!/bin/bash

##
# 使用release.sh, 将browser service替换onebox上的服务
##


ORIG_DIR=`pwd -P`
SCRIPT_DIR=`cd $(dirname $0); pwd -P`

cd $SCRIPT_DIR/
mvn clean install

wwwprj="WindReaderWeb"
echo "replace $wwprj war file"
scp target/${wwwprj}-0.0.1-SNAPSHOT.war  root@oneboxhost:/opt/resin/webapps/windreader.war

cd $ORIG_DIR

