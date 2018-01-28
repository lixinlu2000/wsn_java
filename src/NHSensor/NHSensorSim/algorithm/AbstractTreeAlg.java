package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;
import NHSensor.NHSensorSim.query.Query;

public abstract class AbstractTreeAlg extends Algorithm {
	public static final String NAME = "AbstractTree";
	private boolean justCollect = false;

	public boolean isJustCollect() {
		return justCollect;
	}

	public void setJustCollect(boolean justCollect) {
		this.justCollect = justCollect;
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
	}

	public AbstractTreeAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public AbstractTreeAlg(Query query) {
		super(query);
	}

	public AbstractTreeAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, AbstractTreeAlg.NAME,
				statistics);
		// TODO Auto-generated constructor stub
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new NodeAttachment(node, this, this.getParam()
					.getINIT_ENERGY(), this.getParam().getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		this.getNetwork().setLinkEstimator(new IdealRadioRangeLinkEstimator(this.getNetwork(), this.getParam().getRADIO_RANGE()));
		this.initNeighbors();
	}
	
	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (NodeAttachment) node.getAttachment(this.getName());
			// NOTE initial neighbors
			na.initNeighbors();
		}
	}

	public AbstractTreeAlg() {
		// TODO Auto-generated constructor stub
	}
}
