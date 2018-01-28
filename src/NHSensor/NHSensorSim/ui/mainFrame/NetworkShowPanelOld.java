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

public class NetworkShowPanelOld extends JPanel {
	private final double DIVIDER_LOCATION_RATE = 0.15;
	private final double DIVIDER_LOCATION_RATE_NODE_PROPERTY = 0.78;
	private JSplitPane splitPane = new JSplitPane();
	private JTextField networkWidthField;
	private JTextField networkHeightField;
	private JTextField nodeNumField;
	private JTextField sensorCanvasScaleField;
	private NodeModifyAndPropertyPanel 	nodeModifyAndPropertyPanel;
	private NetworkModel networkModel;
	private NetworkCanvas networkCanvas;
	private NetworkLinkCanvas networkLinkCanvas;
	private JTabbedPane tabbedPane;
	private JSlider sensorCanvasScaleSlider;
	private int minSensorCanvasScale = 1;
	private int maxSensorCanvasScale = 200;
	private int scaleScale = 10;
	private JTextField descriptionTextField;
	private JSplitPane splitPane_2;
	private GenerateNetworkStatusBar generateNetworkStatusBar;
	
	private final String NetworkWidth = "网络宽度：";
	private final String NetworkHeight = "网络高度：";
	private final String NodeNumber = "节点数目：";
	private final String Description = "网络描述：";
	private final String NetworkCanvasScale = "网络显示缩放比例值";
	private final String NetworkCanvasZoomInAndOut = "网络显示缩放";
	private final String CanvasSizeSlider = "网络显示缩放";
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
	public NetworkShowPanelOld() {
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

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{438, 97, 0, 0};
		gbl_panel_1.rowHeights = new int[]{107, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_1);
						
				JPanel generateNetworkPanel = new JPanel();
				generateNetworkPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u7F51\u7EDC\u57FA\u672C\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				
				GridBagLayout gbl_generateNetworkPanel = new GridBagLayout();
				gbl_generateNetworkPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0};
				gbl_generateNetworkPanel.columnWidths = new int[]{0, 66, 0, 75, 108};
				gbl_generateNetworkPanel.rowWeights = new double[] { 0.0, 1.0 };
				generateNetworkPanel.setLayout(gbl_generateNetworkPanel);
				
						JLabel lblNewLabel = new JLabel(NetworkWidth);
						GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
						gbc_lblNewLabel.ipady = 5;
						gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel.gridx = 0;
						gbc_lblNewLabel.gridy = 0;
						generateNetworkPanel.add(lblNewLabel, gbc_lblNewLabel);
						
								networkWidthField = new JTextField();
								GridBagConstraints gbc_networkWidthField = new GridBagConstraints();
								gbc_networkWidthField.weightx = 0.5;
								gbc_networkWidthField.fill = GridBagConstraints.HORIZONTAL;
								gbc_networkWidthField.insets = new Insets(0, 0, 5, 5);
								gbc_networkWidthField.gridx = 1;
								gbc_networkWidthField.gridy = 0;
								generateNetworkPanel.add(networkWidthField, gbc_networkWidthField);
								networkWidthField.setColumns(10);
								
								JLabel lblNewLabel_1 = new JLabel(NetworkHeight);
								GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
								gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
								gbc_lblNewLabel_1.gridx = 2;
								gbc_lblNewLabel_1.gridy = 0;
								generateNetworkPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
								
										networkHeightField = new JTextField();
										GridBagConstraints gbc_networkHeightField = new GridBagConstraints();
										gbc_networkHeightField.weightx = 0.5;
										gbc_networkHeightField.insets = new Insets(0, 0, 5, 5);
										gbc_networkHeightField.fill = GridBagConstraints.HORIZONTAL;
										gbc_networkHeightField.gridx = 3;
										gbc_networkHeightField.gridy = 0;
										generateNetworkPanel.add(networkHeightField, gbc_networkHeightField);
										networkHeightField.setColumns(10);
										
												JLabel lblNodeNumber = new JLabel(NodeNumber);
												GridBagConstraints gbc_lblNodeNumber = new GridBagConstraints();
												gbc_lblNodeNumber.anchor = GridBagConstraints.WEST;
												gbc_lblNodeNumber.insets = new Insets(0, 0, 5, 5);
												gbc_lblNodeNumber.gridx = 0;
												gbc_lblNodeNumber.gridy = 1;
												generateNetworkPanel.add(lblNodeNumber, gbc_lblNodeNumber);
												
														nodeNumField = new JTextField();
														nodeNumField.setColumns(10);
														GridBagConstraints gbc_nodeNumField = new GridBagConstraints();
														gbc_nodeNumField.weightx = 0.5;
														gbc_nodeNumField.insets = new Insets(0, 0, 5, 5);
														gbc_nodeNumField.fill = GridBagConstraints.HORIZONTAL;
														gbc_nodeNumField.gridx = 1;
														gbc_nodeNumField.gridy = 1;
														generateNetworkPanel.add(nodeNumField, gbc_nodeNumField);
														
														JLabel lblDescription = new JLabel(Description);
														GridBagConstraints gbc_lblDescription = new GridBagConstraints();
														gbc_lblDescription.anchor = GridBagConstraints.WEST;
														gbc_lblDescription.insets = new Insets(0, 0, 0, 5);
														gbc_lblDescription.gridx = 2;
														gbc_lblDescription.gridy = 1;
														generateNetworkPanel.add(lblDescription, gbc_lblDescription);
														
