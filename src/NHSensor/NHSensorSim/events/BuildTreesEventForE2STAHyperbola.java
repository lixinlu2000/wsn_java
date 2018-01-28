package NHSensor.NHSensorSim.events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.SectorInRect;

public class BuildTreesEventForE2STAHyperbola extends TreeEvent {
	NodeAttachment[] roots;
	SectorInRect[] sectorInRects;
	NodeAttachment sink;
	Position[] initialPositions;
	
	public BuildTreesEventForE2STAHyperbola(NodeAttachment sink, NodeAttachment[] roots, 
			Position[] initialPositions, 
			SectorInRect[] sectorInRects, 
			Rect rect,
			Message message, Algorithm alg) {
		super(message, alg);
		this.rect = rect;
		this.sink = sink;
		this.roots = roots;
		this.initialPositions = initialPositions;
		this.sectorInRects = sectorInRects;
	}
	
	protected int caculateRootIndex(NodeAttachment na) {
		double minDistance = Double.MAX_VALUE;
		int rootIndex = -1;
		double distance;
		
		for(int i=0;i<this.initialPositions.length;i++) {
			distance = na.getPos().distance(this.initialPositions[i]);
			distance += this.initialPositions[i].distance(sink.getPos());
			if(distance<minDistance) {
				rootIndex = i;
				minDistance = distance;
			}
		}
		return rootIndex;
	}

	public void handle() throws SensornetBaseException {
		LinkedList path = new LinkedList();
		LinkedList path1 = new LinkedList();
		QueryBase query = this.getAlg().getQuery();
		NodeAttachment cur;
		Vector neighbors;
		NodeAttachment neighbor;
		RadioEvent event;
		Message msg;

		for (int i = 0; i < this.roots.length; i++) {
			cur = this.roots[i];
			if (cur!=null&&cur.getNode().getPos().in(rect)) {
				this.ans.add(cur);
				cur.setReceivedQID(this.getAlg().getQuery().getId());
				cur.setHopCount(0);
				cur.setParent(null);
				path.addLast(cur);
			}
		}

		while (!path.isEmpty()) {
			cur = (NodeAttachment) path.getFirst();
			path.removeFirst();
			neighbors = cur.getNeighbors();

			msg = this.getMessage();
			event = new BroadcastEvent(cur, null, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())
						&& neighbor.getReceivedQID() != query.getId()
						) {
					if(this.caculateRootIndex(cur)==this.caculateRootIndex(neighbor)) {
						neighbor.setReceivedQID(query.getId());
						neighbor.setHopCount(cur.getHopCount() + 1);
						neighbor.setParent(cur);
						cur.addChild(neighbor);
	
						path.addLast(neighbor);
						ans.add(neighbor);
					}
					else {
						if(!path1.contains(neighbor))
							neighbor.setHopCount(cur.getHopCount() + 1);
							neighbor.setParent(cur);
							path1.addLast(neighbor);
					}
				}
			}
		}

		while (!path1.isEmpty()) {
			cur = (NodeAttachment) path1.getFirst();
			path1.removeFirst();
			
			if(cur.getReceivedQID() == query.getId()) continue;
			cur.getParent().addChild(cur);
			cur.setReceivedQID(query.getId());
			neighbors = cur.getNeighbors();
			ans.add(cur);

			msg = this.getMessage();
			event = new BroadcastEvent(cur, null, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())
						&& neighbor.getReceivedQID() != query.getId()
						&&!path1.contains(neighbor)) {
						neighbor.setHopCount(cur.getHopCount() + 1);
						neighbor.setParent(cur);
	
						path1.addLast(neighbor);
				}
			}
		}
	}

	public SectorInRect[] getSectorInRects() {
		return sectorInRects;
	}

	public void setSectorInRects(SectorInRect[] sectorInRects) {
		this.sectorInRects = sectorInRects;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append(" roots:" + this.roots);
		sb.append(" rect:" + this.rect);
		return sb.toString();
	}

}
