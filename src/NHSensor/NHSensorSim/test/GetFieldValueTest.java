package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class GetFieldValueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AllParam param = new AllParam();
		param.setNodeNum(1);

		Number number;
		try {
			number = (Number) ReflectUtil.getFieldValue(param, "nodeNum");
			System.out.println(number);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
