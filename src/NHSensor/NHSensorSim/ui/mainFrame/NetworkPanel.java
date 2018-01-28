package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.experiment.ExperimentConfig;
import NHSensor.NHSensorSim.link.FullIsolatedLinkEstimator;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.tools.FileTools;
import NHSensor.NHSensorSim.ui.NetworkCanvas;
import NHSensor.NHSensorSim.ui.NetworkLinkCanvas;
import NHSensor.NHSensorSim.ui.NetworkModel;
import NHSensor.NHSensorSim.util.Convertor;

public class NetworkPanel extends JPanel {
	private final double DIVIDER_LOCATION_RATE = 0.15;
	private final double DIVIDER_LOCATION_RATE_NODE_PROPERTY = 0.78;
	private MainFrame mainFrame;
	private JSplitPane splitPane = new JSplitPane();
	private JTextField networkWidthField;
	private JTextField networkHeightField;
	private JTextField radioRangeField;

	private JTextField nodeNumField;
	private JTextField sensorCanvasScaleField;
	private NodeModifyAndPropertyPanel 	nodeModifyAndPropertyPanel;
	private double networkWidth;
	private double networkHeight;
	private double radioRange;
	private int nodeNum;
	private String description;
	private NetworkModel networkModel;
	private NetworkCanvas networkCanvas;
	private NetworkLinkCanvas networkLinkCanvas;
	private JTabbedPane tabbedPane;
	private JSlider sensorCanvasScaleSlider;
	private int minSensorCanvasScale = 1;
	private int maxSensorCanvasScale = 200;
	private int scaleScale = 10;
	private JTextField descriptionTextField;
	private JNetworkTree tree; //network tree
	private JSplitPane splitPane_2;
	private JPopupMenu popupMenu;
	private JPopupMenu popupMenuForNetworkTree = new JPopupMenu();
	private int popupMenuX, popupMenuY;
	private int popupMenuForGroupX, popupMenuForGroupY;
	private GenerateNetworkStatusBar generateNetworkStatusBar;

	
	private String networkWidthDefault = "900";
	private String networkHeightDefault = "350";
	private String radioRangeDefault = "50";
	private String nodeNumDefault = "400";
	private String DescriptionDefault = "A sample network";
	private boolean isNetworkNew = false;
	
	private final String GenerateNetwork = "生成网络";
	private final String NetworkWidth = "网络宽度：";
	private final String NetworkHeight = "网络高度：";
	private final String NodeNumber = "节点数目：";
	private final String Description = "网络描述：";
	private final String Generate = "生成";
	private final String Reset = "重置";
	private final String RadionRange = "节点通信半径";
	private final String SetRadioRange= "设置节点通信半径";
	private final String NetworkCanvasScale = "网络显示缩放比例值";
	private final String NetworkCanvasZoomInAndOut = "网络显示缩放";
	private final String CanvasSizeSlider = "网络显示缩放";
	private final String NetworkGenerated = "生成的网络";
	private final String Topology = "生成的网络拓扑";

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
	
