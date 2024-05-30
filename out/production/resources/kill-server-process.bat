@echo off
REM 8085 포트를 사용하는 프로세스를 찾아서 강제 종료합니다.
FOR /F "tokens=2" %%i IN ('netstat -aon ^| find ":8085" ^| find "LISTENING"') DO taskkill /F /PID %%i
