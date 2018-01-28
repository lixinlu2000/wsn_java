package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.HasSendAnswerUserObject;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.DrawRectEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToRectExEvent;
import NHSensor.NHSensorSim.events.RadioEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class DGSAAlg extends IWQEAlg {
	static Logger logger = Logger.getLogger(DGSAAlg.class);

	public static final String NAME = "DGSA";
	private Vector rects = new Vector();

	public DGSAAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public DGSAAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, DGSAAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public DGSAAlg() {
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
			nextRect = this.getNextGrid(cur.getNode().getPos(), curRect);
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
