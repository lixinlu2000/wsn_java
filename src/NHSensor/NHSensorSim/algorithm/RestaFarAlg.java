package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.RDCTraverseSectorInRectEventFar;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Sector;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.ShapeUtil;

/*
 * RDC Traverse Far
 */
public class RestaFarAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(RestaFarAlg.class);
	public static final String NAME = "RestaFar";
	private SectorInRect[] sectorInRects;

	// private int traverseDirection = SectorDirection.NEAR;
	// private int traverseDirection = SectorDirection.FAR;

	public RestaFarAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public RestaFarAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, RestaFarAlg.NAME, statistics);
	}

	public void initSectorInRectsBySita(int count) {
		Node orig = this.getQuery().getOrig();
		Rect rect = ((Query) this.getQuery()).getRect();
		double startRadian = orig.getPos().bearing(rect.getLT());
		Sector sector;
		SectorInRect sectorInRect;

		sectorInRects = new SectorInRect[count];
		double maxAlpha = Radian.relativeTo(
				orig.getPos().bearing(rect.getRT()), startRadian);
		double alpha = maxAlpha / count;
		for (int i = 0; i < count; i++) {
			sector = new Sector(orig.getPos(), startRadian, alpha);
			sectorInRect = new SectorInRect(sector, rect);
			sectorInRects[i] = sectorInRect;
			startRadian += alpha;
		}
	}

	public void initSectorInRectByArea(int count) {
		Position rootPosition = this.getQuery().getOrig().getPos();
		Rect rect = ((Query) this.getQuery()).getRect();
		double[] alphas = ShapeUtil.caculateAlphasSectorInRectsByArea(
				rootPosition, rect, count);
		double startRadian = ShapeUtil.caculateAlpha1(rect, rootPosition);
		double sita;
		Sector sector;
		this.sectorInRects = new SectorInRect[count];

		for (int i = 0; i < count; i++) {
			if (i == 0)
				sita = alphas[i];
			else
				sita = alphas[i] - alphas[i - 1];
			sector = new Sector(rootPosition, startRadian, sita);
			sectorInRects[i] = new SectorInRect(sector, rect);
			startRadian += sita;
		}
	}

	public void initSectorInRectByMaxAlpha() {
		Position rootPosition = this.getQuery().getOrig().getPos();
		Rect rect = ((Query) this.getQuery()).getRect();
		Vector alphas = ShapeUtil.caculateAlphasSectorInRectsByMaxAlpha(
				rootPosition, rect, this.getParam().getRADIO_RANGE());
		double startRadian = ShapeUtil.caculateAlpha1(rect, rootPosition);
		double sita;
		Sector sector;
		this.sectorInRects = new SectorInRect[alphas.size()];

		for (int i = 0; i < alphas.size(); i++) {
			if (i == 0)
				sita = ((Double) alphas.elementAt(i)).doubleValue();
			else
				sita = ((Double) alphas.elementAt(i)).doubleValue()
						- ((Double) alphas.elementAt(i - 1)).doubleValue();
			sector = new Sector(rootPosition, startRadian, sita);
			sectorInRects[i] = new SectorInRect(sector, rect);
			startRadian += sita;
		}
	}

	public boolean rdcTraverse() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this
				.getName());
		this.getRoute().add(root);
		Message queryMessage = new Message(this.getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		boolean sendQuery = true;
		RDCTraverseSectorInRectEventFar event;

		for (int i = 0; i < this.sectorInRects.length; i++) {
			SectorInRect subQueryRegion = (SectorInRect) this.sectorInRects[i];
			event = new RDCTraverseSectorInRectEventFar(root, subQueryRegion,
					queryMessage, this);
			try {
				this.getSimulator().handle(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendQuery = event.isSendQuery();
			this.getRoute().addAll(event.getRoute());
		}
		this.getStatistics().setQueryResultCorrectness(
				this.getQueryResultCorrectness());
		if (this.getQueryResultCorrectness().isQueryResultCorrect()) {
			this.getStatistics().setSuccess(true);
			return true;
		} else {
			this.getStatistics().setSuccess(false);
			return true;
		}

	}

	public SectorInRect[] getSectorInRects() {
		return sectorInRects;
	}

	public boolean run() {
		try {
			if (!this.rdcTraverse()) {
				logger.warn("cannot rdcTraverse");
				return false;
			}
			this.insertLostNodesEventToSimulatorEventsHead();
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
