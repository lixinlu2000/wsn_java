package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.GKNNUtil;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.EventsUtil;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.ItineraryTraverseRingEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.KNNQuery;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Ring;

public class IKNNExAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(IKNNExAlg.class);

	public static final String NAME = "IKNNEx";
	private GPSRAttachment homeNodeAttachment;
	private Message queryMessage = new Message(this.getParam()
			.getQUERY_MESSAGE_SIZE(), this.getKNNQuery());
	private GPSRAttachment lastNodeAttachment;
	private Vector rings = new Vector();
	private int currentK = 0;

	public IKNNExAlg(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public GPSRAttachment getHomeNodeAttachment() {
		return homeNodeAttachment;
	}

	public void setHomeNodeAttachment(GPSRAttachment homeNodeAttachment) {
		this.homeNodeAttachment = homeNodeAttachment;
	}

	public GPSRAttachment getLastNodeAttachment() {
		return lastNodeAttachment;
	}

	public void setLastNodeAttachment(GPSRAttachment lastNodeAttachment) {
		this.lastNodeAttachment = lastNodeAttachment;
	}

	public IKNNExAlg(QueryBase query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public void initOthers() {
		super.initOthers();
		this.queryMessage = new Message(
				this.getParam().getQUERY_MESSAGE_SIZE(), this.getKNNQuery());
	}

	public IKNNExAlg(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, IKNNExAlg.NAME, statistics);
	}

	public IKNNExAlg() {
	}

	public boolean forwardBackToSink() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(this
				.getLastNodeAttachment(), orig.getPos(), this.queryMessage,
				this);
		gftpe.setSendQueryTag();
		this.getSimulator().handle(gftpe);
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftpe, true,
						null));
		return true;

		/*
		 * Node sink = this.getKNNQuery().getOrig(); GPSR gpsr = new
		 * GPSR(this.getLastNodeAttachment(),sink.getPos(),
		 * this.getQueryMessage(), this); gpsr.setSendQueryTag();
		 * this.getSimulator().handle(gpsr); if(gpsr.isSuccess()) return true;
		 * else return false;
		 */
	}

	public boolean isHasKAnswers() {
		if (this.currentK >= this.getKNNQuery().getK())
			return true;
		else
			return false;
	}

	public boolean itenararyTraverse() throws SensornetBaseException {
		double lowRadius = this.getHomeNodeAttachment().getNode().getPos()
				.distance(this.getKNNQeury().getCenter());
		double highRadius = GKNNUtil.calculateHighRadius(lowRadius, this
				.getParam().getRADIO_RANGE());
		Ring curRing = new Ring(this.getKNNQeury().getCenter(), lowRadius,
				highRadius);

		GPSRAttachment startNode = (GPSRAttachment) this
				.getHomeNodeAttachment();
		DrawShapeEvent drawShapeEvent;
		ItineraryTraverseRingEvent itre;

		while (!this.isHasKAnswers()) {
			drawShapeEvent = new DrawShapeEvent(this, curRing);
			this.getSimulator().handle(drawShapeEvent);

			itre = new ItineraryTraverseRingEvent(startNode, curRing, this
					.getQueryMessage(), this);
			this.getSimulator().handle(itre);

			startNode = (GPSRAttachment) (itre.getLastNode());
			this.currentK += this.getQueryMessage().getSegments().size();
			this.forwardBackToHomeNode(startNode);
			this.getQueryMessage().clearMessageSegments();

			lowRadius = highRadius;
			highRadius = GKNNUtil.calculateHighRadius(lowRadius, this
					.getParam().getRADIO_RANGE());
			curRing = new Ring(this.getKNNQeury().getCenter(), lowRadius,
					highRadius);

		}
		this.setLastNodeAttachment(startNode);
		return true;
	}

	public boolean forwardBackToHomeNode(GPSRAttachment lastNodeAttachmentInRing)
			throws SensornetBaseException {
		Node homeNode = this.getHomeNodeAttachment().getNode();
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
				lastNodeAttachmentInRing, homeNode.getPos(), this.queryMessage,
				this);
		gftpe.setSendQueryTag();
		this.getSimulator().handle(gftpe);
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftpe, true,
						null));
		return true;
	}

	public boolean run() {
		try {
			this.greedyForward();
			if (this.getKNNQuery().getK() == 1) {
				this.getAns().add(this.getHomeNodeAttachment());
				this.getStatistics().setSuccess(true);
				return true;
			}

			if (!this.itenararyTraverse()) {
				logger.debug("itenarary traverse failed");
				return false;
			}

			if (!this.forwardBackToSink()) {
				logger.debug("forward back to sink failed");
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
		this.getStatistics().setSuccess(true);
		return true;
	}

	public KNNQuery getKNNQeury() {
		return (KNNQuery) this.getQuery();
	}

	public boolean greedyForwardByGPSR() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GPSRAttachment sink = (GPSRAttachment) orig.getAttachment(this
				.getName());
		GPSR gpsr = new GPSR(sink, this.getKNNQuery().getCenter(),
				this.queryMessage, this);
		this.getSimulator().handle(gpsr);
		this.setHomeNodeAttachment((GPSRAttachment) gpsr.getLastNode());
		return true;
	}

	public boolean greedyForward() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GPSRAttachment sink = (GPSRAttachment) orig.getAttachment(this
				.getName());
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(sink,
				this.getKNNQuery().getCenter(), this.queryMessage, this);
		gftpe.setSendQueryTag();
		this.getSimulator().handle(gftpe);
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftpe,
						false, null));
		this.setHomeNodeAttachment((GPSRAttachment) gftpe.getLastNode());
		return true;
	}

	public KNNQuery getKNNQuery() {
		return (KNNQuery) this.getQuery();
	}

	public Message getQueryMessage() {
		return queryMessage;
	}
}
