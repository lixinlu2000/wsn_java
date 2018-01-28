package NHSensor.NHSensorSim.util;

public class TestMatlabClient {
	public static void main(String args[]) {
		MatlabClient c = new MatlabClient();
		try {
			c.eval("x = 0:0.1:2*pi;");
			c.eval("y = sin(x);");
			c.eval("plot(x,y)");
		} catch (Exception e) {
			System.err.println(e);
		}

		try {
			c.eval(MatlabClient.BYE);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
