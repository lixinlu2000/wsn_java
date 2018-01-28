package NHSensor.NHSensorSim.ui.tcl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

public class TclShellFrame extends JFrame {
	private static final long serialVersionUID = 5L;

	private void initMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("open");
		JMenuItem execute = new JMenuItem("execute");
		JSeparator sep = new JSeparator();
		JMenuItem close = new JMenuItem("close");
		file.add(open);
		file.add(execute);
		file.add(sep);
		file.add(close);
		menuBar.add(file);

		JMenu help = new JMenu("help");
		JMenuItem doc = new JMenuItem("document");
		JMenuItem about = new JMenuItem("about");
		help.add(doc);
		help.add(about);
		menuBar.add(help);

		this.setJMenuBar(menuBar);
	}

	private void initToolBar() {
		JToolBar toolBar = new JToolBar();
		JButton open = new JButton("open");
		JButton close = new JButton("close");

		toolBar.add(open);
		toolBar.add(close);
		this.getContentPane().add(toolBar);
	}

	public TclShellFrame() {
		TclShellPanel tclShellPanel = new TclShellPanel();
		this.initMenus();
		this.getContentPane().add(tclShellPanel);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final TclShellFrame tclShellFrame = new TclShellFrame();
		tclShellFrame.setSize(800, 600);
		tclShellFrame.setTitle("NHSensorSim TclShell");
		tclShellFrame.show();

		tclShellFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				tclShellFrame.dispose();
			}
		});

	}
}
