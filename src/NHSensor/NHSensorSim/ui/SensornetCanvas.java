package NHSensor.NHSensorSim.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Coordinator;
import NHSensor.NHSensorSim.core.Link;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.TraversedNodes;
import NHSensor.NHSensorSim.events.AttachmentDebugEvent;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.BroadcastLocationEvent;
import NHSensor.NHSensorSim.events.CollectDataInShapeViaNodeEvent;
import NHSensor.NHSensorSim.events.CollectDataInShapeViaNodeEventForHSA;
import NHSensor.NHSensorSim.events.CollectNeighborLocationsEvent;
import NHSensor.NHSensorSim.events.CommonEventTag;
import NHSensor.NHSensorSim.events.DrawCircleEvent;
import NHSensor.NHSensorSim.events.DrawLinkEvent;
import NHSensor.NHSensorSim.events.DrawLostNodesEvent;
import NHSensor.NHSensorSim.events.DrawNodeEvent;
import NHSensor.NHSensorSim.events.DrawRectEvent;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.DrawShapesEvent;
import NHSensor.NHSensorSim.events.DrawStringEvent;
import NHSensor.NHSensorSim.events.DrawVertexEvent;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.events.ForwardToRectEvent;
import NHSensor.NHSensorSim.events.ForwardToShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;
import NHSensor.NHSensorSim.events.ItineraryRouteEvent;
import NHSensor.NHSensorSim.events.ItineraryTraverseRingEvent;
import NHSensor.NHSensorSim.events.NodeHasQueryResultEvent;
import NHSensor.NHSensorSim.events.RDCTraverseSectorInRectEventFar;
import NHSensor.NHSensorSim.events.ShowTreeTopologyEvent;
import NHSensor.NHSensorSim.query.KNNQuery;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.AngleCircleInRing;
import NHSensor.NHSensorSim.shape.Arc;
import NHSensor.NHSensorSim.shape.BoundBoundInRect;
import NHSensor.NHSensorSim.shape.BoundCircleInRect;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.CircleAngleInRing;
import NHSensor.NHSensorSim.shape.CircleBoundInRect;
import NHSensor.NHSensorSim.shape.CircleCircleInRect;
import NHSensor.NHSensorSim.shape.CircleCircleInRing;
import NHSensor.NHSensorSim.shape.LineLineInSectorInRect;
import NHSensor.NHSensorSim.shape.LineSegment;
import NHSensor.NHSensorSim.shape.PolarPosition;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.util.Convertor;

public class SensornetCanvas extends JPanel implements SensornetView {
	private static final long serialVersionUID = 3L;

	private SensornetModel sensornetModel;
	private double nodeRadius = UIConfig.nodeRadius;
	private Coordinator coordinator;
	private int originalWidth;
	private int originalHeight;
	private int width;
	private int height;
	private Color queryColor = Color.BLUE;
	private Color radioColor = Color.RED;
	private Color nodeColor = UIConfig.nodeColor ;
	private Color answerNodeColor = Color.BLACK;
	private Color locationColor = Color.gray;
	private double answerNodeRadius = UIConfig.answerNodeRadius;
	private Hashtable tagColorMap;
	private Color networkRectangleColor = Color.GREEN;
	private Color descriptionColor = Color.GREEN;
	private float descriptionX = 20;
	private float descriptionY = 20;
	private int coordinatorOx = UIConfig.coordinatorOx;
	private int coordinatorOy = UIConfig.coordinatorOy;
	private double xScale = 1;
	private double yScale = 1;
	private boolean isDrawEvents = true;

	/*
	 * private int xScale = 1; private int yScale = 1;
	 */
	private Color greedyForwardToRectEventRectColor = Color.CYAN;
	private Color drawRectEventRectColor = Color.blue;
	private boolean drawRadioRange = true;
	private boolean isShowDescription = false;
	private boolean isShowNodeID = false;
	private boolean drawGreedyForwardEvent = true;
	private Color itineraryNodeEventColor = Color.RED;
	private Color coordinatorColor = Color.RED;
	private Color topologyColor = Color.RED;
	private Color linkColor = Color.RED;
	private Color linkQualityTextColor = Color.BLACK;
	private boolean isShowBroadcastAnswerEvent = true;
	private Color abnormalBroadcastEventColor = Color.YELLOW;

	private Color itineraryRouteEventLinkColor1 = Color.RED;
	private Color itineraryRouteEventLinkColor2 = Color.BLUE;
	private Color itineraryRouteEventTextColor = Color.BLACK;
	private Color attachmentDebugEventColor = Color.RED;
	private Color failNodeColor = Color.RED;

	public boolean isDrawRadioRange() {
		return drawRadioRange;
	}

	public void setDrawRadioRange(boolean drawRadioRange) {
		this.drawRadioRange = drawRadioRange;
	}

	public float getDescriptionX() {
		return descriptionX;
	}

	public void setDescriptionX(float descriptionX) {
		this.descriptionX = descriptionX;
	}

	public float getDescriptionY() {
		return descriptionY;
	}

	public void setDescriptionY(float descriptionY) {
		this.descriptionY = descriptionY;
	}

	public Color getDescriptionColor() {
		return descriptionColor;
	}

	public void setDescriptionColor(Color descriptionColor) {
		this.descriptionColor = descriptionColor;
	}

	public Color getLocationColor() {
		return locationColor;
	}

	public Color getNodeColor() {
		return nodeColor;
	}

	public void setNodeColor(Color nodeColor) {
		this.nodeColor = nodeColor;
	}

	public Color getQueryColor() {
		return queryColor;
	}

	public void setQueryColor(Color queryColor) {
		this.queryColor = queryColor;
	}

	public Color getRadioColor() {
		return radioColor;
	}

	public void setRadioColor(Color radioColor) {
		this.radioColor = radioColor;
	}

	protected void initTagColorMap() {
		this.tagColorMap = new Hashtable();
		this.tagColorMap.put(new Integer(CommonEventTag.SEND_ANSWER),
				Color.GREEN);
		this.tagColorMap.put(new Integer(CommonEventTag.SEND_QUERY), Color.RED);
	}

	public Color getTagColor(int tag) {
		return (Color) (this.tagColorMap.get(new Integer(tag)));
	}

	public SensornetCanvas(int width, int height, SensornetModel sensornetModel) {
		super();
		this.originalWidth = width;
		this.originalHeight = height;
		this.width = (int) (width * this.xScale + 2 * this.coordinatorOx);
		this.height = (int) (height * this.yScale + 2 * this.coordinatorOy);
		this.coordinator = new Coordinator(this.width, this.height,
				coordinatorOx, coordinatorOy, width * this.xScale, height
						* this.yScale);
		this.coordinator.setXscale(this.xScale);
		this.coordinator.setYscale(this.yScale);
		this.sensornetModel = sensornetModel;
		// Dimension dimension = new Dimension(this.width,this.height);
		// this.setSize(dimension);
		this.initTagColorMap();
		setPreferredSize(new Dimension(this.width, this.height));

	}

