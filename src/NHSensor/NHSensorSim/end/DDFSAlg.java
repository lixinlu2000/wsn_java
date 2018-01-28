package NHSensor.NHSensorSim.end;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildDepthFirstTreeEvent;
import NHSensor.NHSensorSim.events.DetectCutVertexEventForDDFS;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;

public class DDFSAlg extends Algorithm {
	public final static String NAME = "DDFSAlg";
	protected NodeAttachment root;
	protected BuildDepthFirstTreeEvent bte;

	public DDFSAlg(Query query) {
		super(query);
	}

	public DDFSAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public DDFSAlg(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, DDFSAlg.NAME, statistics);
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
		node = query.getOrig();
		this.root = (NodeAttachment) node.getAttachment(this.getName());
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
			na.initNeighbors();
		}
	}

	protected void buildTree() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message probeMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		bte = new BuildDepthFirstTreeEvent(this.getRoot(), rect, probeMessage,
				this);
		this.getSimulator().handle(bte);
	}

	protected void cutVertexDetect() throws SensornetBaseException {
		DetectCutVertexEventForDDFS bce = new DetectCutVertexEventForDDFS(
				this.bte, this);
		this.getSimulator().handle(bce);
	}

	public boolean run() {
		try {
			this.buildTree();
			this.cutVertexDetect();
		} catch (SensornetBaseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public NodeAttachment getRoot() {
		return root;
	}
}
