package NHSensor.NHSensorSim.ui.tcl;

import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.*;

public class AwtTclShellFrame extends Frame {
	private static final long serialVersionUID = 3L;

	public AwtTclShellFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AwtTclShellPanel tclShell = new AwtTclShellPanel();
		final AwtTclShellFrame awtTclShellFrame = new AwtTclShellFrame();
		awtTclShellFrame.pack();
		awtTclShellFrame.setTitle("NHSensorSim TclShell");

		awtTclShellFrame.setSize(800, 600);
		awtTclShellFrame.add(tclShell);
		awtTclShellFrame.show();

		// Make it closeable
		awtTclShellFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				awtTclShellFrame.dispose();
			}
		});

	}

}
