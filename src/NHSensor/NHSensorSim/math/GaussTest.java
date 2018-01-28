package NHSensor.NHSensorSim.math;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MaxIterationsExceededException;
import org.apache.commons.math.analysis.integration.TrapezoidIntegrator;

public class GaussTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GaussFunction gf = new GaussFunction(0.2);
		TrapezoidIntegrator integrator = new TrapezoidIntegrator(gf);
		
		double value;
		try {
			System.out.println("start");
			value = integrator.integrate(-50000,0.5);
			System.out.println(value);
		} catch (MaxIterationsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FunctionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
