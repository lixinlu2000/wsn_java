package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.EDCGridTraverseRectEvent;
import NHSensor.NHSensorSim.events.EventsUtil;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class ESAOldAlg extends IWQEAlg {
	static Logger logger = Logger.getLogger(ESAAlg.class);

	public static final String NAME = "ESAOld";
	private Vector rects = new Vector();

	public ESAOldAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public ESAOldAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, ESAOldAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public ESAOldAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initSubQueryRegionsAndGridsByRegionNum(int subQueryRegionNum) {
		this.subQueryRegionNum = subQueryRegionNum;
		this.subQueryRegions = this
				.caculateSubQueryRegionsByRegionNum(subQueryRegionNum);
		this.subQueryRegionWidth = this
				.caculateSubQueryRegionWidth(subQueryRegionNum);
	}

	public void initSubQueryRegionAndGridsByRegionWidth(
			double subQueryRegionWidth) {
		this.subQueryRegionNum = this
				.caculateSubQueryRegionNum(subQueryRegionWidth);
		this.subQueryRegions = this
				.caculateSubQueryRegionsByRegionWidth(subQueryRegionWidth);
		this.subQueryRegionWidth = subQueryRegionWidth;
	}

	public boolean gridTraverse() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment cur = (GPSRAttachment) orig
				.getAttachment(this.getName());
		this.getRoute().add(cur);
		Message queryAnswerMessage = new Message(this.getParam()
				.getQUERY_MESSAGE_SIZE()
				+ this.getParam().getANSWER_SIZE(), null);
		boolean sendQuery = true;
		EDCGridTraverseRectEvent edcEvent;

		for (int i = 0; i < this.subQueryRegionNum; i++) {
			Rect subQueryRegion = (Rect) this.subQueryRegions.elementAt(i);
			edcEvent = new EDCGridTraverseRectEvent(cur, subQueryRegion, this
					.getDirection(i), queryAnswerMessage, sendQuery, this);
			this.getSimulator().handle(edcEvent);

			sendQuery = edcEvent.isSendQuery();
			if (i == 0) {
				this.setFirstPhaseRoute(edcEvent.getFirstPhaseRoute());
			}
			this.getRoute().addAll(edcEvent.getRoute());
			cur = (GPSRAttachment) edcEvent.getLastNode();
		}
		this.setLast(cur);
		return true;
	}

	public int getDirection(int subQueryRegionIndex) {
		if (subQueryRegionIndex % 2 == 0)
			return Direction.DOWN;
		else
			return Direction.UP;
	}

	public boolean run() {
		try {
			/*
			 * if(!this.forwardToQueryRegion()) {
			 * logger.warn("cannot forwardToQueryRegion"); return false; }
			 */

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
		this.getStatistics().setQueryResultCorrectness(
				this.getQueryResultCorrectness());
		return true;
	}

	public boolean forwardBackToSink() throws SensornetBaseException {
		GPSRAttachment sink = (GPSRAttachment) (this.getSpatialQuery()
				.getOrig().getAttachment(this.name));

		// GPSR query+answer
		Message answerMessage = new Message(this.getParam().getANSWER_SIZE(),
				null);
		GPSR gpsr = new GPSR(this.getLast(), sink.getNode().getPos(),
				answerMessage, this);
		this.getSimulator().handle(gpsr);

		Vector nas = gpsr.getPath();
		nas.add(0, gpsr.getRoot());
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(nas, true));
		return gpsr.isSuccess();
	}

	public Vector getRects() {
		return rects;
	}

	public void setRects(Vector rects) {
		this.rects = rects;
	}

}