		popupMenu = new JPopupMenu();
		addPopup(networkCanvas, popupMenu);
		addPopup(networkLinkCanvas, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u521B\u5EFA\u8282\u70B9");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Position pos = new Position(popupMenuX, popupMenuY);
				Point2D.Double p = networkCanvas
						.getCoordinator()
						.toCoordinatorPoint(
								Convertor
										.positionToPoint2DDouble(pos));
				networkModel.addNode((int)p.getX(),(int)p.getY());
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		JMenuItem menuItem = new JMenuItem("\u5220\u9664\u8282\u70B9");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				networkModel.deleteSelectedNodes();
			}
		});
		popupMenu.add(menuItem);
		
		JMenuItem mi1 = new JMenuItem("创建组");
		JMenuItem mi2 = new JMenuItem("删除");
		JMenuItem mi3 = new JMenuItem("运行算法");
		popupMenuForNetworkTree.add(mi1);
		popupMenuForNetworkTree.add(mi2);
		popupMenuForNetworkTree.add(mi3);
		
		mi1.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()&&lastTreeNodeSelected.toString().endsWith(".net")) {
					JOptionPane.showMessageDialog(NetworkPanel.this, "无法在网络文件上创建组", "创建网络组失败", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					String groupName = JOptionPane.showInputDialog(NetworkPanel.this, "输入创建组的组名");
					if(groupName==null||groupName.equals("")) return;
					File path = new File(tree.caculateFileSelected(treePathSelected));
					File file = new File(path, groupName);
					try {
						file.mkdir();
						tree.reInit();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(NetworkPanel.this, "无法创建组", "创建网络组失败", JOptionPane.INFORMATION_MESSAGE);
					}
				}				
			}
		});

		mi2.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()&&lastTreeNodeSelected.toString().endsWith(".net")) {
					int option = JOptionPane.showConfirmDialog(NetworkPanel.this, "确定删除选中的网络："+lastTreeNodeSelected.toString()+"？", "删除网络", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						File file = new File(tree.caculateFileSelected(treePathSelected));
						try {
							file.delete();
							((DefaultMutableTreeNode)lastTreeNodeSelected.getParent()).remove(lastTreeNodeSelected);
							tree.reInit();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(NetworkPanel.this, "无法创建删除选中的网络："+lastTreeNodeSelected.toString(), "删除网络失败", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						return;
					}
				}
				else {
					int option = JOptionPane.showConfirmDialog(NetworkPanel.this, "确定删除选中的网络组："+lastTreeNodeSelected.toString()+"？", "删除网络组", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						File file = new File(tree.caculateFileSelected(treePathSelected));
						try {
							FileTools.deleteDir(file);
							((DefaultMutableTreeNode)lastTreeNodeSelected.getParent()).remove(lastTreeNodeSelected);
							tree.reInit();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(NetworkPanel.this, "无法删除选中的网络组："+lastTreeNodeSelected.toString(), "删除网络组失败", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						return;
					}
				}				
			}
		});
		
		mi3.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()&&lastTreeNodeSelected.toString().endsWith(".net")) {
					try {
						Network network = Network.read(new File(tree.caculateFileSelected(treePathSelected)));						
						AlgorithmChooserDialog acd = new AlgorithmChooserDialog();
						acd.setModal(true);
						acd.show();
						if(acd.getSelectedAlgorithmProperty()!=null) {
							ExperimentProperty experimentProperty = new ExperimentProperty(network, acd.getSelectedAlgorithmProperty(), new ExperimentConfig());							
							mainFrame.getExperimentPanel().setSelectedExperimentProperty(experimentProperty);
							mainFrame.getExperimentPanel().showTempExperiment(experimentProperty);
							mainFrame.showExperimentPanel();
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(NetworkPanel.this, "请在网络文件上运行算法", "无法运行算法", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		});


	}
	
	

	/**
	 * Create the panel.
	 */
	public NetworkPanel() {
		setLayout(new BorderLayout(0, 0));

		add(splitPane, BorderLayout.CENTER);

		File networkTopDir = new File("./Networks");
		tree = new JNetworkTree(networkTopDir);
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2&&e.getButton()==MouseEvent.BUTTON1)  {
					TreePath treePathSelected = tree.getPathForLocation(e.getX(), e.getY());
					if(treePathSelected==null) return;
					DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
					if(lastTreeNodeSelected.isLeaf()&&lastTreeNodeSelected.toString().endsWith(".net")) {
						try {
							Network network = Network.read(new File(tree.caculateFileSelected(treePathSelected)));
							networkModel.setNetwork(network);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
					}
				}
			}
			
			public void mouseReleased(MouseEvent e){
				if(e.isPopupTrigger()) {
					popupMenuForGroupX = e.getX();
					popupMenuForGroupY = e.getY();
					popupMenuForNetworkTree.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		splitPane.setLeftComponent(tree);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		splitPane_2 = new JSplitPane();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel networkPanel = new JPanel();
		networkPanel.setLayout(new BorderLayout());
		networkPanel.add(tabbedPane, BorderLayout.CENTER);
		splitPane_2.setLeftComponent(networkPanel);
		nodeModifyAndPropertyPanel = new NodeModifyAndPropertyPanel();
		splitPane_2.setRightComponent(nodeModifyAndPropertyPanel);
		splitPane_1.setRightComponent(splitPane_2);

		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(new JScrollPane(panel));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{438, 97, 0, 0};
		gbl_panel_1.rowHeights = new int[]{107, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_1);
		
				
				JPanel generateNetworkPanel = new JPanel();
				generateNetworkPanel.setBorder(new TitledBorder(null, GenerateNetwork, TitledBorder.LEADING, TitledBorder.TOP, null, null));
				
				GridBagLayout gbl_generateNetworkPanel = new GridBagLayout();
				gbl_generateNetworkPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0};
				gbl_generateNetworkPanel.columnWidths = new int[]{0, 66, 0, 75, 108};
				gbl_generateNetworkPanel.rowWeights = new double[] { 0.0, 1.0, 0.0 };
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
														
		JButton btnGenerateNetwork = new JButton(Generate);
		btnGenerateNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				caculateNetworkParameter();
				Network network = Network.createRandomNetwork(networkWidth,
						networkHeight, nodeNum);
				IdealRadioRangeLinkEstimator le = new IdealRadioRangeLinkEstimator(
						network, radioRange);
				network.setLinkEstimator(le);
				network.setDescription(description);
				networkModel.setNetwork(network);						
				isNetworkNew = true;
			}
		});
		
		JButton btnClear = new JButton(Reset);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTextFields();
			}
		});
		
		JPanel radioRangePanel = new JPanel();
		radioRangePanel.setBorder(new TitledBorder(null, SetRadioRange, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_radioRangePanel = new GridBagLayout();
		radioRangePanel.setLayout(gbl_radioRangePanel);
		
		JLabel lblRadionRange = new JLabel(RadionRange);
		GridBagConstraints gbc_lblRadionRange = new GridBagConstraints();
		gbc_lblRadionRange.anchor = GridBagConstraints.WEST;
		gbc_lblRadionRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadionRange.gridx = 0;
		gbc_lblRadionRange.gridy = 0;
		radioRangePanel.add(lblRadionRange, gbc_lblRadionRange);
		
				radioRangeField = new JTextField();
				radioRangeField.setColumns(10);
				GridBagConstraints gbc_radioRangeField = new GridBagConstraints();
				gbc_radioRangeField.weightx = 0.2;
				gbc_radioRangeField.insets = new Insets(0, 0, 5, 5);
				gbc_radioRangeField.fill = GridBagConstraints.HORIZONTAL;
				gbc_radioRangeField.gridx = 1;
				gbc_radioRangeField.gridy = 0;
				gbc_radioRangeField.gridwidth = 2;
				radioRangePanel.add(radioRangeField, gbc_radioRangeField);
				
				JButton btnSetRadioRange = new JButton(SetRadioRange);
				btnSetRadioRange.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							radioRange = Double
									.parseDouble(radioRangeField.getText());
							networkModel.setRadioRange(radioRange);
						} catch (NumberFormatException f) {
							// TODO Auto-generated catch block
							f.printStackTrace();
						}
					}
				});
				GridBagConstraints gbc_btnSetRadioRange = new GridBagConstraints();
				gbc_btnSetRadioRange.weightx = 1.0;
				gbc_btnSetRadioRange.fill = GridBagConstraints.BOTH;
				gbc_btnSetRadioRange.insets = new Insets(0, 0, 0, 5);
				gbc_btnSetRadioRange.gridx = 0;
				gbc_btnSetRadioRange.gridy = 1;
				gbc_btnSetRadioRange.gridwidth = 2;
				radioRangePanel.add(btnSetRadioRange, gbc_btnSetRadioRange);
				GridBagConstraints gbc_radioRangePanel = new GridBagConstraints();
				gbc_radioRangePanel.weighty = 1.0;
				gbc_radioRangePanel.weightx = 0.2;
				gbc_radioRangePanel.anchor = GridBagConstraints.EAST;
				gbc_radioRangePanel.fill = GridBagConstraints.BOTH;
				gbc_radioRangePanel.gridx = 2;
				gbc_radioRangePanel.gridy = 0;
				panel.add(radioRangePanel, gbc_radioRangePanel); 
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.weightx = 0.5;
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.insets = new Insets(0, 0, 0, 5);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 3;
		gbc_btnClear.gridwidth = 2;
		generateNetworkPanel.add(btnClear, gbc_btnClear);
		
		GridBagConstraints gbc_btnGenerateNetwork = new GridBagConstraints();
		gbc_btnGenerateNetwork.weightx = 0.5;
		gbc_btnGenerateNetwork.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerateNetwork.insets = new Insets(0, 0, 0, 5);
		gbc_btnGenerateNetwork.gridx = 2;
		gbc_btnGenerateNetwork.gridy = 3;
		gbc_btnGenerateNetwork.gridwidth = 2;
		generateNetworkPanel.add(btnGenerateNetwork, gbc_btnGenerateNetwork);
		
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
		this.clearTextFields();
		networkPanel.add(generateNetworkStatusBar, BorderLayout.SOUTH);
	}
	
	private void clearTextFields() {
		this.networkWidthField.setText(this.networkWidthDefault);
		this.networkHeightField.setText(this.networkHeightDefault);
		this.radioRangeField.setText(this.radioRangeDefault);
		this.nodeNumField.setText(this.nodeNumDefault);
		this.descriptionTextField.setText(this.DescriptionDefault);
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

	protected void caculateNetworkParameter() {
		try {
			this.networkHeight = Double.parseDouble(this.networkHeightField
					.getText());
		} catch (NumberFormatException e) {

		}
		try {
			this.networkWidth = Double.parseDouble(this.networkWidthField
					.getText());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			this.radioRange = Double
					.parseDouble(this.radioRangeField.getText());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			this.nodeNum = Integer.parseInt(this.nodeNumField.getText());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.description = this.descriptionTextField.getText();
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

	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);					
				}
				else if(e.getButton()==MouseEvent.BUTTON1) {
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
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popupMenuX = e.getX();
				popupMenuY = e.getY();
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){ 
					Point2D.Double p = pointConvertor(e.getX(), e.getY());
					networkModel.addNode((int)p.getX(),(int)p.getY());
				} 
			}
		});
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	private void addPopupForGroup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);					
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popupMenuForGroupX = e.getX();
				popupMenuForGroupY = e.getY();
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public JNetworkTree getTree() {
		return tree;
	}
}
