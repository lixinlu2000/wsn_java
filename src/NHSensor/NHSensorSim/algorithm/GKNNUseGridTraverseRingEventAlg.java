package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.GKNNUtil;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.EventsUtil;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.GridTraverseRingEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.KNNQuery;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;

public class GKNNUseGridTraverseRingEventAlg extends GPSRAlg {
	static Logger logger = Logger
			.getLogger(GKNNUseGridTraverseRingEventAlg.class);

	public static final String NAME = "GKNNUseGridTraverseRingEvent";
	private GPSRAttachment homeNodeAttachment;
	private Message queryMessage = new Message(this.getParam()
			.getQUERY_MESSAGE_SIZE(), this.getKNNQuery());
	private GPSRAttachment lastNodeAttachment;
	private Vector rings = new Vector();
	private Vector allRingSectors = new Vector();

	public Vector getAllRingSectors() {
		return allRingSectors;
	}

	public void setAllRingSectors(Vector allRingSectors) {
		this.allRingSectors = allRingSectors;
	}

	public void appendCurrentRingSectors(RingSector[] currentRingSectors) {
		for (int i = 0; i < currentRingSectors.length; i++) {
			this.getAllRingSectors().add(currentRingSectors[i]);
		}
	}

	public GPSRAttachment getLastNodeAttachment() {
		return lastNodeAttachment;
	}

	public void setLastNodeAttachment(GPSRAttachment lastNodeAttachment) {
		this.lastNodeAttachment = lastNodeAttachment;
	}

	public GPSRAttachment getHomeNodeAttachment() {
		return homeNodeAttachment;
	}

	public void setHomeNodeAttachment(GPSRAttachment homeNodeAttachment) {
		this.homeNodeAttachment = homeNodeAttachment;
	}

	public GKNNUseGridTraverseRingEventAlg(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public GKNNUseGridTraverseRingEventAlg(KNNQuery query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public GKNNUseGridTraverseRingEventAlg(KNNQuery query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param,
				GKNNUseGridTraverseRingEventAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public GKNNUseGridTraverseRingEventAlg() {
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
				na.setHasSendAnswer(false);
			}
		}
		this.queryMessage = new Message(
				this.getParam().getQUERY_MESSAGE_SIZE(), this.getKNNQuery());
	}

	public boolean greedyForwardByGPSR() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GPSRAttachment sink = (GPSRAttachment) orig.getAttachment(this
				.getName());
		GPSR gpsr = new GPSR(sink, this.getKNNQuery().getCenter(),
				this.queryMessage, this);
		gpsr.setSendQueryTag();
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

	public boolean isHasKAnswers() {
		if (this.getQueryMessage().getSegments().size() >= this.getKNNQuery()
				.getK())
			return true;
		else
			return false;
	}

	protected NeighborAttachment traverseRing(
			NeighborAttachment firstNodeAttachmentInRing, Ring ring)
			throws SensornetBaseException {
		GridTraverseRingEvent gtre = new GridTraverseRingEvent(
				firstNodeAttachmentInRing, ring, GKNNUtil
						.calculateRingSectorSita(ring.getLowRadius(), this
								.getParam().getRADIO_RANGE()), this
						.getQueryMessage(), this);
		this.getSimulator().handle(gtre);
		return gtre.getLastNode();
	}

	public boolean itenararyTraverse() throws SensornetBaseException {
		GPSRAttachment firstNodeAttachmentInRing;
		GPSRAttachment lastNodeAttachmentInRing;
		GPSRAttachment firstAttachmentInNextRing;
		double lowRadius;
		double highRadius;

		firstNodeAttachmentInRing = this.getHomeNodeAttachment();
		lowRadius = firstNodeAttachmentInRing.getNode().getPos().distance(
				this.getKNNQuery().getCenter());
		highRadius = GKNNUtil.calculateHighRadius(lowRadius, this.getParam()
				.getRADIO_RANGE());
		Ring ring = new Ring(this.getKNNQuery().getCenter(), lowRadius,
				highRadius);
		this.getRings().add(ring);
		lastNodeAttachmentInRing = (GPSRAttachment) this.traverseRing(
				firstNodeAttachmentInRing, ring);

		while (!this.isHasKAnswers()) {
			firstAttachmentInNextRing = lastNodeAttachmentInRing;
			lowRadius = highRadius;
			highRadius = GKNNUtil.calculateHighRadius(lowRadius, this
					.getParam().getRADIO_RANGE());
			ring = new Ring(this.getKNNQuery().getCenter(), lowRadius,
					highRadius);
			this.getRings().add(ring);
			lastNodeAttachmentInRing = (GPSRAttachment) this.traverseRing(
					firstAttachmentInNextRing, ring);
		}
		this.setLastNodeAttachment(lastNodeAttachmentInRing);
		return true;
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

	public Message getQueryMessage() {
		return queryMessage;
	}

	public void setQueryMessage(Message queryMessage) {
		this.queryMessage = queryMessage;
	}

	public Vector getRings() {
		return rings;
	}

	public void setRings(Vector rings) {
		this.rings = rings;
	}
}
