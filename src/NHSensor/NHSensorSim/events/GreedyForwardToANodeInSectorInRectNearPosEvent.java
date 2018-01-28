package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.LineLineInSectorInRect;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.SectorInRect;

public class GreedyForwardToANodeInSectorInRectNearPosEvent extends ForwardToShapeEvent {
	protected int direction;
	protected NeighborAttachment firstTryLastNode = null;
	protected Position pos;
	
	public GreedyForwardToANodeInSectorInRectNearPosEvent(NeighborAttachment root,Position pos,
			SectorInRect shape, int direction, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
		this.direction = direction;
		this.pos = pos;
	}
	
	public SectorInRect getSectorInRect() {
		return (SectorInRect)this.shape;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		double radioRange = this.getAlg().getParam().getRADIO_RANGE();
		LineLineInSectorInRect destShape = this.getSectorInRect().getInitialShape(direction, radioRange);

		GreedyForwardToAnotherNodeInShapeNearPosEvent event;
		Message msg = this.getMessage();
		boolean firstTry = true;
		
		while(!destShape.contains(cur.getPos())) {
			event = new GreedyForwardToAnotherNodeInShapeNearPosEvent(cur, this.pos, destShape, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);
			this.getRoute().addAll(event.getRoute());
			
			cur = event.getLastNode();
			
			if(firstTry) {
				firstTryLastNode = cur;
				firstTry = false;
			}
			
			if(destShape.contains(cur.getPos()))break;
			
			if(this.getSectorInRect().isEnd(destShape, direction)) break;
			else 
				destShape = this.getSectorInRect().nextShape(destShape, direction, radioRange);
		}

		if(firstTry) {
			firstTryLastNode = cur;
		}

		this.setLastNode(cur);
		if (this.getShape().contains(cur.getNode().getPos())) {
			this.setSuccess(true);
		} else {
			this.setSuccess(false);
		}
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public NeighborAttachment getFirstTryLastNode() {
		return this.firstTryLastNode;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToANodeInSectorInRectNearPosEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.shape.toString());
		return sb.toString();

	}

}
