package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.HasSendAnswerUserObject;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
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
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class GSAAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(GSAAlg.class);
	public static final String NAME = "GSA";

	private GPSRAttachment first;
	private GPSRAttachment last;
	private int xGridNum;
	private int yGridNum;
	private double gridWidth;
	private double gridHeight;
	protected Rect[][] grids;
	private boolean allNodesInRectRecieved = false;

	public void initGridWithGridNum(int xGridNum, int yGridNum) {
		this.xGridNum = xGridNum;
		this.yGridNum = yGridNum;
		this.gridWidth = this.getSpatialQuery().getRect().getWidth()
				/ this.xGridNum;
		this.gridHeight = this.getSpatialQuery().getRect().getHeight()
				/ this.yGridNum;
		this.grids = new Rect[xGridNum][yGridNum];

		double minx = this.getSpatialQuery().getRect().getMinX();
		double miny = this.getSpatialQuery().getRect().getMinY();
		for (int x = 0; x < this.xGridNum; x++) {
			for (int y = 0; y < this.yGridNum; y++) {
				this.grids[x][y] = new Rect(minx + x * this.gridWidth, minx
						+ (x + 1) * this.gridWidth, miny + y * this.gridHeight,
						miny + (y + 1) * this.gridHeight);
			}
		}
	}

	public void initGridWithGridSize(double gridWidth, double gridHeight) {
		double queryRegionWidth = this.getSpatialQuery().getRect().getWidth();
		double queryRegionHeight = this.getSpatialQuery().getRect().getHeight();
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.xGridNum = (int) Math.ceil(queryRegionWidth / gridWidth);
		this.yGridNum = (int) Math.ceil(queryRegionHeight / gridHeight);
		this.grids = new Rect[xGridNum][yGridNum];

		double minx = this.getSpatialQuery().getRect().getMinX();
		double miny = this.getSpatialQuery().getRect().getMinY();

		for (int x = 0; x < this.xGridNum - 1; x++) {
			for (int y = 0; y < this.yGridNum - 1; y++) {

				this.grids[x][y] = new Rect(minx + x * this.gridWidth, minx
						+ (x + 1) * this.gridWidth, miny + y * this.gridHeight,
						miny + (y + 1) * this.gridHeight);
			}
			this.grids[x][this.yGridNum - 1] = new Rect(minx + x
					* this.gridWidth, minx + (x + 1) * this.gridWidth, miny
					+ (this.yGridNum - 1) * this.gridHeight, miny
					+ queryRegionHeight);
		}

		for (int y = 0; y < this.yGridNum - 1; y++) {
			this.grids[this.xGridNum - 1][y] = new Rect(minx
					+ (this.xGridNum - 1) * this.gridWidth, minx
					+ queryRegionWidth, miny + y * this.gridHeight, miny
					+ (y + 1) * this.gridHeight);
		}

		this.grids[this.xGridNum - 1][this.yGridNum - 1] = new Rect(minx
				+ (this.xGridNum - 1) * this.gridWidth,
				minx + queryRegionWidth, miny + (this.yGridNum - 1)
						* this.gridHeight, miny + queryRegionWidth);
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

	public GSAAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public GSAAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, GSAAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public GSAAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initOthers() {
		// TODO Auto-generated method stub
		super.initOthers();
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		GPSRAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (GPSRAttachment) node.getAttachment(this.getName());
			if (na != null) {
				this.setUserObject(na, new HasSendAnswerUserObject(false));
			}
		}
	}

	public void setUserObject(GPSRAttachment na,
			HasSendAnswerUserObject hasSendAnswerUserObject) {
		na.setUserObject(hasSendAnswerUserObject);
	}

	public HasSendAnswerUserObject getUserObject(GPSRAttachment na) {
		return (HasSendAnswerUserObject) na.getUserObject();
	}

	public boolean forwardToQueryRegion() throws SensornetBaseException {
		GPSRAttachment sink = (GPSRAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.getName()));
		GPSRAttachment cur = sink;
		// note
		GridIndex nextGridIndex = new GridIndex(0, this.getYGridNum() - 1);
		Rect nextRect = this.grids[nextGridIndex.getX()][nextGridIndex.getY()];

		logger.debug("start forwardToQueryRegion");
		Message queryMessage = new Message(this.getItineraryAlgParam()
				.getQuerySize(), null);
		GreedyForwardToRectExEvent gftre = new GreedyForwardToRectExEvent(cur,
				nextRect, queryMessage, this);
		this.getSimulator().handle(gftre);
		this.getSimulator().handle(
				EventsUtil
						.NeighborAttachmentsToItinerayNodeEvents(gftre, false));

		while (!gftre.isSuccess()) {
			if (this.getLastGrid().equal(nextGridIndex)) {
				logger
						.warn("try to  reach the last grid  but cannot greedy to it");
				return false;
			}

			logger.debug("forward to next grid");
			cur = (GPSRAttachment) gftre.getLastNode();
			nextGridIndex = this.getNextGrid(nextGridIndex);
			nextRect = this.grids[nextGridIndex.getX()][nextGridIndex.getY()];
			gftre = new GreedyForwardToRectExEvent(cur, nextRect, queryMessage,
					this);
			this.getSimulator().handle(gftre);
			this.getSimulator().handle(
					EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftre,
							false));
		}
		this.setAllNodesInRectRecieved(gftre.isAllNodesInRectReceived());
		this.setFirst((GPSRAttachment) gftre.getLastNode());
		logger.debug("end forwardToQueryRegion");
		return true;
	}

	public boolean forwardBackToSink() throws SensornetBaseException {
		GPSRAttachment sink = (GPSRAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.name));

		// GPSR
		Message resultMessage = new Message(this.getItineraryAlgParam()
				.getResultSize(), null);
		GPSR gpsr = new GPSR(this.getLast(), sink.getNode().getPos(),
				resultMessage, this);

		this.getSimulator().handle(gpsr);

		Vector nas = gpsr.getPath();
		nas.add(0, gpsr.getRoot());
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(nas, true));
		return gpsr.isSuccess();
	}

	public boolean gridTraverse() throws SensornetBaseException {
		GPSRAttachment cur = this.getFirst();
		GPSRAttachment next = null;
		GridIndex gridIndex;
		Rect curRect;
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
		ItineraryNodeEvent itineraryNodeEvent;

		Vector dataNodes = null;

		do {
			// logger.debug(cur.getNode().getPos());
			gridIndex = this.getNodeGridIndex(cur);
			// logger.debug(gridIndex);
			curRect = this.grids[gridIndex.getX()][gridIndex.getY()];
			nairc = new AttachmentInShapeCondition(curRect);
			neighborsInCurRect = cur.getNeighbors(this.getParam()
					.getRADIO_RANGE(), nairc);

			// broadcast query and collect answers
			if (neighborsInCurRect.size() == 0) {
				dataNodes = null;
				// no need for broadcast query because there is no neighbor in
				// current rect
			} else {
				if (!this.isAllNodesInRectRecieved()) {
					queryEvent = new BroadcastEvent(cur, null, queryMessage,
							this);
					this.simulator.handle(queryEvent);
				}
				// broadcast query
				// bug no need to broadcast query
				// queryEvent = new BroadcastEvent(cur,null,queryMessage,this);
				// this.getSimulator().handle(queryEvent);
				// collect answers
				dataNodes = new Vector();
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
					dataNodes.add(neighbor);
					// set has send
					this.setUserObject(neighbor, new HasSendAnswerUserObject(
							true));
				}
			}

			// last node so reach the end
			if (this.inQueryRegionLastGrid(cur)) {
				this.setLast(cur);
				return true;
			}

			// get next
			GridIndex nextGridIndex = this.getNextGrid(cur);
			Rect nextGridRect = this.grids[nextGridIndex.getX()][nextGridIndex
					.getY()];
			nairc = new AttachmentInShapeCondition(nextGridRect);
			neighborsInNextRect = cur.getNeighbors(this.getParam()
					.getRADIO_RANGE(), nairc);
			if (neighborsInNextRect.size() == 0) {
				if (nextGridIndex.equal(this.getLastGrid())) {
					this.setLast(cur);
					return true;
				}
				GridIndex nextNextGridIndex;
				Rect nextNextRect;
				GPSRAttachment greedyLastNode;
				// logger.debug("start new");
				do {
					nextNextGridIndex = this.getNextGrid(nextGridIndex);
					/*
					 * logger.debug("<cannot reach>");
					 * logger.debug(nextGridIndex);
					 * logger.debug(nextNextGridIndex);
					 * logger.debug("</cannot reach>");
					 */
					nextNextRect = this.grids[nextNextGridIndex.getX()][nextNextGridIndex
							.getY()];
					gftre = new GreedyForwardToRectExEvent(cur, nextNextRect,
							queryAnswerMessage, this);
					this.getSimulator().handle(gftre);
					greedyLastNode = (GPSRAttachment) gftre.getLastNode();

					this.getSimulator().handle(
							EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
									gftre, false, dataNodes));
					if (gftre.getRoute().size() > 0) {
						dataNodes = null;
					}

					if (gftre.isSuccess()) {
						cur = greedyLastNode;
						this.setAllNodesInRectRecieved(gftre
								.isAllNodesInRectReceived());
						break;
					} else {
						// check greedy to last grid(not in)

						if (this.getLastGrid().equal(nextNextGridIndex)) {
							this.setLast(greedyLastNode);
							return true;
						} else {
							nextGridIndex = nextNextGridIndex;
							cur = greedyLastNode;
						}
					}
				} while (true);

				// logger.debug("zero neighborsInNextRect");
				// return false;
			} else // choose a next node
			{
				next = this.chooseNextNodeByAdvance(neighborsInNextRect, this
						.isDown(cur));
				queryAnswerEvent = new BroadcastEvent(cur, next,
						queryAnswerMessage, this);
				this.setAllNodesInRectRecieved(true);
				this.simulator.handle(queryAnswerEvent);
				itineraryNodeEvent = new ItineraryNodeEvent(cur, next, this);
				itineraryNodeEvent.setDataNodes(dataNodes);
				this.simulator.handle(itineraryNodeEvent);
				cur = next;
				dataNodes = null;
			}
		} while (true);

	}

	public GPSRAttachment chooseNextNodeByAdvance(Vector neighborsInNextRect,
			boolean down) {
		if (neighborsInNextRect.size() == 0)
			return null;
		GPSRAttachment next = null;
		GPSRAttachment neighbor = null;
		double miny = Double.MAX_VALUE;
		double cury;

		for (int i = 0; i < neighborsInNextRect.size(); i++) {
			neighbor = (GPSRAttachment) neighborsInNextRect.elementAt(i);
			cury = neighbor.getNode().getPos().getY();
			if (!down)
				cury = -cury;
			if (cury < miny) {
				miny = cury;
				next = neighbor;
			}
		}
		return next;
	}

	public GPSRAttachment chooseNextNodeByEnergy(Vector neighborsInNextRect,
			boolean down) {
		if (neighborsInNextRect.size() == 0)
			return null;
		GPSRAttachment next = null;
		GPSRAttachment neighbor = null;
		double maxEnergy = Double.MIN_VALUE;

		for (int i = 0; i < neighborsInNextRect.size(); i++) {
			neighbor = (GPSRAttachment) neighborsInNextRect.elementAt(i);
			if (neighbor.getEnergy() > maxEnergy) {
				maxEnergy = neighbor.getEnergy();
				next = neighbor;
			}

		}
		return next;

	}

	public GridIndex getNextGrid(GPSRAttachment cur) {
		GridIndex curGridIndex = this.getNodeGridIndex(cur);
		return this.getNextGrid(curGridIndex);
	}

	public GridIndex getNextGrid(GridIndex gridIndex) {
		if (gridIndex.getX() % 2 == 0) // is down
		{
			if (gridIndex.getY() == 0) // should turn right
				return new GridIndex(gridIndex.getX() + 1, gridIndex.getY());
			else
				// should turn down
				return new GridIndex(gridIndex.getX(), gridIndex.getY() - 1);
		} else // is up
		{
			if (gridIndex.getY() == this.getYGridNum() - 1) // should turn right
				return new GridIndex(gridIndex.getX() + 1, gridIndex.getY());
			else
				// should turn up
				return new GridIndex(gridIndex.getX(), gridIndex.getY() + 1);
		}
	}

	public GridIndex getLastGrid() {
		if ((this.getXGridNum() - 1) % 2 == 0)// last itenerary is down
		{
			return new GridIndex(this.getXGridNum() - 1, 0);
		} else // last itenerary is up
		{
			return new GridIndex(this.getXGridNum() - 1, this.getYGridNum() - 1);
		}
	}

	public boolean inQueryRegionLastGrid(GPSRAttachment cur) {
		GridIndex gridIndex = this.getNodeGridIndex(cur);
		return gridIndex.equal(this.getLastGrid());
	}

	public boolean isDown(GPSRAttachment cur) {
		GridIndex gridIndex = this.getNodeGridIndex(cur);
		return (gridIndex.getX() % 2 == 0);
	}

	public boolean isReachSubIteneraryEnd(GPSRAttachment cur) {
		GridIndex gridIndex = this.getNodeGridIndex(cur);
		if (this.isDown(cur)) {
			return gridIndex.getY() == 0;
		} else {
			return gridIndex.getY() == this.getYGridNum() - 1;
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

	public GridIndex getNodeGridIndex(GPSRAttachment cur) {
		Position pos = cur.getNode().getPos();
		double minx = this.getSpatialQuery().getRect().getMinX();
		double miny = this.getSpatialQuery().getRect().getMinY();
		int x = (int) ((pos.getX() - minx) / this.getGridWidth());
		int y = (int) ((pos.getY() - miny) / this.getGridHeight());
		if (x == this.getXGridNum())
			x -= 1;
		if (y == this.getYGridNum())
			y -= 1;
		return new GridIndex(x, y);
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
	}

	public double getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(double gridHeight) {
		this.gridHeight = gridHeight;
	}

	public double getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(double gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getXGridNum() {
		return xGridNum;
	}

	public void setXGridNum(int gridNum) {
		xGridNum = gridNum;
	}

	public int getYGridNum() {
		return yGridNum;
	}

	public void setYGridNum(int gridNum) {
		yGridNum = gridNum;
	}

	public class GridIndex {
		private int x;
		private int y;

		public GridIndex(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public boolean equal(GridIndex other) {
			return (this.getX() == other.getX())
					&& (this.getY() == other.getY());
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("<GridIndex><x>");
			sb.append(this.getX());
			sb.append("</x>");
			sb.append("<y>");
			sb.append(this.getY());
			sb.append("</y>");
			sb.append("</GridIndex>");
			return sb.toString();
		}
	}

	public Rect[][] getGrids() {
		return grids;
	}

	public void setGrids(Rect[][] grids) {
		this.grids = grids;
	}

	public ItineraryAlgParam getItineraryAlgParam() {
		return (ItineraryAlgParam) this.getAlgParam();
	}

	public boolean isAllNodesInRectRecieved() {
		return allNodesInRectRecieved;
	}

	public void setAllNodesInRectRecieved(boolean allNodesInRectRecieved) {
		this.allNodesInRectRecieved = allNodesInRectRecieved;
	}

}
