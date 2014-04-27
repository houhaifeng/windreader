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
  REPO="$BASEDIR"/lib
fi

CLASSPATH=$CLASSPATH_PREFIX:"$BASEDIR"/etc:"$REPO"/WindReaderCommon-0.0.1-SNAPSHOT.jar:"$REPO"/libthrift-0.6.1.jar:"$REPO"/slf4j-api-1.5.8.jar:"$REPO"/junit-4.4.jar:"$REPO"/servlet-api-2.5.jar:"$REPO"/httpclient-4.0.1.jar:"$REPO"/httpcore-4.0.1.jar:"$REPO"/slf4j-log4j12-1.6.4.jar:"$REPO"/WindReaderService-0.0.1-SNAPSHOT.jar:"$REPO"/mysql-connector-java-5.1.10.jar:"$REPO"/commons-dbcp-1.2.2.jar:"$REPO"/commons-pool-1.3.jar:"$REPO"/paoding-rose-1.0-SNAPSHOT.jar:"$REPO"/persistence-api-1.0.jar:"$REPO"/commons-collections-3.2.1.jar:"$REPO"/commons-fileupload-1.2.1.jar:"$REPO"/spring-2.5.6.SEC02.jar:"$REPO"/spring-webmvc-2.5.6.SEC02.jar:"$REPO"/spring-beans-2.5.6.SEC02.jar:"$REPO"/spring-context-2.5.6.SEC02.jar:"$REPO"/aopalliance-1.0.jar:"$REPO"/spring-context-support-2.5.6.SEC02.jar:"$REPO"/spring-core-2.5.6.SEC02.jar:"$REPO"/spring-web-2.5.6.SEC02.jar:"$REPO"/velocity-1.6.2.jar:"$REPO"/oro-2.0.8.jar:"$REPO"/velocity-tools-1.3.jar:"$REPO"/commons-beanutils-1.7.0.jar:"$REPO"/commons-digester-1.8.jar:"$REPO"/commons-validator-1.3.1.jar:"$REPO"/paoding-rose-jade-1.1.jar:"$REPO"/commons-jexl-1.1.jar:"$REPO"/paoding-rose-scanning-1.0.jar:"$REPO"/paoding-rose-portal-1.0-SNAPSHOT.jar:"$REPO"/paoding-rose-scanning-1.0-SNAPSHOT.jar:"$REPO"/htmlparser-2.1.jar:"$REPO"/htmllexer-2.1.jar:"$REPO"/commons-httpclient-3.1.jar:"$REPO"/commons-logging-1.0.4.jar:"$REPO"/commons-codec-1.2.jar:"$REPO"/lucene-core-3.5.0.jar:"$REPO"/log4j-1.2.14.jar:"$REPO"/mmseg4j-core-1.9.1.jar:"$REPO"/xerces-2.9.1.jar:"$REPO"/boilerpipe-1.2.0.jar:"$REPO"/nekohtml-1.9.13.jar:"$REPO"/commons-lang-20030203.000129.jar:"$REPO"/PageMine-0.0.1-SNAPSHOT.jar
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
  -Dapp.name="PageMine" \
  -Dapp.pid="$$" \
  -Dapp.repo="$REPO" \
  -Dbasedir="$BASEDIR" \
  cn.wind.com.page.mine.PageMine \
  "$@"
