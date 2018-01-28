package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.SectorInRect;

/*
 * NOTE: the member variable TransferEvent.message means the partial answer message!
 * the member variable TraverseRectEvent.sendQuery means whether send query message first!
 */
public abstract class TraverseSectorInRectEvent extends TransferEvent {
	protected NeighborAttachment root;
	protected SectorInRect sectorInRect;
	protected NeighborAttachment lastNode;
	protected Vector route = new Vector();
	protected boolean sendQuery = false;
	protected Vector firstPhaseRoute = new Vector();
	protected boolean cannotTraverse = false;
	protected int direction;

	public TraverseSectorInRectEvent(Message message, Algorithm alg) {
		super(message, alg);
		// TODO Auto-generated constructor stub
	}

	public TraverseSectorInRectEvent(NeighborAttachment root,
			SectorInRect sectorInRect, int direction, Message message,
			Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.sectorInRect = sectorInRect;
		this.direction = direction;
	}

	public TraverseSectorInRectEvent(NeighborAttachment root,
			SectorInRect sectorInRect, int direction, Message message,
			boolean sendQuery, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.sectorInRect = sectorInRect;
		this.sendQuery = sendQuery;
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public SectorInRect getSectorInRect() {
		return sectorInRect;
	}

	public void setSectorInRect(SectorInRect sectorInRect) {
		this.sectorInRect = sectorInRect;
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
