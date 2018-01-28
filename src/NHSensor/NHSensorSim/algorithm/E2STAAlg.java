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
import NHSensor.NHSensorSim.events.BuildTreesEventForE2STA;
import NHSensor.NHSensorSim.events.CollectTreeResultEventForE2STA;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToShapeEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Sector;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;
import NHSensor.NHSensorSim.shapeTraverse.RDCTraverseSectorInRectIteratorFar;

/*
 *
 */
public class E2STAAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(E2STAAlg.class);
	public static final String NAME = "E2STA";
	private SectorInRect[] sectorInRects;
	Shape[] initialShapes;
	GPSRAttachment[] initialNas;
	protected TreeEvent treeEvent;

	public E2STAAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public E2STAAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, E2STAAlg.NAME, statistics);
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
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

	public boolean multicastQuery() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this
				.getName());
		Message queryMessage = new Message(this.getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		initialShapes = new Shape[this.sectorInRects.length];
		initialNas = new GPSRAttachment[this.sectorInRects.length];
		RDCTraverseSectorInRectIteratorFar rti;

		for (int i = 0; i < this.sectorInRects.length; i++) {
			SectorInRect subQueryRegion = (SectorInRect) this.sectorInRects[i];
			rti = new RDCTraverseSectorInRectIteratorFar(this, subQueryRegion);
			initialShapes[i] = rti.getInitailShape();
		}

		int index = this.minDistanceShapeIndex(orig.getPos(), initialShapes);

		this.getRoute().add(root);

		DrawShapeEvent dse = new DrawShapeEvent(this, initialShapes[index]);
		this.getSimulator().handle(dse);

		GreedyForwardToShapeEvent gftse = new GreedyForwardToShapeEvent(root,
				initialShapes[index], queryMessage, this);
		this.getSimulator().handle(gftse);
		this.getRoute().addAll(gftse.getRoute());
		initialNas[index] = (GPSRAttachment) gftse.getLastNode();

		NeighborAttachment cur = initialNas[index];
		for (int i = index - 1; i >= 0; i--) {
			dse = new DrawShapeEvent(this, initialShapes[i]);
			this.getSimulator().handle(dse);

			gftse = new GreedyForwardToShapeEvent(cur, initialShapes[i],
					queryMessage, this);
			this.getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());
			initialNas[i] = (GPSRAttachment) gftse.getLastNode();
			cur = initialNas[i];
		}

		cur = initialNas[index];
		for (int i = index + 1; i < this.sectorInRects.length; i++) {
			dse = new DrawShapeEvent(this, initialShapes[i]);
			this.getSimulator().handle(dse);

			gftse = new GreedyForwardToShapeEvent(cur, initialShapes[i],
					queryMessage, this);
			this.getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());
			initialNas[i] = (GPSRAttachment) gftse.getLastNode();
			cur = initialNas[i];
		}
		return true;
	}

	protected void buildTree() throws SensornetBaseException {
		Rect rect = this.getSpatialQuery().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildTreesEventForE2STA bte = new BuildTreesEventForE2STA(
				this.initialNas, this.sectorInRects, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}

	public void regionCollect() throws SensornetBaseException {
		CollectTreeResultEventForE2STA ctre = new CollectTreeResultEventForE2STA(this
				.getTreeEvent(), this.getSpatialQuery().getRect(), this);
		this.getSimulator().handle(ctre);
		this.setAns(ctre.getAns());
	}

	public boolean greedyBack() throws SensornetBaseException {
		Position pos = this.getSpatialQuery().getOrig().getPos();
		GPSRAttachment cur;
		Message answerMessage;
		GreedyForwardToPointEvent event;

		for (int i = 0; i < this.initialNas.length; i++) {
			cur = this.initialNas[i];
			answerMessage = new Message(this.getParam().getANSWER_SIZE()
					* cur.getMessageCount(this.getSpatialQuery().getRect()),
					this.getQuery());
			event = new GreedyForwardToPointEvent(cur, pos, answerMessage, this);
			this.getSimulator().handle(event);
		} // set answers
		return true;
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
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
			this.buildTree();
			this.regionCollect();
			this.greedyBack();
			this.insertLostNodesEventToSimulatorEventsHead();
		} catch (HasNoEnergyException e) {
			e.printStackTrace();
			this.setFirstNodeHasNoEnergy(e.getAttachment());
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

}
