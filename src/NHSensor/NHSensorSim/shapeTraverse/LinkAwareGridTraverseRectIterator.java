package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.util.Util;

/*
 * link quality aware grid traverse a rect region
 */
public class LinkAwareGridTraverseRectIterator extends TraverseRectIterator {
	static Logger logger = Logger
			.getLogger(LinkAwareGridTraverseRectIterator.class);
	public double rate = 0.1;
	private double nextRectSizeRatio = 8;

	public LinkAwareGridTraverseRectIterator(Algorithm alg, int direction,
			Rect rect) {
		super(alg, direction, rect);
	}

	public Rect getInitialDestRect(NeighborAttachment root) {
		double width, height;
		double radioRange = this.alg.getParam().getRADIO_RANGE();

		switch (this.direction) {
		case Direction.LEFT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height)
					/ nextRectSizeRatio;
			return new Rect(this.rect.getMaxX() - width, this.rect.getMaxX(),
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.RIGHT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height)
					/ nextRectSizeRatio;
			return new Rect(this.rect.getMinX(), this.rect.getMinX() + width,
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.DOWN:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width)
					/ nextRectSizeRatio;
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), this.rect
					.getMaxY()
					- height, this.rect.getMaxY());
		case Direction.UP:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width)
					/ nextRectSizeRatio;
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
		if (curNa == null && curRect == null) {
			return null;
		} else if (curNa != null && curRect == null) { // first call
			return null;
		} else if (curNa != null && curRect != null) {
			if (!curRect.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				return this.nextNode(curNa, curRect);
			}
		}
		return null;
	}

	public NeighborAttachment nextNode(NeighborAttachment curNa, Rect curRect) {
		Rect maxNextRect = this.caculateMaxNextRect(curNa, curRect);
		// System.out.println("curNa:"+curNa.getNode().getPos());
		// System.out.println("curRect:"+curRect);
		// System.out.println("maxNextRect:"+maxNextRect);
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				maxNextRect);
		Vector neighborsInMaxNextRect = curNa.getNeighbors(this.alg.getParam()
				.getRADIO_RANGE(), nairc);
		if (neighborsInMaxNextRect.size() == 0) {
			logger.debug("has no neighbors to check");
			return null;
		}
		ViaNodeMinEnergyNextRect viaNodeMinEnergyNextRect;
		NeighborAttachment viaNode = null;
		Rect nextRect = maxNextRect;
		double minEnergy = Double.MAX_VALUE;

		logger.debug("has " + neighborsInMaxNextRect.size()
				+ " neighbors to check");

		for (int i = 0; i < neighborsInMaxNextRect.size(); i++) {
			NeighborAttachment nextNa = (NeighborAttachment) neighborsInMaxNextRect
					.elementAt(i);
			viaNodeMinEnergyNextRect = this.viaNodeMinCost(curNa, nextNa,
					this.direction, maxNextRect);
			if (viaNodeMinEnergyNextRect.getEnergy() < minEnergy
					|| (viaNodeMinEnergyNextRect.getEnergy() == minEnergy && this
							.compare(nextNa, viaNode) > 0)) {
				minEnergy = viaNodeMinEnergyNextRect.getEnergy();
				viaNode = viaNodeMinEnergyNextRect.getViaNode();
				nextRect = viaNodeMinEnergyNextRect.getNextRect();
			}
		}
		return viaNode;
	}

	public ViaNodeMinEnergyNextRect viaNodeMinCost(NeighborAttachment curNa,
			NeighborAttachment nextNa, int direction, Rect maxNextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				maxNextRect);
		Vector neighborsInMaxNextRect = curNa.getNeighbors(this.alg.getParam()
				.getRADIO_RANGE(), nairc);
		Rect minRectContainNeighbor;
		double cost;

		Collections.sort(neighborsInMaxNextRect, new NodePositionComparator(
				direction));
		if (neighborsInMaxNextRect.size() == 0) {
			minRectContainNeighbor = this.minRectContainNode(maxNextRect,
					direction, nextNa);
			return new ViaNodeMinEnergyNextRect(nextNa, minRectContainNeighbor,
					0);
		}

		// Collections.sort(neighborsInMaxNextRect, new
		// NodePositionComparator(direction));
		double minCost = Double.MAX_VALUE;
		Rect minCostRect = maxNextRect;

		/*
		 * for(int i=0;i<neighborsInMaxNextRect.size();i++) { NeighborAttachment
		 * neigbhor = (NeighborAttachment)neighborsInMaxNextRect.elementAt(i);
		 * minRectContainNeighbor = this.minRectContainNode(maxNextRect,
		 * direction, neigbhor); cost = this.energyCost(curNa, nextNa,
		 * neighborsInMaxNextRect, i);
		 * logger.debug("Cost:"+nextNa.getNode().getPos
		 * ()+" "+(i+1)+"  "+neigbhor
		 * .getNode().getPos()+minRectContainNeighbor+" "
		 * +Double.toString(cost)); if(cost<minCost) { minCost = cost;
		 * minCostRect = minRectContainNeighbor; } else break; }
		 */
		int nextNaIndex = neighborsInMaxNextRect.indexOf(nextNa);
		// int i;
		// if(nextNaIndex==0) {
		// i = 0;
		// minCost = 0;
		// minCostRect = this.minRectContainNode(maxNextRect, direction,
		// nextNa);
		// }
		// else i = nextNaIndex-1;
		for (int i = nextNaIndex; i < neighborsInMaxNextRect.size(); i++) {
			NeighborAttachment neigbhor = (NeighborAttachment) neighborsInMaxNextRect
					.elementAt(i);
			minRectContainNeighbor = this.minRectContainNode(maxNextRect,
					direction, neigbhor);
			cost = this.energyCost(curNa, nextNa, neighborsInMaxNextRect, i);
			logger.debug("Cost:" + nextNa.getNode().getPos() + " " + (i + 1)
					+ "  " + neigbhor.getNode().getPos()
					+ minRectContainNeighbor + " " + Double.toString(cost));
			if (cost <= minCost) {
				minCost = cost;
				minCostRect = minRectContainNeighbor;
			} else
				break;
		}

		return new ViaNodeMinEnergyNextRect(nextNa, minCostRect, minCost);
	}

	/*
	 * public Rect minRectContainNode(Rect maxNextRect, int direction,
	 * NeighborAttachment na) { switch(direction) { case Direction.LEFT: return
	 * new Rect(na.getNode().getPos().getX(), maxNextRect.getMaxX(),
	 * maxNextRect.getMinY(), maxNextRect.getMaxY()); case Direction.RIGHT:
	 * return new Rect(maxNextRect.getMinX(), na.getNode().getPos().getX(),
	 * maxNextRect.getMinY(), maxNextRect.getMaxY()); case Direction.UP: return
	 * new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
	 * maxNextRect.getMinY(), na.getNode().getPos().getY()); case
	 * Direction.DOWN: return new Rect(maxNextRect.getMinX(),
	 * maxNextRect.getMaxX(), na.getNode().getPos().getY(),
	 * maxNextRect.getMaxY()); default: return null; } }
	 */

	/*
	 * to get rid of node in the boundary of the rectangle
	 */
	public Rect minRectContainNode(Rect maxNextRect, int direction,
			NeighborAttachment na) {
		switch (direction) {
		case Direction.LEFT:
			return new Rect(na.getNode().getPos().getX() - 0.0000001,
					maxNextRect.getMaxX(), maxNextRect.getMinY(), maxNextRect
							.getMaxY());
		case Direction.RIGHT:
			return new Rect(maxNextRect.getMinX(),
					na.getNode().getPos().getX() + 0.0000001, maxNextRect
							.getMinY(), maxNextRect.getMaxY());
		case Direction.UP:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					maxNextRect.getMinY(),
					na.getNode().getPos().getY() + 0.0000001);
		case Direction.DOWN:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(), na
					.getNode().getPos().getY() - 0.0000001, maxNextRect
					.getMaxY());
		default:
			return null;
		}
	}

	public double energyCost(NeighborAttachment curNa,
			NeighborAttachment nextNa, Vector neighborsInMaxNextRect,
			int endIndex) {
		double totalCost = 0;
		NeighborAttachment na;
		double broadcastQueryMessageTimes = 0;
		double times = 0;

		int answerNodeNum = 0;
		int collectedNodeNum = 0;
		for (int i = 0; i <= endIndex; i++) {
			na = (NeighborAttachment) neighborsInMaxNextRect.elementAt(i);
			if (na != nextNa) {
				answerNodeNum++;
				collectedNodeNum++;
				totalCost += this.alg.getParam().getANSWER_SIZE()
						* na.getSendTimes(nextNa);
				times = nextNa.getSendTimes(na);
				broadcastQueryMessageTimes = Math.max(times,
						broadcastQueryMessageTimes);
			}
		}
		if (answerNodeNum > 0)
			totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
					* broadcastQueryMessageTimes;
		if (curNa != nextNa) {
			int queryAndPatialAnswerSize = this.alg.getParam().getANSWER_SIZE()
					+ this.alg.getParam().getQUERY_MESSAGE_SIZE();
			totalCost += queryAndPatialAnswerSize;
			// int queryAndPatialAnswerSize =
			// this.alg.getParam().getANSWER_SIZE(
			// )+this.alg.getParam().getQUERY_MESSAGE_SIZE();
			// totalCost += queryAndPatialAnswerSize*curNa.getSendTimes(nextNa);
			collectedNodeNum++;
		}
		totalCost /= collectedNodeNum;
		return totalCost;
	}

	public Rect getNextRect(NeighborAttachment curNa, Rect curRect) {
		if (this.isEnd(curNa, curRect))
			return null;
		if (curNa != null && curRect == null) {
			return this.getInitialDestRect(curNa);
		} else if (curNa != null && curRect != null) {
			if (!curRect.contains(curNa.getNode().getPos())) {
				double radioRange = this.alg.getParam().getRADIO_RANGE();
				double width, height;
				switch (this.direction) {
				case Direction.LEFT:
					height = curRect.getHeight();
					width = Math
							.sqrt(radioRange * radioRange - height * height)
							/ nextRectSizeRatio;
					return curRect.leftNeighbour(width, this.rect.getMinX());
				case Direction.RIGHT:
					height = curRect.getHeight();
					width = Math
							.sqrt(radioRange * radioRange - height * height)
							/ nextRectSizeRatio;
					return curRect.rightNeighbour(width, this.rect.getMaxX());
				case Direction.UP:
					width = curRect.getWidth();
					height = Math.sqrt(radioRange * radioRange - width * width)
							/ nextRectSizeRatio;
					return curRect.topNeighbour(height, this.rect.getMaxY());
				case Direction.DOWN:
					width = curRect.getWidth();
					height = Math.sqrt(radioRange * radioRange - width * width)
							/ nextRectSizeRatio;
					return curRect.downNeighbour(height, this.rect.getMinY());
				default:
					return null;
				}
			} else {
				Rect maxNextRect = this.caculateMaxNextRect(curNa, curRect);
				AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
						maxNextRect);
				Vector neighborsInMaxNextRect = curNa.getNeighbors(this.alg
						.getParam().getRADIO_RANGE(), nairc);
				if (neighborsInMaxNextRect.size() == 0) {
					return maxNextRect;
				}
				ViaNodeMinEnergyNextRect viaNodeMinEnergyNextRect;
				NeighborAttachment viaNode = null;
				Rect nextRect = maxNextRect;
				double minEnergy = Double.MAX_VALUE;

				for (int i = 0; i < neighborsInMaxNextRect.size(); i++) {
					NeighborAttachment nextNa = (NeighborAttachment) neighborsInMaxNextRect
							.elementAt(i);
					viaNodeMinEnergyNextRect = this.viaNodeMinCost(curNa,
							nextNa, this.direction, maxNextRect);
					if (viaNodeMinEnergyNextRect.getEnergy() < minEnergy
							|| (viaNodeMinEnergyNextRect.getEnergy() == minEnergy && this
									.compare(nextNa, viaNode) > 0)) {
						minEnergy = viaNodeMinEnergyNextRect.getEnergy();
						viaNode = viaNodeMinEnergyNextRect.getViaNode();
						nextRect = viaNodeMinEnergyNextRect.getNextRect();
					}
				}
				return nextRect;
			}
		}
		return null;
	}

	protected Rect caculateMaxNextRect(NeighborAttachment curNa, Rect curRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double x0 = curNa.getNode().getPos().getX();
		double y0 = curNa.getNode().getPos().getY();
		;
		double dx, dy;
		double width, height;
		Rect maxNextRect = null;
		switch (this.direction) {
		case Direction.LEFT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (x0 - curRect.getMinX());
			if (width < 0)
				width = 0;
			maxNextRect = curRect.leftNeighbour(width, this.rect.getMinX());
			break;
		case Direction.RIGHT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (curRect.getMaxX() - x0);
			if (width < 0)
				width = 0;
			maxNextRect = curRect.rightNeighbour(width, this.rect.getMaxX());
			break;
		case Direction.DOWN:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (y0 - curRect.getMinY());
			if (height < 0)
				height = 0;
			maxNextRect = curRect.downNeighbour(height, this.rect.getMinY());
			break;
		case Direction.UP:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (curRect.getMaxY() - y0);
			if (height < 0)
				height = 0;
			maxNextRect = curRect.topNeighbour(height, this.rect.getMaxY());
			break;
		default:
			;
		}
		return maxNextRect;
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
			return false;
		}
	}

	public int compare(NeighborAttachment na1, NeighborAttachment na2) {
		NodePositionComparator npc = new NodePositionComparator(this.direction);
		return npc.compare(na1, na2);
	}

}
