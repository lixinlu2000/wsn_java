package NHSensor.NHSensorSim.ui.tcl;

import tcl.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AwtTclShellPanel extends Panel {
	private static final long serialVersionUID = 4L;

	private StringBuffer commandBuffer = new StringBuffer();

	private Interp tclInterp = new Interp();

	public TextArea textArea;
	private Font font = new Font("SansSerif", Font.PLAIN, 20);

	public String mainPrompt = "% ";
	public String contPrompt = "> ";

	private int promptCursor = 0;
	private int commandCursor = 0;

	public int historyLength = 10;
	private int historyCursor = 0;
	private Vector historyCommands = new Vector();

	public AwtTclShellPanel() {

		super(new BorderLayout());
		textArea = new TextArea("", 800, 600, TextArea.SCROLLBARS_BOTH);
		textArea.setFont(font);
		add("Center", textArea);

		// Event handling part
		textArea.addKeyListener(new ShellKeyListener());

		// Initialize the interpreter
		try {
			tclInterp.setVar("tclShell", ReflectObject.newInstance(tclInterp,
					this.getClass(), this), 0);
			tclInterp.setVar("tclInterp", ReflectObject.newInstance(tclInterp,
					Interp.class, tclInterp), 0);
			tclInterp
					.eval("proc vputs {s} {global tclShell; $tclShell putText $s\\n}");
			tclInterp
					.eval("proc clear {} {global tclShell; $tclShell clearTextArea }");
			tclInterp.eval("package require java");
			tclInterp.evalFile(".\\tcl\\procs\\NHSensorSim.tcl");
			tclInterp
					.eval("proc evalFile {fileName} {global tclInterp; $tclInterp evalFile $fileName}");
			// tclInterp.evalFile("");
		} catch (TclException e) {
			System.out.println(tclInterp.getResult());
		}
	}

	public void addNotify() {
		super.addNotify();
		putText(mainPrompt);
	}

	public void clearTextArea() {
		this.setText(this.mainPrompt);
		// this.putText(this.mainPrompt);
	}

	public void setText(String s) {
		if (s.length() != 0) {
			this.textArea.setText(this.mainPrompt);
			this.promptCursor = 1;
			this.textArea.setCaretPosition(this.promptCursor);
		}
	}

	public void putText(String s) {
		if (s.length() != 0) {
			textArea.append(s);
			promptCursor += s.length();
			textArea.setCaretPosition(promptCursor);
		}
	}

	// Evaluate the command so far, if possible, printing
	// a continuation prompt if not.
	protected void evalCommand() {
		String newtext = textArea.getText().substring(promptCursor);
		promptCursor += newtext.length();
		if (commandBuffer.length() > 0) {
			commandBuffer.append("\n");
		}
		commandBuffer.append(newtext);
		String command = commandBuffer.toString();

		if (Interp.commandComplete(command)) {
			// Process it
			putText("\n");
			Cursor oldCursor = textArea.getCursor();
			textArea.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {
				tclInterp.eval(command);
			} catch (TclException e) {
				// ignore
			}
			String result = tclInterp.getResult().toString();
			if (result.length() > 0) {
				putText(result + "\n" + mainPrompt);
			} else {
				putText(mainPrompt);
			}
			commandBuffer.setLength(0);
			commandCursor = promptCursor;
			textArea.setCursor(oldCursor);
			updateHistory(command);
		} else {
			putText("\n" + contPrompt);
		}
	}

	// Update the command history
	void updateHistory(String command) {
		historyCursor = 0;
		if (historyCommands.size() == historyLength) {
			historyCommands.removeElementAt(0);
		}
		historyCommands.addElement(command);
	}

	// Replace the command with an entry from the history
	void nextCommand() {
		String text;
		if (historyCursor == 0) {
			text = "";
		} else {
			historyCursor--;
			text = (String) historyCommands.elementAt(historyCommands.size()
					- historyCursor - 1);
		}
		textArea.replaceRange(text, commandCursor, textArea.getText().length());
	}

	void previousCommand() {
		String text;
		if (historyCursor == historyCommands.size()) {
			return;
		} else {
			historyCursor++;
			text = (String) historyCommands.elementAt(historyCommands.size()
					- historyCursor);
		}
		textArea.replaceRange(text, commandCursor, textArea.getText().length());
	}

	// The key listener
	class ShellKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent keyEvent) {
			// Process keys
			switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				keyEvent.consume();
				evalCommand();
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (textArea.getCaretPosition() == promptCursor) {
					keyEvent.consume(); // don't backspace over prompt!
				}
				break;
			case KeyEvent.VK_LEFT:
				if (textArea.getCaretPosition() == promptCursor) {
					keyEvent.consume();
				}
				break;
			case KeyEvent.VK_UP:
				previousCommand();
				keyEvent.consume();
				break;
			case KeyEvent.VK_DOWN:
				nextCommand();
				keyEvent.consume();
				break;
			default:
			}
		}
	}
}
