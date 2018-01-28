package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;

public class ItineraryNodeEvent extends InformationEvent {
	private NeighborAttachment curItineraryNode;
	private Vector dataNodes = new Vector();// nextItineraryNode's backup nodes
	private NeighborAttachment nextItineraryNode;

	public ItineraryNodeEvent(NeighborAttachment curItineraryNode,
			NeighborAttachment nextItineraryNode, Algorithm alg) {
		super(alg);
		this.curItineraryNode = curItineraryNode;
		this.nextItineraryNode = nextItineraryNode;
	}

	public NeighborAttachment getCurItineraryNode() {
		return curItineraryNode;
	}

	public void setCurItineraryNode(NeighborAttachment curItineraryNode) {
		this.curItineraryNode = curItineraryNode;
	}

	public NeighborAttachment getNextItineraryNode() {
		return nextItineraryNode;
	}

	public void setNextItineraryNode(NeighborAttachment nextItineraryNode) {
		this.nextItineraryNode = nextItineraryNode;
	}

	public Vector getDataNodes() {
		return dataNodes;
	}

	public void setDataNodes(Vector dataNodes) {
		this.dataNodes = dataNodes;
	}

	public boolean addDataNode(NeighborAttachment dataNode) {
		return dataNodes.add(dataNode);
	}

	public int getNodeCount() {
		return this.getDataNodeCount() + 1;
	}

	public int getDataNodeCount() {
		if (this.dataNodes == null)
			return 0;
		return this.getDataNodes().size();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ItineraryNodeEvent ");
		sb.append("DataNodeCount(" + this.getNodeCount() + ") ");
		sb.append(this.getCurItineraryNode().getNode().getPos());
		sb.append("-->");
		if (this.getNextItineraryNode() != null) {
			sb.append(this.getNextItineraryNode().getNode().getPos());
		} else {
			sb.append("null");
		}
		return sb.toString();
	}
}
