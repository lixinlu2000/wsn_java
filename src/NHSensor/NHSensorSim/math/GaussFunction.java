package NHSensor.NHSensorSim.math;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

public class GaussFunction implements UnivariateRealFunction {
	double sita;
	
	public GaussFunction() {
		// TODO Auto-generated constructor stub
	}

	public GaussFunction(double sita) {
		super();
		this.sita = sita;
	}

	public double value(double x) throws FunctionEvaluationException {
		double v1 = 1/(this.sita*Math.sqrt(2*Math.PI));
		double v2 = Math.exp(-x*x/(2*this.sita*this.sita));
		return v1*v2;
	}

	public double getSita() {
		return sita;
	}

	public void setSita(double sita) {
		this.sita = sita;
	}
}
