package NHSensor.NHSensorSim.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.events.TraceEventListener;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.util.Convertor;

public class AnimatorPanel extends JPanel {
	private static final long serialVersionUID = 2L;
	protected Algorithm algorithm;
	protected Vector events;
	protected SensornetModel sensornetModel;
	protected SensornetCanvas sensornetCanvas;
	protected SensornetInfoCanvas sensornetInfoCanvas;
	protected SensornetLinkCanvas sensornetLinkCanvas;
	protected AlgorithmItinerayCanvas algorithmItinerayCanvas;
	protected SensornetQueryResultCanvas sensornetQueryResultCanvas;
	protected SensornetNetworkCanvas sensornetNetworkCanvas;
	protected EventsList eventsList;
	protected EventsMessageSizeList eventsMessageSizeList;
	protected EventsEnergyList eventsEnergyList;
	protected EnergyUtilEventsList energyUtilEventsList;
	protected JTabbedPane tabbedPane;
	protected JTabbedPane leftTabbedPane;
	protected JScrollPane scrollPane;
	protected JScrollPane scrollPaneForMessageSize;
	protected EventController eventController;
	protected Thread eventControllerThread;
	private boolean isShowEventList = true;
	private boolean isShowEventsMessageSizeList = true;
	private boolean isShowEventsEnergyList = true;
	private boolean isShowEnergyUtilEventsList = true;
	private boolean isShowStatusBar = true;
	private boolean isShowSensornetTable = true;
	private boolean showLinkTab = true;
	private boolean showQueryResultTab = true;
	private boolean showNetworkTab = true;
	private boolean showAlgorithmItinerary = true;
	private final String Algorithm = "算法";
	private final String Network = "网络";
	private final String Itinerary = "路线";
	private final String QueryResult = "查询结果";
	private final String Links = "链路";
	private final String LinkQuality = "链路质量";
	
	private SensornetControlPanel controlPanel;

	private JSplitPane mainPane = new JSplitPane();

	private SensornetStatusBar statusBar;
	
	public AnimatorPanel(Algorithm algorithm) {
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
		this.initOtherView();
		this.initC();
		if (this.isShowEventList)
			this.initEventsList();
		if (this.isShowEventsMessageSizeList)
			this.initEventsMessageSizeList();
		if (this.isShowEventsEnergyList)
			this.initEventsEnergyList();
		if (this.isShowEnergyUtilEventsList)
			this.initEnergyUtilEventsList();
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
		this.setVisible(true);
	}

	public void runC() {
		this.eventControllerThread.start();
	}

	private void initLeftTabbedPane() {
		this.leftTabbedPane = new JTabbedPane();
		mainPane.setLeftComponent(this.leftTabbedPane);

		this.leftTabbedPane.addTab(Algorithm, new JScrollPane(
				this.sensornetCanvas));

		if (this.showNetworkTab) {
			this.leftTabbedPane.addTab(Network, new JScrollPane(
					this.sensornetNetworkCanvas));
		}

		if (this.showAlgorithmItinerary) {
			this.leftTabbedPane.addTab(Itinerary, new JScrollPane(
					this.algorithmItinerayCanvas));
		}

		if (this.showQueryResultTab) {
			this.leftTabbedPane.addTab(QueryResult, new JScrollPane(
					this.sensornetQueryResultCanvas));
		}

		if (this.showLinkTab) {
			this.leftTabbedPane.addTab(Links, new JScrollPane(
					this.sensornetLinkCanvas));
			this.leftTabbedPane.addTab(LinkQuality, new JScrollPane(
					this.sensornetInfoCanvas));
		}
	}

