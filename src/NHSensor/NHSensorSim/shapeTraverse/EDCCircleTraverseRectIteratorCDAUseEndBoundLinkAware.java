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
public class EDCCircleTraverseRectIteratorCDAUseEndBoundLinkAware extends
		TraverseRectByCircleIterator implements TraverseShapeOptimizeIterator {
	private final double MINOR_DOUBLE = 0.00001;
	private double averageAnswerSentTimesThrehold = 10;

	public EDCCircleTraverseRectIteratorCDAUseEndBoundLinkAware(Algorithm alg,
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
					return this.nextNeighborAttachment(curNa, curShape, false);
				}
			}
		}
		return null;
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape, boolean isOptimize) {
		if (curShape instanceof Rect) {
			return this.nextNeighborAttachment(curNa, (Rect) curShape,
					isOptimize);
		} else if (curShape instanceof CircleCircleInRect) {
			return this.nextNeighborAttachment(curNa,
					(CircleCircleInRect) curShape, isOptimize);
		} else if (curShape instanceof CircleBoundInRect) {
			return this.nextNeighborAttachment(curNa,
					(CircleBoundInRect) curShape, isOptimize);
		} else if (curShape instanceof BoundCircleInRect) {
			return this.nextNeighborAttachment(curNa,
					(BoundCircleInRect) curShape, isOptimize);
		} else if (curShape instanceof BoundBoundInRect) {
			return this.nextNeighborAttachment(curNa,
					(BoundBoundInRect) curShape, isOptimize);
		}

		else
			return null;
	}

	protected double viaNodeCostNoOptimize(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;

		for (int i = 0; i <= nextNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			if (i != nextNaIndex)
				collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
						* na.getSendTimes(nextNa);
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
				* curNa.getSendTimes(nextNa);
		totalCost += collectAnswerCost;
		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;
		totalCost /= (nextNaIndex + 1);
		return totalCost;
	}

	protected double viaNodeCost(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, boolean isOptimize) {
		if (isOptimize)
			return this.viaNodeCostOptimize(curNa, sortedNasInMaxNextShape,
					nextNaIndex);
		else
			return this.viaNodeCostNoOptimize(curNa, sortedNasInMaxNextShape,
					nextNaIndex);
	}

	protected double viaNodeCostOptimize(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;

		int collectedNodeNum = 0;
		for (int i = 0; i <= nextNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			if (na != nextNa && na != curNa) {
				collectedNodeNum++;
				collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
						* na.getSendTimes(nextNa);
			}
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		if (curNa != nextNa) {
			collectedNodeNum++;
			collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
					* curNa.getSendTimes(nextNa);
		}
		totalCost += collectAnswerCost;

		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;

		if (collectedNodeNum == 0)
			return 0;
		else {
			totalCost /= collectedNodeNum;
			return totalCost;
		}
	}

	protected double viaNodeTimes(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, boolean isOptimize) {
		double totalCost;
		if (isOptimize)
			totalCost = this.viaNodeCostOptimize(curNa,
					sortedNasInMaxNextShape, nextNaIndex);
		else
			totalCost = this.viaNodeCostNoOptimize(curNa,
					sortedNasInMaxNextShape, nextNaIndex);
		return totalCost / this.alg.getParam().getANSWER_SIZE();
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			Rect curShape, boolean isOptimize) {
		//
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		nasInCurShape.add(curNa);
		Collections.sort(nasInCurShape,
				new NodeDirectionDistanceHasDirectionComparator(this.direction,
						curNa.getPos()));
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
		Collections.sort(nas, new NodeDirectionDistanceHasDirectionComparator(
				this.direction, curNa.getPos()));
		if (isOptimize) {
			nasInCurShape.addAll(nas);
			nas = nasInCurShape;
		}
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = null, candidateNa, prevNa;
			boolean isNextNa;
			double minCost = Double.MAX_VALUE;
			double cost;
			for (int i = 0; i < nas.size(); i++) {
				candidateNa = (NeighborAttachment) nas.elementAt(i);
				isNextNa = true;
				for (int j = 0; j < i; j++) {
					prevNa = (NeighborAttachment) nas.elementAt(j);
					if (prevNa.getPos().distance(candidateNa.getPos()) > radioRange) {
						isNextNa = false;
						break;
					}
				}

				if (isNextNa) {
					cost = this.viaNodeCost(curNa, nas, i, isOptimize);
					if (cost < minCost) {
						nextNa = candidateNa;
						minCost = cost;
					}
				}
			}
			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			double averageAnwerSentTimes = this.viaNodeTimes(curNa, nas, nas
					.indexOf(nextNa), isOptimize);
			if (!isOptimize
					&& averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				BoundBoundInRect ns = new BoundBoundInRect(curShape
						.getEndBound(this.getDirection()), na.getPos()
						.getBound(direction, -MINOR_DOUBLE), this.getRect(),
						this.direction);
				nextShape = ns.toRect();
				if (this.isEnd(ns)) {
					nextShape = this.truncateShape(ns);
				}
				return new NextNaAndShape(null, nextShape);
			}

			if (isOptimize
					&& averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				BoundBoundInRect ns = new BoundBoundInRect(curShape
						.getBeginBound(this.getDirection()), na.getPos()
						.getBound(this.direction, MINOR_DOUBLE),
						this.getRect(), this.direction);
				nextShape = ns;
				return new NextNaAndShape(na, nextShape);
			}

			if (isOptimize && curShape.contains(nextNa.getPos())) {
				BoundBoundInRect newNextShape = new BoundBoundInRect(curShape
						.getBeginBound(this.getDirection()), nextNa.getPos()
						.getBound(this.direction, MINOR_DOUBLE),
						this.getRect(), this.direction);
				return new NextNaAndShape(nextNa, newNextShape.toRect());
			}

			BoundBoundInRect newNextShape = new BoundBoundInRect(
					isOptimize ? curShape.getBeginBound(this.getDirection())
							: curShape.getEndBound(this.getDirection()), nextNa
							.getPos().getBound(direction, this.MINOR_DOUBLE),
					this.getRect(), this.direction);
			nextShape = newNextShape;
			if (this.isEnd(newNextShape)) {
				nextShape = this.truncateShape(newNextShape);
			}
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, Rect curShape, boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
		return nextNaAndShape.getNextNa();

	}

	protected NeighborAttachment nextNeighborAttachment(
			NeighborAttachment curNa, BoundBoundInRect curShape,
			boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape.toRect(), isOptimize);
		return nextNaAndShape.getNextNa();

	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			CircleCircleInRect curShape, boolean isOptimize) {
		//
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		nasInCurShape.add(curNa);
		Collections.sort(nasInCurShape,
				new NodeDirectionDistanceHasDirectionComparator(this.direction,
						curNa.getPos()));
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
		Collections.sort(nas, new NodeDirectionDistanceHasDirectionComparator(
				this.direction, curNa.getPos()));
		if (isOptimize) {
			nasInCurShape.addAll(nas);
			nas = nasInCurShape;
		}
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = null, candidateNa, prevNa;
			boolean isNextNa;
			double minCost = Double.MAX_VALUE;
			double cost;
			for (int i = 0; i < nas.size(); i++) {
				candidateNa = (NeighborAttachment) nas.elementAt(i);
				isNextNa = true;
				for (int j = 0; j < i; j++) {
					prevNa = (NeighborAttachment) nas.elementAt(j);
					if (prevNa.getPos().distance(candidateNa.getPos()) > radioRange) {
						isNextNa = false;
						break;
					}
				}

				if (isNextNa) {
					cost = this.viaNodeCost(curNa, nas, i, isOptimize);
					if (cost < minCost) {
						nextNa = candidateNa;
						minCost = cost;
					}
				}
			}
			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			double averageAnwerSentTimes = this.viaNodeTimes(curNa, nas, nas
					.indexOf(nextNa), isOptimize);
			if (!isOptimize
					&& averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				CircleBoundInRect ns = new CircleBoundInRect(curShape
						.getEndCircle(), na.getPos().getBound(direction,
						-MINOR_DOUBLE), this.getRect(), this.direction);
				nextShape = ns;
				if (this.isEnd(ns)) {
					nextShape = this.truncateShape(ns);
				}
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
			NeighborAttachment curNa, CircleCircleInRect curShape,
			boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
		return nextNaAndShape.getNextNa();
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			CircleBoundInRect curShape, boolean isOptimize) {
		//
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		nasInCurShape.add(curNa);
		Collections.sort(nasInCurShape,
				new NodeDirectionDistanceHasDirectionComparator(this.direction,
						curNa.getPos()));
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
		Collections.sort(nas, new NodeDirectionDistanceHasDirectionComparator(
				this.direction, curNa.getPos()));
		if (isOptimize) {
			nasInCurShape.addAll(nas);
			nas = nasInCurShape;
		}
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = null, candidateNa, prevNa;
			boolean isNextNa;
			double minCost = Double.MAX_VALUE;
			double cost;
			for (int i = 0; i < nas.size(); i++) {
				candidateNa = (NeighborAttachment) nas.elementAt(i);
				isNextNa = true;
				for (int j = 0; j < i; j++) {
					prevNa = (NeighborAttachment) nas.elementAt(j);
					if (prevNa.getPos().distance(candidateNa.getPos()) > radioRange) {
						isNextNa = false;
						break;
					}
				}

				if (isNextNa) {
					cost = this.viaNodeCost(curNa, nas, i, isOptimize);
					if (cost < minCost) {
						nextNa = candidateNa;
						minCost = cost;
					}
				}
			}
			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			double averageAnwerSentTimes = this.viaNodeTimes(curNa, nas, nas
					.indexOf(nextNa), isOptimize);
			if (!isOptimize
					&& averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				BoundBoundInRect ns = new BoundBoundInRect(curShape
						.getEndBound(), na.getPos().getBound(direction,
						-MINOR_DOUBLE), this.getRect(), this.direction);
				nextShape = ns;
				if (this.isEnd(ns)) {
					nextShape = this.truncateShape(ns);
				}
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
			NeighborAttachment curNa, CircleBoundInRect curShape,
			boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
		return nextNaAndShape.getNextNa();

	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			BoundCircleInRect curShape, boolean isOptimize) {
		//
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		nasInCurShape.add(curNa);
		Collections.sort(nasInCurShape,
				new NodeDirectionDistanceHasDirectionComparator(this.direction,
						curNa.getPos()));
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
		Collections.sort(nas, new NodeDirectionDistanceHasDirectionComparator(
				this.direction, curNa.getPos()));
		if (isOptimize) {
			nasInCurShape.addAll(nas);
			nas = nasInCurShape;
		}
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = null, candidateNa, prevNa;
			boolean isNextNa;
			double minCost = Double.MAX_VALUE;
			double cost;
			for (int i = 0; i < nas.size(); i++) {
				candidateNa = (NeighborAttachment) nas.elementAt(i);
				isNextNa = true;
				for (int j = 0; j < i; j++) {
					prevNa = (NeighborAttachment) nas.elementAt(j);
					if (prevNa.getPos().distance(candidateNa.getPos()) > radioRange) {
						isNextNa = false;
						break;
					}
				}

				if (isNextNa) {
					cost = this.viaNodeCost(curNa, nas, i, isOptimize);
					if (cost < minCost) {
						nextNa = candidateNa;
						minCost = cost;
					}
				}
			}
			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

			double averageAnwerSentTimes = this.viaNodeTimes(curNa, nas, nas
					.indexOf(nextNa), isOptimize);
			if (!isOptimize
					&& averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				CircleBoundInRect ns = new CircleBoundInRect(curShape
						.getEndCircle(), na.getPos().getBound(direction,
						-MINOR_DOUBLE), this.getRect(), this.direction);
				nextShape = ns;
				if (this.isEnd(ns)) {
					nextShape = this.truncateShape(ns);
				}
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
			NeighborAttachment curNa, BoundCircleInRect curShape,
			boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
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
			return this.nextShape(curNa, (Rect) curShape, isOptimize);
		} else if (curShape instanceof CircleCircleInRect) {
			return this.nextShape(curNa, (CircleCircleInRect) curShape,
					isOptimize);
		} else if (curShape instanceof CircleBoundInRect) {
			return this.nextShape(curNa, (CircleBoundInRect) curShape,
					isOptimize);
		} else if (curShape instanceof BoundCircleInRect) {
			return this.nextShape(curNa, (BoundCircleInRect) curShape,
					isOptimize);
		} else if (curShape instanceof BoundBoundInRect) {
			return this.nextShape(curNa, (BoundBoundInRect) curShape,
					isOptimize);
		} else
			return null;
	}

	protected Shape nextShape(NeighborAttachment curNa, Rect curShape,
			boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
		return nextNaAndShape.getNextShape();
	}

	protected Shape nextShape(NeighborAttachment curNa,
			BoundBoundInRect curShape, boolean isOptimize) {
		return this.nextShape(curNa, curShape.toRect(), isOptimize);
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
			CircleCircleInRect curShape, boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
		return nextNaAndShape.getNextShape();

	}

	protected Shape nextShape(NeighborAttachment curNa,
			CircleBoundInRect curShape, boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
		return nextNaAndShape.getNextShape();

	}

	protected Shape nextShape(NeighborAttachment curNa,
			BoundCircleInRect curShape, boolean isOptimize) {
		NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(curNa,
				curShape, isOptimize);
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

	public NeighborAttachment getOptimizeNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		NeighborAttachment na = this.nextNeighborAttachment(curNa, curShape,
				true);
		if (na != null) {
			return na;
		} else
			return curNa;
	}

	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape) {
		Shape nextShape = this.nextShape(curNa, curShape, true);
		if (nextShape != null) {
			return nextShape;
		} else
			return curShape;
	}

	protected Rect caculateNextRect(Rect curRect, NeighborAttachment nextNa) {
		double minor = 0;
		switch (direction) {
		case Direction.UP:
		case Direction.RIGHT:
			minor = this.MINOR_DOUBLE;
			break;
		case Direction.DOWN:
		case Direction.LEFT:
			minor = -this.MINOR_DOUBLE;
			break;
		default:
		}

		BoundBoundInRect newNextShape = new BoundBoundInRect(curRect
				.getBeginBound(this.getDirection()), nextNa.getPos().getBound(
				direction)
				+ minor, this.getRect(), this.direction);
		return newNextShape.toRect();
	}

}
