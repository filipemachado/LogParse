@echo off  
cls  
echo -----------------------------  
echo        Means of Death
echo -----------------------------  
rem set INPUT=  
rem set /P INPUT=Informe o diretorio local: %=%  
java -jar "logParser.jar" ./arquivos/games.log report_means_death 
set /p option=Fim. Presione uma tecla para finalizar