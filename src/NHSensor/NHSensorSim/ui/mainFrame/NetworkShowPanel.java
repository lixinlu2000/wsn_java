package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.link.FullIsolatedLinkEstimator;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.ui.NetworkCanvas;
import NHSensor.NHSensorSim.ui.NetworkLinkCanvas;
import NHSensor.NHSensorSim.ui.NetworkModel;
import NHSensor.NHSensorSim.util.Convertor;

public class NetworkShowPanel extends JPanel {
	private final double DIVIDER_LOCATION_RATE = 0.15;
	private final double DIVIDER_LOCATION_RATE_NODE_PROPERTY = 0.78;
	private JSplitPane splitPane = new JSplitPane();
	private NodeModifyAndPropertyPanel 	nodeModifyAndPropertyPanel;
	private NetworkModel networkModel;
	private NetworkCanvas networkCanvas;
	private NetworkLinkCanvas networkLinkCanvas;
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane_2;
	private GenerateNetworkStatusBar generateNetworkStatusBar;
	NetworkControlPanel controlPanel = new NetworkControlPanel();
	
	private final String NetworkGenerated = "实验网络";
	private final String Topology = "实验网络拓扑";

	protected void initNetworkCanvas() {
		Network network = Network.createRandomNetwork(this.tabbedPane
				.getPreferredSize().getWidth(), this.tabbedPane
				.getPreferredSize().getHeight(), 0);
		FullIsolatedLinkEstimator le = new FullIsolatedLinkEstimator();
		network.setLinkEstimator(le);
		networkModel = new NetworkModel(network);
		networkCanvas = new NetworkCanvas(networkModel);
		networkLinkCanvas = new NetworkLinkCanvas(networkModel);

		generateNetworkStatusBar = new GenerateNetworkStatusBar(networkModel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(networkCanvas);
		tabbedPane.addTab(NetworkGenerated, null, scrollPane, null);
		
		JScrollPane topologyScrollPane = new JScrollPane();
		topologyScrollPane.setViewportView(networkLinkCanvas);
		tabbedPane.addTab(Topology, null, topologyScrollPane, null);
		
		MouseMotionAdapter ma = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				Position pos = new Position(e.getX(), e.getY());
				Point2D.Double p = networkCanvas
						.getCoordinator()
						.toCoordinatorPoint(
								Convertor
										.positionToPoint2DDouble(pos));
				String mousePos = "Mouse Pos：(" + p.getX() + ','
						+ p.getY() + ')';
				generateNetworkStatusBar.setStatus(0, mousePos);
			}
		};
		this.networkCanvas.addMouseMotionListener(ma);
		this.networkLinkCanvas.addMouseMotionListener(ma);
		this.addNodeSelectionListerner(networkCanvas);
		this.addNodeSelectionListerner(networkLinkCanvas);
	}

	/**
	 * Create the panel.
	 */
	public NetworkShowPanel() {
		setLayout(new BorderLayout(0, 0));

		add(splitPane, BorderLayout.CENTER);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		splitPane_2 = new JSplitPane();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel networkPanel = new JPanel();
		networkPanel.setLayout(new BorderLayout());
		networkPanel.add(tabbedPane, BorderLayout.CENTER);
		splitPane_2.setLeftComponent(networkPanel);
		nodeModifyAndPropertyPanel = new NodeModifyAndPropertyPanel();
		splitPane_2.setRightComponent(nodeModifyAndPropertyPanel);
		splitPane.setRightComponent(splitPane_2);

		this.initNetworkCanvas();
		controlPanel.setNetworkModel(this.getNetworkModel());
		this.getNetworkModel().addNetworkView(controlPanel);
		splitPane.setLeftComponent(controlPanel);		
		this.controlPanel.initNetworkCanvasScaleSlider();
		networkPanel.add(generateNetworkStatusBar, BorderLayout.SOUTH);
	}
	
	
	public void setNetwork(Network network) {
		this.networkModel.setNetwork(network);
	}
	
	public void saveNetwork() {
		
	}

	public void updateSplitPane() {
		splitPane.setDividerLocation(DIVIDER_LOCATION_RATE);
		this.splitPane_2.setDividerLocation(DIVIDER_LOCATION_RATE_NODE_PROPERTY);		
	}

	public NetworkModel getNetworkModel() {
		return networkModel;
	}

	public void setNetworkModel(NetworkModel networkModel) {
		this.networkModel = networkModel;
	}
	
	private Point2D.Double pointConvertor(int x, int y) {
		Position pos = new Position(x, y);
		Point2D.Double p = networkCanvas
				.getCoordinator()
				.toCoordinatorPoint(
						Convertor
								.positionToPoint2DDouble(pos));
		return p;		
	}

	private void addNodeSelectionListerner(Component component) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1) {
					Point2D.Double p = pointConvertor(e.getX(), e.getY());
					networkModel.setSelectedNodes((int)p.getX(),(int)p.getY(), networkCanvas.getNodeWidth(), networkCanvas.getNodeHeight());
					if(!networkModel.getSelectedNodes().isEmpty()) {
						NodePropertyPage npg = new NodePropertyPage(networkModel);
						nodeModifyAndPropertyPanel.setNodePropertyPanel(npg);
						splitPane_2.setDividerLocation(DIVIDER_LOCATION_RATE_NODE_PROPERTY);
					}
					else {
						nodeModifyAndPropertyPanel.setEmptyNodePropertyPanel();
						splitPane_2.setDividerLocation(DIVIDER_LOCATION_RATE_NODE_PROPERTY);
					}
				}
			}
		});
	}
}
