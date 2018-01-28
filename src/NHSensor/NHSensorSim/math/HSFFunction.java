package NHSensor.NHSensorSim.math;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

public class HSFFunction implements UnivariateRealFunction {
	private double sb;
	private double beta;
	private double r;

	public HSFFunction(double r, double beta, double sb) {
		super();
		this.r = r;
		this.beta = beta;
		this.sb = sb;
	}

	public double value(double alpha) throws FunctionEvaluationException {
		double sh = sb * Math.sin(beta) / Math.sin(alpha + beta);

		return Math.tan(alpha) - Math.sqrt(3) * 0.5 * this.r
				/ (sh - 0.5 * this.r);
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
