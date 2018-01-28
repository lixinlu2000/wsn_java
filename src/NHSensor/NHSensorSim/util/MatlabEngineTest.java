package NHSensor.NHSensorSim.util;

/**
 * Demonstration program for connecting Java with Matlab using the Java Runtime
 * class. Communication is achieved by acquiring Matlab's standard input and
 * output streams.
 **/
public class MatlabEngineTest {
	public static void main(String[] args) {
		MatlabEngine engine = new MatlabEngine();
		try {
			// Matlab start command:
			engine.open("matlab -nosplash -nojvm");
			// Display output:
			System.out.println(engine.getOutputString(500));
			// Example: Solve the system of linear equations Ax = f with
			// Matlab's Preconditioned Conjugate Gradients method.
			engine.evalString("x = 0:0.1:2*pi;"); // Define Matrix A
			engine.evalString("y = sin(x);"); // Define vector f
			engine.evalString("plot(x,y)"); // Compute x
			System.out.println("end");
			// Retrieve output:
			System.out.println(engine.getOutputString(500));
			// Close the Matlab session:
			engine.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
