package NHSensor.NHSensorSim.shape;

import NHSensor.NHSensorSim.core.Constants;

public class RingSector extends Ring {
	private double startRadian;
	private double sita;

	private double convertRadian(double radian) {
		double twoPI = 2 * Math.PI;

		while (radian >= twoPI) {
			radian -= twoPI;
		}

		while (radian < 0) {
			radian += twoPI;
		}

		return radian;
	}

	public Ring getRing() {
		return new Ring(this.circleCentre, this.lowRadius, this.highRadius);
	}

	public boolean equals(RingSector ringSector) {
		if (this.getRing().equals(ringSector.getRing())
				&& this.startRadian == ringSector.getStartRadian()
				&& this.sita == ringSector.getSita())
			return true;
		else
			return false;
	}

	public RingSector(Position centre, double lowRadius, double highRadius) {
		super(centre, lowRadius, highRadius);
		this.startRadian = 0;
		this.sita = 2 * Math.PI;
	}

	public RingSector(Ring ring) {
		this(ring, 0, Math.PI * 2);
	}

	public RingSector(Ring ring, double startRadian, double sita) {
		this(ring.getCentre(), ring.getLowRadius(), ring.getHighRadius(),
				startRadian, sita);
	}

	public double area() {
		double ringArea = super.area();
		return Math.PI / 2 * this.getSita() * ringArea;
	}

	public RingSector(Position centre, double lowRadius, double highRadius,
			double startRadian, double sita) {
		super(centre, lowRadius, highRadius);
		double sr = this.convertRadian(startRadian);
		this.startRadian = sr;
		this.sita = sita;
	}

	public double getSita() {
		return sita;
	}

	public void setSita(double sita) {
		this.sita = this.convertRadian(sita);
	}

	public double getStartRadian() {
		return this.startRadian;
	}

	public void setStartRadian(double startRadian) {
		this.startRadian = this.convertRadian(startRadian);
	}

	public double getEndRadian() {
		double endRadian = this.getStartRadian() + this.getSita();
		return this.convertRadian(endRadian);
	}

	// TODO fix bug
	public void setEndRadian(double endRadian) {
		double newSita = endRadian - this.getStartRadian();
		newSita = this.convertRadian(newSita);

		if (newSita < 0) {
			this.startRadian = endRadian;
			this.sita = -newSita;
		} else {
			this.sita = newSita;
		}
	}

	public double getStartDegree() {
		return this.getStartRadian() / (2 * Math.PI) * 360;
	}

	public double getEndDegree() {
		return this.getEndRadian() / (2 * Math.PI) * 360;
	}

	public double relativeRadian(Position position) {
		double bearing = this.getCircleCentre().bearing(position);
		return this.convertRadian(bearing - this.getStartRadian());
	}

	public boolean contains(Position pos) {
		double endRadian;
		if (!super.contains(pos))
			return false;
		else {
			double radian = this.getCircleCentre().bearing(pos);

			endRadian = this.getStartRadian() + this.getSita();
			if (endRadian >= 2 * Math.PI) {
				if (radian >= this.getStartRadian() && radian <= 2 * Math.PI)
					return true;
				if (radian >= 0 && radian <= endRadian - 2 * Math.PI)
					return true;
				return false;
			} else {
				if (radian >= this.getStartRadian() && radian <= endRadian)
					return true;
				else
					return false;
			}
		}
	}

	public Object clone() {
		Ring r = (Ring) super.clone();
		return new RingSector(r, this.startRadian, this.sita);
	}

	public Position getCentre() {
		PolarPosition centre = new PolarPosition(this.getCircleCentre(), (this
				.getLowRadius() + this.getHighRadius()) / 2, this
				.getStartRadian()
				+ this.getSita() / 2);
		return centre.toPosition();
	}

	public Position[] getVertexs() {
		Position[] vertexs = new Position[4];
		PolarPosition[] polarVertexs = new PolarPosition[4];
		polarVertexs[0] = new PolarPosition(this.getCircleCentre(), this
				.getLowRadius(), this.getStartRadian());
		polarVertexs[1] = new PolarPosition(this.getCircleCentre(), this
				.getHighRadius(), this.getStartRadian());
		polarVertexs[2] = new PolarPosition(this.getCircleCentre(), this
				.getLowRadius(), this.getEndRadian());
		polarVertexs[3] = new PolarPosition(this.getCircleCentre(), this
				.getHighRadius(), this.getEndRadian());

		for (int i = 0; i < 4; i++) {
			vertexs[i] = polarVertexs[i].toPosition();
		}
		return vertexs;
	}

	public Position getRightDownVertex() {
		PolarPosition vertex = new PolarPosition(this.getCircleCentre(), this
				.getLowRadius(), this.getStartRadian());
		return vertex.toPosition();
	}

	public Position getRightUpVertex() {
		PolarPosition vertex = new PolarPosition(this.getCircleCentre(), this
				.getHighRadius(), this.getStartRadian());
		return vertex.toPosition();
	}

	public Position getRightDownVertex0() {
		PolarPosition vertex = new PolarPosition(this.getCircleCentre(), this
				.getLowRadius()
				+ Constants.DOUBLE_EQUAEL_VALUE, this.getStartRadian());
		return vertex.toPosition();
	}

	public Position getRightUpVertex0() {
		PolarPosition vertex = new PolarPosition(this.getCircleCentre(), this
				.getHighRadius()
				- Constants.DOUBLE_EQUAEL_VALUE, this.getStartRadian());
		return vertex.toPosition();
	}

	public Position getLeftDownVertex() {
		PolarPosition vertex = new PolarPosition(this.getCircleCentre(), this
				.getLowRadius(), this.getEndRadian());
		return vertex.toPosition();
	}

	public Position getLeftUpVertex() {
		PolarPosition vertex = new PolarPosition(this.getCircleCentre(), this
				.getHighRadius(), this.getEndRadian());
		return vertex.toPosition();
	}

	public LineLineInRing toLineLineInRing() {
		Line begin = new Line(this.getLeftDownVertex(), this.getStartRadian());
		Line end = new Line(this.getLeftUpVertex(), this.getEndRadian());
		// TODO urgly code
		return new LineLineInRing(begin, end, this.getRing(),
				RingDirection.ANTICLOCKWISE);
	}

	public RingSector add(RingSector rs) {
		double newSita = this.getSita() + rs.getSita();
		return new RingSector(this.getCircleCentre(), this.getLowRadius(), this
				.getHighRadius(), this.getStartRadian(), newSita);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RingSector(");
//		sb.append(this.getCircleCentre());
//		sb.append(" [");
//		sb.append(this.getLowRadius());
//		sb.append(",");
//		sb.append(this.getHighRadius());
//		sb.append("] [");
//		sb.append(this.getStartDegree());
//		sb.append(",");
//		sb.append(this.getEndDegree());
//		sb.append("])");
		return sb.toString();
	}

}
