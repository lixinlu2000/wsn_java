package NHSensor.NHSensorSim.cds;

import java.awt.Color;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.events.DrawLinkEvent;
import NHSensor.NHSensorSim.events.DrawNodeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class CDSAttachment extends NodeAttachment {
	private int color = WHITE;
	private int nodeAddedSize = 3;
	private CDSAttachment cdsParent = null;
	private Vector cdsChilds = new Vector();
	static final int GRAY = 0;
	static final int WHITE = 1;
	static final int BLACK = 2;

	public CDSAttachment(Node node, Algorithm algorithm, double energy,
			double radioRange) {
		super(node, algorithm, energy, radioRange);
		// TODO Auto-generated constructor stub
	}

	protected boolean lowerRankThan(CDSAttachment other) {
		if (this.getHopCount() < other.getHopCount())
			return true;
		else if (this.getHopCount() > other.getHopCount())
			return false;
		else {
			return this.getNode().getId() < other.getNode().getId();
		}
	}

	protected Vector getLowRankedNeighbors() {
		Vector neigbhors = this.getNeighbors();
		Vector lowRankedNeighbors = new Vector();
		CDSAttachment na;
		for (int i = 0; i < neigbhors.size(); i++) {
			na = (CDSAttachment) neigbhors.elementAt(i);
			if (na.lowerRankThan(this))
				lowRankedNeighbors.add(na);
		}
		return lowRankedNeighbors;
	}

	public boolean shouldBeBlack() {
		Vector lowRankedNeighbors = this.getLowRankedNeighbors();
		CDSAttachment na;
		for (int i = 0; i < lowRankedNeighbors.size(); i++) {
			na = (CDSAttachment) lowRankedNeighbors.elementAt(i);
			if (!na.isGrayNode())
				return false;
		}
		return true;
	}

	public CDSAttachment getCdsParent() {
		return cdsParent;
	}

	public void setCdsParent(CDSAttachment cdsParent)
			throws SensornetBaseException {
		this.cdsParent = cdsParent;

		Color color;
		if (cdsParent.isGrayNode() && this.isBlackNode())
			color = Color.RED;
		else
			color = Color.BLUE;
		DrawLinkEvent dle = new DrawLinkEvent(this.getAlgorithm(), this
				.getNode(), cdsParent.getNode(), color);
		this.getAlgorithm().getSimulator().handle(dle);
	}

	public boolean addCdsChild(NodeAttachment child) {
		return cdsChilds.add(child);
	}

	public Vector getCdsChilds() {
		return cdsChilds;
	}

	public int getColor() {
		return color;
	}

	public boolean isGrayNode() {
		return this.color == GRAY;
	}

	public boolean isBlackNode() {
		return this.color == BLACK;
	}

	public boolean isWhiteNode() {
		return this.color == WHITE;
	}

	public void setGray() throws SensornetBaseException {
		this.color = GRAY;
		DrawNodeEvent dne = new DrawNodeEvent(this.getAlgorithm(), this
				.getNode(), nodeAddedSize, Color.RED);
		this.getAlgorithm().getSimulator().handle(dne);

	}

	public void setBlack() throws SensornetBaseException {
		this.color = BLACK;
		DrawNodeEvent dne = new DrawNodeEvent(this.getAlgorithm(), this
				.getNode(), nodeAddedSize, Color.BLACK);
		this.getAlgorithm().getSimulator().handle(dne);
	}
}
