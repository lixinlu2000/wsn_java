package NHSensor.NHSensorSim.events;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.util.NodeAttachmentHopCountComparator;

public class BuildTreesEventForE2STAFinal extends TreeEvent {
	Vector roots;
	SectorInRect[] sectorInRects;

	public BuildTreesEventForE2STAFinal(Vector roots, SectorInRect[] sectorInRects, 
			Rect rect,
			Message message, Algorithm alg) {
		super(message, alg);
		this.rect = rect;
		this.roots = roots;
		this.sectorInRects = sectorInRects;
	}
	
	protected SectorInRect getSectorInRectContainsNode(NodeAttachment na) {
		for(int i=0;i<this.sectorInRects.length;i++) {
			if(this.sectorInRects[i].contains(na.getPos()))
				return this.sectorInRects[i];
		}
		return null;
	}

	public void handle() throws SensornetBaseException {
		PriorityQueue nodes = new PriorityQueue(10, new NodeAttachmentHopCountComparator());
		QueryBase query = this.getAlg().getQuery();
		NodeAttachment cur;
		Vector neighbors;
		NodeAttachment neighbor;
		RadioEvent event;
		Message msg;

		for (int i = 0; i < this.roots.size(); i++) {
			cur = (NodeAttachment)this.roots.elementAt(i);
			if (cur.getNode().getPos().in(rect)) {
				cur.setReceivedQID(this.getAlg().getQuery().getId());
				nodes.add(cur);
			}
		}

		while (!nodes.isEmpty()) {
			cur = (NodeAttachment) nodes.remove();
			this.ans.add(cur);
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
					neighbor.setReceivedQID(query.getId());
					neighbor.setHopCount(cur.getHopCount() + 1);
					neighbor.setParent(cur);
					cur.addChild(neighbor);

					nodes.add(neighbor);
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
