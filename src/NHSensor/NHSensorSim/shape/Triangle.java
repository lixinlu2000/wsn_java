package NHSensor.NHSensorSim.shape;

public class Triangle extends Shape {
	Position[] vertexs = new Position[3];

	public Triangle(Position[] vertexs) {
		this.vertexs = vertexs;
	}

	public Triangle(Position p1, Position p2, Position p3) {
		this.vertexs[0] = p1;
		this.vertexs[1] = p2;
		this.vertexs[2] = p3;
	}

	public boolean contains(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	public Position getCentre() {
		double x = 0, y = 0;
		for (int i = 0; i < vertexs.length; i++) {
			x += vertexs[i].getX();
			y += vertexs[i].getY();
		}
		x /= 3;
		y /= 3;
		return new Position(x, y);
	}

	public Line[] getLines() {
		Line[] ls = new Line[3];
		ls[0] = new Line(this.vertexs[0], this.vertexs[0]
				.bearing(this.vertexs[1]));
		ls[1] = new Line(this.vertexs[1], this.vertexs[1]
				.bearing(this.vertexs[2]));
		ls[2] = new Line(this.vertexs[2], this.vertexs[2]
				.bearing(this.vertexs[0]));
		return ls;
	}

	public LineSegment[] getLineSegments() {
		LineSegment[] ls = new LineSegment[3];
		ls[0] = new LineSegment(this.vertexs[0], this.vertexs[1]);
		ls[1] = new LineSegment(this.vertexs[1], this.vertexs[2]);
		ls[2] = new LineSegment(this.vertexs[2], this.vertexs[0]);
		return ls;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Triangle(");
		sb.append(this.vertexs[0]);
		sb.append(" " + this.vertexs[1]);
		sb.append(" " + this.vertexs[2]);
		sb.append(")");
		return sb.toString();
	}

}
