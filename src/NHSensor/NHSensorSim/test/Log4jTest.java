package NHSensor.NHSensorSim.test;

import org.apache.log4j.*;

public class Log4jTest {

	static Logger logger = Logger.getLogger(Log4jTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure("config/log4j.properties");
		BasicConfigurator.configure();
		logger.info("enter main");
		logger.info("exit app");
	}

}
