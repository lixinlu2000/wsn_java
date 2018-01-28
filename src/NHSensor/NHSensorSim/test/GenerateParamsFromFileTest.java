package NHSensor.NHSensorSim.test;

import java.util.Vector;

import NHSensor.NHSensorSim.experiment.ParamsGenerator;

public class GenerateParamsFromFileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ParamsGenerator paramsGenerator = new ParamsGenerator(
					"result\\RSA\\params.txt");
			Vector params = paramsGenerator.getParams();
			for (int i = 0; i < params.size(); i++) {
				System.out.println(params.elementAt(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
