package NHSensor.NHSensorSim.test;

import java.util.Hashtable;

import NHSensor.NHSensorSim.tools.UTool;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		Hashtable h = new Hashtable();
		h.put(new Integer(1), new Integer(2));

		Hashtable hc = (Hashtable) h.clone();
		System.out.println(hc.get(new Integer(1)));
		
		UTool.checkKey();
	}

}
