@echo run shell
@cd ..\

@echo run portal
@set ATOSSENSORSIM_HOME=..\
@cd %ATOSSENSORSIM_HOME%
@set CLASSPATH=.\class\
@for %%i in ("%%ATOSSENSORSIM_HOME%\external-lib\*.jar") do @call "%ATOSSENSORSIM_HOME%\bin\windows\cpappend.bat" %%i
java jade.Boot -gui portal:nhgriddbdemo.queryProcessing.NewPortal
pause
java -classpath $CLASSPATH;.\class;.\external-lib\jacl.jar;.\external-lib\log4j-1.2.15.jar;.\external-lib\tcljava.jar NHSensor.NHSensorSim.ui.tcl.TclShellFrame
pause