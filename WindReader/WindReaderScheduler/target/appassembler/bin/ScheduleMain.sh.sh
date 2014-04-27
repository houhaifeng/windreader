#!/bin/sh
# ----------------------------------------------------------------------------
#  Copyright 2001-2006 The Apache Software Foundation.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
# ----------------------------------------------------------------------------

#   Copyright (c) 2001-2002 The Apache Software Foundation.  All rights
#   reserved.

BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`



# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  Darwin*) darwin=true
           if [ -z "$JAVA_VERSION" ] ; then
             JAVA_VERSION="CurrentJDK"
           else
             echo "Using Java version: $JAVA_VERSION"
           fi
           if [ -z "$JAVA_HOME" ] ; then
             JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
           fi
           ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi

if [ -z "$REPO" ]
then
  REPO="$BASEDIR"/repo
fi

CLASSPATH=$CLASSPATH_PREFIX:"$BASEDIR"/etc:"$REPO"/cn/wind/com/PageMine/0.0.1-SNAPSHOT/PageMine-0.0.1-SNAPSHOT.jar:"$REPO"/org/apache/lucene/lucene-core/3.5.0/lucene-core-3.5.0.jar:"$REPO"/com/chenlb/mmseg4j/mmseg4j-core/1.10.0/mmseg4j-core-1.10.0.jar:"$REPO"/de/l3s/boilerpipe/boilerpipe/1.1.0/boilerpipe-1.1.0.jar:"$REPO"/commons-lang/commons-lang/20030203.000129/commons-lang-20030203.000129.jar:"$REPO"/cn/wind/com/WindReaderCommon/0.0.1-SNAPSHOT/WindReaderCommon-0.0.1-SNAPSHOT.jar:"$REPO"/org/slf4j/slf4j-log4j12/1.6.4/slf4j-log4j12-1.6.4.jar:"$REPO"/cn/wind/com/WindReaderService/0.0.1-SNAPSHOT/WindReaderService-0.0.1-SNAPSHOT.jar:"$REPO"/mysql/mysql-connector-java/5.1.10/mysql-connector-java-5.1.10.jar:"$REPO"/commons-dbcp/commons-dbcp/1.2.2/commons-dbcp-1.2.2.jar:"$REPO"/commons-pool/commons-pool/1.3/commons-pool-1.3.jar:"$REPO"/com/54chen/paoding-rose-jade/1.1/paoding-rose-jade-1.1.jar:"$REPO"/org/springframework/spring/2.5.6.SEC02/spring-2.5.6.SEC02.jar:"$REPO"/commons-jexl/commons-jexl/1.1/commons-jexl-1.1.jar:"$REPO"/com/54chen/paoding-rose-scanning/1.0/paoding-rose-scanning-1.0.jar:"$REPO"/net/paoding/paoding-rose-portal/1.0-SNAPSHOT/paoding-rose-portal-1.0-SNAPSHOT.jar:"$REPO"/net/paoding/paoding-rose-scanning/1.0-SNAPSHOT/paoding-rose-scanning-1.0-SNAPSHOT.jar:"$REPO"/commons-httpclient/commons-httpclient/3.1/commons-httpclient-3.1.jar:"$REPO"/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar:"$REPO"/commons-codec/commons-codec/1.2/commons-codec-1.2.jar:"$REPO"/org/apache/thrift/libthrift/0.6.1/libthrift-0.6.1.jar:"$REPO"/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar:"$REPO"/junit/junit/4.4/junit-4.4.jar:"$REPO"/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar:"$REPO"/org/apache/httpcomponents/httpclient/4.0.1/httpclient-4.0.1.jar:"$REPO"/org/apache/httpcomponents/httpcore/4.0.1/httpcore-4.0.1.jar:"$REPO"/org/htmlparser/htmlparser/2.1/htmlparser-2.1.jar:"$REPO"/org/htmlparser/htmllexer/2.1/htmllexer-2.1.jar:"$REPO"/org/opensymphony/quartz/quartz-all/1.6.1/quartz-all-1.6.1.jar:"$REPO"/org/springframework/spring-mock/2.0/spring-mock-2.0.jar:"$REPO"/org/springframework/spring-beans/2.0/spring-beans-2.0.jar:"$REPO"/org/springframework/spring-context/2.0/spring-context-2.0.jar:"$REPO"/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:"$REPO"/org/springframework/spring-aop/2.0/spring-aop-2.0.jar:"$REPO"/org/springframework/spring-core/2.0/spring-core-2.0.jar:"$REPO"/org/springframework/spring-dao/2.0/spring-dao-2.0.jar:"$REPO"/org/springframework/spring-jdbc/2.0/spring-jdbc-2.0.jar:"$REPO"/org/springframework/spring-jpa/2.0/spring-jpa-2.0.jar:"$REPO"/org/springframework/spring-web/2.0/spring-web-2.0.jar:"$REPO"/org/springframework/spring-webmvc/2.0/spring-webmvc-2.0.jar:"$REPO"/struts/struts/1.2.9/struts-1.2.9.jar:"$REPO"/commons-beanutils/commons-beanutils/1.7.0/commons-beanutils-1.7.0.jar:"$REPO"/commons-digester/commons-digester/1.6/commons-digester-1.6.jar:"$REPO"/commons-collections/commons-collections/2.1/commons-collections-2.1.jar:"$REPO"/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:"$REPO"/commons-fileupload/commons-fileupload/1.0/commons-fileupload-1.0.jar:"$REPO"/commons-validator/commons-validator/1.1.4/commons-validator-1.1.4.jar:"$REPO"/oro/oro/2.0.7/oro-2.0.7.jar:"$REPO"/antlr/antlr/2.7.2/antlr-2.7.2.jar:"$REPO"/xalan/xalan/2.5.1/xalan-2.5.1.jar:"$REPO"/org/springframework/spring-support/2.0/spring-support-2.0.jar:"$REPO"/log4j/log4j/1.2.14/log4j-1.2.14.jar:"$REPO"/cn/wind/com/WindReaderScheduler/0.0.1-SNAPSHOT/WindReaderScheduler-0.0.1-SNAPSHOT.jar
EXTRA_JVM_ARGUMENTS=""

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  [ -n "$HOME" ] && HOME=`cygpath --path --windows "$HOME"`
  [ -n "$BASEDIR" ] && BASEDIR=`cygpath --path --windows "$BASEDIR"`
  [ -n "$REPO" ] && REPO=`cygpath --path --windows "$REPO"`
fi

exec "$JAVACMD" $JAVA_OPTS \
  $EXTRA_JVM_ARGUMENTS \
  -classpath "$CLASSPATH" \
  -Dapp.name="ScheduleMain.sh" \
  -Dapp.pid="$$" \
  -Dapp.repo="$REPO" \
  -Dbasedir="$BASEDIR" \
  cn.wind.com.reader.scheduler.ScheduleMain \
  "$@"
