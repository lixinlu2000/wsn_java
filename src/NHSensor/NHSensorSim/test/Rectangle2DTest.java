package NHSensor.NHSensorSim.test;

import java.awt.geom.Rectangle2D;

public class Rectangle2DTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Rectangle2D.Double r = new Rectangle2D.Double(20, 30, 100, 50);
		System.out.println(r.getMinX());
		System.out.println(r.getMaxX());
		System.out.println(r.getMinY());
		System.out.println(r.getMaxY());

	}

}
