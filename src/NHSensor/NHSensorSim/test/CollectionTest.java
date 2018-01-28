package NHSensor.NHSensorSim.test;

import java.util.TreeSet;

public class CollectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeSet hs = new TreeSet();
		hs.add(new Integer(1));
		hs.add(new Integer(1));
		hs.add(new Integer(2));
		hs.add(new Integer(4));
		System.out.println(hs.toString());

	}

}
