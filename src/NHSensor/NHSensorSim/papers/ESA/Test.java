package NHSensor.NHSensorSim.papers.ESA;

import java.util.Vector;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ESAParamsFactory pf = new ESAParamsFactory();
		Vector params = pf.nodeNumVariable();
		AllParam p;

		for (int i = 0; i < params.size(); i++) {
			p = (AllParam) params.elementAt(i);
			System.out.println(p);
		}

	}

}
