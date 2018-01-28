package NHSensor.NHSensorSim.test;

import java.lang.reflect.Field;

import NHSensor.NHSensorSim.papers.RESA.AllParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class ParamFromStringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AllParam p = new AllParam();
		Field[] fileds = ReflectUtil.getAllFields(AllParam.class);
		for (int i = 0; i < fileds.length; i++) {
			System.out.println(fileds[i]);
		}

		String ps = "networkID(2) nodeNum(320) queryRegionRate(0.64) gridWidthRate(0.8660254037844386) queryMessageSize(22) answerMessageSize(110) networkWidth(100.0) networkHeight(100.0) queryAndPatialAnswerSize(132) resultSize(110) nodeFailProbability(0.04) k(30)";
		System.out.println(ps);
		try {
			System.out.println(AllParam.fromString(ps));
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

}
