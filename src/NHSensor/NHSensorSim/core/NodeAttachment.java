package NHSensor.NHSensorSim.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;

public class NodeAttachment extends NeighborAttachment {
	private int hopCount = -1;
	private NodeAttachment parent = null;
	private int receivedQID = -1;
	protected Vector childs = new Vector();
	private Hashtable objectsFromChilds = new Hashtable();
	private Hashtable cachedObjectsFromChilds = new Hashtable();
	private Object cachedObject;
	private int treeNodeCount = -1;
	boolean broadcasted = false;
	Hashtable childsAncestorMinDepth = new Hashtable();
	Rect mbr = null;

	protected int messageCount = -1;

	public int getMessageCount(Shape shape) {
		if (messageCount != -1)
			return this.messageCount;

		else {
			messageCount = 0;
			NodeAttachment child;
			for (int i = 0; i < this.childs.size(); i++) {
				child = (NodeAttachment) this.childs.elementAt(i);
				//bug
				//if (shape.contains(child.getPos()))
					messageCount += child.getMessageCount(shape);
			}

			if (shape.contains(this.getPos()))
				messageCount++;
			return this.messageCount;
		}
	}

	public Hashtable getChildsAncestorMinDepth() {
		return childsAncestorMinDepth;
	}

	public boolean isAllChildsAncestorMinDepthReceived() {
		return this.getChildsAncestorMinDepth().keySet().size() == this
				.getChilds().size();
	}
	
	public int getHopCountToSink() {
		int hopCount = 0;
		NodeAttachment parent = this.getParent();
		
		while(parent!=null) {
			hopCount++;
			parent = parent.getParent();
		}
		return hopCount;
	}
	
	public NodeAttachment getRoot() {
		NodeAttachment cur = this;
		NodeAttachment parent = cur.getParent();
		
		while(parent!=null) {
			cur = parent;
			parent = parent.getParent();
			
		}
		return cur;	
	}


	public Vector getChildsAncestorMinDepthVector() {
		Vector result = new Vector();
		Iterator it = this.childsAncestorMinDepth.values().iterator();
		Integer childAncestorMinDepth;
		while (it.hasNext()) {
			childAncestorMinDepth = (Integer) it.next();
			result.add(childAncestorMinDepth);
		}
		return result;
	}

	public Vector getAncestorDepths() {
		Vector result = new Vector();
		NodeAttachment friend;

		for (int i = 0; i < this.getFriends().size(); i++) {
			friend = (NodeAttachment) this.getFriends().elementAt(i);
			result.add(new Integer(friend.getHopCount()));
		}
		return result;
	}

	public int getAncestorMinDepth() {
		Vector ancestorDepths = this.getAncestorDepths();
		if (!this.isLeaf())
			ancestorDepths.addAll(this.getChildsAncestorMinDepthVector());
		int minDepth = this.getHopCount();
		int depth;
		for (int i = 0; i < ancestorDepths.size(); i++) {
			depth = ((Integer) ancestorDepths.elementAt(i)).intValue();
			if (depth < minDepth) {
				minDepth = depth;
			}
		}
		return minDepth;
	}

	public boolean detectWhetherOrNotVertex() {
		Iterator it = this.childsAncestorMinDepth.values().iterator();
		Integer childAncestorMinDepth;
		while (it.hasNext()) {
			childAncestorMinDepth = (Integer) it.next();
			if (childAncestorMinDepth.intValue() >= this.getHopCount())
				return true;
		}
		return false;
	}