														descriptionTextField = new JTextField();
														GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
														gbc_descriptionTextField.weighty = 0.5;
														gbc_descriptionTextField.weightx = 0.2;
														gbc_descriptionTextField.insets = new Insets(0, 0, 0, 5);
														gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
														gbc_descriptionTextField.gridx = 3;
														gbc_descriptionTextField.gridy = 1;
														generateNetworkPanel.add(descriptionTextField, gbc_descriptionTextField);
														descriptionTextField.setColumns(10);
		
		GridBagConstraints gbc_generateNetworkPanel = new GridBagConstraints();
		gbc_generateNetworkPanel.weighty = 1.0;
		gbc_generateNetworkPanel.weightx = 0.6;
		gbc_generateNetworkPanel.fill = GridBagConstraints.BOTH;
		gbc_generateNetworkPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_generateNetworkPanel.insets = new Insets(0, 0, 0, 5);
		gbc_generateNetworkPanel.gridx = 0;
		gbc_generateNetworkPanel.gridy = 0;
		panel.add(generateNetworkPanel, gbc_generateNetworkPanel);
		
				JPanel sensorCanvasScalePanel = new JPanel();
				sensorCanvasScalePanel.setBorder(new TitledBorder(null, NetworkCanvasZoomInAndOut, TitledBorder.LEADING, TitledBorder.TOP, null, null));
				GridBagLayout gbl_sensorCanvasScalePanel = new GridBagLayout();
				gbl_sensorCanvasScalePanel.columnWidths = new int[] {0, 0};
				sensorCanvasScalePanel.setLayout(gbl_sensorCanvasScalePanel);
				
						sensorCanvasScaleSlider = new JSlider();
						sensorCanvasScaleSlider.setValue(this.scaleScale);
						GridBagConstraints gbc_slider = new GridBagConstraints();
						gbc_slider.fill = GridBagConstraints.HORIZONTAL;
						gbc_slider.weightx = 0.6;
						gbc_slider.gridwidth = 2;
						gbc_slider.gridheight = 2;
						gbc_slider.gridx = 0;
						gbc_slider.gridy = 0;
						sensorCanvasScalePanel.add(sensorCanvasScaleSlider, gbc_slider);
						
						JLabel lblSensorCanvasScale = new JLabel(NetworkCanvasScale);
						GridBagConstraints gbc_lblSensorCanvasScale = new GridBagConstraints();
						gbc_lblSensorCanvasScale.anchor = GridBagConstraints.WEST;
						gbc_lblSensorCanvasScale.insets = new Insets(0, 0, 5, 5);
						gbc_lblSensorCanvasScale.gridx = 0;
						gbc_lblSensorCanvasScale.gridy = 2;
						sensorCanvasScalePanel.add(lblSensorCanvasScale, gbc_lblSensorCanvasScale);
						
								sensorCanvasScaleField = new JTextField();
								sensorCanvasScaleField.setColumns(10);
								GridBagConstraints gbc_sensorCanvasScaleField = new GridBagConstraints();
								gbc_sensorCanvasScaleField.weightx = 0.2;
								gbc_sensorCanvasScaleField.insets = new Insets(0, 0, 5, 5);
								gbc_sensorCanvasScaleField.fill = GridBagConstraints.HORIZONTAL;
								gbc_sensorCanvasScaleField.gridx = 1;
								gbc_sensorCanvasScaleField.gridy = 2;
								sensorCanvasScalePanel.add(sensorCanvasScaleField, gbc_sensorCanvasScaleField);
								GridBagConstraints gbc_sensorCanvasScalePanel = new GridBagConstraints();
								gbc_sensorCanvasScalePanel.weighty = 1.0;
								gbc_sensorCanvasScalePanel.gridx = 1;
								gbc_sensorCanvasScalePanel.weightx = 0.2;
								gbc_sensorCanvasScalePanel.fill = GridBagConstraints.BOTH;
								gbc_sensorCanvasScalePanel.gridy = 0;
								panel.add(sensorCanvasScalePanel, gbc_sensorCanvasScalePanel);
		
		this.initNetworkCanvas();
		this.initNetworkCanvasScaleSlider();
		networkPanel.add(generateNetworkStatusBar, BorderLayout.SOUTH);
	}
	
	private void initNetworkCanvasScaleSlider() {
		sensorCanvasScaleSlider.setMinimum(minSensorCanvasScale);
		sensorCanvasScaleSlider.setMaximum(maxSensorCanvasScale);
		sensorCanvasScaleSlider.setValue(1);
		sensorCanvasScaleSlider.setBorder(BorderFactory
				.createTitledBorder(CanvasSizeSlider));
		sensorCanvasScaleSlider.setMajorTickSpacing(100);
		sensorCanvasScaleSlider.setMinorTickSpacing(10);
		sensorCanvasScaleSlider.setPaintTicks(true);
		sensorCanvasScaleSlider.setPaintLabels(true);
		SensorCanvasSizeSliderChangeListerner sensorCanvasSizeSliderChangeListerner = new SensorCanvasSizeSliderChangeListerner();
		sensorCanvasScaleSlider
				.addChangeListener(sensorCanvasSizeSliderChangeListerner);
	}
	
	public void setNetwork(Network network) {
		this.networkModel.setNetwork(network);
	}

	public class SensorCanvasSizeSliderChangeListerner implements
			ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				try {
					networkModel.setNetworkCanvasSizeScale(source.getValue()
							/ scaleScale);
					sensorCanvasScaleField.setText(""+(double)source.getValue()/scaleScale);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
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
