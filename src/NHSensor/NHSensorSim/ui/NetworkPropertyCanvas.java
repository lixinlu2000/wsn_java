package NHSensor.NHSensorSim.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;

import NHSensor.NHSensorSim.core.Coordinator;
import NHSensor.NHSensorSim.core.Link;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.events.CommonEventTag;
import NHSensor.NHSensorSim.shape.AngleCircleInRing;
import NHSensor.NHSensorSim.shape.Arc;
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
import NHSensor.NHSensorSim.util.Convertor;

public class NetworkPropertyCanvas extends JPanel implements NetworkView {
	private static final long serialVersionUID = 3L;

	private NetworkModel networkModel;
	private double nodeRadius = 3;
	private Coordinator coordinator;
	private int originalWidth;
	private int originalHeight;
	private int width;
	private int height;
	private Color queryColor = Color.BLUE;
	private Color radioColor = Color.RED;
	private Color nodeColor = Color.BLACK;
	private Color locationColor = Color.gray;
	private double answerNodeRadius = 8;
	private Hashtable tagColorMap;
	private Color networkRectangleColor = Color.GREEN;
	private Color descriptionColor = Color.GREEN;
	private float descriptionX = 20;
	private float descriptionY = 20;
	private int coordinatorOx = 60;
	private int coordinatorOy = 60;
	private double xScale = 1;
	private double yScale = 1;

	/*
	 * private int xScale = 1; private int yScale = 1;
	 */
	private boolean drawRadioRange = true;
	private boolean isShowDescription = false;
	private boolean isShowNodeID = false;

	private Color coordinatorColor = Color.RED;
	private Color linkColor = Color.RED;
	private Color linkQualityTextColor = Color.BLACK;

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

	public NetworkPropertyCanvas(NetworkModel networkModel) {
		super();
		int width,height;
		width = (int)networkModel.getNetwork().getWidth();
		height = (int)networkModel.getNetwork().getHeigth();
		this.originalWidth = width;
		this.originalHeight = height;
		this.width = (int) (width * this.xScale + 2 * this.coordinatorOx);
		this.height = (int) (height * this.yScale + 2 * this.coordinatorOy);
		this.coordinator = new Coordinator(this.width, this.height,
				coordinatorOx, coordinatorOy, width * this.xScale, height
						* this.yScale);
		this.coordinator.setXscale(this.xScale);
		this.coordinator.setYscale(this.yScale);
		this.networkModel = networkModel;
		this.networkModel.addNetworkView(this);
		// Dimension dimension = new Dimension(this.width,this.height);
		// this.setSize(dimension);
		this.initTagColorMap();
		setPreferredSize(new Dimension(this.width, this.height));
		networkModel.addNetworkView(this);

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
		this.repaint();
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
		Vector nodes = this.getNetworkModel().getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			node = (Node) nodes.elementAt(i);
			this.drawNode(g2d, node);
		}
	}


	public void drawDescription(Graphics2D g2d) {
		g2d.drawString(this.getNetworkModel().getDescription(), this
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
		this.drawAllLinks(g2d);

		if (this.isShowDescription)
			this.drawDescription(g2d);
		this.drawOthers(g2d);
	}

	private void drawNetwork(Graphics2D g2d) {
		Network network = this.getNetworkModel().getNetwork();
		
		this.drawNetwork(g2d, network.getRect(),
				networkRectangleColor);
		
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
		this.drawNode(g2d, node, false);
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
		this.drawCircle(g2d, Convertor.positionToPoint2DDouble(node.getPos()),
				this.getNodeRadius() / this.getCoordinator().getXscale(), this
						.getNodeColor(), fill);
		if (this.isShowNodeID) {
			this.drawString(g2d, Integer.toString(node.getId()), node.getPos()
					.getAddedPosition(new Position(0.1, 0.1)), this
					.getNodeColor());
		}
		g2d.setColor(oldColor);
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
		Vector failNodes = this.getNetworkModel().getNetwork()
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
		double linkQuality1 = this.getNetworkModel().getNetwork().getLinkPRR(node1, node2);
		if (linkQuality1 < minLinkQuality)
			return;

		Link link = new Link(node1, node2);
		this.drawNode(g2d, node1, true);
		this.drawNode(g2d, node2, true);
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
		double linkQuality1 = this.getNetworkModel().getNetwork().getLinkPRR(node1, node2);
		double linkQuality2 = this.getNetworkModel().getNetwork().getLinkPRR(node2, node1);
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
		Network network = this.getNetworkModel().getNetwork();
		Vector nodes = network.getNodes();
		Node recv, send;
		Integer neighborID;
		for (int i = 0; i < nodes.size(); i++) {
			send = (Node) nodes.elementAt(i);

			for (int j = 0; j < send.getNeighborIDs().size(); j++) {
				neighborID = (Integer) send.getNeighborIDs().elementAt(j);
				recv = network.getNode(neighborID.intValue());
				this.drawLinkQuality(g2d, send, recv);
			}
		}
	}

	public void drawAllLinks(Graphics2D g2d) {
		Network network = this.getNetworkModel().getNetwork();
		Vector nodes = network.getNodes();
		Node recv, send;
		Integer neighborID;

		for (int i = 0; i < nodes.size(); i++) {
			send = (Node) nodes.elementAt(i);

			for (int j = 0; j < send.getNeighborIDs().size(); j++) {
				neighborID = (Integer) send.getNeighborIDs().elementAt(j);
				recv = network.getNode(neighborID.intValue());
				this.drawNodeLink(g2d, send, recv);
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

	public void setNetworkModel(NetworkModel networkModel) {
		this.networkModel = networkModel;
		this.updateNetworkView();
		
	}

	public NetworkModel getNetworkModel() {
		return this.networkModel;
	}

	public void updateNetworkView() {
		int width,height;
		width = (int)networkModel.getNetwork().getWidth();
		height = (int)networkModel.getNetwork().getHeigth();
		this.originalWidth = width;
		this.originalHeight = height;
		this.width = (int) (width * this.xScale + 2 * this.coordinatorOx);
		this.height = (int) (height * this.yScale + 2 * this.coordinatorOy);
		this.coordinator = new Coordinator(this.width, this.height,
				coordinatorOx, coordinatorOy, width * this.xScale, height
						* this.yScale);
		this.coordinator.setXscale(this.xScale);
		this.coordinator.setYscale(this.yScale);
		setPreferredSize(new Dimension(this.width, this.height));
		this.repaint();
	}
	

	public void setNetworkViewScale(double scale) {
		this.setScale(scale);
	}
}
