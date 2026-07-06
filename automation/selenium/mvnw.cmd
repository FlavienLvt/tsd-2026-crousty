@echo off
setlocal

set MAVEN_VERSION=3.9.9
set WRAPPER_DIR=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%
set MAVEN_EXE=%WRAPPER_DIR%\bin\mvn.cmd
set DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip
set ZIP_FILE=%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip

if not exist "%MAVEN_EXE%" (
    echo [mvnw] Apache Maven %MAVEN_VERSION% not found, downloading...
    powershell -Command "Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%ZIP_FILE%'; Expand-Archive -Path '%ZIP_FILE%' -DestinationPath '%USERPROFILE%\.m2\wrapper\dists' -Force; Remove-Item '%ZIP_FILE%'"
    if errorlevel 1 (
        echo [mvnw] Download failed. Please install Maven: https://maven.apache.org/download.cgi
        exit /b 1
    )
    echo [mvnw] Maven %MAVEN_VERSION% installed.
)

"%MAVEN_EXE%" %*
