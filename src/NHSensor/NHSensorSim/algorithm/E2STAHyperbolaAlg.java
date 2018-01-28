package NHSensor.NHSensorSim.algorithm;

import java.awt.Color;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreesEventForE2STAHyperbola;
import NHSensor.NHSensorSim.events.CollectTreeResultEventForE2STA;
import NHSensor.NHSensorSim.events.DrawCircleEvent;
import NHSensor.NHSensorSim.events.DrawNodeEvent;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToANodeInSectorInRectEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToRectEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Line;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Sector;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;

/*
 *
 */
public class E2STAHyperbolaAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(E2STAHyperbolaAlg.class);
	public static final String NAME = "E2STAHyperbola";
	private SectorInRect[] sectorInRects;
	Shape[] initialShapes;
	GPSRAttachment[] initialNas;
	Position[] initialPositions;
	GPSRAttachment ecstaCoordinator;
	protected TreeEvent treeEvent;

	public E2STAHyperbolaAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public E2STAHyperbolaAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, E2STAHyperbolaAlg.NAME, statistics);
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
	
	public void initSectorInRectsMiddleFirst(int count, double middleSitaRate) {
		Node orig = this.getQuery().getOrig();
		Rect rect = ((Query) this.getQuery()).getRect();
		double startRadian0 = orig.getPos().bearing(rect.getLT());
		Sector sector;
		SectorInRect sectorInRect; 
		
		sectorInRects = new SectorInRect[count];
		double maxAlpha = Radian.relativeTo(
				orig.getPos().bearing(rect.getRT()), startRadian0);		
		double middleSita = maxAlpha*middleSitaRate;
		double alpha ;
		double middleRadian = orig.getPos().bearing(rect.getCentre());
		double alpha1 = Radian.relativeTo(middleRadian, startRadian0);
		double startRadian, endRadian;
		if(alpha1<middleSita/2) {
			startRadian = startRadian0;
		}
		else startRadian = middleRadian-middleSita/2;
		alpha1 = Radian.relativeTo(orig.getPos().bearing(rect.getRT()), middleRadian);
		if(alpha1<middleSita/2) {
			endRadian = orig.getPos().bearing(rect.getRT());
		}
		else endRadian = middleRadian+middleSita/2;
		
		sector = new Sector(orig.getPos(), startRadian, Radian.relativeTo(endRadian, startRadian));
		sectorInRect = new SectorInRect(sector, rect);
		
		if(count==1) {
			sectorInRects[0]=sectorInRect;
			return;
		}
		
		double maxRadian = orig.getPos().bearing(rect.getRT());
		double alphaLeft = Radian.relativeTo(startRadian, startRadian0);
		double alphaRight = Radian.relativeTo(maxRadian, endRadian);
		int count1 = (int)Math.round(alphaLeft/(maxAlpha-sectorInRect.getSector().getSita()));
		if(startRadian!=startRadian0&&count1==0) {
			count1 = 1;
		}
		int count2 = count-count1-1;
		if(maxRadian!=endRadian&&count2==0) {
			count2 = 1;
			count1 = count-count2-1;
		}
		double b,e;
		
		alpha = alphaLeft/count1;
		e= startRadian;
		for(int j=0;j<count1;j++) {
			b =  e-alpha;
			b = b>startRadian0?b:startRadian0;
			sector = new Sector(orig.getPos(), b, Radian.relativeTo(e,b));
			sectorInRect = new SectorInRect(sector, rect);
			sectorInRects[count1-j-1] = sectorInRect;
			e-=alpha;
		}
		
		sector = new Sector(orig.getPos(), startRadian, Radian.relativeTo(endRadian, startRadian));
		sectorInRect = new SectorInRect(sector, rect);
		sectorInRects[count1]=sectorInRect;
		
		alpha = alphaRight/count2;
		b = endRadian;
		for(int j=0;j<count2;j++) {
			e =  b+alpha;
			e = e>maxRadian?maxRadian:e;
			sector = new Sector(orig.getPos(), b, Radian.relativeTo(e,b));
			sectorInRect = new SectorInRect(sector, rect);
			sectorInRects[count1+j+1] = sectorInRect;
			b+=alpha;
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

	public boolean unicastQuery() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this
				.getName());
		Message queryMessage = new Message(this.getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		initialNas = new GPSRAttachment[this.sectorInRects.length];
		GreedyForwardToANodeInSectorInRectEvent gftse;

		for (int i = 0; i < this.sectorInRects.length; i++) {
			SectorInRect subQueryRegion = (SectorInRect) this.sectorInRects[i];
			gftse = new GreedyForwardToANodeInSectorInRectEvent(root,
					subQueryRegion,  SectorDirection.FAR, queryMessage, this);
			this.getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());
			initialNas[i] = (GPSRAttachment) gftse.getLastNode();			
		}
		return true;
	}
	
	public boolean multicastQuery() throws SensornetBaseException {
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this
				.getName());
		Message queryMessage = new Message(this.getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		//GreedyForwardToANodeInSectorInRectNearPosEvent gftse;
		GreedyForwardToANodeInSectorInRectEvent gftse;
		NeighborAttachment minShapeFistTryLastNode;
		
		initialShapes = new Shape[this.sectorInRects.length];
		initialNas = new GPSRAttachment[this.sectorInRects.length];
		double radioRange = this.getParam().getRADIO_RANGE();

		for (int i = 0; i < this.sectorInRects.length; i++) {
			SectorInRect subQueryRegion = (SectorInRect) this.sectorInRects[i];
			this.getSimulator().handle(new DrawShapeEvent(this, subQueryRegion));			
			initialShapes[i] = subQueryRegion.getInitialShape(SectorDirection.FAR, radioRange);
		}

		int index = this.minDistanceShapeIndex(orig.getPos(), initialShapes);

		this.getRoute().add(root);

		if(this.sectorInRects[index].contains(this.getSpatialQuery().getRect().getCentre())) {
			Rect rect = this.getSpatialQuery().getRect();

			GreedyForwardToRectEvent event = new GreedyForwardToRectEvent(root,
					rect, queryMessage, this);
			this.getSimulator().handle(event);
			initialNas[index] = (GPSRAttachment)event.getLastNode();
			if(this.sectorInRects[index].contains(initialNas[index].getPos())) {
				ecstaCoordinator = null;
			}
			else ecstaCoordinator = (GPSRAttachment)event.getLastNode();
			this.getSimulator().handle(new DrawNodeEvent(this, initialNas[index].getNode(),
					3, Color.red));
			minShapeFistTryLastNode = initialNas[index];
		}
		else {
			gftse = new GreedyForwardToANodeInSectorInRectEvent(root,
					 //root.getPos(),
					 this.sectorInRects[index],  SectorDirection.FAR, queryMessage, this);
			this.getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());
			if(gftse.isSuccess()) {
				initialNas[index] = (GPSRAttachment) gftse.getLastNode();
				this.getSimulator().handle(new DrawNodeEvent(this, initialNas[index].getNode(),
						3, Color.red));
			}
			minShapeFistTryLastNode = gftse.getFirstTryLastNode();
		}

		
		NeighborAttachment cur = minShapeFistTryLastNode;
		for (int i = index - 1; i >= 0; i--) {

			if(this.sectorInRects[i].contains(this.getSpatialQuery().getRect().getCentre())) {
				Rect rect = this.getSpatialQuery().getRect();

				GreedyForwardToRectEvent event = new GreedyForwardToRectEvent(root,
						rect, queryMessage, this);
				this.getSimulator().handle(event);
				initialNas[i] = (GPSRAttachment)event.getLastNode();
				if(this.sectorInRects[i].contains(initialNas[i].getPos())) {
					ecstaCoordinator = null;
				}
				else ecstaCoordinator = (GPSRAttachment)event.getLastNode();
				this.getSimulator().handle(new DrawNodeEvent(this, initialNas[i].getNode(),
						3, Color.red));
				cur = event.getLastNode();
			}
			else {
				gftse = new GreedyForwardToANodeInSectorInRectEvent(cur,
						 //root.getPos(),
						 this.sectorInRects[i],  SectorDirection.FAR, queryMessage, this);
				this.getSimulator().handle(gftse);
				this.getRoute().addAll(gftse.getRoute());
				if(gftse.isSuccess()) {
					initialNas[i] = (GPSRAttachment) gftse.getLastNode();
					this.getSimulator().handle(new DrawNodeEvent(this, initialNas[i].getNode(),
							3, Color.red));
				}
				cur = gftse.getFirstTryLastNode();
			}
		}

		cur  = minShapeFistTryLastNode;
		for (int i = index + 1; i < this.sectorInRects.length; i++) {
	
			if(this.sectorInRects[i].contains(this.getSpatialQuery().getRect().getCentre())) {
				Rect rect = this.getSpatialQuery().getRect();

				GreedyForwardToRectEvent event = new GreedyForwardToRectEvent(root,
						rect, queryMessage, this);
				this.getSimulator().handle(event);
				initialNas[i] = (GPSRAttachment)event.getLastNode();
				if(this.sectorInRects[i].contains(initialNas[i].getPos())) {
					ecstaCoordinator = null;
				}
				else ecstaCoordinator = (GPSRAttachment)event.getLastNode();
				this.getSimulator().handle(new DrawNodeEvent(this, initialNas[i].getNode(),
						3, Color.red));
				cur = event.getLastNode();
			}
			else {
				gftse = new GreedyForwardToANodeInSectorInRectEvent(cur,
						 //root.getPos(),
						 this.sectorInRects[i],  SectorDirection.FAR, queryMessage, this);
				this.getSimulator().handle(gftse);
				this.getRoute().addAll(gftse.getRoute());
				if(gftse.isSuccess()) {
					initialNas[i] = (GPSRAttachment) gftse.getLastNode();
					this.getSimulator().handle(new DrawNodeEvent(this, initialNas[i].getNode(),
							3, Color.red));
				}
				cur = gftse.getFirstTryLastNode();
			}
		}
		return true;
	}
	
	protected void buildTree() throws SensornetBaseException {
		Rect rect = this.getSpatialQuery().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);
		Node orig = this.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig.getAttachment(this
				.getName());

		BuildTreesEventForE2STAHyperbola bte = new BuildTreesEventForE2STAHyperbola(root,
				this.initialNas, this.initialPositions, this.sectorInRects, rect, queryMessage, this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}
	
	public void initInitialPositions() {
		Line middleLine, nearLine;
		
		this.initialPositions = new Position[this.sectorInRects.length];
		for(int i=0;i<this.sectorInRects.length;i++) {
			middleLine = this.sectorInRects[i].getSector().diagonalLine();
			nearLine = this.sectorInRects[i].caculateNearLine();
			this.initialPositions[i] = middleLine.intersect(nearLine);
			try {
				this.getSimulator().handle(new DrawCircleEvent(this, new Circle(this.initialPositions[i],3), Color.blue));
			} catch (SensornetBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		boolean hasDataCannotSendBack = false;

		for (int i = 0; i < this.initialNas.length; i++) {
			cur = this.initialNas[i];
			if(cur==null)continue;
			answerMessage = new Message(this.getParam().getANSWER_SIZE()
					* cur.getMessageCount(this.getSpatialQuery().getRect()),
					this.getQuery());
			event = new GreedyForwardToPointEvent(cur, pos, answerMessage, this);
			this.getSimulator().handle(event);
			
			if(!event.isSuccess()) hasDataCannotSendBack=true;
			this.formParentChilds(cur, event.getRoute());
		} // set answers
		return !hasDataCannotSendBack;
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
			this.initInitialPositions();
			if (!this.multicastQuery()) {
				logger.warn("cannot multicast query message");
				return false;
			}
			this.buildTree();
			this.regionCollect();
			if(!this.greedyBack()) {
				this.getStatistics().setSuccess(false);
				return false;
			}
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
