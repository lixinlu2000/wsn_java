package NHSensor.NHSensorSim.math;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

public class HSDFunction implements UnivariateRealFunction {
	private double sb;
	private double beta;
	private double r;

	public HSDFunction(double r, double beta, double sb) {
		super();
		this.r = r;
		this.beta = beta;
		this.sb = sb;
	}

	public double value(double alpha) throws FunctionEvaluationException {
		return Math.tan(alpha) - this.r * Math.sin(alpha + beta)
				/ (sb * Math.sin(beta));
	}

	public double getSb() {
		return sb;
	}

	public void setSb(double sb) {
		this.sb = sb;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}
}
