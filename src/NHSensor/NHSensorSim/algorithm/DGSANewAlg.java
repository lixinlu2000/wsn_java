package NHSensor.NHSensorSim.algorithm;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.AttachmentToDesDistanceRank;
import NHSensor.NHSensorSim.core.ForwardToDestRectResult;
import NHSensor.NHSensorSim.core.GetNextGridResult;
import NHSensor.NHSensorSim.core.HasSendAnswerUserObject;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.NodeAngleComparator;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.core.TopKQueryProcessor;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.DrawRectEvent;
import NHSensor.NHSensorSim.events.EventsUtil;
import NHSensor.NHSensorSim.events.GreedyForwardToRectExNewEvent;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;
import NHSensor.NHSensorSim.events.RadioEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class DGSANewAlg extends IWQEAlg {
	static Logger logger = Logger.getLogger(DGSANewAlg.class);
	public static final int CHOOSE_NEXT_NODE_BY_AREA = 0;
	public static final int CHOOSE_NEXT_NODE_BY_RANDOM = 1;
	public static final int CHOOSE_NEXT_NODE_BY_ADVANCE = 2;

	public static final String NAME = "DGSA_NEW";
	private Vector rects = new Vector();
	private Rect firstGrid;
	private boolean isCollectDataNodeData = true;
	private int chooseNextNodeStrategy = DGSANewAlg.CHOOSE_NEXT_NODE_BY_AREA;

	public int getChooseNextNodeStrategy() {
		return chooseNextNodeStrategy;
	}

	public void setChooseNextNodeStrategy(int chooseNextNodeStrategy) {
		this.chooseNextNodeStrategy = chooseNextNodeStrategy;
	}

	public Rect getFirstGrid() {
		return firstGrid;
	}

	public void setFirstGrid(Rect firstGrid) {
		this.firstGrid = firstGrid;
	}

	public DGSANewAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public DGSANewAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, DGSANewAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public DGSANewAlg() {
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

	public boolean gridTraverse() throws SensornetBaseException {
		GPSRAttachment cur = this.getFirst();
		Rect nextRect = this.getFirstGrid();

		GPSRAttachment next = null;
		GetNextGridResult getNextGridResult;
		Rect nextNextRect;

		AttachmentInShapeCondition nairc;
		Vector neighborsInNextRect;
		Vector neighborsInNextNextRect;
		GPSRAttachment neighbor;

		Message answerMessage = new Message(this.getItineraryAlgParam()
				.getAnswerSize(), null);
		Message queryAnswerMessage = new Message(this.getItineraryAlgParam()
				.getQueryAndPatialAnswerSize(), null);
		RadioEvent answerEvent;
		RadioEvent queryAnswerEvent;
		ItineraryNodeEvent itineraryNodeEvent;
		DrawRectEvent drawRectEvent;
		ForwardToDestRectResult forwardToDestRectResult;

		do {
			nairc = new AttachmentInShapeCondition(nextRect);
			neighborsInNextRect = cur.getNeighbors(this.getParam()
					.getRADIO_RANGE(), nairc);
			this.rects.add(nextRect);
			drawRectEvent = new DrawRectEvent(this, nextRect);
			this.getSimulator().handle(drawRectEvent);

			// choose a cluster in curRect
			if (this.getChooseNextNodeStrategy() == DGSANewAlg.CHOOSE_NEXT_NODE_BY_AREA) {
				next = this.chooseNextNodeByArea(cur, nextRect);
			} else if (this.getChooseNextNodeStrategy() == DGSANewAlg.CHOOSE_NEXT_NODE_BY_RANDOM) {
				next = this.chooseNextNodeByRandom(cur, nextRect);
			} else if (this.getChooseNextNodeStrategy() == DGSANewAlg.CHOOSE_NEXT_NODE_BY_ADVANCE) {
				next = this.chooseNextNodeByAdvance(cur, nextRect);
			} else {
				throw new SensornetBaseException(
						"cannot recognize choose next node strategy");
			}

			queryAnswerEvent = new BroadcastEvent(cur, next,
					queryAnswerMessage, this);
			this.simulator.handle(queryAnswerEvent);
			this.setUserObject(cur, new HasSendAnswerUserObject(true));
			itineraryNodeEvent = new ItineraryNodeEvent(cur, next, this);
			this.simulator.handle(itineraryNodeEvent);

			if (this.isCollectDataNodeData) {
				neighborsInNextRect.removeElement(next);
				Collections.sort(neighborsInNextRect, new NodeAngleComparator(
						next.getNode().getPos()));
				for (int i = 0; i < neighborsInNextRect.size(); i++) {
					// must be answers
					neighbor = (GPSRAttachment) neighborsInNextRect
							.elementAt(i);
					boolean hasSend = this.getUserObject(neighbor)
							.isHasSendAnswer();
					if (hasSend)
						continue;
					answerEvent = new BroadcastEvent(neighbor, next,
							answerMessage, this);
					itineraryNodeEvent.addDataNode(neighbor);
					this.simulator.handle(answerEvent);
					// set has send
					this.setUserObject(neighbor, new HasSendAnswerUserObject(
							true));
				}
			}

			// last node so reach the end
			if (this.isQueryRegionLastGrid(nextRect)) {
				this.setLast(next);
				return true;
			}

			// get next grid
			getNextGridResult = this.getNextGrid(next.getNode().getPos(),
					nextRect);
			nextNextRect = getNextGridResult.getNextRect();
			// reach the end of the query region
			if (nextNextRect == null) {
				this.setLast(next);
				return true;
			}
			nairc = new AttachmentInShapeCondition(nextNextRect);
			neighborsInNextNextRect = next.getNeighbors(this.getParam()
					.getRADIO_RANGE(), nairc);

			if (!getNextGridResult.isAllNodesReceived()
					|| neighborsInNextNextRect.size() == 0) {
				forwardToDestRectResult = this.forwardToDestRect(next,
						nextNextRect, queryAnswerMessage);
				if (forwardToDestRectResult.isSuccess()) {
					cur = forwardToDestRectResult.getLastNodeAttachment();
					nextRect = forwardToDestRectResult.getDestRect();
				} else {
					cur = forwardToDestRectResult.getLastNodeAttachment();
					this.setLast(cur);
					return true;
				}
			} else // choose a next node
			{
				// next = this.chooseNextNodeByAdvance(neighborsInNextRect,
				// this.isDown(cur));
				cur = next;
				nextRect = nextNextRect;
			}

		} while (true);
	}

	public GPSRAttachment chooseNextNodeByArea(GPSRAttachment cur, Rect nextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRect);
		Vector neighborsInNextRect = cur.getNeighbors(this.getParam()
				.getRADIO_RANGE(), nairc);

		// bug add this code
		if (nairc.isTrue(cur))
			neighborsInNextRect.add(cur);

		if (this.isQueryRegionLastGrid(nextRect)) {
			return this.chooseNextNodeByAdvance(neighborsInNextRect, this
					.isDown(cur));
		}

		double maxArea = Double.MIN_VALUE;
		boolean isFound = false;
		GPSRAttachment maxAreaNeighbor = null;
		GPSRAttachment neighbor;
		Rect neighborNextRect;
		GetNextGridResult neighborGetNextGridResult;
		double neighborNextRectArea;

		for (int i = 0; i < neighborsInNextRect.size(); i++) {
			neighbor = (GPSRAttachment) neighborsInNextRect.elementAt(i);
			neighborGetNextGridResult = this.getNextGrid(neighbor.getNode()
					.getPos(), nextRect);

			if (!neighborGetNextGridResult.isAllNodesReceived())
				continue;
			neighborNextRect = neighborGetNextGridResult.getNextRect();
			neighborNextRectArea = neighborNextRect.area();
			if (neighborNextRectArea > maxArea) {
				maxAreaNeighbor = neighbor;
				maxArea = neighborNextRectArea;
				isFound = true;
			}
		}
		if (isFound) {
			return maxAreaNeighbor;
		} else {
			//
			if (neighborsInNextRect.size() > 0) {
				neighbor = (GPSRAttachment) neighborsInNextRect.elementAt(0);
				neighborGetNextGridResult = this.getNextGrid(neighbor.getNode()
						.getPos(), nextRect);

				neighborNextRect = neighborGetNextGridResult.getNextRect();

				TopKQueryProcessor topkP = new TopKQueryProcessor(
						neighborsInNextRect, new AttachmentToDesDistanceRank(
								neighborNextRect.getCentre()));
				return (GPSRAttachment) topkP.getTop();
				// return (GPSRAttachment)neighborsInNextRect.elementAt(0);
			} else
				return null;
		}
	}

	public GPSRAttachment chooseNextNodeByRandom(GPSRAttachment cur,
			Rect nextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRect);
		Vector neighborsInNextRect = cur.getNeighbors(this.getParam()
				.getRADIO_RANGE(), nairc);

		// bug add this code
		if (nairc.isTrue(cur))
			neighborsInNextRect.add(cur);

		Random r = new Random();
		int randomIndex = r.nextInt(neighborsInNextRect.size());

		return (GPSRAttachment) neighborsInNextRect.elementAt(randomIndex);

	}

	public GPSRAttachment chooseNextNodeByAdvance(GPSRAttachment cur,
			Rect nextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRect);
		Vector neighborsInNextRect = cur.getNeighbors(this.getParam()
				.getRADIO_RANGE(), nairc);

		// bug add this code
		if (nairc.isTrue(cur))
			neighborsInNextRect.add(cur);

		return this.chooseNextNodeByAdvance(neighborsInNextRect, this
				.isDown(nextRect.getCentre()));
	}

	private Rect getRightGrid(Position curPos) {
		double radioRange = this.getParam().getRADIO_RANGE();
		double w = this.getSubQueryRegionWidth();
		double h = Math.sqrt(radioRange * radioRange - w * w);

		GridIndex gridIndex = this.getFirstSubRectGridIndexInNextItenerary(
				curPos, this.isDown(curPos));
		Rect nextGrid = this.grids[gridIndex.getX()][gridIndex.getY()];
		if (this.isDown(curPos)) {
			double maxy = nextGrid.getMinY() + h;
			nextGrid = new Rect(nextGrid.getMinX(), nextGrid.getMaxX(),
					nextGrid.getMinY(), maxy);
			return nextGrid;
		} else {
			double miny = nextGrid.getMaxY() - h;
			nextGrid = new Rect(nextGrid.getMinX(), nextGrid.getMaxX(), miny,
					nextGrid.getMaxY());
			return nextGrid;
		}
	}

	public GetNextGridResult getNextGrid(Position curPos, Rect rect) {
		boolean isDown = this.isDown(curPos);
		double radioRange = this.getParam().getRADIO_RANGE();
		double dx1 = curPos.getX() - rect.getMinX();
		double dx2 = rect.getMaxX() - curPos.getX();
		double dy1 = Math.sqrt(radioRange * radioRange - dx1 * dx1);
		double dy2 = Math.sqrt(radioRange * radioRange - dx2 * dx2);
		double dy3 = Math.sqrt(radioRange * radioRange - rect.getWidth()
				* rect.getWidth());
		// the biggest distance ralative to current node
		double mindy = Math.min(dy1, dy2);
		Rect nextRect;
		GetNextGridResult getNextGridResult;

		// error
		/*
		 * if(this.completeSubItinerary(rect)) { GridIndex gridIndex =
		 * this.getFirstSubRectGridIndexInNextItenerary(curPos,
		 * this.isDown(curPos)); return
		 * this.grids[gridIndex.getX()][gridIndex.getY()]; }
		 */
		// new version
		if (this.isQueryRegionLastGrid(rect)) {
			getNextGridResult = new GetNextGridResult(null, false);
			return getNextGridResult;
		}

		if (this.completeSubItinerary(rect)) {
			Rect nextGrid = this.getRightGrid(curPos);
			double subQueryRegionWidth = this.getSubQueryRegionWidth();
			double w = dx2 + subQueryRegionWidth;
			if (w >= this.getParam().getRADIO_RANGE()) {
				getNextGridResult = new GetNextGridResult(nextGrid, false);
				return getNextGridResult;
			} else {
				double h = Math.sqrt(radioRange * radioRange - w * w);
				double y1 = curPos.getY() - h;
				double y2 = curPos.getY() + h;

				if (this.isDown(curPos)) {
					if (y1 > nextGrid.getMinY()) {
						getNextGridResult = new GetNextGridResult(nextGrid,
								false);
						logger.debug("DOWN RIGHT " + nextGrid.toString());
					} else {
						nextRect = new Rect(nextGrid.getMinX(), nextGrid
								.getMaxX(), nextGrid.getMinY(), Math.min(y2,
								nextGrid.getMaxY()));
						getNextGridResult = new GetNextGridResult(nextRect,
								true);
						logger.debug("DOWN RIGHT " + nextRect.toString());
					}
				} else {
					if (y2 < nextGrid.getMaxY()) {
						getNextGridResult = new GetNextGridResult(nextGrid,
								false);
						logger.debug("TOP RIGHT " + nextGrid.toString());
					} else {
						nextRect = new Rect(nextGrid.getMinX(), nextGrid
								.getMaxX(), Math.max(y1, nextGrid.getMinY()),
								nextGrid.getMaxY());
						getNextGridResult = new GetNextGridResult(nextRect,
								true);
						logger.debug("TOP RIGHT " + nextRect.toString());
					}
				}
			}
			return getNextGridResult;
		}

		if (isDown) {
			double miny = curPos.getY() - mindy;
			miny = Math.max(miny, this.getSpatialQuery().getRect().getMinY());
			miny = Math.max(miny, rect.getMinY() - dy3);
			nextRect = new Rect(rect.getMinX(), rect.getMaxX(), miny, rect
					.getMinY());
			logger.debug("DOWN " + nextRect.toString());
		} else {
			double maxy = curPos.getY() + mindy;
			maxy = Math.min(maxy, this.getSpatialQuery().getRect().getMaxY());
			maxy = Math.min(maxy, rect.getMaxY() + dy3);
			nextRect = new Rect(rect.getMinX(), rect.getMaxX(), rect.getMaxY(),
					maxy);
			logger.debug("TOP " + nextRect.toString());
		}
		getNextGridResult = new GetNextGridResult(nextRect, true);
		return getNextGridResult;
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

		GreedyForwardToRectExNewEvent gftre = new GreedyForwardToRectExNewEvent(
				cur, nextRect, message, this);
		this.getSimulator().handle(gftre);
		this.getSimulator().handle(
				EventsUtil
						.NeighborAttachmentsToItinerayNodeEvents(gftre, false));

		while (!gftre.isSuccess() || !gftre.isHasNodesInRect()) {
			this.rects.add(nextRect);
			drawRectEvent = new DrawRectEvent(this, nextRect);
			this.getSimulator().handle(drawRectEvent);

			nextRect = this.getNextDestRect(nextRect);
			if (nextRect == null) {
				return new ForwardToDestRectResult(false, null,
						(GPSRAttachment) gftre.getLastNode());
			}
			logger.debug("forward to next rect");
			cur = (GPSRAttachment) gftre.getLastNode();
			gftre = new GreedyForwardToRectExNewEvent(cur, nextRect, message,
					this);
			this.getSimulator().handle(gftre);
			this.getSimulator().handle(
					EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftre,
							false));
		}
		ftdrr = new ForwardToDestRectResult(true, gftre.getRect(),
				(GPSRAttachment) gftre.getLastNode());
		logger.debug("end forwardToQueryRegion");
		return ftdrr;
	}

	private Rect getNextDestRect(Rect curdestRect) {
		Position curdestRectCentre = curdestRect.getCentre();
		Rect nextDestRect;

		// if curdestRect is the last dest rect
		if (this.isQueryRegionLastGrid(curdestRect)) {
			return null;
		}

		if (this.completeSubItinerary(curdestRect)) {
			return this.getRightGrid(curdestRectCentre);
		}

		double radioRange = this.getParam().getRADIO_RANGE();
		double gw = this.getSubQueryRegionWidth();
		double gh = Math.sqrt(radioRange * radioRange - gw * gw);

		if (this.isDown(curdestRectCentre)) {
			nextDestRect = new Rect(curdestRect.getMinX(), curdestRect
					.getMaxX(), Math.max(curdestRect.getMinY() - gh, this
					.getSpatialQuery().getRect().getMinY()), curdestRect
					.getMinY());
			return nextDestRect;
		} else {
			nextDestRect = new Rect(curdestRect.getMinX(), curdestRect
					.getMaxX(), curdestRect.getMaxY(), Math.min(curdestRect
					.getMaxY()
					+ gh, this.getSpatialQuery().getRect().getMaxY()));
			return nextDestRect;
		}
	}

	public Rect getFirstDestRect() {
		double radioRange = this.getParam().getRADIO_RANGE();
		double w = this.getSubQueryRegionWidth();
		double h = Math.sqrt(radioRange * radioRange - w * w);
		GridIndex nextGridIndex = new GridIndex(0, this.getYGridNum() - 1);
		Rect nextGrid = this.grids[nextGridIndex.getX()][nextGridIndex.getY()];
		double miny = nextGrid.getMaxY() - h;
		nextGrid = new Rect(nextGrid.getMinX(), nextGrid.getMaxX(), miny,
				nextGrid.getMaxY());
		return nextGrid;
	}

	public boolean forwardToQueryRegion() throws SensornetBaseException {
		GPSRAttachment sink = (GPSRAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.getName()));
		GPSRAttachment cur = sink;
		// note
		Rect nextRect = this.getFirstDestRect();

		logger.debug("start forwardToQueryRegion");
		Message queryMessage = new Message(this.getItineraryAlgParam()
				.getQuerySize(), null);
		GreedyForwardToRectExNewEvent gftre = new GreedyForwardToRectExNewEvent(
				cur, nextRect, queryMessage, this);
		this.getSimulator().handle(gftre);
		this.getSimulator().handle(
				EventsUtil
						.NeighborAttachmentsToItinerayNodeEvents(gftre, false));

		while (!gftre.isSuccess() || !gftre.isHasNodesInRect()) {
			if (this.isQueryRegionLastGrid(nextRect)) {
				logger
						.warn("try to  reach the last rect  but cannot greedy to it");
				return false;
			}

			logger.debug("forward to next grid");
			cur = (GPSRAttachment) gftre.getLastNode();
			nextRect = this.getNextDestRect(nextRect);
			if (nextRect == null) {
				logger.warn("forwardToQueryRegion but reach the last rect");
				return false;
			}
			gftre = new GreedyForwardToRectExNewEvent(cur, nextRect,
					queryMessage, this);
			this.getSimulator().handle(gftre);
			this.getSimulator().handle(
					EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftre,
							false));
		}
		this.setFirst((GPSRAttachment) gftre.getLastNode());
		this.setFirstGrid(gftre.getRect());
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
				this.getStatistics().setSuccess(true);
				if (!this.forwardBackToSink()) {
					logger.warn("cannot forwardBackToSink");
					return false;
				}
			}
			return true;
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
		/*
		 * this.getStatistics().setSuccess(true); return true;
		 */}

	public Vector getRects() {
		return rects;
	}

	public void setRects(Vector rects) {
		this.rects = rects;
	}

}
