package NHSensor.NHSensorSim.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/*
 * Some util for experiment
 */
public class ExperimentUtil {

	public static void invokeClassMain(String className) {
		try {
			Class cls = Class.forName(className);
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

	public static void invokeClassesMain(Vector classNames) {
		String className;
		for (int i = 0; i < classNames.size(); i++) {
			className = (String) classNames.elementAt(i);
			ExperimentUtil.invokeClassMain(className);
		}
	}

	public static void invokeNHSensorSimClassesMain(Vector nHSensorSimClasses) {
		NHSensorSimClass nHSensorSimClass;
		String className;

		for (int i = 0; i < nHSensorSimClasses.size(); i++) {
			nHSensorSimClass = (NHSensorSimClass) nHSensorSimClasses
					.elementAt(i);
			className = nHSensorSimClass.toString();
			ExperimentUtil.invokeClassMain(className);
		}
	}

}