	private void initMouseMotion() {

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initGUI() {
		this.setSize(1000, 700);

		this.setLayout(new BorderLayout());

		this.add(controlPanel, BorderLayout.NORTH);


		mainPane.setDividerLocation(700);
		mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		this.initLeftTabbedPane();
		this.initTabbedPane();

		this.add(mainPane, BorderLayout.CENTER);
		if (this.isShowStatusBar)
			this.add(statusBar, BorderLayout.SOUTH);
		
		MouseMotionAdapter ma = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				Position pos = new Position(e.getX(), e.getY());
				Point2D.Double p = sensornetCanvas
						.getCoordinator()
						.toCoordinatorPoint(
								Convertor
										.positionToPoint2DDouble(pos));
				String mousePos = "Mouse Pos：(" + p.getX() + ','
						+ p.getY() + ')';
				statusBar.setStatus(0, mousePos);
			}
		};
		if (this.isShowStatusBar) {
			this.sensornetCanvas.addMouseMotionListener(ma);
			this.sensornetNetworkCanvas.addMouseMotionListener(ma);
			this.algorithmItinerayCanvas.addMouseMotionListener(ma);
			this.sensornetQueryResultCanvas.addMouseMotionListener(ma);
			this.sensornetLinkCanvas.addMouseMotionListener(ma);
			this.sensornetInfoCanvas.addMouseMotionListener(ma);
		}
	}

	protected void initTabbedPane() {
		this.tabbedPane = new JTabbedPane();
		mainPane.setRightComponent(this.tabbedPane);

		SensornetTable sensornetTable = new SensornetTable(this.sensornetModel);
		this.sensornetModel.addSensornetView(sensornetTable);

		if (this.isShowEventList) {
			this.scrollPane = new JScrollPane(this.eventsList);
			this.tabbedPane.addTab("所有事件", this.scrollPane);
		}

		if (this.isShowEventsMessageSizeList) {
			this.scrollPaneForMessageSize = new JScrollPane(
					this.eventsMessageSizeList);
			this.tabbedPane.addTab("每个事件产生的数据通信量",
					this.scrollPaneForMessageSize);
		}

		if (this.isShowEventsEnergyList) {
			this.tabbedPane.addTab("每个事件产生的能量总消耗", new JScrollPane(
					this.eventsEnergyList));
		}

		if (this.isShowEnergyUtilEventsList) {
			this.tabbedPane.addTab("总能耗", new JScrollPane(
					this.energyUtilEventsList));
		}

		if (this.isShowSensornetTable) {
			JScrollPane tableScrollPane = new JScrollPane(sensornetTable);
			this.tabbedPane.addTab("参数", tableScrollPane);
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

	protected void initEventsMessageSizeList() {
		this.eventsMessageSizeList = new EventsMessageSizeList(
				this.sensornetModel);
		this.sensornetModel.addSensornetView(eventsMessageSizeList);
	}

	protected void initEventsEnergyList() {
		this.eventsEnergyList = new EventsEnergyList(this.sensornetModel);
		this.sensornetModel.addSensornetView(eventsEnergyList);
	}

	protected void initEnergyUtilEventsList() {
		this.energyUtilEventsList = new EnergyUtilEventsList(
				this.sensornetModel);
		this.sensornetModel.addSensornetView(energyUtilEventsList);
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

	protected void initOtherView() {
		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetInfoCanvas = new SensornetInfoCanvas(
				sensornetCanvasWidth, sensornetCanvasHeight, sensornetModel);
		this.sensornetLinkCanvas = new SensornetLinkCanvas(
				sensornetCanvasWidth, sensornetCanvasHeight, sensornetModel);
		this.sensornetQueryResultCanvas = new SensornetQueryResultCanvas(
				sensornetCanvasWidth, sensornetCanvasHeight, sensornetModel);
		this.sensornetNetworkCanvas = new SensornetNetworkCanvas(
				sensornetCanvasWidth, sensornetCanvasHeight, sensornetModel);
		this.algorithmItinerayCanvas = new AlgorithmItinerayCanvas(
				sensornetCanvasWidth, sensornetCanvasHeight, sensornetModel);

		this.sensornetModel.addSensornetView(sensornetInfoCanvas);
		this.sensornetModel.addSensornetView(algorithmItinerayCanvas);
		this.sensornetModel.addSensornetView(sensornetLinkCanvas);
		this.sensornetModel.addSensornetView(sensornetQueryResultCanvas);
		this.sensornetModel.addSensornetView(sensornetNetworkCanvas);
	}

	public void addView(SensornetCanvas sensornetCanvas) {
		this.sensornetModel.addSensornetView(sensornetCanvas);
		this.leftTabbedPane.addTab(sensornetCanvas.getName(), new JScrollPane(
				sensornetCanvas));
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

	public SensornetCanvas getSensornetCanvas() {
		return this.sensornetCanvas;
	}

	public SensornetModel getSensornetModel() {
		return sensornetModel;
	}
}
