package NHSensor.NHSensorSim.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.events.CollectNeighborLocationsEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;

public class NeighborAttachment extends Attachment {
	protected double radioRange;
	protected Vector neighbors = null;
	protected boolean neighborsCaching = true;
	protected Hashtable radioRangeNeighborsMap = new Hashtable();
	protected boolean hasCollectNeighborLocations = false;

	public NeighborAttachment(Node node, Algorithm algorithm, double energy,
			double radioRange) {
		super(node, algorithm, energy);
		this.radioRange = radioRange;
		// TODO Auto-generated constructor stub
	}

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public Vector getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Vector neighbors) {
		this.neighbors = neighbors;
	}

	public Position getPos() {
		return this.getNode().getPos();
	}
	
	public boolean hasNeighborInShape(Shape shape) {
		Vector neighborsInShape = this.getNeighbors(shape);
		if(neighborsInShape.size()==0)return false;
		else return true;
	}

	public boolean hasNeighbor(NeighborAttachment na) {
		NeighborAttachment na1;
		for (int i = 0; i < this.neighbors.size(); i++) {
			na1 = (NeighborAttachment) this.neighbors.elementAt(i);
			if (na1.getNode().getId() == na.getNode().getId())
				return true;
		}
		return false;
	}

	/*
	 * changed since 2010.7.1
	 */
	public Vector getNeighborsOld(double range) {
		Vector nodes = this.getAlgorithm().getNetwork().getNodes();
		Node neighbor;
		NeighborAttachment na;
		Vector nbs = new Vector();

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			neighbor = (Node) it.next();
			if (neighbor != this.node
					&& Position.distance(this.node.getPos(), neighbor.getPos()) <= range) {
				na = (NeighborAttachment) neighbor.getAttachment(this
						.getAlgorithm().getName());
				if (na != null)
					nbs.add(na);
			}
		}
		return nbs;
	}

	/*
	 * 2010-12-15 change getNeighborsOld1 to getNeighbors
	 */
	public Vector getNeighborsOld1(double range) {
		Vector nodes = this.getAlgorithm().getNetwork().getNodes();
		Node neighbor;
		NeighborAttachment na;
		Vector nbs = new Vector();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			neighbor = (Node) it.next();
			if (neighbor != this.node
					&& Position.distance(this.node.getPos(), neighbor.getPos()) <= range) {
				na = (NeighborAttachment) neighbor.getAttachment(this
						.getAlgorithm().getName());
				if (na != null && !na.getNode().isDestroy()) {
					nbs.add(na);
				}
			}
		}
		return nbs;
	}

	public Vector getNeighbors(double range) {
		if (this.neighborsCaching) {
			Vector nbs = (Vector) radioRangeNeighborsMap.get(new Double(range));
			if (nbs != null) {
				return nbs;
			}
		}

		Vector nodes = this.getAlgorithm().getNetwork().getNodes();
		Node neighbor;
		NeighborAttachment na;
		Vector nbs = new Vector();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			neighbor = (Node) it.next();
			if (neighbor != this.node
					&& Position.distance(this.node.getPos(), neighbor.getPos()) <= range) {
				na = (NeighborAttachment) neighbor.getAttachment(this
						.getAlgorithm().getName());
				if (na != null && !na.getNode().isDestroy()) {
					nbs.add(na);
				}
			}
		}

		if (this.neighborsCaching) {
			radioRangeNeighborsMap.put(new Double(range), nbs);
		}
		return nbs;
	}

	public Vector getNeighbors(double range, Condition nac) {
		Vector nodes = this.getNeighbors(range);
		NeighborAttachment na;
		Vector nbs = new Vector();

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			na = (NeighborAttachment) it.next();
			if (na != null && nac.isTrue(na))
				nbs.add(na);
		}
		return nbs;

	}

	public Vector getNodes(double range, Condition nac) {
		Vector nodes = this.getNeighbors(range, nac);
		if (nac.isTrue(this))
			nodes.add(this);
		return nodes;
	}

	public Vector getNeighbors(Condition nac) {
		return this.getNeighbors(this.radioRange, nac);
	}

	public Vector getNeighbors(Shape shape) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(shape);
		return this.getNeighbors(nairc);
	}

	public Vector getNeighbors(double range, Shape shape) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(shape);
		return this.getNeighbors(range, nairc);
	}

	public void initNeighbors() {
		Node node = this.getNode();
		if (node.getNeighborIDs() == null || node.neighborIDs.isEmpty()) {
			this.neighbors = this.getNeighbors(this.radioRange);
		} else {
			this.initNeighborsByNeighborIDs();
		}
	}

	public void initNeighborsByNeighborIDs() {
		Node node = this.getNode();
		Network network = this.getAlgorithm().getNetwork();
		Vector neighborsByNeighborIDs = node.getNeighborIDs();
		Integer neighbor;
		NeighborAttachment na;
		Vector nbs = new Vector();
		for (Iterator it = neighborsByNeighborIDs.iterator(); it.hasNext();) {
			neighbor = (Integer) it.next();
			na = (NeighborAttachment) network.getNode(neighbor.intValue())
					.getAttachment(this.getAlgorithm().getName());
			if (na != null && !na.getNode().isDestroy()) {
				nbs.add(na);
			}
		}
		this.neighbors = nbs;
	}

	public NeighborAttachment getNextNeighborAttachmentInShapeNearPos(Shape shape, Position pos) {
		Vector neighborsInShape = this.getNeighbors(shape);
		double min = Double.MAX_VALUE;
		double distance;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (int i=0;i<neighborsInShape.size();i++) {
			tmp = (NeighborAttachment) neighborsInShape.elementAt(i);
			distance = Position.distance(tmp.getNode().getPos(), pos);
			if (distance < min) {
				min = distance;
				next = tmp;
			}
		}
		return next;
	}
	

	public NeighborAttachment getNextNeighborAttachmentInShapeFarFromPos(Shape shape, Position pos,
			double radian) {
		Vector neighborsInShape = this.getNeighbors(shape);
		if(shape.contains(this.getPos())) 
			neighborsInShape.add(this);

		double max = Double.MIN_VALUE;
		double distance;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (int i=0;i<neighborsInShape.size();i++) {
			tmp = (NeighborAttachment) neighborsInShape.elementAt(i);
			distance = pos.distance(tmp.getNode().getPos(), radian);
			if (distance > max) {
				max = distance;
				next = tmp;
			}
		}
		return next;
	}

	public NeighborAttachment getNextNeighborAttachmentToDest(Position dest) {
		this.collectNeighborLocations();

		double min = Position.distance(this.getNode().getPos(), dest);
		double distance;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = Position.distance(tmp.getNode().getPos(), dest);
			if (distance < min) {
				min = distance;
				next = tmp;
			}
		}
		return next;
	}

	public NeighborAttachment getNextNeighborAttachmentToDestFailureAware(
			Position dest) {
		//
		this.collectNeighborLocations();
		//
		double min = Position.distance(this.getNode().getPos(), dest);
		double distance;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = Position.distance(tmp.getNode().getPos(), dest);
			if (distance < min && tmp.isAlive()) {
				min = distance;
				next = tmp;
			}
		}
		return next;
	}

	/*
	 * This function is used for link aware routing
	 */
	public NeighborAttachment getNextNeighborAttachmentToDestLinkAware(
			Position dest) {
		double curDist = Position.distance(this.getNode().getPos(), dest);
		double maxBenefit = -Double.MAX_VALUE;
		double distance;
		double advanceDistance;
		double benefit;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = Position.distance(tmp.getNode().getPos(), dest);
			advanceDistance = curDist - distance;
			benefit = advanceDistance * this.getLinkQuality(tmp);
			if (benefit > maxBenefit) {
				maxBenefit = benefit;
				next = tmp;
			}
		}

		if (maxBenefit <= 0)
			return this;
		else
			return next;
	}

	/*
	 * This function is used for link aware routing to a shape
	 */
	public NeighborAttachment getNextNeighborAttachmentToShapeLinkAwareOld(
			Shape shape) {
		double curDist = Shape.minDistance(this.getNode().getPos(), shape);
		double maxBenefit = -Double.MAX_VALUE;
		double distance;
		double advanceDistance;
		double benefit;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = Shape.minDistance(tmp.getNode().getPos(), shape);
			advanceDistance = curDist - distance;
			benefit = advanceDistance * this.getLinkQuality(tmp);
			if (benefit > maxBenefit) {
				maxBenefit = benefit;
				next = tmp;
			}
		}

		if (maxBenefit <= 0)
			return this;
		else
			return next;
	}

	/*
	 * Changed since 2010.12.17
	 */
	public NeighborAttachment getNextNeighborAttachmentToShapeLinkAware(
			Shape shape) {
		double curDist = this.getNode().getPos().distance(shape.getCentre());
		double maxBenefit = -Double.MAX_VALUE;
		double distance;
		double advanceDistance;
		double benefit;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = tmp.getNode().getPos().distance(shape.getCentre());
			advanceDistance = curDist - distance;
			benefit = advanceDistance * this.getLinkQuality(tmp);
			if (benefit > maxBenefit) {
				maxBenefit = benefit;
				next = tmp;
			}
		}

		if (maxBenefit <= 0)
			return this;
		else
			return next;
	}
	
	public NeighborAttachment getNextNeighborAttachmentToShapeLinkAware(
			Shape shape, int direction) {
		double curDist = this.getNode().getPos().distance(shape.getCentre());
		double maxBenefit = -Double.MAX_VALUE;
		double distance;
		double advanceDistance;
		double benefit;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = tmp.getNode().getPos().distance(shape.getCentre());
			advanceDistance = curDist - distance;
			benefit = advanceDistance * this.getLinkQuality(tmp);
			if (benefit > maxBenefit) {
				maxBenefit = benefit;
				next = tmp;
			}
		}

		if (maxBenefit <= 0)
			return this;
		else
			return next;
	}


	/*
	 * This function is used for link aware routing to a shape
	 */
	public NeighborAttachment getNextNeighborAttachmentToShape(Shape shape) {
		double curDist = Shape.minDistance(this.getNode().getPos(), shape);
		double maxBenefit = -Double.MAX_VALUE;
		double distance;
		double advanceDistance;
		double benefit;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			distance = Shape.minDistance(tmp.getNode().getPos(), shape);
			advanceDistance = curDist - distance;
			benefit = advanceDistance;
			if (benefit > maxBenefit) {
				maxBenefit = benefit;
				next = tmp;
			}
		}

		if (maxBenefit <= 0)
			return this;
		else
			return next;
	}

	public NeighborAttachment getNextNeighborAttachmentToDestInRect(
			Position dest, Rect rect) {
		double min = Position.distance(this.getNode().getPos(), dest);
		double distance;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		for (Iterator it = this.getNeighbors().iterator(); it.hasNext();) {
			tmp = (NeighborAttachment) it.next();
			if (!tmp.getNode().getPos().in(rect))
				continue;

			distance = Position.distance(tmp.getNode().getPos(), dest);
			if (distance < min) {
				min = distance;
				next = tmp;
			}
		}
		return next;
	}
	
	public void collectNeighborLocations(Shape shape) {
		if(!GlobalConstants.getInstance().isOnDemandCollectNeighborLocations()) {
			return ;
		}
		
		if(this.hasCollectNeighborLocations) {
			return;
		}
		CollectNeighborLocationsEvent cnle = new CollectNeighborLocationsEvent(this,  shape, this.getAlgorithm());
		try {
			this.getAlgorithm().getSimulator().handle(cnle);
			this.hasCollectNeighborLocations = true;
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void collectNeighborLocations() {
		if(!GlobalConstants.getInstance().isOnDemandCollectNeighborLocations()) {
			return ;
		}
		
		if(this.hasCollectNeighborLocations) {
			return;
		}
		CollectNeighborLocationsEvent cnle = new CollectNeighborLocationsEvent(this, this.getAlgorithm());
		try {
			this.getAlgorithm().getSimulator().handle(cnle);
			this.hasCollectNeighborLocations = true;
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NeighborAttachment getNextNeighborAttachmentToDestInShape(
			Position dest, Shape shape) {
		//
		this.collectNeighborLocations();
		//
		
		double min = Position.distance(this.getNode().getPos(), dest);
		double distance;
		NeighborAttachment tmp;
		NeighborAttachment next = this;

		Vector neighborsInShape = this.getNeighbors(shape);
		Vector neighbors = this.getNeighbors();

		Circle circle = new Circle(this.getPos(), this.getRadioRange());
		if (circle.contains(shape) && neighborsInShape.isEmpty())
			return next;

		if (neighborsInShape.size() > 0) {
			min = Double.MAX_VALUE;
			for (Iterator it = neighborsInShape.iterator(); it.hasNext();) {
				tmp = (NeighborAttachment) it.next();

				distance = Position.distance(tmp.getNode().getPos(), dest);
				if (distance < min) {
					min = distance;
					next = tmp;
				}
			}
			return next;
		} else {
			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				tmp = (NeighborAttachment) it.next();

				distance = Position.distance(tmp.getNode().getPos(), dest);
				if (distance < min) {
					min = distance;
					next = tmp;
				}
			}
			return next;
		}
	}

}
