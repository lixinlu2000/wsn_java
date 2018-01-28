package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.CollectTreeResultEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class SWinFloodAlg extends AbstractTreeAlg {
	protected NodeAttachment coordinatorNode; // coordinator node;
	protected TreeEvent treeEvent;

	public final static String NAME = "SWinFlood";

	public SWinFloodAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public SWinFloodAlg(Query query) {
		super(query);
	}

	public SWinFloodAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, SWinFloodAlg.NAME, statistics);
	}

	public SWinFloodAlg() {
	}

	public boolean run() {
		try {
			if (!greedyForward()) {
				System.out.println(this.getName()
						+ " Cannot find coordinator node inside rectangle");
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
			this.regionBroadcast();
			this.regionCollect();

			if (!this.greedyBack()) {
				System.out.println("Cannot route back answers");
				this.getStatistics().setSuccess(false);
				return false;
			}
		} catch (HasNoEnergyException e) {
			e.printStackTrace();
			this.setFirstNodeHasNoEnergy(e.getAttachment());
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
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

	/*
	 * @return whether can find coordinator node?
	 */
	public boolean greedyForward() throws SensornetBaseException {
		Node node = this.getSpatialQuery().getOrig();
		NodeAttachment cur = (NodeAttachment) node
				.getAttachment(this.getName());

		Position dest = this.getSpatialQuery().getRect().getCentre();

		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);
		GreedyForwardToPointEvent event = new GreedyForwardToPointEvent(cur,
				dest, queryMessage, this);
		this.getSimulator().handle(event);

		this.setCoordinatorNode((NodeAttachment) event.getLastNode());

		if (event.getLastNode().getNode().getPos().in(
				this.getSpatialQuery().getRect()))
			return true;
		else
			return false;

	}

	public void regionBroadcast() throws SensornetBaseException {
		NodeAttachment root = this.getCoordinatorNode();
		Rect rect = this.getSpatialQuery().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildTreeEvent bte = new BuildTreeEvent(root, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}

	public void regionCollect() throws SensornetBaseException {
		CollectTreeResultEvent ctre = new CollectTreeResultEvent(this
				.getTreeEvent(), this.getSpatialQuery().getRect(), this);
		this.getSimulator().handle(ctre);
		this.setAns(ctre.getAns());
	}

	public boolean greedyBack() throws SensornetBaseException {
		Position pos = this.getSpatialQuery().getOrig().getPos();

		NodeAttachment src = this.getCoordinatorNode();

		Message answerMessage = new Message(this.getParam().getANSWER_SIZE()
				* src.getMessageCount(this.getSpatialQuery().getRect()), this
				.getQuery());
		GreedyForwardToPointEvent event = new GreedyForwardToPointEvent(src,
				pos, answerMessage, this);
		this.getSimulator().handle(event);

		this.formParentChilds(src, event.getRoute());
		return event.isSuccess();
	}

	public NodeAttachment getCoordinatorNode() {
		return coordinatorNode;
	}

	public void setCoordinatorNode(NodeAttachment coordinatorNode) {
		this.coordinatorNode = coordinatorNode;
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}
}