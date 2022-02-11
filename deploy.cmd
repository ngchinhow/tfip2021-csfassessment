@echo off

@REM 5 flagged parameters:
@REM -c - Name of the folder containing client-side Angular project
@REM -p - Name of the Angular project defined in angular.json as the output of dist
@REM -s - Name of the folder containing server-side Maven project
@REM -m - Message for git commit
@REM -b - Name for the HEAD git branch

@REM Default values for parameters
SET CLIENT=client
SET SERVER=server
SET GIT_COMMIT_MESSAGE="Initial deploy"
SET GIT_MASTER=main

:processargs
SET ARG=%1
IF DEFINED ARG (
    IF "%ARG%"=="-c" SET CLIENT=%2
    IF "%ARG%"=="-s" SET SERVER=%2
    IF "%ARG%"=="-m" SET GIT_COMMIT_MESSAGE=%2
    IF "%ARG%"=="-b" SET GIT_MASTER=%2
    SHIFT
    SHIFT
    GOTO processargs
)

cd %CLIENT% && call ng build
cd ../%SERVER%
robocopy /S ../%CLIENT%/dist/%CLIENT% src/main/resources/static
call mvn clean install
cd ..
git add %SERVER% && git commit -m %GIT_COMMIT_MESSAGE%
@REM Assume that Heroku is already logged in and created
git subtree push --prefix %SERVER% heroku %GIT_MASTER%

SET CLIENT=
SET SERVER=
SET GIT_COMMIT_MESSAGE=
SET GIT_MASTER=