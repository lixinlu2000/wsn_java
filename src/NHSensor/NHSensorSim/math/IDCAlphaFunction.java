package NHSensor.NHSensorSim.math;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

public class IDCAlphaFunction implements UnivariateRealFunction {
	private double sb;
	private double beta;
	private double r;
	private double maxAlphaL;

	public IDCAlphaFunction(double r, double beta, double sb) {
		super();
		this.r = r;
		this.beta = beta;
		this.sb = sb;

		this.maxAlphaL = this.caculateMaxAlphaL();
	}

	public double caculateMaxAlphaL() {

		if (sb >= r) {
			if (beta > Math.PI / 2) {
				return this.f_bsg(r, this.sb, this.beta);
			} else if (beta > Math.PI / 3 && beta <= Math.PI / 2) {
				return Math.min(this.f_bsg(r, sb, beta), this
						.f_bsf(r, sb, beta));

			} else {
				return this.f_bsj(r, sb, beta);
			}
		} else {
			if (beta > Math.PI / 2) {
				return this.f_bsg1(r, sb, beta);
			} else if (beta > this.f_sbf(r, sb) && beta <= Math.PI / 2) {
				return this.f_bsg1(r, sb, beta);
			} else {
				return this.f_bsj1(r, sb, beta);
			}
		}
	}

	public double value(double alpha) throws FunctionEvaluationException {
		double beta_r = this.f_beta_r(alpha, beta);
		double sb_r = this.f_sb_r(sb, alpha, beta);
		double result = 0;

		if (sb_r >= r) {
			if (beta_r > Math.PI / 2) {
				result = this.f_bsg(r, sb_r, beta_r);
			} else if (beta_r > Math.PI / 3 && beta_r <= Math.PI / 2) {
				result = Math.min(this.f_bsg(r, sb_r, beta_r), this.f_bsf(r,
						sb_r, beta_r));

			} else if (beta_r <= Math.PI / 3) {
				result = this.f_bsj(r, sb_r, beta_r);
			}
		} else {
			if (beta_r > Math.PI / 2) {
				result = this.f_bsg1(r, sb_r, beta_r);
			} else if (beta_r > this.f_sbf(r, sb_r) && beta_r <= Math.PI / 2) {
				result = this.f_bsg1(r, sb_r, beta_r);
			} else if (beta_r <= this.f_sbf(r, sb_r)) {
				result = this.f_bsj1(r, sb_r, beta_r);
			}
		}
		return result - alpha;
	}

	public double f_bsg(double r, double sb, double beta) {
		double bsg = Math.asin(r * Math.sin(beta)
				/ Math.sqrt(sb * sb + r * r - 2 * sb * r * Math.cos(beta)));
		return bsg;
	}

	public double f_bsf(double r, double sb, double beta) {
		double bsf = Math.atan(Math.sqrt(3) * r / 2 / (sb - 0.5 * r));
		return bsf;

	}

	public double f_bsj(double r, double sb, double beta) {
		double bsj = Math.atan(Math.sqrt(3) * r / 2
				/ (sb - Math.sqrt(3) * r / 2 / Math.tan(beta)));
		return bsj;
	}

	public double f_bsg1(double r, double sb, double beta) {
		return this.f_bsg(r, sb, beta);
	}

	public double f_bsj1(double r, double sb, double beta) {
		double bsj1 = Math.PI - beta - Math.asin(sb * Math.sin(beta) / r);
		return bsj1;
	}

	public double f_sbf(double r, double sb) {
		return Math.acos(sb / 2 / r);
	}

	public double f_sb_r(double sb, double alpha, double beta) {
		return sb * Math.sin(beta) / Math.sin(alpha + beta);
	}

	public double f_beta_r(double alpha, double beta) {
		return Math.PI - alpha - beta;
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

	public double getMaxAlphaL() {
		return maxAlphaL;
	}
}
