@echo run shell
@cd ..\
java -classpath $CLASSPATH;.\class;.\external-lib\jacl.jar;.\external-lib\log4j-1.2.15.jar;.\external-lib\tcljava.jar NHSensor.NHSensorSim.ui.tcl.TclShellFrame
pause