package NHSensor.NHSensorSim.end;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.cds.BuildCDSEvent;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildCodeEventForCVD;
import NHSensor.NHSensorSim.events.BuildTreeEventForEnd;
import NHSensor.NHSensorSim.events.CollectTreeNodeCountEvent;
import NHSensor.NHSensorSim.events.DetectCutVertexEventForCVD;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;

public class EndAlg extends Algorithm {
	public final static String NAME = "EndAlg";
	protected EndAttachment root;
	protected TreeEvent treeEvent;

	public EndAlg(Query query) {
		super(query);
	}

	public EndAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public EndAlg(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, EndAlg.NAME, statistics);
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		EndAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new EndAttachment(node, this,
					this.getParam().getINIT_ENERGY(), this.getParam()
							.getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		node = query.getOrig();
		this.root = (EndAttachment) node.getAttachment(this.getName());
		this.initNeighbors();
	}

	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		EndAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (EndAttachment) node.getAttachment(this.getName());
			na.initNeighbors();
		}
	}

	protected void buildTree() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message probeMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);
		Message setParentMessage = new Message(param.getQUERY_MESSAGE_SIZE(),
				query);

		BuildTreeEventForEnd bte = new BuildTreeEventForEnd(root, rect,
				probeMessage, setParentMessage, this);
		this.getSimulator().handle(bte);
	}

	protected void buildCDS() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message colorMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildCDSEvent bcdse = new BuildCDSEvent(this.getRoot(), rect,
				colorMessage, this);
		this.getSimulator().handle(bcdse);
	}

	protected void buildCDSTree() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message joinMessage = new Message(0, query);
		Message setParentMessage = new Message(param.getQUERY_MESSAGE_SIZE(),
				query);

		treeEvent = new BuildCDSTreeEventForEnd(this.getRoot(), rect,
				joinMessage, setParentMessage, this);
		this.getSimulator().handle(treeEvent);
	}

	protected void collectTreeNodeCount() throws SensornetBaseException {
		CollectTreeNodeCountEvent ctre = new CollectTreeNodeCountEvent(
				this.treeEvent, this.getNetwork().getRect(), this);
		this.getSimulator().handle(ctre);
		this.setAns(ctre.getAns());
	}

	protected void buildCode() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message assignMessage = new Message(param.getQUERY_MESSAGE_SIZE(),
				query);
		Message endMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildCodeEventForCVD bce = new BuildCodeEventForCVD(this.getRoot(),
				rect, assignMessage, endMessage, this);
		this.getSimulator().handle(bce);
	}

	protected void coding() throws SensornetBaseException {
		this.collectTreeNodeCount();
		this.buildCode();
	}

	protected void cutVertexDetect() throws SensornetBaseException {
		DetectCutVertexEventForCVD bce = new DetectCutVertexEventForCVD(
				this.treeEvent, this.getNetwork().getRect(), this);
		this.getSimulator().handle(bce);
	}

	public boolean run() {
		try {
			this.buildTree();
			this.buildCDS();
			this.buildCDSTree();
			// this.coding();
			// this.cutVertexDetect();
		} catch (SensornetBaseException e) {
			e.printStackTrace();
		}
		return true;
	}

	public EndAttachment getRoot() {
		return root;
	}
}
