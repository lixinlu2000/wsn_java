package NHSensor.NHSensorSim.ui;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.routing.gpsr.*;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.util.Convertor;

public class SimulationPanel extends JPanel {
	private SensorSim sensorSim = null; // @jve:decl-index=0:
	private Vector path = null;
	private Position orig = null;
	private Position dest = null;
	private double nodeRadius = 3;
	private Coordinator coordinator;

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor
	 */
	public SimulationPanel() {
		super();
		initialize();
		initAlgorithm();
		initCoordinator();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		this.setLayout(null);
	}

	private void initCoordinator() {
		coordinator = new Coordinator(this.getWidth(), this.getHeight(), 0, 0);
		// coordinator.setXscale(40);
		// coordinator.setYscale(40);
	}

	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		this.drawOrigDest(g2d);
		this.drawNetwork(g2d);
		this.drawPath(g2d, this.path);
	}

	protected void drawStringInCarCoord(Graphics2D g2d, String str,
			Position pos, double xOffset, double yOffset) {
		Position newPosition = new Position();
		newPosition.setX(pos.getX() + xOffset);
		newPosition.setY(pos.getY() + yOffset);

		Point2D.Double p = this.coordinator.point(Convertor
				.positionToPoint2DDouble(newPosition));
		g2d.drawString(str, (float) p.getX(), (float) p.getY());
	}

	protected void initAlgorithm() {
		// sensorSim = SensorSim.createSensorSim("Random2");
		Network network = Network.createGPSRTestNetwork(800, 600, 300, 9,
				new Position(400, 300), 50);
		// Network network = Network.createRandomNetwork(800, 600, 200);
		// Network network = Network.createNetworkFromFile(new File(
		// "D:\\research\\project\\NHSensorSim\\dataset\\intel-lab-data\\mote_locs.txt"
		// ));
		sensorSim = new SensorSim(network);
		sensorSim.getParam().setRADIO_RANGE(100);
		sensorSim.addAlgorithm(GPSRAlg.NAME);
		GPSRAlg gpsrAlg = (GPSRAlg) sensorSim.getAlgorithm(GPSRAlg.NAME);

		int graphType = GraphType.GG;
		gpsrAlg.setGraphType(graphType);
		gpsrAlg.init();

		Node source = sensorSim.getNetwork().getLBNode();
		// Node source =
		// sensorSim.getNetwork().getNearestNodeToPos(sensorSim.getNetwork
		// ().getCentreNode().getPos());
		// Node source = sensorSim.getNetwork().getNearestNodeToPos(new
		// Position(0,300));
		this.orig = source.getPos();
		GPSRAttachment sourceAttachment = (GPSRAttachment) source
				.getAttachment(gpsrAlg.getName());
		// this.dest = sensorSim.getNetwork().getRect().getCentre();
		// this.dest = new Position(400,0);
		this.dest = new Position(400, 300);
		Node destNode = sensorSim.getNetwork().getNearestNodeToPos(this.dest);
		this.dest = destNode.getPos();
		Message message = new Message(1, null);

		GPSR gpsr = new GPSR(sourceAttachment, this.dest, message, gpsrAlg);
		GreedyForwardToPointEvent gf2p = new GreedyForwardToPointEvent(
				sourceAttachment, this.dest, message, gpsrAlg);
		boolean testGpsr = true;
		// boolean testGpsr = false;
		if (testGpsr) {
			gpsrAlg.run(gpsr);
			this.path = gpsr.getPath();
		} else {
			gpsrAlg.run(gf2p);
			this.path = gf2p.getRoute();
		}

	}

	protected void drawNetwork(Graphics2D g2d) {
		Rect networkRect = this.getSensorSim().getNetwork().getRect();
		Vector nodes = this.getSensorSim().getNetwork().getNodes();

		Rectangle2D.Double rect = coordinator.rectangle(Convertor
				.rectToRectangle2DDouble(networkRect));
		g2d.setColor(Color.GREEN);
		g2d.draw(rect);

		g2d.setColor(Color.BLACK);
		for (int i = 0; i < nodes.size(); i++) {
			Node cur = (Node) nodes.elementAt(i);
			g2d.draw(coordinator.ellipse(this.getPositionCircle(cur.getPos())));
			// this.drawStringInCarCoord(g2d, String.valueOf(i),
			// cur.getPos(),-10,-10);
			// g2d.setColor(Color.RED);
			// g2d.fill(nodeCircle);
		}
	}

	protected Ellipse2D.Double getPositionCircle(Position pos) {
		return new Ellipse2D.Double(pos.getX() - this.getNodeRadius(), pos
				.getY()
				- this.getNodeRadius(), 2 * this.getNodeRadius(), 2 * this
				.getNodeRadius());
	}

	protected void drawOrigDest(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		Ellipse2D.Double origCircle = this.coordinator.ellipse(this
				.getPositionCircle(this.orig));
		g2d.draw(origCircle);
		g2d.fill(origCircle);

		Ellipse2D.Double destCircle = this.coordinator.ellipse(this
				.getPositionCircle(this.dest));
		g2d.draw(destCircle);
		g2d.fill(destCircle);

	}

	protected void drawPath(Graphics2D g2d, Vector path) {
		Position cur = null;
		Position next = null;
		Line2D.Double line;

		if (path.size() < 2)
			return;
		for (int i = 0; i < path.size() - 1; i++) {
			cur = ((Attachment) path.elementAt(i)).getNode().getPos();
			g2d.setColor(Color.BLUE);
			g2d.fill(this.coordinator.ellipse(this.getPositionCircle(cur)));
			this.drawStringInCarCoord(g2d, String.valueOf(i), cur, 5, 5);

			next = ((Attachment) path.elementAt(i + 1)).getNode().getPos();
			line = this.coordinator.line(new Line2D.Double(cur.getX(), cur
					.getY(), next.getX(), next.getY()));
			g2d.setColor(Color.RED);
			g2d.draw(line);
		}
		g2d.setColor(Color.BLUE);
		g2d.fill(this.coordinator.ellipse(this.getPositionCircle(next)));
		this.drawStringInCarCoord(g2d, String.valueOf(path.size() - 1), next,
				5, 5);

	}

	public SensorSim getSensorSim() {
		return sensorSim;
	}

	public void setSensorSim(SensorSim sensorSim) {
		this.sensorSim = sensorSim;
	}

	public double getNodeRadius() {
		return nodeRadius;
	}

	public void setNodeRadius(double nodeRadius) {
		this.nodeRadius = nodeRadius;
	}

}
