package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * NOTE: the member variable TransferEvent.message means the partial answer message!
 * the member variable TraverseRectEvent.sendQuery means whether send query message first!
 */
public abstract class TraverseRectEvent extends TransferEvent {
	protected NeighborAttachment root;
	protected Rect rect;
	protected NeighborAttachment lastNode;
	protected Vector route = new Vector();
	protected boolean sendQuery = false;
	protected Vector firstPhaseRoute = new Vector();
	protected boolean cannotTraverse = false;

	public TraverseRectEvent(Message message, Algorithm alg) {
		super(message, alg);
		// TODO Auto-generated constructor stub
	}

	public TraverseRectEvent(NeighborAttachment root, Rect rect,
			Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.rect = rect;
	}

	public TraverseRectEvent(NeighborAttachment root, Rect rect,
			Message message, boolean sendQuery, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.rect = rect;
		this.sendQuery = sendQuery;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
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

	public Vector getRoute() {
		return route;
	}

	public int getRouteSize() {
		return this.route.size();
	}

	public void setLastNode(NeighborAttachment lastNode) {
		this.lastNode = lastNode;
	}

	public Vector getFirstPhaseRoute() {
		return firstPhaseRoute;
	}

	public void setFirstPhaseRoute(Vector firstPhaseRoute) {
		this.firstPhaseRoute = firstPhaseRoute;
	}

	public boolean isSendQuery() {
		return sendQuery;
	}

	public void setSendQuery(boolean sendQuery) {
		this.sendQuery = sendQuery;
	}

	public boolean isCannotTraverse() {
		return cannotTraverse;
	}

	public void setCannotTraverse(boolean cannotTraverse) {
		this.cannotTraverse = cannotTraverse;
	}

}
