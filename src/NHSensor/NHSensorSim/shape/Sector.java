package NHSensor.NHSensorSim.shape;

import java.text.DecimalFormat;

import NHSensor.NHSensorSim.exception.IntersectException;

public class Sector extends Shape {
	Position centre;
	double startRadian;
	double sita;

	public Sector(Position centre, double startRadian, double sita) {
		super();
		this.centre = centre;
		this.startRadian = startRadian;
		this.sita = sita;
	}

	public Sector(Line begin, Line end) {
		this.centre = begin.intersect(end);
		this.startRadian = begin.getAlpha().getRadian();
		this.sita = Radian.relativeTo(end.getAlpha().getRadian(),
				this.startRadian);
	}

	public boolean contains(Position position) {
		double alpha = this.centre.bearing(position);
		double relativeAlpha = Radian.relativeTo(alpha, startRadian);
		return relativeAlpha <= sita;
	}

	public Position getCentre() {
		return centre;
	}

	public double getStartRadian() {
		return startRadian;
	}

	public void setStartRadian(double startRadian) {
		this.startRadian = startRadian;
	}

	public double getSita() {
		return sita;
	}

	public void setSita(double sita) {
		this.sita = sita;
	}

	public void setCentre(Position centre) {
		this.centre = centre;
	}

	public Line diagonalLine() {
		return new Line(this.getCentre(), new Radian(this.getStartRadian()
				+ this.getSita() / 2));
	}

	public double diagonalRadian() {
		return this.getStartRadian() + this.getSita() / 2;
	}

	public Line getBeginLine() {
		return new Line(this.getCentre(), new Radian(this.getStartRadian()));
	}

	public Line getEndLine() {
		return new Line(this.getCentre(), new Radian(this.getStartRadian()
				+ this.getSita()));
	}

	public Position getBeginNear(Circle circle) throws IntersectException {
		Line line = this.getBeginLine();
		double d1 = this.getCentre().distance(circle.getCentre());
		double d2 = circle.getRadius();
		double d3 = line.distance(circle.getCentre());
		if (d3 > circle.getRadius())
			throw new IntersectException();

		double r1 = Math.sqrt(d1 * d1 - d3 * d3);
		double r2 = Math.sqrt(d2 * d2 - d3 * d3);
		PolarPosition polarPosition = new PolarPosition(this.getCentre(), r1
				- r2, line.getAlpha().getRadian());

		return polarPosition.toPosition();
	}

	public Position getBeginFar(Circle circle) throws IntersectException {
		Line line = this.getBeginLine();

		double d3 = line.distance(circle.getCentre());
		if (d3 > circle.getRadius())
			throw new IntersectException();

		double d1 = this.getCentre().distance(circle.getCentre());
		double d2 = circle.getRadius();

		double r1 = Math.sqrt(d1 * d1 - d3 * d3);
		double r2 = Math.sqrt(d2 * d2 - d3 * d3);
		PolarPosition polarPosition = new PolarPosition(this.getCentre(), r1
				+ r2, line.getAlpha().getRadian());

		return polarPosition.toPosition();
	}

	public Position getEndNear(Circle circle) throws IntersectException {
		Line line = this.getEndLine();
		double d1 = this.getCentre().distance(circle.getCentre());
		double d2 = circle.getRadius();
		double d3 = line.distance(circle.getCentre());

		if (d3 > circle.getRadius())
			throw new IntersectException();

		double r1 = Math.sqrt(d1 * d1 - d3 * d3);
		double r2 = Math.sqrt(d2 * d2 - d3 * d3);
		PolarPosition polarPosition = new PolarPosition(this.getCentre(), r1
				- r2, line.getAlpha().getRadian());

		return polarPosition.toPosition();
	}

	public Line getLine(Circle circle, int sectorDirection)
			throws IntersectException {
		if (sectorDirection == SectorDirection.FAR)
			return this.getLineFar(circle);
		else
			return this.getLineNear(circle);
	}

	public Line getLineFar(Circle circle) throws IntersectException {
		Position p1, p2;
		p1 = this.getBeginFar(circle);
		p2 = this.getEndFar(circle);
		return new Line(p1, p1.bearing(p2));
	}

	public Line getLineNear(Circle circle) throws IntersectException {
		Position p1, p2;
		p1 = this.getBeginNear(circle);
		p2 = this.getEndNear(circle);
		return new Line(p1, p1.bearing(p2));
	}

	public Position getEndFar(Circle circle) throws IntersectException {
		Line line = this.getEndLine();
		double d1 = this.getCentre().distance(circle.getCentre());
		double d2 = circle.getRadius();
		double d3 = line.distance(circle.getCentre());
		if (d3 > circle.getRadius())
			throw new IntersectException();

		double r1 = Math.sqrt(d1 * d1 - d3 * d3);
		double r2 = Math.sqrt(d2 * d2 - d3 * d3);
		PolarPosition polarPosition = new PolarPosition(this.getCentre(), r1
				+ r2, line.getAlpha().getRadian());

		return polarPosition.toPosition();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#.##");
		sb.append("Sector(");
		sb.append(this.centre);
		sb.append(" startRadian(");
		sb.append(df.format(Radian.getDegree(this.startRadian)));
		sb.append(" sita(");
		sb.append(df.format(Radian.getDegree(this.sita)));
		sb.append(" )");
		return sb.toString();
	}

}
