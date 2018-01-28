package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.HasSendAnswerUserObject;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.EventsUtil;
import NHSensor.NHSensorSim.events.GreedyForwardToRectExEvent;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;
import NHSensor.NHSensorSim.events.RadioEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class IWQEAlg extends GSAAlg {
	static Logger logger = Logger.getLogger(IWQEAlg.class);

	public final static String NAME = "IWQE";
	private String nextStrategy = "MPI";
	protected int subQueryRegionNum;
	protected Vector subQueryRegions;
	protected double subQueryRegionWidth;
	private GPSRAttachment first;
	private GPSRAttachment last;
	private boolean useGSAGreedy = true;
	private Vector firstPhaseRoute = new Vector();

	public boolean isUseGSAGreedy() {
		return useGSAGreedy;
	}

	public void setUseGSAGreedy(boolean useGSAGreedy) {
		this.useGSAGreedy = useGSAGreedy;
	}

	public int getSubQueryRegionNum() {
		return subQueryRegionNum;
	}

	public void setSubQueryRegionNum(int subQueryRegionNum) {
		this.subQueryRegionNum = subQueryRegionNum;
	}

	public IWQEAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public IWQEAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, IWQEAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public IWQEAlg() {
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
				* this.getParam().getRADIO_RANGE() - gridWidth * gridWidth);
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
				* this.getParam().getRADIO_RANGE() - gridWidth * gridWidth);
		this.initGridWithGridSize(gridWidth, gridHeight);
	}

	public boolean forwardBackToSink() throws SensornetBaseException {
		GPSRAttachment sink = (GPSRAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.name));

		// GPSR query+answer
		Message answerMessage = new Message(this.getItineraryAlgParam()
				.getResultSize(), null);
		GPSR gpsr = new GPSR(this.getLast(), sink.getNode().getPos(),
				answerMessage, this);
		this.getSimulator().handle(gpsr);

		Vector nas = gpsr.getPath();
		nas.add(0, gpsr.getRoot());
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(nas, true));
		return gpsr.isSuccess();
	}

	public boolean iteneraryTraverse() throws SensornetBaseException {
		GPSRAttachment cur = this.getFirst();
		GPSRAttachment next = null;// ..getNextByMPI(cur, this.isDown(cur));

		Message queryMsg = new Message(this.getItineraryAlgParam()
				.getQuerySize(), null);
		Message queryAnswerMsg = new Message(this.getItineraryAlgParam()
				.getQueryAndPatialAnswerSize(), null);
		RadioEvent queryEvent;
		RadioEvent queryAnswerEvent;
		GreedyForwardToRectExEvent gftre;
		ItineraryNodeEvent itineraryNodeEvent;
		Vector dataNodes = null;

		do {
			// todo check if there is any nodes in cur's neighbor

			queryEvent = new BroadcastEvent(cur, null, queryMsg, this);
			this.getSimulator().handle(queryEvent);

			// collect answer;answerNodes send answer

			// dataNodes = this.collectAnswers(cur, cur.getRadioRange());
			this.collectAnswers(cur, cur.getRadioRange());

			if (this.myCompleteSubItinerary(cur, this.isDown(cur))) {
				if (this.getSubQueryRegionNo(cur) == this
						.getSubQueryRegionNum() - 1) {
					// set last node
					this.setLast(cur);
					return true;
				} else // change itenerary
				{
					GridIndex nextGridIndex = this
							.getFirstSubRectGridIndexInNextItenerary(cur, this
									.isDown(cur));//this.getNextGrid(curGridIndex
													// );
					Rect nextRect = this.grids[nextGridIndex.getX()][nextGridIndex
							.getY()];

					this.setUserObject(cur, new HasSendAnswerUserObject(true));
					gftre = new GreedyForwardToRectExEvent(cur, nextRect,
							queryAnswerMsg, this);
					this.getSimulator().handle(gftre);
					this.getSimulator().handle(
							EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
									gftre, false, null));

					while (!gftre.isSuccess()) {
						if (this.getLastGrid().equal(nextGridIndex)) {
							this.setLast((GPSRAttachment) gftre.getLastNode());
							return true;
							// logger.warn(
							// "try to  reach the last grid  but cannot greedy to it"
							// );
							// return false;
						}

						cur = (GPSRAttachment) gftre.getLastNode();
						nextGridIndex = this.getNextGrid(nextGridIndex);
						nextRect = this.grids[nextGridIndex.getX()][nextGridIndex
								.getY()];
						gftre = new GreedyForwardToRectExEvent(cur, nextRect,
								queryAnswerMsg, this);
						this.getSimulator().handle(gftre);

						this
								.getSimulator()
								.handle(
										EventsUtil
												.NeighborAttachmentsToItinerayNodeEvents(
														gftre, false, null));
					}
					cur = (GPSRAttachment) gftre.getLastNode();
				}
			} else {
				next = this.getNext(cur, this.isDown(cur));
				if (next == cur) {
					if (!this.isUseGSAGreedy())
						return false;
					// get annother next
					// logger.debug("cannot getNext by " +
					// this.getNextStrategy());
					// return false;
					GridIndex curGridIndex = this.getNodeGridIndex(cur);
					GridIndex nextGridIndex = this.getNextGrid(curGridIndex);
					Rect nextRect = this.grids[nextGridIndex.getX()][nextGridIndex
							.getY()];

					this.setUserObject(cur, new HasSendAnswerUserObject(true));
					gftre = new GreedyForwardToRectExEvent(cur, nextRect,
							queryAnswerMsg, this);
					this.getSimulator().handle(gftre);

					this.getSimulator().handle(
							EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
									gftre, false, null));

					while (!gftre.isSuccess()) {
						if (this.getLastGrid().equal(nextGridIndex)) {
							// logger.warn(
							// "try to  reach the last grid  but cannot greedy to it"
							// );
							this.setLast((GPSRAttachment) gftre.getLastNode());
							return true;
						}

						cur = (GPSRAttachment) gftre.getLastNode();
						nextGridIndex = this.getNextGrid(nextGridIndex);
						nextRect = this.grids[nextGridIndex.getX()][nextGridIndex
								.getY()];
						gftre = new GreedyForwardToRectExEvent(cur, nextRect,
								queryAnswerMsg, this);
						this.getSimulator().handle(gftre);
						this
								.getSimulator()
								.handle(
										EventsUtil
												.NeighborAttachmentsToItinerayNodeEvents(
														gftre, false, null));
					}
					cur = (GPSRAttachment) gftre.getLastNode();

				} else {
					queryAnswerEvent = new BroadcastEvent(cur, next,
							queryAnswerMsg, this);
					this.setUserObject(cur, new HasSendAnswerUserObject(true));
					this.getSimulator().handle(queryAnswerEvent);

					itineraryNodeEvent = new ItineraryNodeEvent(cur, next, this);
					this.getSimulator().handle(itineraryNodeEvent);

					cur = next;
					dataNodes = null;
					// get answer
				}
			}
		} while (true);
	}

	// return nodes in cur's neighbors which send answer
	public Vector collectAnswers(GPSRAttachment cur, double radioRange)
			throws SensornetBaseException {
		Rect curSubQueryRegion = this.getSubQueryRegion(cur);
		Vector neighbors = cur.getNeighbors(radioRange);
		GPSRAttachment neighbor;
		Message answerMsg = new Message(this.getItineraryAlgParam()
				.getAnswerSize(), null);
		RadioEvent answerEvent;
		Vector dataNodes = new Vector();

		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (GPSRAttachment) neighbors.elementAt(i);
			HasSendAnswerUserObject uo = this.getUserObject(neighbor);

			if (!neighbor.getNode().getPos().in(curSubQueryRegion)
					|| uo.isHasSendAnswer()) {
				continue;
			}
			answerEvent = new BroadcastEvent(neighbor, cur, answerMsg, this);
			this.setUserObject(neighbor, new HasSendAnswerUserObject(true));
			this.simulator.handle(answerEvent);
			dataNodes.add(neighbor);
		}
		return dataNodes;
	}

	public GPSRAttachment getNext(GPSRAttachment cur, boolean down) {
		if (this.getNextStrategy().equalsIgnoreCase("MPI")) {
			return this.getNextByMPI(cur, down);
		} else
			return this.getNextByCTI(cur, down);
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
	}

	public boolean run() {
		try {
			if (!this.forwardToQueryRegion()) {
				logger.warn("cannot forwardToQueryRegion");
				return false;
			}

			if (this.iteneraryTraverse()) {
				if (this.forwardBackToSink()) {
					this.getStatistics().setSuccess(true);
					return true;
				} else {
					logger.warn("cannot forwardBackToSink");
					return false;
				}
			} else {
				logger.warn("cannot iteneraryTraverse");
				return false;
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
	}

	public GPSRAttachment getNextIteneraryFirstNode(GPSRAttachment cur) {
		int neighborSubQueryRegionNo = this.getSubQueryRegionNo(cur) + 1;
		Rect neighborSubQueryRegion = (Rect) this.getSubQueryRegions()
				.elementAt(neighborSubQueryRegionNo);

		Vector neighbors = cur.getNeighbors();
		GPSRAttachment neighbor;
		double y;
		double miny = Double.MAX_VALUE;
		boolean neighborItenararyDown = !this.isDown(cur);
		GPSRAttachment next = cur;

		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (GPSRAttachment) neighbors.elementAt(i);
			if (!neighbor.getNode().getPos().in(neighborSubQueryRegion)) {
				continue;
			} else {
				y = neighbor.getNode().getPos().getY();
				if (!neighborItenararyDown)
					y = -y;
				if (y < miny) {
					miny = y;
					next = neighbor;
				}
			}
		}
		return next;
	}

	public boolean isDown(Position pos) {
		int curSubQueryRegionNo = this.getSubQueryRegionNo(pos);
		if (curSubQueryRegionNo % 2 == 0)
			return true;
		else
			return false;
	}

	public boolean isDown(GPSRAttachment cur) {
		int curSubQueryRegionNo = this.getSubQueryRegionNo(cur);
		if (curSubQueryRegionNo % 2 == 0)
			return true;
		else
			return false;
	}

	public boolean completeSubItinerary(GPSRAttachment cur, boolean down) {
		// TODO greedy forward
		if (cur == this.getNext(cur, this.isDown(cur)))
			return true;
		else
			return false;
	}

	public boolean myCompleteSubItinerary(GPSRAttachment cur, boolean down) {
		Rect curSubQueryRegion = this.getSubQueryRegion(cur);
		Position p1, p2;
		Position curPos = cur.getNode().getPos();
		double radioRange = cur.getRadioRange();

		if (down) {
			p1 = curSubQueryRegion.getLB();
			p2 = curSubQueryRegion.getRB();
		} else {
			p1 = curSubQueryRegion.getLT();
			p2 = curSubQueryRegion.getRT();
		}

		if (p1.distance(curPos) <= radioRange
				&& p2.distance(curPos) <= radioRange) {
			return true;
		} else {
			return false;
		}
	}

	public Node getFirstNodeInRect(Rect rect, boolean down) {
		Node firstNode = null;
		Node curNode;
		Vector nodes = this.getNetwork().getNodesInRect(rect);
		double miny = Double.MAX_VALUE;
		double cury = 0;
		for (int i = 0; i < nodes.size(); i++) {
			curNode = (Node) nodes.elementAt(i);
			cury = curNode.getPos().getY();
			if (down)
				cury = -cury;
			if (cury < miny) {
				miny = cury;
				firstNode = curNode;
			}
		}
		return firstNode;
	}

	public Rect getFirstSubRectInQueryRegion() {
		Rect rect = (Rect) (this.getSubQueryRegions().elementAt(0));
		return this.getFirstSubRectInRect(rect, true);
	}

	public Rect getFirstSubRectInRect(Rect rect, boolean down) {
		Rect subRect;
		double subRectWidth = rect.getWidth();
		double y = Math.sqrt(this.getParam().getRADIO_RANGE()
				* this.getParam().getRADIO_RANGE() - subRectWidth
				* subRectWidth);
		if (down) {
			subRect = new Rect(rect.getMinX(), rect.getMaxX(), rect.getMaxY()
					- y, rect.getMaxY());
		} else {
			subRect = new Rect(rect.getMinX(), rect.getMaxX(), rect.getMinY(),
					rect.getMinY() + y);
		}
		return subRect;
	}

	public Rect getFirstSubRectInNextItenerary(GPSRAttachment cur, boolean down) {
		int nextSubQueryRegionNo = this.getSubQueryRegionNo(cur) + 1;
		Rect nextRect = (Rect) this.getSubQueryRegions().elementAt(
				nextSubQueryRegionNo);
		boolean nexDown = !down;
		return this.getFirstSubRectInRect(nextRect, nexDown);
	}

	public GridIndex getFirstSubRectGridIndexInNextItenerary(
			GPSRAttachment cur, boolean down) {
		int gridX = this.getSubQueryRegionNo(cur) + 1;
		int gridY;

		if (down)
			gridY = 0;
		else
			gridY = this.getYGridNum() - 1;

		return new GridIndex(gridX, gridY);
	}

	public GridIndex getFirstSubRectGridIndexInNextItenerary(Position pos,
			boolean down) {
		int gridX = this.getSubQueryRegionNo(pos) + 1;
		int gridY;

		if (down)
			gridY = 0;
		else
			gridY = this.getYGridNum() - 1;

		return new GridIndex(gridX, gridY);
	}

	public Vector getSubQueryRegions() {
		return this.subQueryRegions;
	}

	public Vector caculateSubQueryRegionsByRegionNum(int regionNum) {
		Vector subRegions = new Vector();
		Rect queryRect = this.getSpatialQuery().getRect();
		double minx = queryRect.getMinX();
		double miny = queryRect.getMinY();
		double maxy = queryRect.getMaxY();
		Rect subRect;
		double subRectWidth = queryRect.getWidth() / regionNum;

		for (int i = 0; i < regionNum; i++) {
			subRect = new Rect(minx + i * subRectWidth, minx + (i + 1)
					* subRectWidth, miny, maxy);
			subRegions.add(subRect);
		}
		return subRegions;
	}

	public int caculateSubQueryRegionNum(double regionWidth) {
		Rect queryRect = this.getSpatialQuery().getRect();
		return (int) Math.ceil(queryRect.getWidth() / regionWidth);
	}

	public Vector caculateSubQueryRegionsByRegionWidth(double regionWidth) {
		Vector subRegions = new Vector();
		Rect queryRect = this.getSpatialQuery().getRect();
		double minx = queryRect.getMinX();
		double miny = queryRect.getMinY();
		double maxy = queryRect.getMaxY();
		Rect subRect;
		int regionNum = (int) Math.ceil(queryRect.getWidth() / regionWidth);

		for (int i = 0; i < regionNum - 1; i++) {
			subRect = new Rect(minx + i * regionWidth, minx + (i + 1)
					* regionWidth, miny, maxy);
			subRegions.add(subRect);
		}
		// create the last rect
		subRect = new Rect(minx + (regionNum - 1) * regionWidth, minx
				+ queryRect.getWidth(), miny, maxy);
		subRegions.add(subRect);

		return subRegions;

	}

	public double getSubQueryRegionWidth() {
		return this.subQueryRegionWidth;
	}

	public double caculateSubQueryRegionWidth(int subQueryRegionNum) {
		Rect queryRect = this.getSpatialQuery().getRect();
		double subRectWidth = queryRect.getWidth() / subQueryRegionNum;
		return subRectWidth;
	}

	public int getSubQueryRegionNo(Position pos) {
		if (!pos.in(this.getSpatialQuery().getRect()))
			return -1;
		if (pos.getX() == this.getSpatialQuery().getRect().getMaxX())
			return this.getSubQueryRegionNum() - 1;
		int no = (int) ((pos.getX() - this.getSpatialQuery().getRect()
				.getMinX()) / this.getSubQueryRegionWidth());
		return no;
	}

	public int getSubQueryRegionNo(GPSRAttachment cur) {
		return this.getSubQueryRegionNo(cur.getNode().getPos());
	}

	public Rect getSubQueryRegion(GPSRAttachment cur) {
		int curSubQueryRegionNo = this.getSubQueryRegionNo(cur);
		Rect curSubQueryRegion = (Rect) this.getSubQueryRegions().elementAt(
				curSubQueryRegionNo);
		return curSubQueryRegion;
	}

	public Rect getSubQueryRegion(Position pos) {
		int curSubQueryRegionNo = this.getSubQueryRegionNo(pos);
		Rect curSubQueryRegion = (Rect) this.getSubQueryRegions().elementAt(
				curSubQueryRegionNo);
		return curSubQueryRegion;
	}

	// get next neighbor by Most Progress on Itinerary
	public GPSRAttachment getNextByMPI(GPSRAttachment cur, boolean down) {
		GPSRAttachment na = null;
		Vector neighbors = cur.getNeighbors();
		double maxYAdvance = Double.MIN_VALUE;
		double neighborAdvance = 0;
		GPSRAttachment maxYAdvanceNeighbor = cur;
		Rect curSubQueryRegion = this.getSubQueryRegion(cur);

		for (int i = 0; i < neighbors.size(); i++) {
			na = (GPSRAttachment) neighbors.elementAt(i);
			if (na.getNode().getPos().in(curSubQueryRegion)) {
				neighborAdvance = na.getNode().getPos().getY()
						- cur.getNode().getPos().getY();
				if (down)
					neighborAdvance = -neighborAdvance;
				if (neighborAdvance > maxYAdvance) {
					maxYAdvance = neighborAdvance;
					maxYAdvanceNeighbor = na;
				}
			}
		}
		return maxYAdvanceNeighbor;
	}

	// get next neighbor by Closest To Itinerary
	public GPSRAttachment getNextByCTI(GPSRAttachment cur, boolean down) {
		GPSRAttachment na = null;
		Vector neighbors = cur.getNeighbors();
		double neighborAdvance = 0;
		GPSRAttachment minXAdvanceNeighbor = cur;
		Rect curSubQueryRegion = this.getSubQueryRegion(cur);
		double centreX = curSubQueryRegion.getCentre().getX();
		double minXAdvance = Double.MAX_VALUE;

		for (int i = 0; i < neighbors.size(); i++) {
			na = (GPSRAttachment) neighbors.elementAt(i);
			if (na.getNode().getPos().in(curSubQueryRegion)) {
				neighborAdvance = Math.abs(na.getNode().getPos().getX()
						- centreX);

				// keep progress
				if (down) {
					if (na.getNode().getPos().getY() >= cur.getNode().getPos()
							.getY())
						continue;
				} else {
					if (na.getNode().getPos().getY() <= cur.getNode().getPos()
							.getY())
						continue;
				}
				if (neighborAdvance < minXAdvance) {
					minXAdvance = neighborAdvance;
					minXAdvanceNeighbor = na;
				}
			}
		}
		return minXAdvanceNeighbor;
	}

	public GPSRAttachment getFirst() {
		return first;
	}

	public void setFirst(GPSRAttachment first) {
		this.first = first;
	}

	public GPSRAttachment getLast() {
		return last;
	}

	public void setLast(GPSRAttachment last) {
		this.last = last;
	}

	public String getNextStrategy() {
		return nextStrategy;
	}

	public void setNextStrategy(String nextStrategy) {
		this.nextStrategy = nextStrategy;
	}

	public Vector getFirstPhaseRoute() {
		return firstPhaseRoute;
	}

	public void setFirstPhaseRoute(Vector firstPhaseRoute) {
		this.firstPhaseRoute = firstPhaseRoute;
	}

	public int getFirstPhaseNodeNum() {
		return this.getFirstPhaseRoute().size();
	}

	public int getQueryNodeNum() {
		int queryNodeNum = this.getItineraryNodeSize()
				- this.getFirstPhaseNodeNum();
		return queryNodeNum;
	}
}
