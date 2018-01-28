package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.TreeAggregationForPEVAEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.privacy.PEVAAttachment;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;

public class PEVAAlg extends AbstractTreeAlg {
	public final static String NAME = "PEVA";
	protected TreeEvent treeEvent;
	int dataSize;

	public PEVAAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public PEVAAlg(Query query) {
		super(query);
	}

	public PEVAAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, PEVAAlg.NAME, statistics);
	}

	public PEVAAlg() {
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		PEVAAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new PEVAAttachment(node, this, this.getParam()
					.getINIT_ENERGY(), this.getParam().getRADIO_RANGE());
			na.setDataSize(this.getDataSize());
			node.attach(this.getName(), na);
		}
		this.initNeighbors();
	}

	public boolean run() {
		try {
			this.regionBroadcast();
			this.regionCollect();
		} catch (HasNoEnergyException e) {
			e.printStackTrace();
			this.setFirstNodeHasNoEnergy(e.getAttachment());
		} catch (SensornetBaseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.getStatistics().setQueryResultCorrectness(
				this.getQueryResultCorrectness());
		if (this.getQueryResultCorrectness().isQueryResultCorrect()) {
			this.getStatistics().setSuccess(true);
			return true;
		} else {
			this.getStatistics().setSuccess(false);
			return true;
		}
	}

	protected NodeAttachment getRootNodeAttachment() {
		Node node = this.getSpatialQuery().getOrig();
		return (NodeAttachment) node.getAttachment(this.getName());
	}

	public void regionBroadcast() throws SensornetBaseException {
		NodeAttachment root = this.getRootNodeAttachment();
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildTreeEvent bte = new BuildTreeEvent(root, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}

	public void regionBroadcastHighestBit() throws SensornetBaseException {
		NodeAttachment root = this.getRootNodeAttachment();
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(1, query);

		BuildTreeEvent bte = new BuildTreeEvent(root, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}

	public void regionCollect() throws SensornetBaseException {
		TreeAggregationForPEVAEvent e = new TreeAggregationForPEVAEvent(this
				.getTreeEvent(), this.getSpatialQuery().getRect(), this);
		this.getSimulator().handle(e);
		this.setAns(e.getAns());
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
}
