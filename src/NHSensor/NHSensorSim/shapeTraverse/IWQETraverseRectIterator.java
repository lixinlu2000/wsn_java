package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * iwqe traverse a rect region
 */
public class IWQETraverseRectIterator extends TraverseRectIterator {
	static Logger logger = Logger.getLogger(IWQETraverseRectIterator.class);

	public IWQETraverseRectIterator(Algorithm alg, int direction, Rect rect) {
		super(alg, direction, rect);
	}

	public Rect getInitialDestRect() {
		double width, height;
		double radioRange = this.alg.getParam().getRADIO_RANGE();

		switch (this.direction) {
		case Direction.LEFT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			return new Rect(this.rect.getMaxX() - width, this.rect.getMaxX(),
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.RIGHT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			return new Rect(this.rect.getMinX(), this.rect.getMinX() + width,
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.DOWN:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), this.rect
					.getMaxY()
					- height, this.rect.getMaxY());
		case Direction.UP:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), this.rect
					.getMinY(), this.rect.getMinY() + height);
		default:
			return null;
		}

	}

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Rect curRect) {
		if (this.isEnd(curNa, curRect)) {
			return null;
		}

		if (curNa == null) {
			return null;
		} else {
			if (curRect != null) {
				if (curRect.contains(curNa.getNode().getPos())) {
					NeighborAttachment next = this.getNextByMPI(curNa);
					if (next == curNa)
						return null;
					else
						return next;
				} else
					return null;
			} else {
				NeighborAttachment next = this.getNextByMPI(curNa);
				if (next == curNa)
					return null;
				else
					return next;
			}
		}
	}

	// get next neighbor by Most Progress on Itinerary
	public NeighborAttachment getNextByMPI(NeighborAttachment cur) {
		NeighborAttachment na = null;
		Vector neighbors = cur.getNeighbors();
		double maxAdvance = 0;
		double neighborAdvance = 0;
		NeighborAttachment maxAdvanceNeighbor = cur;

		for (int i = 0; i < neighbors.size(); i++) {
			na = (GPSRAttachment) neighbors.elementAt(i);
			if (na.getNode().getPos().in(this.rect)) {
				switch (this.direction) {
				case Direction.LEFT:
					neighborAdvance = cur.getNode().getPos().getX()
							- na.getNode().getPos().getX();
					if (neighborAdvance > maxAdvance) {
						maxAdvance = neighborAdvance;
						maxAdvanceNeighbor = na;
					}
					break;
				case Direction.RIGHT:
					neighborAdvance = na.getNode().getPos().getX()
							- cur.getNode().getPos().getX();
					if (neighborAdvance > maxAdvance) {
						maxAdvance = neighborAdvance;
						maxAdvanceNeighbor = na;
					}
					break;
				case Direction.UP:
					neighborAdvance = na.getNode().getPos().getY()
							- cur.getNode().getPos().getY();
					if (neighborAdvance > maxAdvance) {
						maxAdvance = neighborAdvance;
						maxAdvanceNeighbor = na;
					}
					break;
				case Direction.DOWN:
					neighborAdvance = cur.getNode().getPos().getY()
							- na.getNode().getPos().getY();
					if (neighborAdvance > maxAdvance) {
						maxAdvance = neighborAdvance;
						maxAdvanceNeighbor = na;
					}
					break;
				}
			}
		}
		return maxAdvanceNeighbor;
	}

	public Rect getNextRect(NeighborAttachment curNa, Rect curRect) {
		if (this.isEnd(curNa, curRect))
			return null;

		if (curNa == null) {
			return null;
		} else {
			if (curRect != null) {
				if (curRect.contains(curNa.getNode().getPos())) {
					NeighborAttachment next = this.getNextByMPI(curNa);
					if (next == curNa) {
						return this.nextRect(curRect);
					} else
						return null;
				} else {
					return this.nextRect(curRect);
				}
			} else {
				NeighborAttachment next = this.getNextByMPI(curNa);
				if (next == curNa) {
					return this.nextRect(curNa);
				} else
					return null;
			}
		}
	}

	public Rect nextRect(Rect curRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double width, height;
		switch (this.direction) {
		case Direction.LEFT:
			height = curRect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			return curRect.leftNeighbour(width, this.rect.getMinX());
		case Direction.RIGHT:
			height = curRect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			return curRect.rightNeighbour(width, this.rect.getMaxX());
		case Direction.UP:
			width = curRect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			return curRect.topNeighbour(height, this.rect.getMaxY());
		case Direction.DOWN:
			width = curRect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			return curRect.downNeighbour(height, this.rect.getMinY());
		default:
			return null;
		}
	}

	public Rect nextRect(NeighborAttachment cur) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double width, height;
		Rect rect;
		double x = cur.getNode().getPos().getX();
		double y = cur.getNode().getPos().getY();

		switch (this.direction) {
		case Direction.LEFT:
			height = this.rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			rect = new Rect(x - 0.01, x - 0.01, this.rect.getMinY(), this.rect
					.getMaxY());
			return rect.leftNeighbour(width, this.rect.getMinX());
		case Direction.RIGHT:
			height = this.rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			rect = new Rect(x + 0.01, x + 0.01, this.rect.getMinY(), this.rect
					.getMaxY());
			return rect.rightNeighbour(width, this.rect.getMaxX());
		case Direction.UP:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			rect = new Rect(this.rect.getMinX(), this.rect.getMaxX(), y + 0.01,
					y + 0.01);
			return rect.topNeighbour(height, this.rect.getMaxY());
		case Direction.DOWN:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			rect = new Rect(this.rect.getMinX(), this.rect.getMaxX(), y - 0.01,
					y - 0.01);
			return rect.downNeighbour(height, this.rect.getMinY());
		default:
			return null;
		}
	}

	public boolean isEnd(NeighborAttachment na, Rect curRect) {
		if (curRect != null) {
			switch (this.direction) {
			case Direction.LEFT:
				return curRect.getMinX() <= this.rect.getMinX();
			case Direction.RIGHT:
				return curRect.getMaxX() >= this.rect.getMaxX();
			case Direction.DOWN:
				return curRect.getMinY() <= this.rect.getMinY();
			case Direction.UP:
				return curRect.getMaxY() >= this.rect.getMaxY();
			default:
				return true;
			}
		} else {
			return this.isEnd(na);
		}
	}

	public boolean isEnd(NeighborAttachment cur) {
		Position p1, p2;
		Position curPos = cur.getNode().getPos();
		double radioRange = cur.getRadioRange();

		switch (this.direction) {
		case Direction.LEFT:
			p1 = this.rect.getLT();
			p2 = this.rect.getLB();
			break;
		case Direction.RIGHT:
			p1 = this.rect.getRT();
			p2 = this.rect.getRB();
			break;
		case Direction.DOWN:
			p1 = this.rect.getLB();
			p2 = this.rect.getRB();
			break;
		case Direction.UP:
			p1 = this.rect.getLT();
			p2 = this.rect.getRT();
			break;
		default:
			return true;
		}

		if (p1.distance(curPos) <= radioRange
				&& p2.distance(curPos) <= radioRange) {
			return true;
		} else {
			return false;
		}
	}

}
