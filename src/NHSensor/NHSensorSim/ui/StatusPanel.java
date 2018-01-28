package NHSensor.NHSensorSim.ui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel energy = null;
	private JTextField energyTextField = null;

	/**
	 * This is the default constructor
	 */
	public StatusPanel() {
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
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		energy = new JLabel();
		energy.setText("energy");
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(energy, gridBagConstraints);
		this.add(getEnergyTextField(), gridBagConstraints1);
	}

	/**
	 * This method initializes energyTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEnergyTextField() {
		if (energyTextField == null) {
			energyTextField = new JTextField();
			energyTextField.setText("test text field");

		}
		return energyTextField;
	}

}
