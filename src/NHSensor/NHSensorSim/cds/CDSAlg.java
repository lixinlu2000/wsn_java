package NHSensor.NHSensorSim.cds;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreeEventForCDS;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;

public class CDSAlg extends Algorithm {
	public final static String NAME = "CDSAlg";
	protected CDSAttachment root;

	public CDSAlg(Query query) {
		super(query);
	}

	public CDSAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public CDSAlg(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, CDSAlg.NAME, statistics);
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		CDSAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new CDSAttachment(node, this,
					this.getParam().getINIT_ENERGY(), this.getParam()
							.getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		node = query.getOrig();
		this.root = (CDSAttachment) node.getAttachment(this.getName());
		this.initNeighbors();
	}

	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		CDSAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (CDSAttachment) node.getAttachment(this.getName());
			// NOTE initial neighbors and lives
			na.initNeighbors();
		}
	}

	protected void buildTree() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);
		Message setParentMessage = new Message(param.getQUERY_MESSAGE_SIZE(),
				query);

		BuildTreeEventForCDS bte = new BuildTreeEventForCDS(this.getRoot(),
				rect, queryMessage, setParentMessage, this);
		this.getSimulator().handle(bte);
	}

	protected void buildCDS() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildCDSEvent bcdse = new BuildCDSEvent(this.getRoot(), rect,
				queryMessage, this);
		this.getSimulator().handle(bcdse);
	}

	protected void buildCDSTree() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildCDSTreeEvent bcdse = new BuildCDSTreeEvent(this.getRoot(), rect,
				queryMessage, this);
		this.getSimulator().handle(bcdse);
	}

	public boolean run() {
		try {
			this.buildTree();
			this.buildCDS();
			this.buildCDSTree();
		} catch (SensornetBaseException e) {
			e.printStackTrace();
		}
		return true;
	}

	public CDSAttachment getRoot() {
		return root;
	}
}
