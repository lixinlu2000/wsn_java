package NHSensor.NHSensorSim.math;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolver;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory;

import NHSensor.NHSensorSim.shape.Radian;

public class MaxAlphaForIDC {
	private double sb;
	private double beta;
	private double r;
	private double maxAlpha = Double.MAX_VALUE;

	public MaxAlphaForIDC(double r, double beta, double sb) {
		super();
		this.r = r;
		this.beta = beta;
		this.sb = sb;

		UnivariateRealSolverFactory factory = UnivariateRealSolverFactory
				.newInstance();
		UnivariateRealSolver solver = factory.newDefaultSolver();
		IDCAlphaFunction iDCAlphaFunction = new IDCAlphaFunction(r, beta, sb);

		try {
			maxAlpha = solver.solve(iDCAlphaFunction, 0, Math.PI / 2);
			// System.out.println("solved alpha radian:"+maxAlpha);
			//System.out.println("solved alpha degree:"+Radian.getDegree(maxAlpha
			// ));
			// System.out.println("**left alpha radian:"+iDCAlphaFunction.
			// getMaxAlphaL());
			// System.out.println("**left alpha degree:"+Radian.getDegree(
			// iDCAlphaFunction.getMaxAlphaL()));

		} catch (ConvergenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FunctionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maxAlpha = Math.min(maxAlpha, iDCAlphaFunction.getMaxAlphaL());
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
