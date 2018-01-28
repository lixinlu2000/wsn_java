@echo run MainFrame
@set ATOSSENSORSIM_HOME=..\..\
@cd %ATOSSENSORSIM_HOME%
@set CLASSPATH=.\class\
@for %%i in ("%ATOSSENSORSIM_HOME%\external-lib\*.jar") do @call "%ATOSSENSORSIM_HOME%\bin\windows\cpappend.bat" %%i
@rem echo %CLASSPATH%
java -classpath $CLASSPATH NHSensor.NHSensorSim.ui.tcl.TclShellFrame
pause