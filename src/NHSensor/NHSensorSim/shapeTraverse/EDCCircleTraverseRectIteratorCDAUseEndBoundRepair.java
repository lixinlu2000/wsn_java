package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.BoundBoundInRect;
import NHSensor.NHSensorSim.shape.BoundCircleInRect;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.CircleBoundInRect;
import NHSensor.NHSensorSim.shape.CircleCircleInRect;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;

/*
 * circle traverse a rect region
 */
public class EDCCircleTraverseRectIteratorCDAUseEndBoundRepair extends
		TraverseRectByCircleIterator {
	private final double MINOR_DOUBLE = 0.00001;

	public EDCCircleTraverseRectIteratorCDAUseEndBoundRepair(Algorithm alg,
			int direction, Rect rect) {
		super(alg, direction, rect);
	}

	public Rect getInitialDestRect(NeighborAttachment root) {
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
			NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape)) {
			return null;
		}
		if (curNa == null && curShape == null) {
			return null;
		} else if (curNa != null && curShape == null) { // first call
			return null;
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				Shape nextShape = this.getNextShape(curNa, curShape);
				if (nextShape == null)
					return null;
				else {
					return this.nextNeighborAttachment(curNa, curShape);
				}
			}
		}
		return null;
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		if (curShape instanceof Rect) {
			return this.nextNeighborAttachment(curNa, (Rect) curShape);
		} else if (curShape instanceof CircleCircleInRect) {
			return this.nextNeighborAttachment(curNa,
					(CircleCircleInRect) curShape);
		} else if (curShape instanceof CircleBoundInRect) {
			return this.nextNeighborAttachment(curNa,
					(CircleBoundInRect) curShape);
		} else if (curShape instanceof BoundCircleInRect) {
			return this.nextNeighborAttachment(curNa,
					(BoundCircleInRect) curShape);
		} else if (curShape instanceof BoundBoundInRect) {
			return this.nextNeighborAttachment(curNa,
					(BoundBoundInRect) curShape);
		}

		else
			return null;
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, Vector nasInNextRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		if (nasInNextRect == null || nasInNextRect.size() == 0) {
			return null;
		}

		Collections.sort(nasInNextRect, new NodeDirectionDistanceComparator(
				this.direction, curNa.getPos()));
		NeighborAttachment nextNa = null, candidateNa, prevNa;
		boolean isNextNa;
		for (int i = 0; i < nasInNextRect.size(); i++) {
			candidateNa = (NeighborAttachment) nasInNextRect.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				prevNa = (NeighborAttachment) nasInNextRect.elementAt(j);
				if (prevNa.getPos().distance(candidateNa.getPos()) > radioRange) {
					isNextNa = false;
					break;
				}
			}
			//
			if (isNextNa) {
				nextNa = candidateNa;
			} else {
				break;
			}
		}

		if (nextNa == null)
			return null;
		else {
			if (nextNa.isAlive())
				return nextNa;
			else {
				int index = nasInNextRect.indexOf(nextNa);
				for (int i = index - 1; i >= 0; i--) {
					candidateNa = (NeighborAttachment) nasInNextRect
							.elementAt(i);
					if (candidateNa.isAlive())
						return candidateNa;
				}
				return null;
			}
		}
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			Rect curShape) {
		//
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Circle curNaRadioCircle = new Circle(curNa.getPos(), radioRange);
		BoundBoundInRect nextShapeCandidate = new BoundBoundInRect(curShape
				.getEndBound(this.getDirection()), ShapeUtil.bound(
				curNaRadioCircle, this.rect, direction), this.getRect(),
				this.direction);

		Shape nextShape = nextShapeCandidate;
		if (this.isEnd(nextShapeCandidate)) {
			nextShape = this.truncateShape(nextShapeCandidate);
		}

		Vector nas = curNa.getNeighbors(nextShape);
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = nextNeighborAttachment(curNa, nas);

			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			BoundBoundInRect newNextShape = new BoundBoundInRect(curShape
					.getEndBound(this.getDirection()), nextNa.getPos()
					.getBound(direction, this.MINOR_DOUBLE), this.getRect(),
					this.direction);
			nextShape = newNextShape;
			if (this.isEnd(newNextShape)) {
				nextShape = this.truncateShape(newNextShape);
			}
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, Rect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextNa();

	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, BoundBoundInRect curShape,
			boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape.toRect());
		return nextNaAndShape.getNextNa();

	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			CircleCircleInRect curShape) {
		//
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Circle curNaRadioCircle = new Circle(curNa.getPos(), radioRange);
		CircleBoundInRect nextShapeCandidate = new CircleBoundInRect(curShape
				.getEndCircle(), ShapeUtil.bound(curNaRadioCircle, this.rect,
				direction), this.getRect(), this.direction);
		Shape nextShape = nextShapeCandidate;
		if (this.isEnd(nextShapeCandidate)) {
			nextShape = this.truncateShape(nextShapeCandidate);
		}

		Vector nas = curNa.getNeighbors(nextShape);
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = nextNeighborAttachment(curNa, nas);

			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			CircleBoundInRect newNextShape = new CircleBoundInRect(curShape
					.getEndCircle(), nextNa.getPos().getBound(direction,
					this.MINOR_DOUBLE), this.getRect(), this.direction);
			nextShape = newNextShape;
			if (this.isEnd(newNextShape)) {
				nextShape = this.truncateShape(newNextShape);
			}
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, CircleCircleInRect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextNa();
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			CircleBoundInRect curShape) {
		//
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Circle curNaRadioCircle = new Circle(curNa.getPos(), radioRange);
		BoundBoundInRect nextShapeCandidate = new BoundBoundInRect(curShape
				.getEndBound(), ShapeUtil.bound(curNaRadioCircle, this.rect,
				direction), this.getRect(), this.direction);
		Shape nextShape = nextShapeCandidate;
		if (this.isEnd(nextShapeCandidate)) {
			nextShape = this.truncateShape(nextShapeCandidate);
		}

		Vector nas = curNa.getNeighbors(nextShape);
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = nextNeighborAttachment(curNa, nas);

			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			BoundBoundInRect newNextShape = new BoundBoundInRect(curShape
					.getEndBound(), nextNa.getPos().getBound(direction,
					this.MINOR_DOUBLE), this.getRect(), this.direction);
			nextShape = newNextShape;
			if (this.isEnd(newNextShape)) {
				nextShape = this.truncateShape(newNextShape);
			}
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, CircleBoundInRect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextNa();

	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			BoundCircleInRect curShape) {
		//
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Circle curNaRadioCircle = new Circle(curNa.getPos(), radioRange);
		CircleBoundInRect nextShapeCandidate = new CircleBoundInRect(curShape
				.getEndCircle(), ShapeUtil.bound(curNaRadioCircle, this.rect,
				direction), this.getRect(), this.direction);
		Shape nextShape = nextShapeCandidate;
		if (this.isEnd(nextShapeCandidate)) {
			nextShape = this.truncateShape(nextShapeCandidate);
		}

		Vector nas = curNa.getNeighbors(nextShape);
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = nextNeighborAttachment(curNa, nas);

			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			CircleBoundInRect newNextShape = new CircleBoundInRect(curShape
					.getEndCircle(), nextNa.getPos().getBound(direction,
					this.MINOR_DOUBLE), this.getRect(), this.direction);
			nextShape = newNextShape;
			if (this.isEnd(newNextShape)) {
				nextShape = this.truncateShape(newNextShape);
			}
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, BoundCircleInRect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextNa();
	}

	protected CircleBoundInRect truncateShape(CircleBoundInRect curShape) {
		if (curShape != null) {
			switch (this.direction) {
			case Direction.LEFT:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMinX(), this.rect, this.direction);
			case Direction.RIGHT:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMaxX(), this.rect, this.direction);
			case Direction.DOWN:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMinY(), this.rect, this.direction);
			case Direction.UP:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMaxY(), this.rect, this.direction);
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	protected Rect truncateShape(BoundCircleInRect curShape) {
		if (curShape != null) {
			switch (this.direction) {
			case Direction.LEFT:
				return new Rect(this.rect.getMinX(), curShape.getBeginBound(),
						this.rect.getMinY(), this.rect.getMaxY());
			case Direction.RIGHT:
				return new Rect(curShape.getBeginBound(), this.rect.getMaxX(),
						this.rect.getMinY(), this.rect.getMaxY());
			case Direction.DOWN:
				return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
						this.rect.getMinY(), curShape.getBeginBound());
			case Direction.UP:
				return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
						curShape.getBeginBound(), this.rect.getMaxY());
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	protected Rect truncateShape(BoundBoundInRect curShape) {
		if (curShape != null) {
			switch (this.direction) {
			case Direction.LEFT:
				return new Rect(this.rect.getMinX(), curShape.getBeginBound(),
						this.rect.getMinY(), this.rect.getMaxY());
			case Direction.RIGHT:
				return new Rect(curShape.getBeginBound(), this.rect.getMaxX(),
						this.rect.getMinY(), this.rect.getMaxY());
			case Direction.DOWN:
				return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
						this.rect.getMinY(), curShape.getBeginBound());
			case Direction.UP:
				return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
						curShape.getBeginBound(), this.rect.getMaxY());
			default:
				return null;
			}
		} else {
			return null;
		}

	}

	protected CircleBoundInRect truncateShape(CircleCircleInRect curShape) {
		if (curShape != null) {
			switch (this.direction) {
			case Direction.LEFT:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMinX(), curShape.getRect(), this.direction);
			case Direction.RIGHT:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMaxX(), curShape.getRect(), this.direction);
			case Direction.DOWN:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMinY(), curShape.getRect(), this.direction);
			case Direction.UP:
				return new CircleBoundInRect(curShape.getBeginCircle(),
						this.rect.getMaxY(), curShape.getRect(), this.direction);
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape))
			return null;
		if (curNa != null && curShape == null) {
			return this.getInitialDestRect(curNa);
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return this.nextShape(curShape);
			} else {
				return this.nextShape(curNa, curShape, false);
			}
		}
		return null;
	}

	protected Shape nextShape(Shape curShape) {
		if (curShape instanceof Rect) {
			return this.nextShape((Rect) curShape);
		} else if (curShape instanceof CircleCircleInRect) {
			return this.nextShape((CircleCircleInRect) curShape);
		} else if (curShape instanceof CircleBoundInRect) {
			return this.nextShape((CircleCircleInRect) curShape);
		} else if (curShape instanceof BoundCircleInRect) {
			return this.nextShape((CircleCircleInRect) curShape);
		} else if (curShape instanceof BoundBoundInRect) {
			return this.nextShape((BoundBoundInRect) curShape);
		}

		else
			return null;
	}

	protected Shape nextShape(CircleCircleInRect curShape) {
		Rect rect = curShape.getMaxRectContained();
		return this.nextShape(rect);
	}

	protected Shape nextShape(CircleBoundInRect curShape) {
		Rect rect = curShape.getMaxRectContained();
		return this.nextShape(rect);
	}

	protected Shape nextShape(BoundCircleInRect curShape) {
		Rect rect = curShape.getMaxRectContained();
		return this.nextShape(rect);
	}

	protected Shape nextShape(Rect curRect) {
		// TODO
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

	protected Shape nextShape(BoundBoundInRect curShape) {
		return this.nextShape(curShape.toRect());
	}

	protected Shape nextShape(NeighborAttachment curNa, Shape curShape,
			boolean isOptimize) {
		if (curShape instanceof Rect) {
			return this.nextShape(curNa, (Rect) curShape);
		} else if (curShape instanceof CircleCircleInRect) {
			return this.nextShape(curNa, (CircleCircleInRect) curShape);
		} else if (curShape instanceof CircleBoundInRect) {
			return this.nextShape(curNa, (CircleBoundInRect) curShape);
		} else if (curShape instanceof BoundCircleInRect) {
			return this.nextShape(curNa, (BoundCircleInRect) curShape);
		} else if (curShape instanceof BoundBoundInRect) {
			return this.nextShape(curNa, (BoundBoundInRect) curShape);
		} else
			return null;
	}

	protected Shape nextShape(NeighborAttachment curNa, Rect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextShape();
	}

	protected Shape nextShape(NeighborAttachment curNa,
			BoundBoundInRect curShape) {
		return this.nextShape(curNa, curShape);
	}

	/*
	 * to get rid of node in the boundary of the rectangle
	 */
	public Rect minRectContainNode(Rect maxNextRect, int direction,
			NeighborAttachment na) {
		switch (direction) {
		case Direction.LEFT:
			return new Rect(na.getNode().getPos().getX() - 0.01, maxNextRect
					.getMaxX(), maxNextRect.getMinY(), maxNextRect.getMaxY());
		case Direction.RIGHT:
			return new Rect(maxNextRect.getMinX(),
					na.getNode().getPos().getX() + 0.01, maxNextRect.getMinY(),
					maxNextRect.getMaxY());
		case Direction.UP:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					maxNextRect.getMinY(), na.getNode().getPos().getY() + 0.01);
		case Direction.DOWN:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(), na
					.getNode().getPos().getY() - 0.01, maxNextRect.getMaxY());
		default:
			return null;
		}
	}

	protected Shape nextShape(NeighborAttachment curNa,
			CircleCircleInRect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextShape();

	}

	protected Shape nextShape(NeighborAttachment curNa,
			CircleBoundInRect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextShape();

	}

	protected Shape nextShape(NeighborAttachment curNa,
			BoundCircleInRect curShape) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape);
		return nextNaAndShape.getNextShape();

	}

	public boolean isEnd(NeighborAttachment na, Shape curShape) {
		return this.isEnd(curShape);
	}

	public boolean isEnd(Shape curShape) {
		Position p1, p2;

		if (curShape != null) {
			switch (this.direction) {
			case Direction.LEFT:
				p1 = this.rect.getLB();
				p2 = this.rect.getLT();
				break;
			case Direction.RIGHT:
				p1 = this.rect.getRB();
				p2 = this.rect.getRT();
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
			return curShape.contains(p1) && curShape.contains(p2);
		} else {
			return false;
		}
	}
}
