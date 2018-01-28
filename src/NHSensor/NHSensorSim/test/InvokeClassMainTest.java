package NHSensor.NHSensorSim.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeClassMainTest {

	/**
	 * @param args
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) {
		try {
			Class cls = Class
					.forName("NHSensor.NHSensorSim.papers.ROCAdaptiveGridTranverseRing.energy.NodeNumEnergy");
			Method mainMethod = cls.getDeclaredMethod("main",
					new Class[] { String[].class });
			mainMethod.invoke(null, new String[1]);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
