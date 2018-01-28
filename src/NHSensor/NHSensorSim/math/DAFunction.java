package NHSensor.NHSensorSim.math;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.ShapeUtil;

public class DAFunction implements UnivariateRealFunction {
	private Position rootPosition;
	private Rect rect;
	private double y = 0;

	double as, ab, beta, ar, asd, asb, asc;
	double sb, abs, leftArea, leftAndMiddleArea;

	public DAFunction(Position rootPosition, Rect rect) {
		super();
		this.rect = rect;
		this.rootPosition = rootPosition;
		this.caculateParameters();
	}

	protected void caculateParameters() {
		as = rootPosition.distance(rect.getLT());
		ab = rect.getHeight();
		beta = rect.getLT().bearing(rootPosition);
		ar = rect.area();
		double startRadian = rootPosition.bearing(rect.getLT());
		asb = ShapeUtil.caculateBeta1(rect, rootPosition);
		asc = ShapeUtil.caculateBeta2(rect, rootPosition);
		asd = Radian
				.relativeTo(rootPosition.bearing(rect.getRT()), startRadian);

		sb = rootPosition.distance(rect.getLB());
		abs = Math.acos((ab * ab + sb * sb - as * as) / (2 * ab * sb));
		leftArea = ab * ab * Math.tan(abs) / 2;
		leftAndMiddleArea = this.value3(asc);
	}

	public double value(double alpha) throws FunctionEvaluationException {
		if (alpha >= 0 && alpha <= asb)
			return this.value1(alpha) - this.y;
		else if (alpha > asb && alpha <= asc)
			return this.value2(alpha) - this.y;
		else if (alpha > asc && alpha <= asd)
			return this.value3(alpha) - this.y;
		else
			throw new FunctionEvaluationException(alpha);
	}

	protected double value1(double alpha) {
		double sinAlpha = Math.sin(alpha);
		double cosAlphaAndBeta = Math.cos(alpha + beta);
		double sinAlphaAndBeta = Math.sin(alpha + beta);
		return as * as * sinAlpha * sinAlpha
				/ (2 * sinAlphaAndBeta * cosAlphaAndBeta);
	}

	protected double value2(double alpha) {
		double sinAlpha = Math.sin(alpha);
		double sinAlphaAndBeta = Math.sin(alpha + beta);
		double tanAlphaAndBeta = Math.tan(alpha + beta);
		return ab / 2
				* (2 * as * sinAlpha / sinAlphaAndBeta - ab / tanAlphaAndBeta);
	}

	protected double value3(double alpha) {
		double ag = as * Math.sin(alpha) / Math.sin(alpha + beta);
		double gd = rect.getWidth() - ag;
		double dh = -gd * Math.tan(alpha + beta);
		return ar - 0.5 * gd * dh;
	}

	public Position getRootPosition() {
		return rootPosition;
	}

	public void setRootPosition(Position rootPosition) {
		this.rootPosition = rootPosition;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public double getMax() {
		return this.asd;
	}

	public double getAr() {
		return this.ar;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		StringBuffer sb1 = new StringBuffer();
		sb1.append("as:" + as);
		sb1.append(" ab:" + ab);
		sb1.append(" beta:" + beta);
		sb1.append(" ar:" + ar);
		sb1.append(" asd:" + asd);
		sb1.append(" asb" + asb);
		sb1.append(" asc" + asc);
		sb1.append(" sb:" + as);
		sb1.append(" abs:" + as);
		sb1.append(" leftArea:" + leftArea);
		sb1.append(" leftAndMiddleArea:" + leftAndMiddleArea);
		return sb1.toString();
	}
}