	public SensornetCanvas(int width, int height,
			SensornetModel sensornetModel, boolean isDrawEvents) {
		this(width, height, sensornetModel);
		this.isDrawEvents = isDrawEvents;
	}

	public boolean isDrawEvents() {
		return isDrawEvents;
	}

	public void setDrawEvents(boolean isDrawEvents) {
		this.isDrawEvents = isDrawEvents;
	}

	public double getXScale() {
		return xScale;
	}

	public void setScale(double scale) {
		this.xScale = scale;
		this.yScale = scale;
		this.width = (int) (this.originalWidth * this.xScale + 2 * this.coordinatorOx);
		this.height = (int) (this.originalHeight * this.yScale + 2 * this.coordinatorOy);
		this.coordinator = new Coordinator(this.width, this.height,
				coordinatorOx, coordinatorOy, this.originalWidth * this.xScale,
				this.originalHeight * this.yScale);
		this.coordinator.setXscale(this.xScale);
		this.coordinator.setYscale(this.yScale);
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.revalidate();
		// this.repaint();
	}

	public void setXScale(double scale) {
		this.xScale = scale;
		this.width = (int) (this.originalWidth * this.xScale + 2 * this.coordinatorOx);
		this.height = (int) (this.originalHeight * this.yScale + 2 * this.coordinatorOy);
		this.coordinator = new Coordinator(this.width, this.height,
				coordinatorOx, coordinatorOy, this.originalWidth * this.xScale,
				this.originalHeight * this.yScale);
		this.coordinator.setXscale(this.xScale);
		this.repaint();
	}

	public double getYScale() {
		return yScale;
	}

	public void setYScale(double scale) {
		this.yScale = scale;
		this.width = (int) (this.originalWidth * this.xScale + 2 * this.coordinatorOx);
		this.height = (int) (this.originalHeight * this.yScale + 2 * this.coordinatorOy);
		this.coordinator = new Coordinator(this.width, this.height,
				coordinatorOx, coordinatorOy, this.originalWidth * this.xScale,
				this.originalHeight * this.yScale);
		this.coordinator.setYscale(this.yScale);
		this.repaint();
	}

