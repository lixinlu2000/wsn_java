package NHSensor.NHSensorSim.ui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;

public class ConfigPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel algorithm = null;
	private JComboBox algComboBox = null;

	/**
	 * This is the default constructor
	 */
	public ConfigPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		algorithm = new JLabel();
		algorithm.setText("Algorithm");
		this.setLayout(new GridBagLayout());
		this.setSize(300, 242);
		this.add(algorithm, gridBagConstraints);
		this.add(getAlgComboBox(), gridBagConstraints1);
	}

	/**
	 * This method initializes algComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getAlgComboBox() {
		if (algComboBox == null) {
			algComboBox = new JComboBox();
			algComboBox.addItem("TAG");
			algComboBox.addItem("SWinFlood");
		}
		return algComboBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
