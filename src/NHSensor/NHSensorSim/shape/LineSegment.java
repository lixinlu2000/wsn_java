package NHSensor.NHSensorSim.shape;

public class LineSegment extends Shape {
	Position pos1;
	Position pos2;

	public LineSegment(Position pos1, Position pos2) {
		super();
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public Position getPos1() {
		return pos1;
	}

	public void setPos1(Position pos1) {
		this.pos1 = pos1;
	}

	public Position getPos2() {
		return pos2;
	}

	public void setPos2(Position pos2) {
		this.pos2 = pos2;
	}

	public boolean contains(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	public double getLength() {
		return this.pos1.distance(this.pos2);
	}

	public Position getCentre() {
		double xMiddle = (this.pos1.getX() + this.pos2.getX()) / 2;
		double yMiddle = (this.pos1.getY() + this.pos2.getY()) / 2;
		return new Position(xMiddle, yMiddle);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LineSegment(");
		sb.append(this.pos1);
		sb.append(" ");
		sb.append(this.pos2);
		sb.append(")");
		return sb.toString();
	}

}
