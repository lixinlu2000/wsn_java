package NHSensor.NHSensorSim.test;

import java.lang.reflect.Field;
import java.util.Arrays;

import NHSensor.NHSensorSim.papers.RISC.TraverseRingExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class ReflectTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class c = TraverseRingExperimentParam.class;
		System.out.println(Arrays.asList(ReflectUtil.getAllClasses(c)));
		System.out.println(Arrays.asList(ReflectUtil.getAllFields(c)));

		Field[] allFields = ReflectUtil.getAllFields(c);
		for (int i = 0; i < allFields.length; i++) {
			System.out.println(ReflectUtil.getFieldSetMethod(c, allFields[i]));
		}
	}
}