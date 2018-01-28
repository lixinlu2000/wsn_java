@echo run Atos-SensorSim
@set ATOSSENSORSIM_HOME=..\..\
@cd %ATOSSENSORSIM_HOME%
@set CLASSPATH=.\class\
@for %%i in (".\external-lib\*.jar") do @call ".\bin\windows\cpappend.bat" %%i
@for %%i in (".\external-lib\license\*.jar") do @call ".\bin\windows\cpappend.bat" %%i
@rem echo %CLASSPATH%
java -classpath %CLASSPATH% NHSensor.NHSensorSim.ui.mainFrame.MainFrame
pause