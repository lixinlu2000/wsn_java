package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Ring;

public abstract class TraverseRingEvent extends TransferEvent {
	protected NeighborAttachment root;
	protected Ring ring;
	protected NeighborAttachment lastNode;
	protected Vector route = new Vector();
	protected boolean sendQuery = false;
	protected Vector firstPhaseRoute = new Vector();
	protected double backupNodeNum = 0;

	public TraverseRingEvent(Message message, Algorithm alg) {
		super(message, alg);
		// TODO Auto-generated constructor stub
	}

	public TraverseRingEvent(NeighborAttachment root, Ring ring,
			Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.ring = ring;
	}

	public TraverseRingEvent(NeighborAttachment root, Ring ring,
			Message message, boolean sendQuery, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.ring = ring;
		this.sendQuery = sendQuery;
	}

	public NeighborAttachment getRoot() {
		return root;
	}

	public void setRoot(NeighborAttachment root) {
		this.root = root;
	}

	public NeighborAttachment getLastNode() {
		return lastNode;
	}

	public void setLastNode(NeighborAttachment lastNode) {
		this.lastNode = lastNode;
	}

	public Vector getRoute() {
		return route;
	}

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

	public boolean isSendQuery() {
		return sendQuery;
	}

	public void setSendQuery(boolean sendQuery) {
		this.sendQuery = sendQuery;
	}

	public Vector getFirstPhaseRoute() {
		return firstPhaseRoute;
	}

	public void setFirstPhaseRoute(Vector firstPhaseRoute) {
		this.firstPhaseRoute = firstPhaseRoute;
	}

	public void setRoute(Vector route) {
		this.route = route;
	}

	public int getQueryNodeNum() {
		return this.route.size() - this.firstPhaseRoute.size() + 1;
	}

	public double getBackupNodeNum() {
		return backupNodeNum;
	}
}
