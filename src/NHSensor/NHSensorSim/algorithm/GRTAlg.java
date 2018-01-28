package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.CaculateMBREvent;
import NHSensor.NHSensorSim.events.CollectTreeResultEvent;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.SendQueryMessageThroughMBREvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;

public class GRTAlg extends AbstractTreeAlg {
	public final static String NAME = "GRT";
	protected TreeEvent treeEvent;

	public GRTAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public GRTAlg(Query query) {
		super(query);
	}

	public GRTAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, GRTAlg.NAME, statistics);
	}

	public GRTAlg() {
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
	}

	public boolean run() {
		try {
			this.broadcastToBuildTree();
			this.caculateMBR();
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

	public void broadcastToBuildTree() throws SensornetBaseException {
		NodeAttachment root = this.getRootNodeAttachment();
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(2, query);

		BuildTreeEvent bte = new BuildTreeEvent(root, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}
	
	public void regionBroadcast() throws SensornetBaseException {
		NodeAttachment root = this.getRootNodeAttachment();
		Rect rect = this.getSpatialQuery().getRect();
		Message queryMessage = new Message(this.getParam().getQUERY_MESSAGE_SIZE(), query);

		SendQueryMessageThroughMBREvent bte = new SendQueryMessageThroughMBREvent(root, rect, queryMessage, this);
		this.getSimulator().handle(bte);
	}
	
	public void caculateMBR() throws SensornetBaseException {
		CaculateMBREvent caculateMBREvent = new CaculateMBREvent(this.treeEvent,
				this);
		this.getSimulator().handle(caculateMBREvent);
		
		//debug
		NodeAttachment root = this.getRootNodeAttachment();
		DrawShapeEvent dse = new DrawShapeEvent(this, root.getMBR());
		this.getSimulator().handle(dse);		
	}

	public void regionCollect() throws SensornetBaseException {
		CollectTreeResultEvent ctre = new CollectTreeResultEvent(this
				.getTreeEvent(), this.getSpatialQuery().getRect(), this);
		this.getSimulator().handle(ctre);
		this.setAns(ctre.getAns());
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}
}
