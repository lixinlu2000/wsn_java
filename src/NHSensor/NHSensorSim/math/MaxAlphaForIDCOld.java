package NHSensor.NHSensorSim.math;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolver;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory;

public class MaxAlphaForIDCOld {
	private double sb;
	private double beta;
	private double r;

	private double bsd;
	private double bsf;
	private double hsf;
	private double hsd;

	private double maxAlpha;

	public MaxAlphaForIDCOld(double r, double beta, double sb) {
		super();
		this.r = r;
		this.beta = beta;
		this.sb = sb;

		this.bsd = Math.atan(this.r / this.sb);
		this.bsf = Math.atan(Math.sqrt(3) * 0.5 * this.r
				/ (this.sb - 0.5 * this.r));

		double tempMaxAlpha = Math.min(this.bsd, this.bsf);

		UnivariateRealSolverFactory factory = UnivariateRealSolverFactory
				.newInstance();
		UnivariateRealSolver solver = factory.newDefaultSolver();
		HSFFunction hsfFunction = new HSFFunction(r, beta, sb);
		HSDFunction hsdFunction = new HSDFunction(r, beta, sb);

		try {
			this.hsf = solver.solve(hsfFunction, 0, Math.PI / 2 * 0.999);
			this.hsd = solver.solve(hsdFunction, 0, Math.PI / 2 * 0.999);
		} catch (ConvergenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FunctionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double tempMax1 = Math.min(this.bsd, this.bsf);
		double tempMax2 = Math.min(this.hsf, this.hsd);
		this.maxAlpha = Math.min(tempMax1, tempMax2);
	}

	public double getMaxAlpha() {
		return this.maxAlpha;
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
