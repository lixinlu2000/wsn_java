package NHSensor.NHSensorSim.routing.gpsr;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.shape.Position;

public class GPSRAttachment extends NodeAttachment {
	private boolean[] live;
	private int graphType = GraphType.RNG;

	public GPSRAttachment(Node node, Algorithm algorithm, double energy,
			double radioRange) {
		super(node, algorithm, energy, radioRange);
		// TODO Auto-generated constructor stub
	}

	public void initNeighbors() {
		if (this.getGraphType() == GraphType.RNG) {
			this.initRNGNeighbors();
		} else if (this.getGraphType() == GraphType.GG) {
			this.initGGNeighbors();
		} else {
			return;
		}
	}

	public void initLive() {
		this.live = new boolean[this.getNeighbors().size()];
		for (int i = 0; i < live.length; i++) {
			live[i] = true;
		}
	}

	public void initRNGNeighbors() {
		Node u = this.getNode();
		super.initNeighbors();
		this.initLive();

		for (int vi = 0; vi < this.getNeighbors().size(); vi++) {
			for (int wi = 0; wi < this.getNeighbors().size(); wi++) {
				if (vi == wi)
					continue;
				Node v = ((Attachment) this.getNeighbors().elementAt(vi))
						.getNode();
				Node w = ((Attachment) this.getNeighbors().elementAt(wi))
						.getNode();

				if (Position.distance(u.getPos(), v.getPos()) > Math.max(
						Position.distance(u.getPos(), w.getPos()), Position
								.distance(v.getPos(), w.getPos()))) {
					this.live[vi] = false;
					break;
				}
			}
		}
	}

	public void initGGNeighbors() {
		Node u = this.getNode();
		super.initNeighbors();
		this.initLive();

		for (int vi = 0; vi < this.getNeighbors().size(); vi++) {
			for (int wi = 0; wi < this.getNeighbors().size(); wi++) {
				if (vi == wi)
					continue;
				Node v = ((Attachment) this.getNeighbors().elementAt(vi))
						.getNode();
				Node w = ((Attachment) this.getNeighbors().elementAt(wi))
						.getNode();

				if (Math.pow(Position.distance(u.getPos(), v.getPos()), 2) > Math
						.pow(Position.distance(u.getPos(), w.getPos()), 2)
						+ Math
								.pow(Position.distance(v.getPos(), w.getPos()),
										2)) {
					this.live[vi] = false;
					break;
				}
			}
		}
	}

	public int getGraphType() {
		return graphType;
	}

	public void setGraphType(int graphType) {
		this.graphType = graphType;
	}

	public GPSRAttachment getNextGPSRAttachmentToDest(Position dest) {
		return (GPSRAttachment) this.getNextNeighborAttachmentToDest(dest);
	}

	public GPSRAttachment findFirstCCW(double bearing, GPSRAttachment ga) {
		double neighborBearing;
		double delta;
		double minDelta = 3 * Math.PI;
		GPSRAttachment next = this;

		GPSRAttachment liveNeighbor;

		for (int i = 0; i < this.getNeighbors().size(); i++) {
			if (this.live[i] == false)
				continue;
			liveNeighbor = (GPSRAttachment) this.getNeighbors().elementAt(i);
			if (liveNeighbor == ga)
				continue;
			neighborBearing = this.getNode().getPos().bearing(
					liveNeighbor.getNode().getPos());
			delta = neighborBearing - bearing;
			if (delta < 0)
				delta += 2 * Math.PI;

			if (delta < minDelta) {
				minDelta = delta;
				next = liveNeighbor;
			}
		}
		return next;
	}

}
