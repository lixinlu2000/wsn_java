package NHSensor.NHSensorSim.core;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.shape.Position;

public class Node implements Serializable{
	private int id;
	private Position pos;
	private double radioRange = 10;
	private double initialEnergy = 100;
	
	// private Network network;
	private Hashtable attachments = new Hashtable();
	// radio chip
	boolean alive = true;
	boolean destroy = false;
	Vector neighborIDs = new Vector();

	public Node() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node(int id, double x, double y) {
		this.id = id;
		this.pos = new Position(x, y);
	}

	public Node(int id, Position pos) {
		this.id = id;
		this.pos = pos;
		// this.network = network;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public void attach(String name, Attachment attachment) {
		if (this.attachments.get(name) != null) {
			return;
		}
		this.attachments.put(name, attachment);
	}

	public Attachment getAttachment(String algName) {
		return (Attachment) this.attachments.get(algName);
	}

	public Hashtable getAttachments() {
		return attachments;
	}

	public void setAttachments(Hashtable attachments) {
		this.attachments = attachments;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public void destroy() {
		this.destroy = true;
	}

	public Vector getNeighborIDs() {
		return neighborIDs;
	}
	
	public void addNeighbor(int id) {
		if(!this.neighborIDs.contains(id)) 
			this.neighborIDs.add(id);
	}

	public void setNeighborIDs(Vector neighborIDs) {
		this.neighborIDs = neighborIDs;
	}
	
	public void clearNeighborIDS() {
		this.neighborIDs.clear();
	}
	
	public boolean equals(Object obj) {
		Node other = (Node)obj;
		if(other==this)return true;
		else if(other.getPos().getX()==this.getPos().getX()&&
				other.getPos().getY()==this.getPos().getY()) {
			return true;
		}
		else return false;
	}
	
	

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public double getInitialEnergy() {
		return initialEnergy;
	}

	public void setInitialEnergy(double initialEnergy) {
		this.initialEnergy = initialEnergy;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Node>");
		sb.append("<ID>");
		sb.append(this.id);
		sb.append("</ID>");
		sb.append(this.pos);
		sb.append("<radioRange>");
		sb.append(this.radioRange);
		sb.append("</radioRange>");
		sb.append("<initialEnergy>");
		sb.append(this.initialEnergy);
		sb.append("</initialEnergy>");
		sb.append("</Node>");
		return sb.toString();
	}
}
