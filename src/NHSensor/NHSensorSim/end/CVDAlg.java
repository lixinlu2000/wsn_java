package NHSensor.NHSensorSim.end;

import java.util.Iterator;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildCodeEventForCVD;
import NHSensor.NHSensorSim.events.BuildTreeEventForCVD;
import NHSensor.NHSensorSim.events.CollectTreeNodeCountEvent;
import NHSensor.NHSensorSim.events.DetectCutVertexEventForCVD;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;

public class CVDAlg extends Algorithm {
	public final static String NAME = "CVDAlg";
	protected EndAttachment root;
	protected BuildTreeEventForCVD bte;

	public CVDAlg(Query query) {
		super(query);
	}

	public CVDAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public CVDAlg(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, CVDAlg.NAME, statistics);
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
		this.getNetwork().setLinkEstimator(new IdealRadioRangeLinkEstimator(this.getNetwork(), this.getParam().getRADIO_RANGE()));
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
		Message setFriendMessage = new Message(param.getQUERY_MESSAGE_SIZE(),
				query);

		bte = new BuildTreeEventForCVD(this.getRoot(), rect, probeMessage,
				setParentMessage, setFriendMessage, this);
		this.getSimulator().handle(bte);
	}

	protected void collectTreeNodeCount() throws SensornetBaseException {
		CollectTreeNodeCountEvent ctre = new CollectTreeNodeCountEvent(
				this.bte, this.getNetwork().getRect(), this);
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
				this.bte, this.getNetwork().getRect(), this);
		this.getSimulator().handle(bce);
	}

	public boolean run() {
		try {
			this.buildTree();
			this.coding();
			this.cutVertexDetect();
		} catch (SensornetBaseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public EndAttachment getRoot() {
		return root;
	}
}
