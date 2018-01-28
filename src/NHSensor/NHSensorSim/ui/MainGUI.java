package NHSensor.NHSensorSim.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JToolBar;

import org.apache.log4j.PropertyConfigurator;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGUI mainGUI = new MainGUI();
				mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainGUI.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(900, 700);
		this.setTitle("NHSensorSim");
		this.initMenus();
		this.initToolbar();
		this.initPanels();
	}

	private void initPanels() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new SimulationPanel(), BorderLayout.CENTER);
		this.getContentPane().add(new ConfigPanel(), BorderLayout.WEST);
		this.getContentPane().add(new StatusPanel(), BorderLayout.SOUTH);
	}

	private void initToolbar() {
		JToolBar toolBar = new JToolBar();
		JButton open = new JButton("open");
		toolBar.add(open);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	private void initMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("open");
		file.add(open);
		JMenu edit = new JMenu("Edit");
		menuBar.add(file);
		menuBar.add(edit);
		this.setJMenuBar(menuBar);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
