package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.IdealItineraryTraverseRectEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.Direction;

/*
 * IWQE using IteneraryTraverseRectEvent
 * the former query node send quey and partial answer message to the next query node!
 * 
 *  * The difference between IWQEIdealUseICAlg and IWQEIdealUseICAlgOld is 
 * how to judge success
 */
public class IWQEIdealUseICAlg extends IWQEAlg {
	static Logger logger = Logger.getLogger(IWQEIdealUseICAlg.class);

	public static final String NAME = "IWQEIdealUseIC";
	private Vector rects = new Vector();
	private Rect firstDestRect;
	private NeighborAttachment lastNodeInQueryRegion;
	//
	private boolean useTraversedNodeSumForSuccessRate = false;

	public Rect getFirstDestRect() {
		return firstDestRect;
	}

	public void setFirstDestRect(Rect firstDestRect) {
		this.firstDestRect = firstDestRect;
	}

	public boolean isUseTraversedNodeSumForSuccessRate() {
		return useTraversedNodeSumForSuccessRate;
	}

	public void setUseTraversedNodeSumForSuccessRate(
			boolean useTraversedNodeSumForSuccessRate) {
		this.useTraversedNodeSumForSuccessRate = useTraversedNodeSumForSuccessRate;
	}

	public IWQEIdealUseICAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public IWQEIdealUseICAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, DGSANewAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public IWQEIdealUseICAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initSubQueryRegionsByRegionNum(int subQueryRegionNum) {
		this.subQueryRegionNum = subQueryRegionNum;
		this.subQueryRegions = this
				.caculateSubQueryRegionsByRegionNum(subQueryRegionNum);
		this.subQueryRegionWidth = this
				.caculateSubQueryRegionWidth(subQueryRegionNum);
	}

	public void initSubQueryRegionByRegionWidth(double subQueryRegionWidth) {
		this.subQueryRegionNum = this
				.caculateSubQueryRegionNum(subQueryRegionWidth);
		this.subQueryRegions = this
				.caculateSubQueryRegionsByRegionWidth(subQueryRegionWidth);
		this.subQueryRegionWidth = subQueryRegionWidth;
	}

	public boolean run() {
		try {
			NeighborAttachment startNa = (NeighborAttachment) this
					.getSpatialQuery().getOrig().getAttachment(this.getName());
			this.getRoute().add(startNa);
			Message queryAndAnswerMessage = new Message(this.getParam()
					.getQUERY_MESSAGE_SIZE()
					+ this.getParam().getANSWER_SIZE(), null);
			boolean sendQuery = true;

			for (int subQueryRegionIndex = 0; subQueryRegionIndex < this.subQueryRegions
					.size(); subQueryRegionIndex++) {
				Rect subQueryRegion = (Rect) this.subQueryRegions
						.elementAt(subQueryRegionIndex);
				int direction = this.caculateDirection(subQueryRegionIndex);

				IdealItineraryTraverseRectEvent e = new IdealItineraryTraverseRectEvent(
						startNa, subQueryRegion, direction,
						queryAndAnswerMessage, sendQuery, this);
				this.getSimulator().handle(e);

				sendQuery = e.isSendQuery();
				if (subQueryRegionIndex == 0) {
					this.setFirstPhaseRoute(e.getFirstPhaseRoute());
				}
				this.getRoute().addAll(e.getRoute());
				startNa = e.getLastNode();
			}
			this.lastNodeInQueryRegion = startNa;
			this.forwardBackToSink();

		} catch (Exception e) {
			e.printStackTrace();
			this.getStatistics().setSuccess(false);
			this.getStatistics().setQueryResultCorrectness(
					this.getQueryResultCorrectness());
			return false;
		}
		this.getStatistics().setQueryResultCorrectness(
				this.getQueryResultCorrectness());
		if (this.isUseTraversedNodeSumForSuccessRate()) {
			if (this.getQueryResultCorrectness()
					.isQueryResultCorrectInTraversedResultNodeRate()) {
				this.getStatistics().setSuccess(true);
				return true;
			} else {
				this.getStatistics().setSuccess(false);
				return true;
			}
		} else {
			if (this.getQueryResultCorrectness().isQueryResultCorrect()) {
				this.getStatistics().setSuccess(true);
				return true;
			} else {
				this.getStatistics().setSuccess(false);
				return true;
			}
		}
	}

	public boolean forwardBackToSink() throws SensornetBaseException {
		NeighborAttachment sink = (NeighborAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.name));

		// GPSR query+answer
		Message answerMessage = new Message(this.getParam().getANSWER_SIZE(),
				null);
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
				this.lastNodeInQueryRegion, sink.getNode().getPos(),
				answerMessage, this);
		this.getSimulator().handle(gftpe);
		// this.getRoute().add(gftpe.getRoute());
		return gftpe.isSuccess();
	}

	protected int caculateDirection(int subQueryRegionIndex) {
		if (subQueryRegionIndex % 2 == 0) {
			return Direction.DOWN;
		} else {
			return Direction.UP;
		}
	}

	public Vector getRects() {
		return rects;
	}

	public void setRects(Vector rects) {
		this.rects = rects;
	}

}
