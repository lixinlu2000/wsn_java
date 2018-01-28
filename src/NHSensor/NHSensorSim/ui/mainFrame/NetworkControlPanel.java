package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import NHSensor.NHSensorSim.ui.NetworkModel;
import NHSensor.NHSensorSim.ui.NetworkView;

public class NetworkControlPanel extends JPanel  implements NetworkView {
	private JTextField networkWidthField;
	private JTextField networkHeightField;
	private JTextField nodeNumField;
	private JTextField sensorCanvasScaleField;
	private NetworkModel networkModel;
	private JSlider sensorCanvasScaleSlider;
	private JTextField descriptionTextField;

	private int minSensorCanvasScale = -4;
	private int maxSensorCanvasScale = 10;

	private final String NetworkWidth = "网络宽度：";
	private final String NetworkHeight = "网络高度：";
	private final String NodeNumber = "节点数目：";
	private final String Description = "网络描述：";
	private final String NetworkCanvasScale = "网络显示缩放比例值";
	private final String NetworkCanvasZoomInAndOut = "网络显示缩放";
	private final String CanvasSizeSlider = "网络显示缩放";

	/**
	 * Create the panel.
	 */
	public NetworkControlPanel() {
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 438, 97, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 107, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_panel_1);

		JPanel generateNetworkPanel = new JPanel();
		generateNetworkPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				"\u7F51\u7EDC\u57FA\u672C\u53C2\u6570", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		GridBagLayout gbl_generateNetworkPanel = new GridBagLayout();
		gbl_generateNetworkPanel.columnWeights = new double[] { 0.0, 1.0, 0.0,
				0.0, 0.0 };
		gbl_generateNetworkPanel.columnWidths = new int[] { 0, 66, 0, 75, 108 };
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
		generateNetworkPanel
				.add(descriptionTextField, gbc_descriptionTextField);
		descriptionTextField.setColumns(10);

		GridBagConstraints gbc_generateNetworkPanel = new GridBagConstraints();
		gbc_generateNetworkPanel.weighty = 1.0;
		gbc_generateNetworkPanel.weightx = 0.6;
		gbc_generateNetworkPanel.fill = GridBagConstraints.BOTH;
		gbc_generateNetworkPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_generateNetworkPanel.insets = new Insets(0, 0, 0, 5);
		gbc_generateNetworkPanel.gridx = 0;
		gbc_generateNetworkPanel.gridy = 0;
		this.add(generateNetworkPanel, gbc_generateNetworkPanel);

		JPanel sensorCanvasScalePanel = new JPanel();
		sensorCanvasScalePanel.setBorder(new TitledBorder(null,
				NetworkCanvasZoomInAndOut, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagLayout gbl_sensorCanvasScalePanel = new GridBagLayout();
		gbl_sensorCanvasScalePanel.columnWidths = new int[] { 0, 0 };
		sensorCanvasScalePanel.setLayout(gbl_sensorCanvasScalePanel);

		sensorCanvasScaleSlider = new JSlider();
		sensorCanvasScaleSlider.setValue(0);
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
		sensorCanvasScalePanel.add(lblSensorCanvasScale,
				gbc_lblSensorCanvasScale);

		sensorCanvasScaleField = new JTextField();
		sensorCanvasScaleField.setColumns(10);
		GridBagConstraints gbc_sensorCanvasScaleField = new GridBagConstraints();
		gbc_sensorCanvasScaleField.weightx = 0.2;
		gbc_sensorCanvasScaleField.insets = new Insets(0, 0, 5, 5);
		gbc_sensorCanvasScaleField.fill = GridBagConstraints.HORIZONTAL;
		gbc_sensorCanvasScaleField.gridx = 1;
		gbc_sensorCanvasScaleField.gridy = 2;
		sensorCanvasScalePanel.add(sensorCanvasScaleField,
				gbc_sensorCanvasScaleField);
		GridBagConstraints gbc_sensorCanvasScalePanel = new GridBagConstraints();
		gbc_sensorCanvasScalePanel.weighty = 1.0;
		gbc_sensorCanvasScalePanel.gridx = 1;
		gbc_sensorCanvasScalePanel.weightx = 0.2;
		gbc_sensorCanvasScalePanel.fill = GridBagConstraints.BOTH;
		gbc_sensorCanvasScalePanel.gridy = 0;
		this.add(sensorCanvasScalePanel, gbc_sensorCanvasScalePanel);

	}

	public void initNetworkCanvasScaleSlider() {
		sensorCanvasScaleSlider.setMinimum(minSensorCanvasScale);
		sensorCanvasScaleSlider.setMaximum(maxSensorCanvasScale);
		sensorCanvasScaleSlider.setValue(0);
		sensorCanvasScaleSlider.setBorder(BorderFactory
				.createTitledBorder(CanvasSizeSlider));
		sensorCanvasScaleSlider.setMajorTickSpacing(1);
		sensorCanvasScaleSlider.setMinorTickSpacing(1);
		sensorCanvasScaleSlider.setPaintTicks(true);
		sensorCanvasScaleSlider.setPaintLabels(true);
		SensorCanvasSizeSliderChangeListerner sensorCanvasSizeSliderChangeListerner = new SensorCanvasSizeSliderChangeListerner();
		sensorCanvasScaleSlider
				.addChangeListener(sensorCanvasSizeSliderChangeListerner);
	}

	public NetworkModel getNetworkModel() {
		return networkModel;
	}

	public void setNetworkModel(NetworkModel networkModel) {
		this.networkModel = networkModel;
		this.updateNetworkView();
	}

	public class SensorCanvasSizeSliderChangeListerner implements
			ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				try {
					networkModel.setNetworkCanvasSizeScale(source.getValue());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	protected void initViewFromModel() {
		this.networkWidthField.setText(""+this.networkModel.getNetwork().getWidth());
		this.networkHeightField.setText(""+this.networkModel.getNetwork().getHeigth());
		this.nodeNumField.setText(""+this.networkModel.getNetwork().getNodeNum());
		this.descriptionTextField.setText(""+this.networkModel.getDescription());
		this.sensorCanvasScaleField.setText(""+Math.pow(2,this.networkModel.getNetworkCanvasSizeScale()));
		this.sensorCanvasScaleSlider.setValue((int)this.networkModel.getNetworkCanvasSizeScale());
		
	}

	public void updateNetworkView() {
		this.initViewFromModel();
	}

	public void setNetworkViewScale(double scale) {
		sensorCanvasScaleField.setText(""+scale);
	}

}
