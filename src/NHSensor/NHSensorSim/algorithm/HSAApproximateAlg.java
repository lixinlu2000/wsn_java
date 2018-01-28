package NHSensor.NHSensorSim.algorithm;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToANodeInSectorInRectFarFromPosEvent;
import NHSensor.NHSensorSim.events.RDCTraverseSectorInRectEventNearApproximate;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Sector;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;
import NHSensor.NHSensorSim.shapeTraverse.RDCTraverseSectorInRectIteratorNear;

/*
 * RDC Traverse Near multicast Query message
 */
public class HSAApproximateAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(HSAApproximateAlg.class);
	public static final String NAME = "HSAApproximate";
	private SectorInRect[] sectorInRects;
	Shape[] initialShapes;
	NeighborAttachment[] initialNas;
	double delta;

	public HSAApproximateAlg(Query query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public HSAApproximateAlg(Query query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, HSAApproximateAlg.NAME,
				statistics);
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

	protected int minDistanceShapeIndex(Position pos, Shape[] initialShapes) {
		double minDistance = Double.MAX_VALUE;
		double distance;
		int index = -1;

		for (int i = 0; i < initialShapes.length; i++) {
			distance = initialShapes[i].getCentre().distance(pos);
			if (distance < minDistance) {
				index = i;
				minDistance = distance;
			}
		}
		return index;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public boolean multicastQuery() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this.getName());
		Message queryMessage = new Message(this.getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		initialShapes = new Shape[this.sectorInRects.length];
		initialNas = new NeighborAttachment[this.sectorInRects.length];
		RDCTraverseSectorInRectIteratorNear rti;

		for (int i = 0; i < this.sectorInRects.length; i++) {
			SectorInRect subQueryRegion = (SectorInRect) this.sectorInRects[i];
			rti = new RDCTraverseSectorInRectIteratorNear(this, subQueryRegion);
			initialShapes[i] = rti.getInitailShape();
		}

		int index = this.minDistanceShapeIndex(orig.getPos(), initialShapes);

		this.getRoute().add(root);

		DrawShapeEvent dse = new DrawShapeEvent(this, initialShapes[index]);
		this.getSimulator().handle(dse);

		GreedyForwardToANodeInSectorInRectFarFromPosEvent gftse = new GreedyForwardToANodeInSectorInRectFarFromPosEvent(root,
				this.sectorInRects[index].getSector().getCentre(), this.sectorInRects[index].getSector().diagonalRadian(),
				this.sectorInRects[index], SectorDirection.NEAR, 
				queryMessage, this);
		this.getSimulator().handle(gftse);
		this.getRoute().addAll(gftse.getRoute());
		initialNas[index] = (NeighborAttachment) gftse.getLastNode();

		NeighborAttachment firstTryLastNode = gftse.getFirstTryLastNode();
		NeighborAttachment cur = firstTryLastNode;
		for (int i = index - 1; i >= 0; i--) {
			dse = new DrawShapeEvent(this, initialShapes[i]);
			this.getSimulator().handle(dse);

			gftse = new GreedyForwardToANodeInSectorInRectFarFromPosEvent(cur,
					this.sectorInRects[i].getSector().getCentre(), this.sectorInRects[i].getSector().diagonalRadian(),
					this.sectorInRects[i], SectorDirection.NEAR, 
					queryMessage, this);
			this.getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());
			initialNas[i] = (NeighborAttachment) gftse.getLastNode();
			cur = gftse.getFirstTryLastNode();
		}

		cur = firstTryLastNode;
		for (int i = index + 1; i < this.sectorInRects.length; i++) {
			dse = new DrawShapeEvent(this, initialShapes[i]);
			this.getSimulator().handle(dse);

			gftse = new GreedyForwardToANodeInSectorInRectFarFromPosEvent(cur,
					this.sectorInRects[i].getSector().getCentre(), this.sectorInRects[i].getSector().diagonalRadian(),
					this.sectorInRects[i], SectorDirection.NEAR, 
					queryMessage, this);
			this.getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());
			initialNas[i] = (NeighborAttachment) gftse.getLastNode();
			cur = gftse.getFirstTryLastNode();
		}
		return true;
	}

	public boolean rdcTraverse() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this
				.getName());
		boolean sendQuery = true;
		RDCTraverseSectorInRectEventNearApproximate event;

		for (int i = 0; i < this.sectorInRects.length; i++) {
			SectorInRect subQueryRegion = (SectorInRect) this.sectorInRects[i];
			if(!subQueryRegion.contains(initialNas[i].getPos()))continue;
			Message queryAndDataMessage = new Message(this.getParam()
					.getQUERY_MESSAGE_SIZE()+this.getParam().getANSWER_SIZE(), null);
			try {
				event = new RDCTraverseSectorInRectEventNearApproximate(root, initialNas[i],
						subQueryRegion, queryAndDataMessage, this, delta);
				this.getSimulator().handle(event);
				this.getRoute().addAll(event.getRoute());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			if (!this.multicastQuery()) {
				logger.warn("cannot multicast query message");
				return false;
			}
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
