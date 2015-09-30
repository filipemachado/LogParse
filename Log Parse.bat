@echo off  
cls  
echo -------------------------  
echo        Log Parser  
echo -------------------------  
rem set INPUT=  
rem set /P INPUT=Informe o diretorio local: %=%  
java -jar "logParser.jar" ./arquivos/games.log report_parse_log
set /p option=Fim. Presione uma tecla para finalizar