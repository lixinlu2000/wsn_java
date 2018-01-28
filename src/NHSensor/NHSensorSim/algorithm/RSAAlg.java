package NHSensor.NHSensorSim.algorithm;

import java.util.Collections;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.AttachmentYAdvanceComparator;
import NHSensor.NHSensorSim.core.ForwardToDestRectResult;
import NHSensor.NHSensorSim.core.HasSendAnswerUserObject;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.DrawRectEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToRectEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToRectExEvent;
import NHSensor.NHSensorSim.events.RadioEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class RSAAlg extends IWQEAlg {
	static Logger logger = Logger.getLogger(RSAAlg.class);

	public static final String NAME = "RSA";
	private Vector rects = new Vector();
	private Rect firstDestRect;

	public Rect getFirstDestRect() {
		return firstDestRect;
	}

	public void setFirstDestRect(Rect firstDestRect) {
		this.firstDestRect = firstDestRect;
	}

	public RSAAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public RSAAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, DGSANewAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public RSAAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initSubQueryRegionsAndGridsByRegionNum(int subQueryRegionNum) {
		this.subQueryRegionNum = subQueryRegionNum;
		this.subQueryRegions = this
				.caculateSubQueryRegionsByRegionNum(subQueryRegionNum);
		this.subQueryRegionWidth = this
				.caculateSubQueryRegionWidth(subQueryRegionNum);

		//
		double gridWidth = subQueryRegionWidth;
		double gridHeight = Math.sqrt(this.getParam().getRADIO_RANGE()
				* this.getParam().getRADIO_RANGE() - gridWidth * gridWidth) / 2;
		this.initGridWithGridSize(gridWidth, gridHeight);

	}

	public void initSubQueryRegionAndGridsByRegionWidth(
			double subQueryRegionWidth) {
		this.subQueryRegionNum = this
				.caculateSubQueryRegionNum(subQueryRegionWidth);
		this.subQueryRegions = this
				.caculateSubQueryRegionsByRegionWidth(subQueryRegionWidth);
		this.subQueryRegionWidth = subQueryRegionWidth;

		//
		double gridWidth = subQueryRegionWidth;
		double gridHeight = Math.sqrt(this.getParam().getRADIO_RANGE()
				* this.getParam().getRADIO_RANGE() - gridWidth * gridWidth) / 2;
		this.initGridWithGridSize(gridWidth, gridHeight);
	}

	// Calculate start grid
	private Rect getStartGrid(GPSRAttachment cur, Rect curDestRect) {
		Rect rect = this.getNextGrid(cur, curDestRect);

		if (this.completeSubItinerary(rect)) {
			return curDestRect;
		}

		if (this.isDown(cur)) {
			return new Rect(curDestRect.getMinX(), curDestRect.getMaxX(), rect
					.getMinY(), curDestRect.getMaxY());
		} else {
			return new Rect(curDestRect.getMinX(), curDestRect.getMaxX(),
					curDestRect.getMinY(), rect.getMaxY());
		}
	}

	public boolean gridTraverse() throws SensornetBaseException {
		GPSRAttachment cur = this.getFirst();
		Rect curDestRect = this.getFirstDestRect();
		GridIndex curGridIndex = this.getNodeGridIndex(cur);
		Rect curRect = this.grids[curGridIndex.getX()][curGridIndex.getY()];

		GPSRAttachment next = null;
		Rect nextRect;

		AttachmentInShapeCondition nairc;
		Vector neighborsInCurRect;
		Vector neighborsInNextRect;
		GPSRAttachment neighbor;

		Message queryMessage = new Message(this.getItineraryAlgParam()
				.getQuerySize(), null);
		Message answerMessage = new Message(this.getItineraryAlgParam()
				.getAnswerSize(), null);
		Message queryAnswerMessage = new Message(this.getItineraryAlgParam()
				.getQueryAndPatialAnswerSize(), null);
		RadioEvent queryEvent;
		RadioEvent answerEvent;
		RadioEvent queryAnswerEvent;
		GreedyForwardToRectExEvent gftre;
		DrawRectEvent drawRectEvent;

		do {
			nairc = new AttachmentInShapeCondition(curRect);
			neighborsInCurRect = cur.getNeighbors(this.getParam()
					.getRADIO_RANGE(), nairc);
			this.rects.add(curRect);
			drawRectEvent = new DrawRectEvent(this, curRect);
			this.getSimulator().handle(drawRectEvent);

			// broadcast query and collect answers
			if (neighborsInCurRect.size() == 0) {
				// no need for broadcast query because there is no neighbor in
				// current rect
			} else {
				if (!this.isAllNodesInRectRecieved()) {
					queryEvent = new BroadcastEvent(cur, null, queryMessage,
							this);
					this.simulator.handle(queryEvent);
				}

				for (int i = 0; i < neighborsInCurRect.size(); i++) {
					// must be answers
					neighbor = (GPSRAttachment) neighborsInCurRect.elementAt(i);
					boolean hasSend = this.getUserObject(neighbor)
							.isHasSendAnswer();
					if (hasSend)
						continue;
					answerEvent = new BroadcastEvent(neighbor, cur,
							answerMessage, this);
					this.simulator.handle(answerEvent);
					// set has send
					this.setUserObject(neighbor, new HasSendAnswerUserObject(
							true));
				}
			}

			// last node so reach the end
			if (this.isQueryRegionLastGrid(curRect)) {
				this.setLast(cur);
				return true;
			}

			// get next grid
			nextRect = this.getNextGrid(cur, curRect);
			nairc = new AttachmentInShapeCondition(nextRect);
			neighborsInNextRect = cur.getNeighbors(this.getParam()
					.getRADIO_RANGE(), nairc);
			drawRectEvent = new DrawRectEvent(this, nextRect);
			this.getSimulator().handle(drawRectEvent);

			// nextRect has no nodes
			if (neighborsInNextRect.size() == 0) {
				do {
					if (this.isQueryRegionLastGrid(nextRect)) {
						this.setLast(cur);
						return true;
					}

					if (this.isDown(nextRect.getCentre()))
						nextRect = this.getNextGrid(nextRect.getCB(), nextRect);
					else
						nextRect = this.getNextGrid(nextRect.getCT(), nextRect);

					drawRectEvent = new DrawRectEvent(this, nextRect);
					this.getSimulator().handle(drawRectEvent);

					this.setUserObject(cur, new HasSendAnswerUserObject(true));
					gftre = new GreedyForwardToRectExEvent(cur, nextRect,
							queryAnswerMessage, this);
					this.simulator.handle(gftre);
					cur = (GPSRAttachment) gftre.getLastNode();
					curRect = nextRect;

					if (gftre.isSuccess()) {
						// broad cast queryAnswerMessage
						this.setAllNodesInRectRecieved(gftre
								.isAllNodesInRectReceived());
						break;
					} else {
						// check greedy to last grid(not in)
						if (this.isQueryRegionLastGrid(nextRect)) {
							this.setLast(cur);
							return true;
						}
					}
				} while (true);

				// logger.debug("zero neighborsInNextRect");
				// return false;
			} else // choose a next node
			{
				// next = this.chooseNextNodeByAdvance(neighborsInNextRect,
				// this.isDown(cur));
				next = this.chooseNextNodeByArea(cur, nextRect);
				queryAnswerEvent = new BroadcastEvent(cur, next,
						queryAnswerMessage, this);
				this.setAllNodesInRectRecieved(true);
				this.simulator.handle(queryAnswerEvent);
				this.setUserObject(cur, new HasSendAnswerUserObject(true));
				cur = next;
				curRect = nextRect;
			}

		} while (true);
	}

	public GPSRAttachment chooseNextNodeByArea(GPSRAttachment cur, Rect nextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRect);
		Vector neighborsInNextRect = cur.getNeighbors(this.getParam()
				.getRADIO_RANGE(), nairc);

		if (this.isQueryRegionLastGrid(nextRect)) {
			return this.chooseNextNodeByAdvance(neighborsInNextRect, this
					.isDown(cur));
		}

		double maxArea = Double.MIN_VALUE;
		GPSRAttachment maxAreaNeighbor = null;
		GPSRAttachment neighbor;
		Rect neighborNextRect;
		double neighborNextRectArea;

		for (int i = 0; i < neighborsInNextRect.size(); i++) {
			neighbor = (GPSRAttachment) neighborsInNextRect.elementAt(i);
			neighborNextRect = this.getNextGrid(neighbor.getNode().getPos(),
					nextRect);
			neighborNextRectArea = neighborNextRect.area();
			if (neighborNextRectArea > maxArea) {
				maxAreaNeighbor = neighbor;
				maxArea = neighborNextRectArea;
			}
		}
		return maxAreaNeighbor;
	}

	/*
	 * if nextRect has no nodes, then call this function to compute the next
	 * rect that would contains a node
	 */
	public Rect getNextGrid(Position curPos, Rect rect) {
		boolean isDown = this.isDown(curPos);
		double radioRange = this.getParam().getRADIO_RANGE();
		double dx1 = curPos.getX() - rect.getMinX();
		double dx2 = rect.getMaxX() - curPos.getX();
		double dy1 = Math.sqrt(radioRange * radioRange - dx1 * dx1);
		double dy2 = Math.sqrt(radioRange * radioRange - dx2 * dx2);
		double dy3 = Math.sqrt(radioRange * radioRange - rect.getWidth()
				* rect.getWidth());
		double mindy = Math.min(dy1, dy2);
		Rect nextRect;

		if (this.completeSubItinerary(rect)) {
			GridIndex gridIndex = this.getFirstSubRectGridIndexInNextItenerary(
					curPos, this.isDown(curPos));
			return this.grids[gridIndex.getX()][gridIndex.getY()];
		}

		if (isDown) {
			double miny = curPos.getY() - mindy;
			miny = Math.max(miny, this.getSpatialQuery().getRect().getMinY());
			miny = Math.max(miny, rect.getMinY() - dy3);
			nextRect = new Rect(rect.getMinX(), rect.getMaxX(), miny, rect
					.getMinY());
		} else {
			double maxy = curPos.getY() + mindy;
			maxy = Math.min(maxy, this.getSpatialQuery().getRect().getMaxY());
			maxy = Math.min(maxy, rect.getMaxY() + dy3);
			nextRect = new Rect(rect.getMinX(), rect.getMaxX(), rect.getMaxY(),
					maxy);
		}
		return nextRect;
	}

	public Rect getNextGrid(GPSRAttachment cur, Rect rect) {
		Position curPos = cur.getNode().getPos();
		boolean isDown = this.isDown(curPos);
		double radioRange = this.getParam().getRADIO_RANGE();
		double dx1 = curPos.getX() - rect.getMinX();
		double dx2 = rect.getMaxX() - curPos.getX();
		double dy1 = Math.sqrt(radioRange * radioRange - dx1 * dx1);
		double dy2 = Math.sqrt(radioRange * radioRange - dx2 * dx2);
		double dy3 = Math.sqrt(radioRange * radioRange - rect.getWidth()
				* rect.getWidth());
		double mindy = Math.min(dy1, dy2);
		Rect nextRect1;
		Rect nextRect2;

		// TODO next grid is not correct
		if (this.completeSubItinerary(rect)) {
			GridIndex gridIndex = this.getFirstSubRectGridIndexInNextItenerary(
					curPos, this.isDown(curPos));
			return this.grids[gridIndex.getX()][gridIndex.getY()];
		}

		if (isDown) {
			double miny = curPos.getY() - mindy;
			miny = Math.max(miny, this.getSpatialQuery().getRect().getMinY());
			nextRect1 = new Rect(rect.getMinX(), rect.getMaxX(), miny, rect
					.getMinY());
			miny = Math.max(miny, rect.getMinY() - dy3);
			nextRect2 = new Rect(rect.getMinX(), rect.getMaxX(), miny, rect
					.getMinY());
		} else {
			double maxy = curPos.getY() + mindy;
			maxy = Math.min(maxy, this.getSpatialQuery().getRect().getMaxY());
			nextRect1 = new Rect(rect.getMinX(), rect.getMaxX(),
					rect.getMaxY(), maxy);
			maxy = Math.min(maxy, rect.getMaxY() + dy3);
			nextRect2 = new Rect(rect.getMinX(), rect.getMaxX(),
					rect.getMaxY(), maxy);
		}

		if (this.completeSubItinerary(nextRect2)) {
			return nextRect2;
		}

		AttachmentInShapeCondition nairc;
		nairc = new AttachmentInShapeCondition(this.getSubQueryRegion(cur));
		Vector neighbors = cur.getNeighbors(this.getParam().getRADIO_RANGE(),
				nairc);
		GPSRAttachment neighbor;
		Vector advancedNeighbors = new Vector();

		// choose advanced neighbor
		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (GPSRAttachment) neighbors.elementAt(i);
			if (isDown && neighbor.getNode().getPos().getY() < curPos.getY()) {
				advancedNeighbors.add(neighbor);
			} else if (!isDown
					&& neighbor.getNode().getPos().getY() > curPos.getY()) {
				advancedNeighbors.add(neighbor);
			}
		}

		// no advanced neighbors
		if (advancedNeighbors.isEmpty()) {
			return nextRect1;
		}

		// sort advanced neighbors by advanced y distance in accend
		Collections.sort(advancedNeighbors, new AttachmentYAdvanceComparator(
				isDown));
		GPSRAttachment lastConnectedNode = null;
		GPSRAttachment prevNode;
		boolean connected = true;
		for (int i = 0; i < advancedNeighbors.size(); i++) {
			neighbor = (GPSRAttachment) advancedNeighbors.elementAt(i);
			for (int j = 0; j < i; j++) {
				prevNode = (GPSRAttachment) advancedNeighbors.elementAt(j);
				if (neighbor.getNode().getPos().distance(
						prevNode.getNode().getPos()) > this.getParam()
						.getRADIO_RANGE()) {
					connected = false;
					break;
				}
			}
			if (connected) {
				lastConnectedNode = neighbor;
			} else {
				break; // find a node that cannot connect so break from the
						// outer for loop
			}
		}
		if (isDown) {
			return new Rect(rect.getMinX(), rect.getMaxX(), lastConnectedNode
					.getNode().getPos().getY(), rect.getMinY());
		} else {
			return new Rect(rect.getMinX(), rect.getMaxX(), rect.getMaxY(),
					lastConnectedNode.getNode().getPos().getY());
		}
	}

	public boolean completeSubItinerary(Rect rect) {
		boolean down = this.isDown(rect.getCentre());
		Rect subQueryRegion = this.getSubQueryRegion(rect.getCentre());

		if (down) {
			if (subQueryRegion.getMinY() >= rect.getMinY()
					&& subQueryRegion.getMinY() <= rect.getMaxY())
				return true;
			else
				return false;
		} else {
			if (subQueryRegion.getMaxY() >= rect.getMinY()
					&& subQueryRegion.getMaxY() <= rect.getMaxY())
				return true;
			else
				return false;
		}
	}

	public boolean isQueryRegionLastGrid(Rect rect) {
		if (this.getSubQueryRegionNo(rect.getCentre()) != this
				.getSubQueryRegionNum() - 1)
			return false;
		else {
			return this.completeSubItinerary(rect);
		}
	}

	/*
	 * forward message to destRect, if destRect has no nodes, then continue to
	 * forward until there is a node.
	 */
	public ForwardToDestRectResult forwardToDestRect(GPSRAttachment source,
			Rect destRect, Message message) throws SensornetBaseException {
		GPSRAttachment cur = source;
		// note
		Rect nextRect = destRect;
		ForwardToDestRectResult ftdrr = null;
		DrawRectEvent drawRectEvent;

		logger.debug("start forwardToDestRect");

		this.rects.add(nextRect);
		drawRectEvent = new DrawRectEvent(this, nextRect);
		this.getSimulator().handle(drawRectEvent);

		GreedyForwardToRectEvent gftre = new GreedyForwardToRectEvent(cur,
				nextRect, message, this);
		this.getSimulator().handle(gftre);

		while (!gftre.isSuccess()) {
			nextRect = this.getNextDestRect(nextRect);
			if (nextRect == null) {
				return new ForwardToDestRectResult(false, null,
						(GPSRAttachment) gftre.getLastNode());
			}
			logger.debug("forward to next grid");
			cur = (GPSRAttachment) gftre.getLastNode();
			this.rects.add(nextRect);
			drawRectEvent = new DrawRectEvent(this, nextRect);
			this.getSimulator().handle(drawRectEvent);
			gftre = new GreedyForwardToRectEvent(cur, nextRect, message, this);
			this.getSimulator().handle(gftre);
		}
		ftdrr = new ForwardToDestRectResult(true, nextRect,
				(GPSRAttachment) gftre.getLastNode());
		logger.debug("end forwardToQueryRegion");
		return ftdrr;
	}

	private Rect getNextDestRect(Rect curdestRect) {
		Position curdestRectCentre = curdestRect.getCentre();
		Rect nextDestRect;

		if (this.completeSubItinerary(curdestRect)) {
			GridIndex gridIndex = this.getFirstSubRectGridIndexInNextItenerary(
					curdestRectCentre, this.isDown(curdestRectCentre));
			return this.grids[gridIndex.getX()][gridIndex.getY()];
		}
		// if curdestRect is the last dest rect
		if (this.isQueryRegionLastGrid(curdestRect)) {
			return null;
		}

		if (this.isDown(curdestRectCentre)) {
			double gh = this.getGridHeight();
			nextDestRect = new Rect(curdestRect.getMinX(), curdestRect
					.getMaxX(), Math.max(curdestRect.getMinY() - gh, this
					.getSpatialQuery().getRect().getMinY()), curdestRect
					.getMinY());
			return nextDestRect;
		} else {
			double gh = this.getGridHeight();
			nextDestRect = new Rect(curdestRect.getMinX(), curdestRect
					.getMaxX(), curdestRect.getMaxY(), Math.min(curdestRect
					.getMaxY()
					+ gh, this.getSpatialQuery().getRect().getMaxY()));
			return nextDestRect;
		}
	}

	// override GSA forwardToQueryRegion method
	public boolean forwardToQueryRegion() throws SensornetBaseException {
		GPSRAttachment sink = (GPSRAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.getName()));
		GPSRAttachment cur = sink;
		// note
		GridIndex nextGridIndex = new GridIndex(0, this.getYGridNum() - 1);
		Rect destRect = this.grids[nextGridIndex.getX()][nextGridIndex.getY()];

		logger.debug("start forwardToQueryRegion");
		Message queryMessage = new Message(this.getItineraryAlgParam()
				.getQuerySize(), null);
		ForwardToDestRectResult ftdrr = this.forwardToDestRect(cur, destRect,
				queryMessage);

		if (!ftdrr.isSuccess()) {
			logger.debug("cannot forward to query regeion");
			return false;
		}
		this.setFirst(ftdrr.getLastNodeAttachment());
		this.setFirstDestRect(ftdrr.getDestRect());
		logger.debug("end forwardToQueryRegion");
		return true;
	}

	public boolean run() {
		try {
			if (!this.forwardToQueryRegion()) {
				logger.warn("cannot forwardToQueryRegion");
				return false;
			}

			if (!this.gridTraverse()) {
				logger.warn("cannot gridTranverse");
				return false;
			} else {
				if (!this.forwardBackToSink()) {
					logger.warn("cannot forwardBackToSink");
					return false;
				}
			}
		} catch (HasNoEnergyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getAlgBlackBox().setAttachmentWhichHasNoEnergy(
					e.getAttachment());
			return false;
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.getStatistics().setSuccess(true);
		return true;
	}

	public Vector getRects() {
		return rects;
	}

	public void setRects(Vector rects) {
		this.rects = rects;
	}

}
