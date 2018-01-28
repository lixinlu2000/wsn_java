package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;

public class EDCGridTraverseRingIteratorLinkAware extends TraverseRingIterator 
	implements TraverseShapeOptimizeIterator {
	RingSector firstRingSector;
	private final double MINOR_DOUBLE = 0.0001;
	private double averageAnswerSentTimesThrehold = 10;
	private double partialQueryResultSize = 0;
	
	public EDCGridTraverseRingIteratorLinkAware(Algorithm alg, NeighborAttachment root,
			Ring ring) {
		super(alg, root, ring);
		this.firstRingSector = this.caculateFirstRingSector();
	}

	public static double calculateRingSectorSita(double lowRadius,
			double highRadius, double radioRange) {
		double l = lowRadius;
		double h = highRadius;
		double r = radioRange;
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;

		// bug fixed
		if (r < h + l) {
			cosSita1 = (h2 + l2 - r2) / (2 * h * l);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (2 * h2 - r2) / (2 * h2);
			sita2 = Math.acos(cosSita2);
		} else if (radioRange >= 2 * h) {
			return 2 * Math.PI;
		} else {
			sita1 = Math.PI;
			cosSita2 = (2 * h2 - r2) / (2 * h2);
			sita2 = Math.acos(cosSita2);
		}
		return Math.min(sita1, sita2);
	}

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		if (curNa == null && curShape == null) {
			return null;
		} else if (curNa != null && curShape == null) { // first call
			return null;
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(
						curNa, (RingSector)curShape, false);
				return nextNaAndShape.getNextNa();
			}
		}
		return null;
	}

	protected RingSector caculateFirstRingSector() {
		double firstBearing = this.getRing().getCircleCentre().bearing(
				this.getRoot().getNode().getPos());

		// fixed code
		double firstRingSectorSita = this.caculateFirstRingSectorSita();
		firstBearing = firstBearing - firstRingSectorSita / 2;

		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		return new RingSector(this.ring.getCircleCentre(), lowRadius,
				highRadius, firstBearing, firstRingSectorSita);
	}
	
	public double viaNodeCostIdeal(NeighborAttachment curNa, Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;
		double collectedNodeNum = 0;

		for (int i = 0; i <= boundNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			collectAnswerCost += this.alg.getParam().getANSWER_SIZE();
		}
		
		collectAnswerCost += this.getPartialQueryResultSize();
		totalCost += collectAnswerCost;
		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE();
		if (nextNaIndex > boundNaIndex) {
			collectedNodeNum = boundNaIndex + 2;
		} else
			collectedNodeNum = boundNaIndex + 1;
		totalCost /= collectedNodeNum;
		return totalCost;
	}
	
	protected double viaNodeCostNoOptimize(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;
		double collectedNodeNum = 0;

		for (int i = 0; i <= boundNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
					* na.getSendTimes(nextNa);
		}
		
		for (int i = 0; i <= boundNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}

		if (nextNaIndex > boundNaIndex) {
			times = curNa.getSendTimes(nextNa);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		collectAnswerCost += this.getPartialQueryResultSize()
				* curNa.getSendTimes(nextNa);
		totalCost += collectAnswerCost;
		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;
		if (nextNaIndex > boundNaIndex) {
			collectedNodeNum = boundNaIndex + 2;
		} else
			collectedNodeNum = boundNaIndex + 1;
		totalCost /= collectedNodeNum;
		return totalCost;
	}
	
	protected double sensoryDataNum(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex) {
		double collectedNodeNum;
		if (nextNaIndex > boundNaIndex) {
			collectedNodeNum = boundNaIndex + 2;
		} else
			collectedNodeNum = boundNaIndex + 1;
		return collectedNodeNum;
	}

	protected double viaNodeCost(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex,
			boolean isOptimize) {
		if (isOptimize)
			return this.viaNodeCostOptimize(curNa, sortedNasInMaxNextShape,
					nextNaIndex, boundNaIndex);
		else
			return this.viaNodeCostNoOptimize(curNa, sortedNasInMaxNextShape,
					nextNaIndex, boundNaIndex);
	}

	protected double viaNodeTimes(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex,
			boolean isOptimize) {
		double totalCost;
		if (isOptimize)
			totalCost = this.viaNodeCostOptimize(curNa,
					sortedNasInMaxNextShape, nextNaIndex, boundNaIndex);
		else
			totalCost = this.viaNodeCostNoOptimize(curNa,
					sortedNasInMaxNextShape, nextNaIndex, boundNaIndex);
		totalCost = this.viaNodeCostNoOptimize(curNa,
				sortedNasInMaxNextShape, nextNaIndex, boundNaIndex);
		double sdn = this.sensoryDataNum(curNa, sortedNasInMaxNextShape, nextNaIndex, boundNaIndex);
		return totalCost / (sdn*this.alg.getParam().getANSWER_SIZE());
	}

	protected double viaNodeCostOptimize(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;
		int collectedNodeNum = 0;
		int curNaIndex = sortedNasInMaxNextShape.indexOf(curNa);
		
		for (int i = 0; i <= boundNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}

		if (nextNaIndex > boundNaIndex) {
			times = curNa.getSendTimes(nextNa);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		
		for (int i = 0; i <= boundNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
					* na.getSendTimes(nextNa);
		}
		
		collectAnswerCost += this.getPartialQueryResultSize() * curNa.getSendTimes(nextNa);
		totalCost += collectAnswerCost;
		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;

		if (nextNaIndex > boundNaIndex) {
			collectedNodeNum = boundNaIndex + 2;
		} else
			collectedNodeNum = boundNaIndex + 1;
		
		totalCost /= collectedNodeNum;
		return totalCost;
	}

	protected boolean canCommunicateWith(NeighborAttachment curNa, Vector nas,
			int endIndex) {
		NeighborAttachment na;
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		for (int i = 0; i <= endIndex; i++) {
			na = (NeighborAttachment) nas.elementAt(i);
			if (na.getPos().distance(curNa.getPos()) > radioRange)
				return false;
		}
		return true;
	}

	protected boolean isQualifiedNextNaAndBoundNa(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, int boundNaIndex) {
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		if (this.canCommunicateWith(curNa, sortedNasInMaxNextShape,
				boundNaIndex)
				&& this.canCommunicateWith(nextNa, sortedNasInMaxNextShape,
						boundNaIndex))
			return true;
		else
			return false;
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			RingSector curShape, boolean isOptimize) {
		//
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		nasInCurShape.add(curNa);
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				curShape.getStartRadian());
		Collections.sort(nasInCurShape, comparator);
		Shape nextShape = this.caculateNextRingSector(curNa, curShape);
		
		Vector nas = curNa.getNeighbors(nextShape);
		Collections.sort(nas, comparator);
		if (isOptimize) {
			nasInCurShape.addAll(nas);
			nas = nasInCurShape;
		}
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = null, candidateNextNa;
			NeighborAttachment boundNa = null, candidateBoundNaNa;
			double cost, minCost = Double.MAX_VALUE;
			for (int i = 0; i < nas.size(); i++) {
				candidateNextNa = (NeighborAttachment) nas.elementAt(i);
//				for (int j = 0; j < nas.size(); j++) {
//					candidateBoundNaNa = (NeighborAttachment) nas.elementAt(j);
				int j = i;
				candidateBoundNaNa = candidateNextNa;
					if (this.isQualifiedNextNaAndBoundNa(curNa, nas, i, j)) {
						cost = this.viaNodeCost(curNa, nas, i, j, isOptimize);
						if (cost < minCost) {
							nextNa = candidateNextNa;
							boundNa = candidateBoundNaNa;
							minCost = cost;
						}
					}
//				}
			}
			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

		    cost = this.viaNodeCost(curNa, nas,nas
					.indexOf(nextNa), nas.indexOf(boundNa), isOptimize);
			double threhold = this.viaNodeCostIdeal(curNa, nas, nas
					.indexOf(nextNa), nas.indexOf(boundNa));
			double naRadian;
			if (!isOptimize
					&& cost >= this.averageAnswerSentTimesThrehold*threhold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				naRadian = this.ring.getCircleCentre().bearing(na.getPos());
				nextShape = new RingSector(this.ring.getCircleCentre(), this.ring.getLowRadius(), this.ring.getHighRadius(),
						curShape.getEndRadian(), Radian.relativeTo(naRadian,curShape.getEndRadian())-MINOR_DOUBLE);
				return new NextNaAndShape(null, nextShape);
			}
			if (isOptimize
					&& cost >= this.averageAnswerSentTimesThrehold*threhold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);			
				naRadian = this.ring.getCircleCentre().bearing(na.getPos());
				nextShape = new RingSector(this.ring.getCircleCentre(), this.ring.getLowRadius(), this.ring.getHighRadius(),
						curShape.getStartRadian(), Radian.relativeTo(naRadian,curShape.getStartRadian())+MINOR_DOUBLE);
				return new NextNaAndShape(null, nextShape);
			}

			double startRadian = isOptimize ? curShape.getStartRadian()
					: curShape.getEndRadian();
			naRadian = this.ring.getCircleCentre().bearing(boundNa.getPos());
			nextShape = new RingSector(this.ring.getCircleCentre(),
					this.ring.getLowRadius(), 
					this.ring.getHighRadius(),
					startRadian,
					Radian.relativeTo(naRadian,startRadian)+MINOR_DOUBLE);
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected NextNaAndShape caculateNextNaAndShapeOld(NeighborAttachment curNa,
			RingSector curShape, boolean isOptimize) {
		//
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		nasInCurShape.add(curNa);
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				curShape.getStartRadian());
		Collections.sort(nasInCurShape, comparator);
		Shape nextShape = this.caculateNextRingSector(curNa, curShape);
		
		Vector nas = curNa.getNeighbors(nextShape);
		Collections.sort(nas, comparator);
		if (isOptimize) {
			nasInCurShape.addAll(nas);
			nas = nasInCurShape;
		}
		if (nas.isEmpty()) {
			return new NextNaAndShape(null, nextShape);
		} else {
			NeighborAttachment nextNa = null, candidateNextNa;
			NeighborAttachment boundNa = null, candidateBoundNaNa;
			double cost, minCost = Double.MAX_VALUE;
			for (int i = 0; i < nas.size(); i++) {
				candidateNextNa = (NeighborAttachment) nas.elementAt(i);
				for (int j = 0; j < nas.size(); j++) {
					candidateBoundNaNa = (NeighborAttachment) nas.elementAt(j);
					if (this.isQualifiedNextNaAndBoundNa(curNa, nas, i, j)) {
						cost = this.viaNodeCost(curNa, nas, i, j, isOptimize);
						if (cost < minCost) {
							nextNa = candidateNextNa;
							boundNa = candidateBoundNaNa;
							minCost = cost;
						}
					}
				}
			}
			//
			if (nextNa == null) {
				return new NextNaAndShape(null, nextShape);
			}

		    cost = this.viaNodeCost(curNa, nas,nas
					.indexOf(nextNa), nas.indexOf(boundNa), isOptimize);
			double threhold = this.viaNodeCostIdeal(curNa, nas, nas
					.indexOf(nextNa), nas.indexOf(boundNa));
			double naRadian;
			if (!isOptimize
					&& cost >= this.averageAnswerSentTimesThrehold*threhold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);
				naRadian = this.ring.getCircleCentre().bearing(na.getPos());
				nextShape = new RingSector(this.ring.getCircleCentre(), this.ring.getLowRadius(), this.ring.getHighRadius(),
						curShape.getEndRadian(), Radian.relativeTo(naRadian,curShape.getEndRadian())-MINOR_DOUBLE);
				return new NextNaAndShape(null, nextShape);
			}
			if (isOptimize
					&& cost >= this.averageAnswerSentTimesThrehold*threhold) {
				NeighborAttachment na = (NeighborAttachment) nas.elementAt(0);			
				naRadian = this.ring.getCircleCentre().bearing(na.getPos());
				nextShape = new RingSector(this.ring.getCircleCentre(), this.ring.getLowRadius(), this.ring.getHighRadius(),
						curShape.getStartRadian(), Radian.relativeTo(naRadian,curShape.getStartRadian())+MINOR_DOUBLE);
				return new NextNaAndShape(null, nextShape);
			}

			double startRadian = isOptimize ? curShape.getStartRadian()
					: curShape.getEndRadian();
			naRadian = this.ring.getCircleCentre().bearing(boundNa.getPos());
			nextShape = new RingSector(this.ring.getCircleCentre(),
					this.ring.getLowRadius(), 
					this.ring.getHighRadius(),
					startRadian,
					Radian.relativeTo(naRadian,startRadian)+MINOR_DOUBLE);
			return new NextNaAndShape(nextNa, nextShape);
		}
	}

	protected double caculateFirstRingSectorSita() {
		return this.caculateMaxSita();
	}

	/*
	 * caculate the max sita of the ringSector that any two node in this
	 * ringSector can communicate with each other.
	 */
	protected double caculateMaxSita() {
		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		return EDCGridTraverseRingIteratorLinkAware.calculateRingSectorSita(lowRadius,
				highRadius, this.getAlg().getParam().getRADIO_RANGE());
	}

	/*
	 * if cannot greedy forward to curRingSector then caculate the ring sector
	 * after curRingSector. All the node in curRingSector can communicate with
	 * each other.
	 */
	protected RingSector caculateNextRingSector(RingSector curRingSector) {
		RingSector nextRingSector;
		double maxSita = this.caculateMaxSita()/4;
		double l = this.getRing().getLowRadius();
		double h = this.getRing().getHighRadius();

		nextRingSector = new RingSector(this.ring.getCircleCentre(), l, h,
				curRingSector.getEndRadian(), maxSita);

		// check if reach the end of the ring
		double relativeSita = Radian.relativeTo(this.firstRingSector
				.getStartRadian(), nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > relativeSita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());

		return nextRingSector;
	}

	/*
	 * if curRingSector has nodes and curNa is the grid node, then caculate the
	 * ring sector after curRingSector. All the node in curRingSector can
	 * communicate with curNa and communicate with each other.
	 */
	protected RingSector caculateNextRingSector(NeighborAttachment curNa,
			RingSector curRingSector) {
		RingSector nextRingSector;

		double l = this.getRing().getLowRadius();
		double h = this.getRing().getHighRadius();
		double r = this.getAlg().getParam().getRADIO_RANGE();
		double d = curNa.getNode().getPos().distance(
				this.getRing().getCircleCentre());
		double startRadian = this.getRing().getCircleCentre().bearing(
				curNa.getNode().getPos());
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double d2 = d * d;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;
		double sita;

		// bug fixed
		if (r >= d + h) {
			sita = 2 * Math.PI;
		} else if (r >= d + l) {
			sita1 = Math.PI;
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			sita = Math.min(sita1, sita2);
		} else {
			cosSita1 = (l2 + d2 - r2) / (2 * l * d);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			sita = Math.min(sita1, sita2);
		}

		// bug
		sita -= Radian.relativeTo(curRingSector.getEndRadian(), startRadian);


		nextRingSector = new RingSector(this.ring.getCircleCentre(), l, h,
				curRingSector.getEndRadian(), sita);
		// check if reach the end of the ring
		double relativeSita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > relativeSita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		return nextRingSector;
	}

	public class RelativeRadianComparator implements Comparator {
		private double baseRadian;

		public RelativeRadianComparator(double baseRadian) {
			this.baseRadian = baseRadian;
		}

		public int compare(Object arg0, Object arg1) {
			NeighborAttachment n1 = (NeighborAttachment) arg0;
			NeighborAttachment n2 = (NeighborAttachment) arg1;

			double relativeRadian1 = Radian.relativeTo(ring.getCircleCentre()
					.bearing(n1.getNode().getPos()), baseRadian);
			double relativeRadian2 = Radian.relativeTo(ring.getCircleCentre()
					.bearing(n2.getNode().getPos()), baseRadian);
			if (relativeRadian1 > relativeRadian2)
				return 1;
			else if (relativeRadian1 < relativeRadian2)
				return -1; 
			else
				return 0;
		}
	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (curNa != null && curShape == null) { // first call
			return this.firstRingSector;
		} else if (curNa != null && curShape != null) {
			if (curShape.contains(curNa.getPos())) {
				NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(
						curNa, (RingSector)curShape, false);
				return nextNaAndShape.getNextShape();
			} else {
				return this.caculateNextRingSector((RingSector) curShape);
			}
		}
		return null;
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		RingSector curRingSector = (RingSector) curShape;
		if (curShape != null) {
			if (this.firstRingSector.getStartRadian() == curRingSector
					.getEndRadian())
				return true;
			else
				return false;
		} else
			return false;
	}

	public double getPartialQueryResultSize() {
		return partialQueryResultSize;
	}

	public void setPartialQueryResultSize(double partialQueryResultSize) {
		this.partialQueryResultSize = partialQueryResultSize;
	}
	

	public NeighborAttachment getOptimizeNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		return this.caculateNextNaAndShape(curNa, (RingSector)curShape, true).getNextNa();
	}

	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape) {
		return this.caculateNextNaAndShape(curNa, (RingSector)curShape, true).getNextShape();
	}
}
