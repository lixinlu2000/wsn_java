package NHSensor.NHSensorSim.shape;

public class Line extends Shape {
	Position p;
	Radian alpha;
	double x;
	boolean verticalLine = false;

	public Line(Position p, Radian alpha) {
		super();
		this.p = p;
		this.alpha = alpha;
		if (alpha.isVertical()) {
			this.verticalLine = true;
			x = p.getX();
		}
	}

	public Line(Position p, double alpha) {
		this(p, new Radian(alpha));
	}

	public Line(Position p1, Position p2) {
		this.p = new Position(p1.getX(), p1.getY());
		this.alpha = new Radian(p1.bearing(p2));
	}

	public Line(double x) {
		this.verticalLine = true;
		this.x = x;
	}

	public Line perpendicularLine(Position position) {
		if (!this.verticalLine) {
			return new Line(position, this.alpha.getRadian() + Math.PI / 2);
		} else
			return new Line(position, 0);
	}

	public boolean contains(Position position) {
		double delta;
		if (!this.verticalLine) {
			delta = position.getY() - this.getK()
					* (position.getX() - this.getP().getX())
					- this.getP().getY();
		} else {
			delta = position.getX() - this.x;
		}
		if (Math.abs(delta) <= 0.0001)
			return true;
		else
			return false;
	}

	public Position getCentre() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getK() {
		return Math.tan(this.getAlpha().getRadian());
	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}

	public double getB() {
		return -this.getK() * this.getP().getX() + this.getP().getY();
	}

	public Radian getAlpha() {
//		if (!this.verticalLine)
//			return alpha;
//		else
//			return new Radian(Math.PI / 2);
		return alpha;
	}

	public void setAlpha(Radian alpha) {
		this.alpha = alpha;
	}

	public Position intersect(Line line) {
		if (!this.isVerticalLine() && !line.isVerticalLine()) {
			double k1 = Math.tan(this.getAlpha().getRadian());
			double x1 = this.getP().getX();
			double y1 = this.getP().getY();

			double k2 = Math.tan(line.getAlpha().getRadian());
			double x2 = line.getP().getX();
			double y2 = line.getP().getY();

			double x = (y2 - y1 + k1 * x1 - k2 * x2) / (k1 - k2);
			double y = k1 * (x - x1) + y1;
			return new Position(x, y);
		} else if (this.isVerticalLine() && line.isVerticalLine())
			return null;
		if (line.isVerticalLine()) {
			return new Position(line.getX(), this.getY(line.getX()));
		}
		if (this.isVerticalLine()) {
			return new Position(this.getX(), line.getY(this.getX()));
		}
		return null;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public boolean isVerticalLine() {
		return verticalLine;
	}

	/*
	 * return the distance between a line and a point
	 */
	public double distance(Position other) {
		if(this.isVerticalLine()) {
			return Math.abs(other.getX()-this.getX());
		}
		double tanAlpha = Math.tan(this.getAlpha().getRadian());
		double up = tanAlpha * other.getX() - other.getY() + this.getP().getY()
				- tanAlpha * this.getP().getX();
		double down = Math.sqrt(tanAlpha * tanAlpha + 1);
		return Math.abs(up) / down;
	}

	public boolean lowerThan(Position pos) {
		double delta = pos.getY() - this.getK()
				* (pos.getX() - this.getP().getX()) - this.getP().getY();
		if (Math.abs(delta) <= 0.0001)
			return false;
		else if (delta > 0)
			return true;
		else
			return false;
	}

	public boolean higherThan(Position pos) {
		double delta = pos.getY() - this.getK()
				* (pos.getX() - this.getP().getX()) - this.getP().getY();
		if (Math.abs(delta) <= 0.0001)
			return false;
		else if (delta > 0)
			return false;
		else
			return true;
	}

	public double getY(double x) {
		return this.getK() * x + this.getB();
	}

	/*
	 * 
	 */
	public Position intersectNear(Circle circle) {
		double d1 = this.p.distance(circle.getCentre());
		double d2 = circle.getRadius();
		double d3 = this.distance(circle.getCentre());

		double r1 = Math.sqrt(d1 * d1 - d3 * d3);
		double r2 = Math.sqrt(d2 * d2 - d3 * d3);
		PolarPosition polarPosition = new PolarPosition(this.getCentre(), r1
				- r2, this.getAlpha().getRadian());

		return polarPosition.toPosition();
	}

	public Position intersectFar(Circle circle) {
		double d1 = this.p.distance(circle.getCentre());
		double d2 = circle.getRadius();
		double d3 = this.distance(circle.getCentre());

		double r1 = Math.sqrt(d1 * d1 - d3 * d3);
		double r2 = Math.sqrt(d2 * d2 - d3 * d3);
		PolarPosition polarPosition = new PolarPosition(this.getCentre(), r1
				+ r2, this.getAlpha().getRadian());

		return polarPosition.toPosition();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Line(");
		sb.append(this.p);
		sb.append(" degree(");
		sb.append(this.alpha.getDegree());
		sb.append("))");
		return sb.toString();
	}
}