	public void drawNodes(Graphics2D g2d) {
		Node node;
		Vector nodes = this.getSensornetModel().getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			node = (Node) nodes.elementAt(i);
			this.drawNode(g2d, node);
		}
	}

	public void drawEvents(Graphics2D g2d) {
		Vector events = this.getSensornetModel().getEvents();
		Event event;
		try {
			for (int i = 0; i < events.size(); i++) {
				event = (Event) events.elementAt(i);
				if (i == events.size() - 1)
					this.drawEvent(g2d, event, true);
				else
					this.drawEvent(g2d, event, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawDescription(Graphics2D g2d) {
		g2d.drawString(this.getSensornetModel().getDescription(), this
				.getDescriptionX(), this.getDescriptionY());
	}

	protected void drawView(Graphics2D g2d) {
		Rectangle2D.Double rectangle = new Rectangle2D.Double(0, 0, this
				.getWidth() - 1, this.getHeight() - 1);

		Color oldColor = g2d.getColor();
		g2d.setColor(coordinatorColor);
		g2d.draw(rectangle);
		g2d.setColor(oldColor);
	}

	protected void drawCoordinator(Graphics2D g2d) {
		Rectangle2D.Double rectangle = new Rectangle2D.Double(this
				.getCoordinator().getOx(), this.getCoordinator().getOy(), this
				.getCoordinator().getWidth(), this.getCoordinator().getHeight());

		Color oldColor = g2d.getColor();
		g2d.setColor(coordinatorColor);
		g2d.draw(rectangle);
		g2d.setColor(oldColor);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		this.drawView(g2d);
		this.drawCoordinator(g2d);
		this.drawNodes(g2d);
		this.drawNetwork(g2d);
		this.drawFailNodes(g2d);

		if (this.isDrawEvents()) {
			this.drawEvents(g2d);
		}
		this.drawQuery(g2d);
		if (this.isShowDescription)
			this.drawDescription(g2d);
		this.drawOthers(g2d);
	}

	private void drawNetwork(Graphics2D g2d) {
		Network network = this.getSensornetModel().getNetwork();
		
		//this.drawNetwork(g2d, network.getRect(),networkRectangleColor);
		
		Circle[] circles = network.getHoles();
		if(circles!=null) {
			for(int i=0;i<circles.length;i++) {
				this.drawCircleForHole(g2d, circles[i], networkRectangleColor, false);
			}
		}
	}
	
	protected void drawCircleForHole(Graphics2D g2d, Circle circle, Color color, boolean fill) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(circle.getCentre()),
				(circle.getRadius()), color, fill);
		g2d.setColor(oldColor);
	}

	public void drawNode(Graphics2D g2d, Node node) {
		this.drawNode(g2d, node, true);
	}

	public void drawNode(Graphics2D g2d, Node node, boolean fill, boolean bigger) {
		if (bigger) {
			double addedSize = 2;
			this
					.drawBiggerNode(g2d, node, addedSize, this.getNodeColor(),
							fill);
		} else {
			this.drawNode(g2d, node, fill);
		}
	}
	
	public void drawNode(Graphics2D g2d, Node node, boolean fill, boolean bigger, Color color) {
		if (bigger) {
			double addedSize = 2;
			this
					.drawBiggerNode(g2d, node, addedSize, color,
							fill);
		} else {
			this.drawNode(g2d, node, fill, color);
		}
	}


	public void drawNode(Graphics2D g2d, Node node, boolean fill) {
		Color oldColor = g2d.getColor();
		g2d.setColor(this.getNodeColor());
		this.drawCircleForNode(g2d, Convertor.positionToPoint2DDouble(node.getPos()),
				this.getNodeRadius() / this.getCoordinator().getXscale(), this
						.getNodeColor(), fill);
		if (this.isShowNodeID) {
			this.drawString(g2d, Integer.toString(node.getId()), node.getPos()
					.getAddedPosition(new Position(0.1, 0.1)), this
					.getNodeColor());
		}
		g2d.setColor(oldColor);
	}
	
	public Color getAnswerNodeColor() {
		return answerNodeColor;
	}

	public void setAnswerNodeColor(Color answerNodeColor) {
		this.answerNodeColor = answerNodeColor;
	}

	public double getAnswerNodeRadius() {
		return answerNodeRadius;
	}

	public void setAnswerNodeRadius(double answerNodeRadius) {
		this.answerNodeRadius = answerNodeRadius;
	}

	public void drawAnswerNode(Graphics2D g2d, Node node, boolean fill) {
		Color oldColor = g2d.getColor();
		g2d.setColor(this.getAnswerNodeColor());
		this.drawCircleForNode(g2d, Convertor.positionToPoint2DDouble(node.getPos()),
				this.getAnswerNodeRadius() / this.getCoordinator().getXscale(), this.getAnswerNodeColor(), fill);
		if (this.isShowNodeID) {
			this.drawString(g2d, Integer.toString(node.getId()), node.getPos()
					.getAddedPosition(new Position(0.1, 0.1)), this
					.getNodeColor());
		}
		g2d.setColor(oldColor);
	}

	
	
	public void drawCircleForNode(Graphics2D g2d, Point2D.Double point, double radius,
			Color color, boolean fill) {
		Ellipse2D.Double ellipse2D = new Ellipse2D.Double(
				point.getX() - radius, point.getY() - radius, 2 * radius,
				2 * radius);
		Ellipse2D.Double ellipse2DReal = this.getCoordinator().ellipse(ellipse2D);
		Color oldColor = g2d.getColor();
		Paint oldPaint = g2d.getPaint();
		g2d.setColor(color);
		g2d.draw(ellipse2DReal);
		GradientPaint paint = new GradientPaint((float)(ellipse2DReal.getCenterX()-radius),  (float)(ellipse2DReal.getCenterY()-radius), color, 
				(float)(ellipse2DReal.getCenterX()), (float)(ellipse2DReal.getCenterY() + radius), color.brighter().brighter().brighter()); 
		g2d.setPaint(paint); 
		if (fill) {
			g2d.fill(ellipse2DReal);
		}
		
		g2d.setColor(oldColor);
		g2d.setPaint(oldPaint);
	}


	public void drawNode(Graphics2D g2d, Node node, boolean fill, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(node.getPos()),
				this.getNodeRadius() / this.getCoordinator().getXscale(), color, fill);
		if (this.isShowNodeID) {
			this.drawString(g2d, Integer.toString(node.getId()), node.getPos()
					.getAddedPosition(new Position(0.1, 0.1)), color);
		}
		g2d.setColor(oldColor);
	}

	public void drawBiggerNode(Graphics2D g2d, Node node, double addedSize,
			Color color, boolean fill) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(node.getPos()),
				(this.getNodeRadius() + addedSize)
						/ this.getCoordinator().getXscale(), color, fill);
		g2d.setColor(oldColor);
	}
	
	public void drawCircle(Graphics2D g2d, Circle circle,
			Color color, boolean fill) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(circle.getCentre()),
				(circle.getRadius())
						/ this.getCoordinator().getXscale(), color, fill);
		g2d.setColor(oldColor);
	}


	public void drawNodes(Graphics2D g2d, Vector nodes, Color color,
			boolean fill) {
		Node node;
		for (int i = 0; i < nodes.size(); i++) {
			node = (Node) nodes.elementAt(i);
			this.drawNode(g2d, node, color, fill);
		}
	}

	public void drawFailNodes(Graphics2D g2d) {
		Vector failNodes = this.getSensornetModel().getAlgorithm().getNetwork()
				.getFailNodes();
		this.drawNodes(g2d, failNodes, this.failNodeColor, true);
	}

	public void drawNode(Graphics2D g2d, Node node, Color color, boolean fill) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(node.getPos()),
				this.getNodeRadius() / this.getCoordinator().getXscale(),
				color, fill);
		// this.drawString(g2d, Integer.toString(node.getId()),
		// node.getPos().getAddedPosition(new Position(3,3)),
		// this.getNodeColor());
		g2d.setColor(oldColor);
	}

	public void drawString(Graphics2D g2d, String string, Position position,
			Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		Point2D.Double point = this.getCoordinator().point(
				Convertor.positionToPoint2DDouble(position));
		g2d.drawString(string, (float) point.getX(), (float) point.getY());
		g2d.setColor(oldColor);
	}

	public void drawCircle(Graphics2D g2d, Point2D.Double point, Color color,
			double radius) {
		this.drawCircle(g2d, point, radius, color, false);
	}

	public void drawRing(Graphics2D g2d, Ring ring, Color color, boolean fill) {
		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(ring
				.getCircleCentre()), ring.getLowRadius(), color, fill);
		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(ring
				.getCircleCentre()), ring.getHighRadius(), color, fill);
	}

	public void drawRingSector(Graphics2D g2d, RingSector ringSector,
			Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		Position circleCentre = ringSector.getCircleCentre();
		double lowDegree = Convertor
				.radianToDegree(ringSector.getStartRadian());
		double sita = Convertor.radianToDegree(ringSector.getSita());

		Rectangle2D.Double r1 = new Rectangle2D.Double(circleCentre.getX()
				- ringSector.getLowRadius(), circleCentre.getY()
				- ringSector.getLowRadius(), 2 * ringSector.getLowRadius(),
				2 * ringSector.getLowRadius());
		Rectangle2D.Double r2 = new Rectangle2D.Double(circleCentre.getX()
				- ringSector.getHighRadius(), circleCentre.getY()
				- ringSector.getHighRadius(), 2 * ringSector.getHighRadius(),
				2 * ringSector.getHighRadius());

		Arc2D.Double arc1 = new Arc2D.Double(this.getCoordinator()
				.rectangle(r1), lowDegree, sita, Arc2D.OPEN);
		Arc2D.Double arc2 = new Arc2D.Double(this.getCoordinator()
				.rectangle(r2), lowDegree, sita, Arc2D.OPEN);

		g2d.draw(arc1);
		g2d.draw(arc2);

		PolarPosition p1 = new PolarPosition(ringSector.getLowRadius(),
				ringSector.getStartRadian());
		PolarPosition p2 = new PolarPosition(ringSector.getHighRadius(),
				ringSector.getStartRadian());
		PolarPosition p3 = new PolarPosition(ringSector.getLowRadius(),
				ringSector.getEndRadian());
		PolarPosition p4 = new PolarPosition(ringSector.getHighRadius(),
				ringSector.getEndRadian());
		Line2D.Double line1 = new Line2D.Double(Convertor
				.positionToPoint2DDouble(p1.toPosition().getAddedPosition(
						ringSector.getCircleCentre())), Convertor
				.positionToPoint2DDouble(p2.toPosition().getAddedPosition(
						ringSector.getCircleCentre())));
		Line2D.Double line2 = new Line2D.Double(Convertor
				.positionToPoint2DDouble(p3.toPosition().getAddedPosition(
						ringSector.getCircleCentre())), Convertor
				.positionToPoint2DDouble(p4.toPosition().getAddedPosition(
						ringSector.getCircleCentre())));
		g2d.draw(this.getCoordinator().line(line1));
		g2d.draw(this.getCoordinator().line(line2));
		g2d.setColor(oldColor);
	}

	public void drawLink(Graphics2D g2d, Link link) {
		Line2D.Double line = new Line2D.Double(Convertor
				.positionToPoint2DDouble(link.getNode1().getPos()), Convertor
				.positionToPoint2DDouble(link.getNode2().getPos()));
		g2d.draw(this.getCoordinator().line(line));
	}

	public void drawLine(Graphics2D g2d, Position p1, Position p2, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		Line2D.Double line = new Line2D.Double(Convertor
				.positionToPoint2DDouble(p1), Convertor
				.positionToPoint2DDouble(p2));
		g2d.draw(this.getCoordinator().line(line));
		g2d.setColor(oldColor);
	}

	public void drawLink(Graphics2D g2d, Link link, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		Line2D.Double line = new Line2D.Double(Convertor
				.positionToPoint2DDouble(link.getNode1().getPos()), Convertor
				.positionToPoint2DDouble(link.getNode2().getPos()));
		g2d.draw(this.getCoordinator().line(line));
		g2d.setColor(oldColor);
	}

	public void drawCircle(Graphics2D g2d, Point2D.Double point, double radius,
			Color color, boolean fill) {
		Ellipse2D.Double ellipse2D = new Ellipse2D.Double(
				point.getX() - radius, point.getY() - radius, 2 * radius,
				2 * radius);
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		g2d.draw(this.getCoordinator().ellipse(ellipse2D));
		if (fill) {
			g2d.fill(this.getCoordinator().ellipse(ellipse2D));

		}
		g2d.setColor(oldColor);
	}

	public void drawLinkQuality(Graphics2D g2d, Node node1, Node node2) {
		if (node1 == node2 || node1.getId() == node2.getId())
			return;

		double minLinkQuality = 0.001;
		double linkQuality1 = this.getSensornetModel().getAlgorithm()
				.getNetwork().getLinkPRR(node1, node2);
		if (linkQuality1 < minLinkQuality)
			return;

		Link link = new Link(node1, node2);
		this.drawLink(g2d, link, this.linkColor);

		double x1 = node1.getPos().getX()
				+ (node2.getPos().getX() - node1.getPos().getX()) / 4.0;
		double y1 = node1.getPos().getY()
				+ (node2.getPos().getY() - node1.getPos().getY()) / 4.0;
		Position p1 = new Position(x1, y1);
		String linkQualityString1 = linkQuality1 + "";
		this.drawString(g2d, linkQualityString1, p1, this.linkQualityTextColor);
	}

	public void drawPairLinkQuality(Graphics2D g2d, Node node1, Node node2) {
		if (node1 == node2 || node1.getId() == node2.getId())
			return;

		double minLinkQuality = 0.001;
		double linkQuality1 = this.getSensornetModel().getAlgorithm()
				.getNetwork().getLinkPRR(node1, node2);
		double linkQuality2 = this.getSensornetModel().getAlgorithm()
				.getNetwork().getLinkPRR(node2, node1);
		if (linkQuality1 < minLinkQuality && linkQuality2 < minLinkQuality)
			return;

		Link link = new Link(node1, node2);
		this.drawNode(g2d, node1, true);
		this.drawNode(g2d, node2, true);
		this.drawLink(g2d, link, this.linkColor);

		double x1 = node1.getPos().getX()
				+ (node2.getPos().getX() - node1.getPos().getX()) / 4.0;
		double y1 = node1.getPos().getY()
				+ (node2.getPos().getY() - node1.getPos().getY()) / 4.0;
		double x2 = node1.getPos().getX()
				+ (node2.getPos().getX() - node1.getPos().getX()) / 4.0 * 3;
		double y2 = node1.getPos().getY()
				+ (node2.getPos().getY() - node1.getPos().getY()) / 4.0 * 3;
		Position p1 = new Position(x1, y1);
		Position p2 = new Position(x2, y2);
		String linkQualityString1 = linkQuality1 + "";
		String linkQualityString2 = linkQuality1 + "";
		this.drawString(g2d, linkQualityString1, p1, this.linkQualityTextColor);
		this.drawString(g2d, linkQualityString2, p2, this.linkQualityTextColor);
	}

	public void drawLinkWithText(Graphics2D g2d, Node node1, Node node2,
			String text, Color linkColor, Color textColor) {
		if (node1 == node2 || node1.getId() == node2.getId())
			return;

		Link link = new Link(node1, node2);
		this.drawLink(g2d, link, linkColor);

		double x1 = node1.getPos().getX()
				+ (node2.getPos().getX() - node1.getPos().getX()) / 4.0;
		double y1 = node1.getPos().getY()
				+ (node2.getPos().getY() - node1.getPos().getY()) / 4.0;
		Position p1 = new Position(x1, y1);
		this.drawString(g2d, text, p1, textColor);
	}

	public void drawAllLinkQuality(Graphics2D g2d) {
		Network network = this.getSensornetModel().getNetwork();
		Vector nodes = network.getNodes();
		Node recv, send;
		Integer neighborID;
		Algorithm alg = this.getSensornetModel().getAlgorithm();
		for (int i = 0; i < nodes.size(); i++) {
			send = (Node) nodes.elementAt(i);
			if (send.getNeighborIDs() == null || send.getNeighborIDs().size()==0) {
				for (int j = 0; j < nodes.size(); j++) {
					recv = (Node) nodes.elementAt(j);
					if (send != recv
							&& send.getPos().distance(recv.getPos()) <= alg
									.getParam().getRADIO_RANGE()) {
						this.drawLinkQuality(g2d, send, recv);
					}
				}
			} else {
				for (int j = 0; j < send.getNeighborIDs().size(); j++) {
					neighborID = (Integer) send.getNeighborIDs().elementAt(j);
					recv = network.getNode(neighborID.intValue());
					this.drawLinkQuality(g2d, send, recv);
				}
			}
		}
	}

	public void drawAllLinks(Graphics2D g2d) {
		Network network = this.getSensornetModel().getNetwork();
		Vector nodes = network.getNodes();
		Node recv, send;
		Algorithm alg = this.getSensornetModel().getAlgorithm();
		Integer neighborID;

		for (int i = 0; i < nodes.size(); i++) {
			send = (Node) nodes.elementAt(i);
			if (send.getNeighborIDs() == null || send.getNeighborIDs().size()==0) {
				for (int j = 0; j < nodes.size(); j++) {
					recv = (Node) nodes.elementAt(j);
					if (send != recv
							&& send.getPos().distance(recv.getPos()) <= alg
									.getParam().getRADIO_RANGE()) {
						this.drawNodeLink(g2d, send, recv);
					}
				}
			} else {
				for (int j = 0; j < send.getNeighborIDs().size(); j++) {
					neighborID = (Integer) send.getNeighborIDs().elementAt(j);
					recv = network.getNode(neighborID.intValue());
					this.drawNodeLink(g2d, send, recv);
				}
			}
		}
	}

	public void drawNodeLink(Graphics2D g2d, Node node1, Node node2) {
		if (node1 == node2 || node1.getId() == node2.getId())
			return;

		Link link = new Link(node1, node2);
		this.drawNode(g2d, node1, true);
		this.drawNode(g2d, node2, true);
		this.drawLink(g2d, link, this.linkColor);

	}

	public void drawNodeLink(Graphics2D g2d, Node node1, Node node2, Color color) {
		if (node1 == node2 || node1.getId() == node2.getId())
			return;

		Link link = new Link(node1, node2);
		this.drawNode(g2d, node1, true);
		this.drawNode(g2d, node2, true);
		this.drawLink(g2d, link, color);
	}

	public void drawEvent(Graphics2D g2d, Event event, boolean isLast) {
		if (!event.isVisible())
			return;
		
		if (event instanceof BroadcastEvent) {
			BroadcastEvent be = (BroadcastEvent) event;
			Color oldColor = g2d.getColor();

			if (be.getTag() == CommonEventTag.SEND_ANSWER
					&& !this.isShowBroadcastAnswerEvent) {
				return;
			}
			Color tagColor = this.getTagColor(be.getTag());
			if (tagColor != null) {
				g2d.setColor(tagColor);
			} else {
				g2d.setColor(this.getRadioColor());
			}

			if (be.getColor() != null) {
				g2d.setColor(be.getColor());
			}

			if (this.isDrawRadioRange() && isLast) {
				Position p1 = be.getSender().getNode().getPos();
				this.drawCircle(g2d, Convertor.positionToPoint2DDouble(p1), be
						.getRadioRange(), this.getRadioColor(), false);

				// PolarPosition p2 = new
				// PolarPosition(be.getSender().getNode().getPos(),
				// be.getRadioRange(), 0);
				// this.drawLine(g2d, p1, p2.toPosition(),this.getRadioColor());
			}
			if (be.getShape() != null) {
				this.drawNode(g2d, be.getSender().getNode(), true, isLast);
				// /*
				Vector neighborNodes = be.getNeighborNodes();
				Node neighborNode;
				for (int i = 0; i < neighborNodes.size(); i++) {
					neighborNode = (Node) neighborNodes.elementAt(i);
					if (!neighborNode.isAlive()
							|| !be.getShape().contains(neighborNode.getPos()))
						continue;
					this.drawNode(g2d, neighborNode, true);
				}
			} else if (be.getReceiver() != null) {
				if (be.getBroadcastTimes() >= NHSensor.NHSensorSim.core.Constants.MAX_BROADCAST_TIMES)
					g2d.setColor(abnormalBroadcastEventColor);
				Link link = new Link(be.getSender().getNode(), be.getReceiver()
						.getNode());
				this.drawNode(g2d, be.getSender().getNode(), true, isLast, Color.red);
				this.drawNode(g2d, be.getReceiver().getNode(), true, false, Color.black);
				this.drawLink(g2d, link);
			} else {
				Color color = this.getNodeColor();
				if(be.getColor()!=null) color = be.getColor();
				this.drawNode(g2d, be.getSender().getNode(), true, isLast, Color.red);

				// /*
				Vector neighborNodes = be.getNeighborNodes();
				Node neighborNode;
				for (int i = 0; i < neighborNodes.size(); i++) {
					neighborNode = (Node) neighborNodes.elementAt(i);
					if (!neighborNode.isAlive())
						continue;
					this.drawNode(g2d, neighborNode, true, false, Color.black);
				}
			}
			g2d.setColor(oldColor);
		} else if (event instanceof BroadcastLocationEvent) {
			BroadcastLocationEvent be = (BroadcastLocationEvent) event;
			Color oldColor = g2d.getColor();

			if (be.getTag() == CommonEventTag.SEND_ANSWER
					&& !this.isShowBroadcastAnswerEvent) {
				return;
			}
			Color tagColor = this.getTagColor(be.getTag());
			if (tagColor != null) {
				g2d.setColor(tagColor);
			} else {
				g2d.setColor(this.getLocationColor());
			}

			if (be.getColor() != null) {
				g2d.setColor(be.getColor());
			}

			if (this.isDrawRadioRange() && isLast) {
				Position p1 = be.getSender().getNode().getPos();
				this.drawCircle(g2d, Convertor.positionToPoint2DDouble(p1), be
						.getRadioRange(), this.getLocationColor(), false);

				// PolarPosition p2 = new
				// PolarPosition(be.getSender().getNode().getPos(),
				// be.getRadioRange(), 0);
				// this.drawLine(g2d, p1, p2.toPosition(),this.getRadioColor());
			}
			if (be.getShape() != null) {
				this.drawNode(g2d, be.getSender().getNode(), true, isLast);
				// /*
				Vector neighborNodes = be.getNeighborNodes();
				Node neighborNode;
				for (int i = 0; i < neighborNodes.size(); i++) {
					neighborNode = (Node) neighborNodes.elementAt(i);
					if (!neighborNode.isAlive()
							|| !be.getShape().contains(neighborNode.getPos()))
						continue;
					this.drawNode(g2d, neighborNode, true);
				}
			} else if (be.getReceiver() != null) {
				if (be.getBroadcastTimes() >= NHSensor.NHSensorSim.core.Constants.MAX_BROADCAST_TIMES)
					g2d.setColor(abnormalBroadcastEventColor);
				Link link = new Link(be.getSender().getNode(), be.getReceiver()
						.getNode());
				this.drawNode(g2d, be.getSender().getNode(), true, isLast);
				this.drawNode(g2d, be.getReceiver().getNode(), true);
				this.drawLink(g2d, link);
			} else {
				Color color = this.getNodeColor();
				if(be.getColor()!=null) color = be.getColor();
				this.drawNode(g2d, be.getSender().getNode(), true, isLast, color);

				// /*
				Vector neighborNodes = be.getNeighborNodes();
				Node neighborNode;
				for (int i = 0; i < neighborNodes.size(); i++) {
					neighborNode = (Node) neighborNodes.elementAt(i);
					if (!neighborNode.isAlive())
						continue;
					this.drawNode(g2d, neighborNode, true);
				}
			}
			g2d.setColor(oldColor);
		} else if(event instanceof CollectNeighborLocationsEvent) {
			CollectNeighborLocationsEvent cnle = (CollectNeighborLocationsEvent)event;
			Color oldColor = g2d.getColor();
			g2d.setColor(this.getLocationColor());
			
			if (this.isDrawRadioRange() && isLast) {
				Position p1 = cnle.getNode().getPos();
				this.drawCircle(g2d, Convertor.positionToPoint2DDouble(p1), cnle
						.getRadioRange(), this.getLocationColor(), false);
			}
			this.drawNode(g2d, cnle.getNode().getNode(), true, isLast, this.getLocationColor());

			// /*
			Vector neighborNodes = cnle.getNeighborNodes();
			Node neighborNode;
			for (int i = 0; i < neighborNodes.size(); i++) {
				neighborNode = (Node) neighborNodes.elementAt(i);
				if (!neighborNode.isAlive())
					continue;
				this.drawNode(g2d, neighborNode, true);
			}
			g2d.setColor(oldColor);
		
		} else if (event instanceof DrawRectEvent) {
			DrawRectEvent drawRectEvent = (DrawRectEvent) event;
			this.drawRectangle(g2d, drawRectEvent.getRect(),
					drawRectEventRectColor);
		} else if (event instanceof DrawShapeEvent) {
			DrawShapeEvent drawShapeEvent = (DrawShapeEvent) event;
			Shape shape = drawShapeEvent.getShape();

			if (shape instanceof RingSector) {
				this.drawRingSector(g2d, (RingSector) shape, this
						.getQueryColor());
			} else if (shape instanceof Ring) {
				this.drawRing(g2d, (Ring) shape, this.getQueryColor(), false);
			} else if (shape instanceof Rect) {
				this.drawRectangle(g2d, (Rect) shape, drawRectEventRectColor);
			} else if (shape instanceof AngleCircleInRing) {
				this.drawAngleCircleInRing(g2d, (AngleCircleInRing) shape, this
						.getQueryColor());
			} else if (shape instanceof CircleAngleInRing) {
				this.drawCircleAngleInRing(g2d, (CircleAngleInRing) shape, this
						.getQueryColor());
			} else if (shape instanceof CircleCircleInRing) {
				this.drawCircleCircleInRing(g2d, (CircleCircleInRing) shape,
						this.getQueryColor());
			} else if (shape instanceof CircleCircleInRect) {
				this.drawCircleCircleInRect(g2d, (CircleCircleInRect) shape,
						this.getQueryColor());
			} else if (shape instanceof CircleBoundInRect) {
				this.drawCircleBoundInRect(g2d, (CircleBoundInRect) shape, this
						.getQueryColor());
			} else if (shape instanceof BoundCircleInRect) {
				this.drawBoundCircleInRect(g2d, (BoundCircleInRect) shape, this
						.getQueryColor());
			} else if (shape instanceof Circle) {
				Circle circle = (Circle) shape;
				this.drawCircle(g2d, Convertor.positionToPoint2DDouble(circle
						.getCentre()), circle.getRadius(),
						this.getQueryColor(), false);
			} else if (shape instanceof BoundBoundInRect) {
				this.drawRectangle(g2d, ((BoundBoundInRect) shape).toRect(),
						this.getQueryColor());

			} else if (shape instanceof SectorInRect) {
				this.drawSectorInRect(g2d, (SectorInRect) shape, this
						.getQueryColor());
			} else if (shape instanceof LineLineInSectorInRect) {
				this.drawLineLineInSectorInRect(g2d,
						(LineLineInSectorInRect) shape, this.getQueryColor());
			} else if (shape instanceof LineSegment) {
				this.drawLineSegment(g2d, (LineSegment) shape,
						((DrawShapeEvent) event).getColor());
			}
		} else if (event instanceof DrawShapesEvent) {
			DrawShapesEvent drawShapesEvent = (DrawShapesEvent) event;
			Vector shapes = drawShapesEvent.getShapes();
			Shape shape;
			DrawShapeEvent drawShapeEvent;

			for (int i = 0; i < shapes.size(); i++) {
				shape = (Shape) shapes.elementAt(i);
				drawShapeEvent = new DrawShapeEvent(event.getAlg(), shape);
				this.drawEvent(g2d, drawShapeEvent, isLast);
			}
		} else if (event instanceof NodeHasQueryResultEvent) {
			NodeHasQueryResultEvent e = (NodeHasQueryResultEvent) event;
			this.drawCircle(g2d, Convertor.positionToPoint2DDouble(e.getNode()
					.getNode().getPos()), this.answerNodeRadius,
					this.answerNodeColor, false);
		} else if (event instanceof AttachmentDebugEvent) {
			AttachmentDebugEvent e = (AttachmentDebugEvent) event;
			this.drawNode(g2d, e.getNode(), attachmentDebugEventColor, true);
		} else if (event instanceof ForwardToRectEvent) {
			ForwardToRectEvent e = (ForwardToRectEvent) event;
			Rect destRect = e.getRect();
			Position rootPosition = e.getRoot().getNode().getPos();
			Line2D.Double line = new Line2D.Double(Convertor
					.positionToPoint2DDouble(destRect.getCentre()), Convertor
					.positionToPoint2DDouble(rootPosition));
			if (this.drawGreedyForwardEvent && isLast) {
				this.drawRectangle(g2d, destRect,
						greedyForwardToRectEventRectColor);
				g2d.draw(this.getCoordinator().line(line));
			}
		} else if (event instanceof GreedyForwardToPointEvent) {
			GreedyForwardToPointEvent e = (GreedyForwardToPointEvent) event;
			Position rootPosition = e.getRoot().getNode().getPos();
			Position destPosition = e.getDest();
			Line2D.Double line = new Line2D.Double(Convertor
					.positionToPoint2DDouble(destPosition), Convertor
					.positionToPoint2DDouble(rootPosition));
			if (this.drawGreedyForwardEvent && isLast) {
				g2d.draw(this.getCoordinator().line(line));
			}
		} else if (event instanceof ForwardToShapeEvent) {
			ForwardToShapeEvent ftse = (ForwardToShapeEvent) event;
			Position rootPosition = ftse.getRoot().getNode().getPos();
			Shape destShape = ftse.getShape();
			Position destPosition = destShape.getCentre();
			Line2D.Double line = new Line2D.Double(Convertor
					.positionToPoint2DDouble(destPosition), Convertor
					.positionToPoint2DDouble(rootPosition));
			if (this.drawGreedyForwardEvent && isLast) {
				DrawShapeEvent drawShapeEvent = new DrawShapeEvent(event
						.getAlg(), destShape);
				this.drawEvent(g2d, drawShapeEvent, isLast);
				g2d.draw(this.getCoordinator().line(line));
				this.drawCircle(g2d, Convertor
						.positionToPoint2DDouble(rootPosition), ftse.getRoot()
						.getRadioRange(), this.getRadioColor(), false);

			}
		} else if (event instanceof ItineraryRouteEvent) {
			ItineraryRouteEvent e = (ItineraryRouteEvent) event;

			Node curNode, nextNode;
			Link link;

			if (e.getRoute().size() > 0) {
				curNode = e.getNode(0);
				this.drawNode(g2d, curNode, true);
			}

			for (int i = 0; i < e.getRoute().size() - 1; i++) {
				curNode = e.getNode(i);
				nextNode = e.getNode(i + 1);
				this.drawNode(g2d, nextNode, true);
				link = new Link(curNode, nextNode);
				if (i % 2 == 0) {
					this.drawLinkWithText(g2d, curNode, nextNode, Integer
							.toString(i), this.itineraryRouteEventLinkColor1,
							this.itineraryRouteEventTextColor);
				} else {
					this.drawLinkWithText(g2d, curNode, nextNode, Integer
							.toString(i), this.itineraryRouteEventLinkColor2,
							this.itineraryRouteEventTextColor);
				}

			}
		} else if (event instanceof ItineraryNodeEvent) {
			ItineraryNodeEvent e = (ItineraryNodeEvent) event;
			Node curNode, nextNode, dataNode;
			Vector dataNodes = e.getDataNodes();
			Link link;
			curNode = e.getCurItineraryNode().getNode();
			this.drawNode(g2d, curNode, true);

			/*
			 * if(dataNodes!=null) { for(int i=0;i<dataNodes.size();i++) {
			 * dataNode =
			 * ((NeighborAttachment)dataNodes.elementAt(i)).getNode();
			 * this.drawNode(g2d, dataNode,true); } }
			 */

			if (e.getNextItineraryNode() == null) {
				return;
			} else {
				nextNode = e.getNextItineraryNode().getNode();
				this.drawNode(g2d, nextNode, true);
				link = new Link(curNode, nextNode);
				this.drawLink(g2d, link, this.itineraryNodeEventColor);
			}

		} else if (event instanceof ItineraryTraverseRingEvent) {
			ItineraryTraverseRingEvent e = (ItineraryTraverseRingEvent) event;
			DrawShapeEvent drawShapeEvent = new DrawShapeEvent(event.getAlg(),
					e.getRing());
			this.drawEvent(g2d, drawShapeEvent, isLast);

			Color oldColor = g2d.getColor();
			g2d.setColor(this.getQueryColor());
			this.drawNode(g2d, e.getRoot().getNode(), true);
			g2d.setColor(oldColor);
		} else if (event instanceof ShowTreeTopologyEvent) {
			ShowTreeTopologyEvent stte = (ShowTreeTopologyEvent) event;
			Vector nodeAttachments = stte.getNodeAttachments();

			NodeAttachment parent, child;
			Link link;
			for (int i = 0; i < nodeAttachments.size(); i++) {
				child = (NodeAttachment) nodeAttachments.elementAt(i);
				parent = child.getParent();

				if (parent != null) {
					link = new Link(child.getNode(), parent.getNode());
					this.drawLink(g2d, link, this.topologyColor);
				}
			}
		} else if (event instanceof CollectDataInShapeViaNodeEvent) {
			CollectDataInShapeViaNodeEvent e = (CollectDataInShapeViaNodeEvent) event;
			Position rootPosition = e.getRoot().getNode().getPos();
			Shape destShape = e.getShape();
			Position destPosition = destShape.getCentre();
			Line2D.Double line = new Line2D.Double(Convertor
					.positionToPoint2DDouble(destPosition), Convertor
					.positionToPoint2DDouble(rootPosition));
			if (this.drawGreedyForwardEvent && isLast) {
				DrawShapeEvent drawShapeEvent = new DrawShapeEvent(event
						.getAlg(), destShape);
				this.drawEvent(g2d, drawShapeEvent, isLast);
				g2d.draw(this.getCoordinator().line(line));
				this.drawCircle(g2d, Convertor
						.positionToPoint2DDouble(rootPosition), e.getRoot()
						.getRadioRange(), this.getRadioColor(), false);

			}
			
		} else if (event instanceof CollectDataInShapeViaNodeEventForHSA) {
			CollectDataInShapeViaNodeEventForHSA e = (CollectDataInShapeViaNodeEventForHSA) event;
			Position rootPosition = e.getRoot().getNode().getPos();
			Shape destShape = e.getShape();
			Position destPosition = destShape.getCentre();
			Line2D.Double line = new Line2D.Double(Convertor
					.positionToPoint2DDouble(destPosition), Convertor
					.positionToPoint2DDouble(rootPosition));
			if (this.drawGreedyForwardEvent && isLast) {
				DrawShapeEvent drawShapeEvent = new DrawShapeEvent(event
						.getAlg(), destShape);
				this.drawEvent(g2d, drawShapeEvent, isLast);
				g2d.draw(this.getCoordinator().line(line));
				this.drawCircle(g2d, Convertor
						.positionToPoint2DDouble(rootPosition), e.getRoot()
						.getRadioRange(), this.getRadioColor(), false);

			}
			
		}else if (event instanceof DrawNodeEvent) {
			DrawNodeEvent dne = (DrawNodeEvent) event;
			this.drawBiggerNode(g2d, dne.getNode(), dne.getAddedSize(), dne
					.getColor(), dne.isFill());
		} else if (event instanceof DrawCircleEvent) {
			DrawCircleEvent dce = (DrawCircleEvent) event;
			this.drawCircle(g2d, dce.getCircle(), dce.getColor(), dce.isFill());
		} else if (event instanceof DrawStringEvent) {
			DrawStringEvent dse = (DrawStringEvent) event;
			Position position = dse.getPos().move(dse.getxMove(), dse.getyMove());
			this.drawString(g2d, dse.getString(), position, dse.getColor());
		}else if (event instanceof DrawLostNodesEvent) {
			DrawLostNodesEvent dlne = (DrawLostNodesEvent) event;
			int addedSize = 5;
			Color color = Color.orange;
			for (int i = 0; i < dlne.getLostNodes().size(); i++) {
				this.drawBiggerNode(g2d, (Node) dlne.getLostNodes()
						.elementAt(i), addedSize, color, true);
			}
		} else if (event instanceof DrawVertexEvent) {
			DrawVertexEvent dve = (DrawVertexEvent) event;
			int addedSize = 5;
			Color color = Color.orange;
			this.drawBiggerNode(g2d, dve.getVertex(), addedSize, color, true);
		} else if (event instanceof DrawLinkEvent) {
			DrawLinkEvent dle = (DrawLinkEvent) event;
			Link link = new Link(dle.getNode1(), dle.getNode2());
			this.drawLink(g2d, link, dle.getColor());
		} else if (event instanceof RDCTraverseSectorInRectEventFar) {
			RDCTraverseSectorInRectEventFar re = (RDCTraverseSectorInRectEventFar) event;
			SectorInRect sectorInRect = re.getSectorInRect();
			DrawShapeEvent drawShapeEvent = new DrawShapeEvent(re.getAlg(),
					sectorInRect);
			this.drawEvent(g2d, drawShapeEvent, isLast);
		}
		/*
		 * else if(event instanceof DrawNodeEvent) { }
		 */
		else {

		}
	}

	public void drawNodesHaveQueryResult(Graphics2D g2d,
			TraversedNodes traversedNodes) {
		Vector nodes = traversedNodes.getNodes();
		Node node;

		for (int i = 0; i < nodes.size(); i++) {
			node = (Node) nodes.elementAt(i);
			this.drawAnswerNode(g2d, node, true);
			// this.drawCircle(g2d,
			// Convertor.positionToPoint2DDouble(node.getPos()),
			// this.answerNodeRadius, this.answerNodeColor, false);
		}
	}

	public void drawQuery(Graphics2D g2d) {
		QueryBase query = this.getSensornetModel().getQuery();
		if (query == null)
			return;
		// spatial query
		Color oldColor = g2d.getColor();
		g2d.setColor(this.getQueryColor());
		if (query instanceof Query) {
			Query q = (Query) query;
			this.drawNode(g2d, q.getOrig(), true);
			this.drawRectangle(g2d, q.getRect());
		} else if (query instanceof KNNQuery) {
			KNNQuery q = (KNNQuery) query;
			this.drawNode(g2d, q.getOrig(), true);
			this.drawCircle(g2d, Convertor.positionToPoint2DDouble(q
					.getCenter()), this.getNodeRadius(), this.getQueryColor(),
					true);
		}
		g2d.setColor(oldColor);
	}

	public void drawOthers(Graphics2D g2d) {
		// Position p = new Position(200,200);
		// Arc arc = new Arc(p,
		// 100,Math.PI/3,Math.PI*2/3);
		// this.drawArc(g2d, arc, this.getQueryColor());
		return;
	}

	public void drawRectangle(Graphics2D g2d, Rect rect, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		g2d.draw(this.getCoordinator().rectangle(
				Convertor.rectToRectangle2DDouble(rect)));
		g2d.setColor(oldColor);
	}
	

	public void drawAngleCircleInRing(Graphics2D g2d,
			AngleCircleInRing angleCircleInRing, Color color) {
		Arc arc = angleCircleInRing.getArc();
		LineSegment lineSegment = angleCircleInRing.getAngleLineSegment();
		this.drawLineSegment(g2d, lineSegment, color);
		this.drawArc(g2d, arc, color);
	}

	public void drawLineSegment(Graphics2D g2d, LineSegment lineSegment,
			Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);

		Line2D.Double line = new Line2D.Double(Convertor
				.positionToPoint2DDouble(lineSegment.getPos1()), Convertor
				.positionToPoint2DDouble(lineSegment.getPos2()));
		g2d.draw(this.getCoordinator().line(line));
		g2d.setColor(oldColor);
	}

	public void drawArc(Graphics2D g2d, Arc arc, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		g2d.draw(this.getCoordinator().arc(Convertor.arcToArc2DDouble(arc)));
		g2d.setColor(oldColor);
	}

	public void drawCircleAngleInRing(Graphics2D g2d,
			CircleAngleInRing circleAngleInRing, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		Arc arc = circleAngleInRing.getArc();
		LineSegment lineSegment = circleAngleInRing.getAngleLineSegment();
		this.drawLineSegment(g2d, lineSegment, color);
		this.drawArc(g2d, arc, color);
		g2d.setColor(oldColor);
	}

	public void drawCircleCircleInRing(Graphics2D g2d,
			CircleCircleInRing circleCircleInRing, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		Arc arc1 = circleCircleInRing.getArcBegin();
		Arc arc2 = circleCircleInRing.getArcEnd();
		this.drawArc(g2d, arc1, color);
		this.drawArc(g2d, arc2, color);
		g2d.setColor(oldColor);
	}

	public void drawCircleCircleInRect(Graphics2D g2d,
			CircleCircleInRect circleCircleInRect, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		Arc arc1 = circleCircleInRect.getBeginArc();
		Arc arc2 = circleCircleInRect.getEndArc();
		this.drawArc(g2d, arc1, color);
		this.drawArc(g2d, arc2, color);
		g2d.setColor(oldColor);
	}

	public void drawCircleBoundInRect(Graphics2D g2d,
			CircleBoundInRect circleBoundInRect, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		Arc arc1 = circleBoundInRect.getBeginArc();
		LineSegment lineSegment = circleBoundInRect.getEndLineSegment();
		this.drawArc(g2d, arc1, color);
		this.drawLineSegment(g2d, lineSegment, color);
		g2d.setColor(oldColor);
	}

	public void drawBoundCircleInRect(Graphics2D g2d,
			BoundCircleInRect boundCircleInRect, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		LineSegment lineSegment = boundCircleInRect.getBeginLineSegment();
		Arc arc1 = boundCircleInRect.getEndArc();
		this.drawArc(g2d, arc1, color);
		this.drawLineSegment(g2d, lineSegment, color);
		g2d.setColor(oldColor);
	}

	public void drawLineLineInSectorInRect(Graphics2D g2d,
			LineLineInSectorInRect lineLineInSectorInRect, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		LineSegment[] ls = lineLineInSectorInRect.getLineSegments();

		for (int i = 0; i < ls.length; i++) {
			this.drawLineSegment(g2d, ls[i], color);
		}
		g2d.setColor(oldColor);
	}

	public void drawSectorInRect(Graphics2D g2d, SectorInRect sectorInRect,
			Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		LineSegment bl = sectorInRect.getBeginLineSegment();
		LineSegment el = sectorInRect.getEndLineSegment();

		this.drawLineSegment(g2d, bl, color);
		this.drawLineSegment(g2d, el, color);
		g2d.setColor(oldColor);
	}

	public void drawNetwork(Graphics2D g2d, Rect rect, Color color) {
		Color oldColor = g2d.getColor();
		g2d.setColor(color);
		g2d.draw(this.getCoordinator().network(
				Convertor.rectToRectangle2DDouble(rect)));
		g2d.setColor(oldColor);
	}

	public void drawRectangle(Graphics2D g2d, Rect rect) {
		g2d.draw(this.getCoordinator().rectangle(
				Convertor.rectToRectangle2DDouble(rect)));
	}

	public Coordinator getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getNodeRadius() {
		return nodeRadius;
	}

	public void setNodeRadius(double nodeRadius) {
		this.nodeRadius = nodeRadius;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public SensornetModel getSensornetModel() {
		return sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public void updateSensornetView() {
		this.repaint();
	}
}
