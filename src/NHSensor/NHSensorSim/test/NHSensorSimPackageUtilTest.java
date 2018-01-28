package NHSensor.NHSensorSim.test;

import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.util.NHSensorSimPackageUtil;

public class NHSensorSimPackageUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String packageName = "NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.energy";
		try {
			Vector classNames = NHSensorSimPackageUtil.getClasses(packageName);
			System.out.println(classNames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