	public Vector getFriends() {
		Vector friends = new Vector();
		Vector nodes = this.getNeighbors();
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			na = (NodeAttachment) it.next();
			if (na != null && !this.isParentOf(na) && !na.isParentOf(this))
				friends.add(na);
		}
		return friends;
	}

	public boolean isBroadcasted() {
		return broadcasted;
	}

	public void setBroadcasted(boolean broadcasted) {
		this.broadcasted = broadcasted;
	}

	public int getTreeNodeCount() {
		if (this.treeNodeCount != -1)
			return this.treeNodeCount;

		NodeAttachment child;
		int totalCount = 0;

		if (this.isLeaf()) {
			this.treeNodeCount = 1;
		} else {
			for (int i = 0; i < this.childs.size(); i++) {
				child = (NodeAttachment) this.childs.elementAt(i);
				totalCount += child.getTreeNodeCount();
			}
			totalCount++;
			this.treeNodeCount = totalCount;
		}
		return this.treeNodeCount;
	}

	public int whichChildOfParent() {
		return this.parent.getChilds().indexOf(this);
	}

	public int formerSiblingAndHisDescendantNodesCount() {
		int k = this.whichChildOfParent();
		NodeAttachment sibling;
		int count = 0;

		for (int i = 0; i < k; i++) {
			sibling = (NodeAttachment) this.parent.getChilds().elementAt(i);
			count += sibling.getTreeNodeCount();
		}
		return count;
	}

	public void setTreeNodeCount(int treeNodeCount) {
		this.treeNodeCount = treeNodeCount;
	}

	public Object getCachedObject() {
		return cachedObject;
	}

	public void setCachedObject(Object cachedObject) {
		this.cachedObject = cachedObject;
	}

	public Hashtable getCachedObjectsFromChilds() {
		return cachedObjectsFromChilds;
	}

	public NodeAttachment(Node node, Algorithm algorithm, double energy,
			double radioRange) {
		super(node, algorithm, energy, radioRange);
	}

	public Hashtable getObjectsFromChilds() {
		return objectsFromChilds;
	}

	public int getReceivedQID() {
		return receivedQID;
	}

	public boolean hasFriend(NodeAttachment na) {
		if (this.isParentOf(na) || na.isParentOf(this))
			return false;
		if (!this.hasNeighbor(na))
			return false;
		else
			return true;
	}

	public boolean isParentOf(NodeAttachment na) {
		NodeAttachment child;
		for (int i = 0; i < this.childs.size(); i++) {
			child = (NodeAttachment) this.childs.elementAt(i);
			if (child.getNode().getId() == na.getNode().getId())
				return true;
		}
		return false;
	}

	public void setReceivedQID(int receivedQID) {
		this.receivedQID = receivedQID;
	}

	public boolean addChild(NodeAttachment child) {
		return childs.add(child);
	}

	public void storeObjectFromChild(NodeAttachment child, Object object) {
		this.objectsFromChilds.put(child, object);
	}

	public boolean isLeaf() {
		return this.getChilds().size() == 0;
	}

	public Vector getChilds() {
		return childs;
	}

	public NodeAttachment getParent() {
		return parent;
	}

	public void setParent(NodeAttachment parent) {
		this.parent = parent;
	}

	public int getHopCount() {
		return hopCount;
	}

	public void setHopCount(int hopCount) {
		this.hopCount = hopCount;
	}

	public NodeAttachment getNextNodeAttachmentToDest(Position dest) {
		return (NodeAttachment) this.getNextNeighborAttachmentToDest(dest);
	}

	public NodeAttachment getNextNodeAttachmentToDestInRect(Position dest,
			Rect rect) {
		return (NodeAttachment) this.getNextNeighborAttachmentToDestInRect(
				dest, rect);
	}

	public void updateCachedObjectFromChilds() {
		NodeAttachment child;
		Object object;

		for (int i = 0; i < this.childs.size(); i++) {
			child = (NodeAttachment) this.childs.elementAt(i);
			object = this.objectsFromChilds.get(child);
			if (object != Constants.NULLOBJECT) {
				this.cachedObjectsFromChilds.put(child, object);
			}
		}
	}
	
	public Rect caculateMBR() {
		if(this.mbr!=null) return this.mbr;
		if(this.isLeaf()) {
			this.mbr = new Rect(this.getPos().getX(), this.getPos().getX(),
					this.getPos().getY(), this.getPos().getY());
			return this.mbr;
		}
		else {
			NodeAttachment child;
			this.mbr = new Rect(this.getPos().getX(), this.getPos().getX(),
					this.getPos().getY(), this.getPos().getY());
			for(int i=0;i<this.childs.size();i++) {
				child = (NodeAttachment)this.childs.elementAt(i);
				this.mbr = Rect.combineMBR(this.mbr, child.caculateMBR());
			}
			return this.mbr;
		}
	}
	
	public Rect getMBR() {
		if(this.mbr!=null) return this.mbr;
		else return this.caculateMBR();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		NodeAttachment na;

		sb.append("NodeAttachment{");
		sb.append("ID{");
		sb.append(this.node.getId());
		sb.append("},");
		sb.append(this.getNode().getPos());
		sb.append(",neighbors{");
		for (Iterator it = this.neighbors.iterator(); it.hasNext();) {
			na = (NodeAttachment) it.next();
			sb.append(na.getNode().getId());
			sb.append(",");
		}
		sb.append("end");
		sb.append("}");
		sb.append("radioRange{");
		sb.append(this.getRadioRange());
		sb.append("},");
		sb.append("receivedQID{");
		sb.append(this.getReceivedQID());
		sb.append("},");
		sb.append("hopCount{");
		sb.append(this.getHopCount());
		sb.append("},");
		// sb.append("parent{");
		// sb.append(this.getParent());
		sb.append("}");
		if(this.mbr!=null) {
			sb.append(",MBR{");
			sb.append(this.getMBR());
			sb.append("}");
		}
		sb.append("}");
		return sb.toString();
	}
}
