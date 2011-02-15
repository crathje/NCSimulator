@echo off
rem /**
rem  *
rem  * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
rem  * admiralcascade@gmail.com
rem  * Licensed under: Creative Commons / Non Commercial / Share Alike
rem  * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
rem  *
rem  */


rem // trying to find java somewhere in the path
set PATHQ=%PATH%
:WHILE
    if "%PATHQ%"=="" goto WEND
    for /F "delims=;" %%i in ("%PATHQ%") do set DIR=%%i
	IF EXIST "%DIR%\java.exe"  goto YES
    for /F "delims=; tokens=1,*" %%i in ("%PATHQ%") do set PATHQ=%%j
    goto WHILE 
:WEND

rem // did not find java in path
goto NO


rem // yeah, java found, start NCSimulator
:YES
echo Found Java in %DIR%
echo Starting NCSimulator

java -Djava.library.path=lib -jar NCSimulator.jar

goto EOF

rem // tell the user where to get java
:NO
echo Could not find Java.
echo Please install Java Runtime Environment from http://www.java.com/download/


:EOF
echo Bye bye