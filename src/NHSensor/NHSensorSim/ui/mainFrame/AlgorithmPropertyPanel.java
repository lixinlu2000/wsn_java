package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AlgorithmPropertyPanel extends JPanel {
	private JTabbedPane tabbedPane;
	private JPanel emptyPanel = new JPanel();
	
	/**
	 * Create the panel.
	 */
	public AlgorithmPropertyPanel() {
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		
		tabbedPane.addTab("À„∑® Ù–‘", null, emptyPanel, null);

	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
	
	public void setAlgorithmPropertyPanel(JPanel panel) {
		this.tabbedPane.setComponentAt(0, panel);
	}
	
	public void setEmptyNodePropertyPanel() {
		this.tabbedPane.setComponentAt(0, emptyPanel);
	}


	public JPanel getEmptyPanel() {
		return emptyPanel;
	}

	public void setEmptyPanel(JPanel emptyPanel) {
		this.emptyPanel = emptyPanel;
	}

}
