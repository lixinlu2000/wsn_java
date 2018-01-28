package NHSensor.NHSensorSim.ui.tcl;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tcl.lang.Interp;
import tcl.lang.ReflectObject;
import tcl.lang.TclException;

public class TclShellPanel extends JPanel {
	private static final long serialVersionUID = 6L;

	private JTextArea textArea = new JTextArea(800, 600);
	private Font font = new Font("SansSerif", Font.PLAIN, 20);
	private JScrollPane scrollPane;
	private JSplitPane mainPane = new JSplitPane();
	private JSplitPane leftPane = new JSplitPane();
	private JPanel fileSystemPanel = new FileTree(new File("."));

	private DefaultListModel historyCommandModel = new DefaultListModel();
	private JList historyCommandList = new JList(historyCommandModel);
	private JScrollPane historyScrollPane = new JScrollPane(historyCommandList);

	private Interp tclInterp = null;
	private String mainPrompt = "%";
	private String contPrompt = ">";
	private int promptPosition = 0;
	private StringBuffer commandBuffer = new StringBuffer();

	public TclShellPanel() {
		this.initPanel();
		this.initTclInterp();
		this.inithistoryCommand();
	}

	private void inithistoryCommand() {
		File commandFile = new File(".\\config\\historyCommand.txt");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					commandFile));
			String command = bufferedReader.readLine();
			while (command != null) {
				historyCommandModel.addElement(command);
				command = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initPanel() {
		this.setLayout(new BorderLayout());

		textArea.addKeyListener(new TextAreaKeyListener());
		textArea.setFont(this.font);
		this.putText(this.getMainPrompt());
		scrollPane = new JScrollPane(textArea);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;

		mainPane.setDividerLocation(150);
		mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		mainPane.setLeftComponent(this.leftPane);
		mainPane.setRightComponent(this.scrollPane);

		this.leftPane.setDividerLocation(400);
		this.leftPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.leftPane.setLeftComponent(fileSystemPanel);
		this.leftPane.setRightComponent(historyScrollPane);
		historyCommandList
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						String command = theList.getModel().getElementAt(index)
								.toString();
						putText(command + "\n");
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

					}
				}
			}
		};
		historyCommandList.addMouseListener(mouseListener);
		historyCommandList
				.addListSelectionListener(new HistoryCommandListSelectionListener());
		this.add(this.mainPane, BorderLayout.CENTER);
	}

	public class HistoryCommandListSelectionListener implements
			ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting())
				return;
			JList theList = (JList) e.getSource();
			String command = theList.getSelectedValue().toString();
			textArea.append(command);
			textArea.requestFocus();
		}

	}

	public void initTclInterp() {
		tclInterp = new Interp();
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

	public void clearTextArea() {
		this.setText(this.mainPrompt);
		// this.putText(this.mainPrompt);
	}

	public void setText(String s) {
		if (s.length() != 0) {
			this.textArea.setText(this.mainPrompt);
			this.promptPosition = 1;
			this.textArea.setCaretPosition(this.promptPosition);
		}
	}

	public void putText(String s) {
		if (s.length() != 0) {
			textArea.append(s);
			promptPosition += s.length();
			textArea.setCaretPosition(promptPosition);
		}
	}

	protected void evalCommand() {
		String newtext = textArea.getText().substring(promptPosition);
		promptPosition += newtext.length();
		if (commandBuffer.length() > 0) {
			commandBuffer.append("\n");
		}
		commandBuffer.append(newtext);
		String command = commandBuffer.toString();

		this.historyCommandModel.addElement(command);
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
			textArea.setCursor(oldCursor);
		} else {
			putText("\n" + contPrompt);
		}
	}

	// The key listener
	class TextAreaKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent keyEvent) {
			switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				keyEvent.consume();
				evalCommand();
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (textArea.getCaretPosition() == 1) {
					keyEvent.consume();
				}
				break;
			case KeyEvent.VK_LEFT:
				if (textArea.getCaretPosition() == 1) {
					keyEvent.consume();
				}
				break;
			case KeyEvent.VK_UP:

				keyEvent.consume();
				break;
			case KeyEvent.VK_DOWN:

				keyEvent.consume();
				break;
			default:
			}
		}
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getMainPrompt() {
		return mainPrompt;
	}

	public void setMainPrompt(String mainPrompt) {
		this.mainPrompt = mainPrompt;
	}

	public String getContPrompt() {
		return contPrompt;
	}

	public void setContPrompt(String contPrompt) {
		this.contPrompt = contPrompt;
	}

	public int getPromptPosition() {
		return promptPosition;
	}

	public void setPromptPosition(int promptPosition) {
		this.promptPosition = promptPosition;
	}
}
