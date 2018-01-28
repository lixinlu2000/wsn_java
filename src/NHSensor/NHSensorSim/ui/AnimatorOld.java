package NHSensor.NHSensorSim.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.EcstaAlg;
import NHSensor.NHSensorSim.algorithm.GStarAlg;
import NHSensor.NHSensorSim.algorithm.SWinFloodAlg;
import NHSensor.NHSensorSim.core.GStarAlgParam;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.TAGAlg;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.SimpleEventListener;
import NHSensor.NHSensorSim.events.TraceEventListener;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.routing.gpsr.GraphType;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.util.Convertor;

public class AnimatorOld extends JFrame {
	private static final long serialVersionUID = 2L;
	protected Algorithm algorithm;
	protected Vector events;
	protected SensornetModel sensornetModel;
	protected SensornetCanvas sensornetCanvas;
	protected EventsList eventsList;
	protected JTabbedPane tabbedPane;
	protected JScrollPane scrollPane;
	protected EventController eventController;
	protected Thread eventControllerThread;
	private boolean isShowEventList = true;
	private boolean isShowStatusBar = true;
	private boolean isShowSensornetTable = true;

	private SensornetControlPanel controlPanel;

	private JSplitPane mainPane = new JSplitPane();

	private SensornetStatusBar statusBar;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AnimatorOld animator = new AnimatorOld();
				animator.start();
			}
		});
	}

	public AnimatorOld() {
		super();
		this.initAndRunOtherAlg();
		this.initAnimator();
	}

	public AnimatorOld(Algorithm algorithm) {
		this.algorithm = algorithm;
		this.initAnimator();
	}

	public void initAnimator() {
		try {
			this.initEvents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.initMV();
		this.initC();
		if (this.isShowEventList)
			this.initEventsList();
		this.initControlPanel();
		if (this.isShowStatusBar)
			this.initStatusBar();
		this.initGUI();
		this.runC();
	}

	private void initEvents() {
		TraceEventListener tel = (TraceEventListener) this.getAlgorithm()
				.getSimulator().getSensornetEventListener("TraceEventListener");
		this.events = tel.getEvents();
	}

	public void start() {
		// this.eventController.setStarted(true);
	}

	public void runC() {
		this.eventControllerThread.start();
	}

	public void initAndRunOtherAlg() {
		SensorSim sensorSim = SensorSim.createSensorSim("Random2");

		SimpleEventListener simpleListener = new SimpleEventListener();
		TraceEventListener traceEventListener = new TraceEventListener();
		sensorSim.getSimulator().addEventListener(traceEventListener);
		sensorSim.getSimulator().addEventListener(simpleListener);

		sensorSim.addAlgorithm(SWinFloodAlg.NAME);
		Algorithm swinFloodAlg = sensorSim.getAlgorithm(SWinFloodAlg.NAME);
		sensorSim.addAlgorithm(EcstaAlg.NAME);
		Algorithm ecstaAlg = sensorSim.getAlgorithm(EcstaAlg.NAME);
		sensorSim.addAlgorithm(TAGAlg.NAME);
		Algorithm tAGAlg = sensorSim.getAlgorithm(TAGAlg.NAME);
		sensorSim.addAlgorithm(GPSRAlg.NAME);
		Algorithm gPSRAlg = sensorSim.getAlgorithm(GPSRAlg.NAME);
		// GreedyTAGAlg gtag =
		// (GreedyTAGAlg)sensorSim.addAlgorithm(GreedyTAGAlg.NAME);
		// gtag.setRadioRange(4*Math.sqrt(2));
		GStarAlg gsa = (GStarAlg) sensorSim.addAlgorithm(GStarAlg.NAME);
		Position subRoot = new Position(10, 10);
		GStarAlgParam gsap = new GStarAlgParam(100, 100, subRoot, Math
				.sqrt(100 * 100 + 200 * 200));
		gsa.setAlgParam(gsap);

		this.algorithm = ecstaAlg;
		this.algorithm.init();
		this.algorithm.run();
	}

	public void initAndRunGPSRAlg() {
		// sensorSim = SensorSim.createSensorSim("Random2");
		Network network = Network.createGPSRTestNetwork(800, 600, 300, 9,
				new Position(400, 300), 50);
		// Network network = Network.createRandomNetwork(800, 600, 200);
		// Network network = Network.createNetworkFromFile(new File(
		// "D:\\research\\project\\NHSensorSim\\dataset\\intel-lab-data\\mote_locs.txt"
		// ));
		SensorSim sensorSim = new SensorSim(network);
		sensorSim.getParam().setRADIO_RANGE(100);
		sensorSim.addAlgorithm(GPSRAlg.NAME);
		GPSRAlg gpsrAlg = (GPSRAlg) sensorSim.getAlgorithm(GPSRAlg.NAME);
		this.algorithm = gpsrAlg;

		SimpleEventListener simpleListener = new SimpleEventListener();
		TraceEventListener traceEventListener = new TraceEventListener();
		gpsrAlg.getSimulator().addEventListener(traceEventListener);
		gpsrAlg.getSimulator().addEventListener(simpleListener);

		int graphType = GraphType.GG;
		gpsrAlg.setGraphType(graphType);
		gpsrAlg.init();

		Node source = sensorSim.getNetwork().getLBNode();
		// Node source =
		// sensorSim.getNetwork().getNearestNodeToPos(sensorSim.getNetwork
		// ().getCentreNode().getPos());
		// Node source = sensorSim.getNetwork().getNearestNodeToPos(new
		// Position(0,300));
		Position orig = source.getPos();
		GPSRAttachment sourceAttachment = (GPSRAttachment) source
				.getAttachment(gpsrAlg.getName());
		// this.dest = sensorSim.getNetwork().getRect().getCentre();
		// this.dest = new Position(400,0);
		Position dest = new Position(400, 300);
		Node destNode = sensorSim.getNetwork().getNearestNodeToPos(dest);
		dest = destNode.getPos();
		Message message = new Message(1, null);

		GPSR gpsr = new GPSR(sourceAttachment, dest, message, gpsrAlg);
		GreedyForwardToPointEvent gf2p = new GreedyForwardToPointEvent(
				sourceAttachment, dest, message, gpsrAlg);
		boolean testGpsr = false;
		Vector path = null;
		// boolean testGpsr = false;
		if (testGpsr) {
			gpsrAlg.run(gpsr);
			path = gpsr.getPath();
		} else {
			gpsrAlg.run(gf2p);
			path = gf2p.getRoute();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initGUI() {
		this.setSize(1000, 700);
		this.setTitle("NHSensorSim");

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(controlPanel, BorderLayout.NORTH);

		if (this.isShowStatusBar) {

			this.sensornetCanvas
					.addMouseMotionListener(new MouseMotionAdapter() {
						public void mouseMoved(MouseEvent e) {
							Position pos = new Position(e.getX(), e.getY());
							Point2D.Double p = sensornetCanvas
									.getCoordinator()
									.toCoordinatorPoint(
											Convertor
													.positionToPoint2DDouble(pos));
							String mousePos = "Mouse Pos£º(" + p.getX() + ','
									+ p.getY() + ')';
							statusBar.setStatus(0, mousePos);
						}
					});
		}

		mainPane.setDividerLocation(700);
		mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		mainPane.setLeftComponent(this.sensornetCanvas);

		this.initTabbedPane();

		contentPane.add(mainPane, BorderLayout.CENTER);
		if (this.isShowStatusBar)
			contentPane.add(statusBar, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	protected void initTabbedPane() {
		this.tabbedPane = new JTabbedPane();
		mainPane.setRightComponent(this.tabbedPane);

		SensornetTable sensornetTable = new SensornetTable(this.sensornetModel);
		this.sensornetModel.addSensornetView(sensornetTable);

		if (this.isShowEventList) {
			this.scrollPane = new JScrollPane(this.eventsList);
			this.tabbedPane.addTab("All Events", this.scrollPane);
		}

		if (this.isShowSensornetTable) {
			JScrollPane tableScrollPane = new JScrollPane(sensornetTable);
			this.tabbedPane.addTab("Information", tableScrollPane);
		}
	}

	protected void initStatusBar() {
		statusBar = new SensornetStatusBar(this.sensornetModel);
		this.sensornetModel.addSensornetView(statusBar);
	}

	protected void initControlPanel() {
		this.controlPanel = new SensornetControlPanel(this.sensornetModel,
				this.eventController);
		this.sensornetModel.addSensornetView(controlPanel);
	}

	protected void initEventsList() {
		this.eventsList = new EventsList(this.sensornetModel);
		this.sensornetModel.addSensornetView(eventsList);
	}

	protected void initMV() {
		this.sensornetModel = new SensornetModel(this.algorithm);
		// this.sensornetModel.setDescription(this.algorithm.getName());
		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetCanvas = new SensornetCanvas(sensornetCanvasWidth,
				sensornetCanvasHeight, sensornetModel);

		this.sensornetModel.addSensornetView(sensornetCanvas);
	}

	private void initC() {
		eventController = new EventController(sensornetModel);
		eventController.setSpeed(this.getSpeed());
		eventControllerThread = new Thread(eventController);
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public double getSpeed() {
		return this.eventController.getSpeed();
	}

	public void setSpeed(double speed) {
		this.eventController.setSpeed(speed);
	}

}
