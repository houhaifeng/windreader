@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\cn\wind\com\page-mine\0.0.1-SNAPSHOT\page-mine-0.0.1-SNAPSHOT.jar;"%REPO%"\IKAnalyzer\IKAnalyzer\4.0.6\IKAnalyzer-4.0.6.jar;"%REPO%"\org\apache\lucene\lucene-core\3.5.0\lucene-core-3.5.0.jar;"%REPO%"\com\chenlb\mmseg4j\mmseg4j-core\1.9.1\mmseg4j-core-1.9.1.jar;"%REPO%"\org\w3c\dom\xerces\2.9.1\xerces-2.9.1.jar;"%REPO%"\de\l3s\boilerpipe\boilerpipe\1.2.0\boilerpipe-1.2.0.jar;"%REPO%"\org\cyberneko\nekohtml\1.9.13\nekohtml-1.9.13.jar;"%REPO%"\cn\wind\com\reader-common\0.0.1-SNAPSHOT\reader-common-0.0.1-SNAPSHOT.jar;"%REPO%"\org\slf4j\slf4j-log4j12\1.6.4\slf4j-log4j12-1.6.4.jar;"%REPO%"\org\slf4j\slf4j-api\1.6.4\slf4j-api-1.6.4.jar;"%REPO%"\cn\wind\com\reader\reader-service\0.0.1-SNAPSHOT\reader-service-0.0.1-SNAPSHOT.jar;"%REPO%"\mysql\mysql-connector-java\5.1.10\mysql-connector-java-5.1.10.jar;"%REPO%"\commons-dbcp\commons-dbcp\1.2.2\commons-dbcp-1.2.2.jar;"%REPO%"\commons-pool\commons-pool\1.3\commons-pool-1.3.jar;"%REPO%"\net\paoding\paoding-rose\1.0-SNAPSHOT\paoding-rose-1.0-20130728.162850-629.jar;"%REPO%"\javax\persistence\persistence-api\1.0\persistence-api-1.0.jar;"%REPO%"\commons-collections\commons-collections\3.2.1\commons-collections-3.2.1.jar;"%REPO%"\commons-fileupload\commons-fileupload\1.2.1\commons-fileupload-1.2.1.jar;"%REPO%"\commons-lang\commons-lang\2.4\commons-lang-2.4.jar;"%REPO%"\org\springframework\spring\2.5.6.SEC02\spring-2.5.6.SEC02.jar;"%REPO%"\org\apache\velocity\velocity\1.6.2\velocity-1.6.2.jar;"%REPO%"\oro\oro\2.0.8\oro-2.0.8.jar;"%REPO%"\org\apache\velocity\velocity-tools\1.3\velocity-tools-1.3.jar;"%REPO%"\commons-beanutils\commons-beanutils\1.7.0\commons-beanutils-1.7.0.jar;"%REPO%"\commons-digester\commons-digester\1.8\commons-digester-1.8.jar;"%REPO%"\commons-validator\commons-validator\1.3.1\commons-validator-1.3.1.jar;"%REPO%"\javax\servlet\servlet-api\2.3\servlet-api-2.3.jar;"%REPO%"\net\paoding\paoding-rose-jade\1.1-SNAPSHOT\paoding-rose-jade-1.1-20130728.163038-634.jar;"%REPO%"\commons-jexl\commons-jexl\1.1\commons-jexl-1.1.jar;"%REPO%"\com\xiaomi\common\perfcounter\xiaomi-common-perfcounter\1.0-SNAPSHOT\xiaomi-common-perfcounter-1.0-20131029.074600-24.jar;"%REPO%"\org\json\json\20090211\json-20090211.jar;"%REPO%"\org\apache\commons\commons-lang3\3.1\commons-lang3-3.1.jar;"%REPO%"\com\xiaomi\xiaomi-common-utils\1.0-SNAPSHOT\xiaomi-common-utils-1.0-20131029.074558-24.jar;"%REPO%"\com\yammer\metrics\metrics-core\2.2.0\metrics-core-2.2.0.jar;"%REPO%"\net\paoding\paoding-rose-portal\1.0-SNAPSHOT\paoding-rose-portal-1.0-20130728.163845-1164.jar;"%REPO%"\net\paoding\paoding-rose-scanning\1.0-SNAPSHOT\paoding-rose-scanning-1.0-20130728.162336-587.jar;"%REPO%"\commons-httpclient\commons-httpclient\3.1\commons-httpclient-3.1.jar;"%REPO%"\commons-logging\commons-logging\1.0.4\commons-logging-1.0.4.jar;"%REPO%"\commons-codec\commons-codec\1.2\commons-codec-1.2.jar;"%REPO%"\org\apache\thrift\thrift\0.5.0\thrift-0.5.0.jar;"%REPO%"\org\htmlparser\htmlparser\2.1\htmlparser-2.1.jar;"%REPO%"\org\htmlparser\htmllexer\2.1\htmllexer-2.1.jar;"%REPO%"\org\opensymphony\quartz\quartz-all\1.6.1\quartz-all-1.6.1.jar;"%REPO%"\org\springframework\spring-mock\2.0\spring-mock-2.0.jar;"%REPO%"\junit\junit\3.8.1\junit-3.8.1.jar;"%REPO%"\org\springframework\spring-beans\2.0\spring-beans-2.0.jar;"%REPO%"\org\springframework\spring-context\2.0\spring-context-2.0.jar;"%REPO%"\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;"%REPO%"\org\springframework\spring-aop\2.0\spring-aop-2.0.jar;"%REPO%"\org\springframework\spring-core\2.0\spring-core-2.0.jar;"%REPO%"\org\springframework\spring-dao\2.0\spring-dao-2.0.jar;"%REPO%"\org\springframework\spring-jdbc\2.0\spring-jdbc-2.0.jar;"%REPO%"\org\springframework\spring-jpa\2.0\spring-jpa-2.0.jar;"%REPO%"\org\springframework\spring-web\2.0\spring-web-2.0.jar;"%REPO%"\org\springframework\spring-webmvc\2.0\spring-webmvc-2.0.jar;"%REPO%"\struts\struts\1.2.9\struts-1.2.9.jar;"%REPO%"\antlr\antlr\2.7.2\antlr-2.7.2.jar;"%REPO%"\xalan\xalan\2.5.1\xalan-2.5.1.jar;"%REPO%"\org\springframework\spring-support\2.0\spring-support-2.0.jar;"%REPO%"\log4j\log4j\1.2.14\log4j-1.2.14.jar;"%REPO%"\cn\wind\com\WindReaderScheduler\0.0.1-SNAPSHOT\WindReaderScheduler-0.0.1-SNAPSHOT.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="ScheduleMain.sh" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" cn.wind.com.reader.scheduler.ScheduleMain %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
