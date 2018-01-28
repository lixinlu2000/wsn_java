package NHSensor.NHSensorSim.core;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.query.QueryBase;

public abstract class NeighborAttachmentAlg extends Algorithm {
	public static final String NAME = "NeighborAttachmentAlg";

	public NeighborAttachmentAlg(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public NeighborAttachmentAlg(QueryBase query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public NeighborAttachmentAlg(QueryBase query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, NeighborAttachmentAlg.NAME,
				statistics);
		// TODO Auto-generated constructor stub
	}

	public NeighborAttachmentAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NeighborAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new NeighborAttachment(node, this, this.getParam()
					.getINIT_ENERGY(), this.getParam().getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		this.initNeighbors();

	}

	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NeighborAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (NeighborAttachment) node.getAttachment(this.getName());
			// NOTE initial neighbors
			na.initNeighbors();
		}
	}
}
