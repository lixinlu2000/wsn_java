package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ExperimentSaveDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fileNameTextField;
	private JExperimentTree tree;
	private TreePath treePathSelected;
	private DefaultMutableTreeNode lastTreeNodeSelected;
	ExperimentProperty experimentProperty;
	private String fileName;
	private boolean experimentGroupSelected = false;
	private boolean experimentSelected = false;

	/**
	 * Create the dialog.
	 */
	public ExperimentSaveDialog(ExperimentProperty experimentProperty) {
		this.experimentProperty = experimentProperty;
		
		setTitle("\u4FDD\u5B58\u5B9E\u9A8C");
		setBounds(100, 100, 479, 304);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			gbl_panel.rowWeights = new double[]{0.0};
			panel.setLayout(gbl_panel);
			{
				JLabel lblNewLabel = new JLabel("\u5B9E\u9A8C\u6587\u4EF6\u540D:");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.weightx = 0.1;
				gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 0;
				panel.add(lblNewLabel, gbc_lblNewLabel);
			}
			{
				fileNameTextField = new JTextField();
				fileNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
				GridBagConstraints gbc_fileNameTextField = new GridBagConstraints();
				gbc_fileNameTextField.weightx = 0.8;
				gbc_fileNameTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_fileNameTextField.anchor = GridBagConstraints.NORTHWEST;
				gbc_fileNameTextField.insets = new Insets(0, 0, 0, 5);
				gbc_fileNameTextField.gridx = 2;
				gbc_fileNameTextField.gridy = 0;
				panel.add(fileNameTextField, gbc_fileNameTextField);
				fileNameTextField.setColumns(10);
			}
		}
		{
			tree = new JExperimentTree();
			JScrollPane scrollPane = new JScrollPane(tree);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					treePathSelected = e.getNewLeadSelectionPath();
					lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
					if(lastTreeNodeSelected.isLeaf()) {							
						fileNameTextField.setText(lastTreeNodeSelected.toString());
						experimentGroupSelected = false;
						experimentSelected = true;
					}
					else {
						experimentGroupSelected = true;
						experimentSelected = false;
					}
				}
			});

		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fileName = fileNameTextField.getText();
						if(fileName.equals("")) {
							JOptionPane.showMessageDialog(ExperimentSaveDialog.this, "实验文件名不能为空");
							return;
						}
						
						if(experimentGroupSelected) {
							File path = new File(tree.caculateDirSelected(treePathSelected), fileName);
							path.mkdirs();
							ExperimentSaveDialog.this.experimentProperty.save(path);
							ExperimentSaveDialog.this.dispose();
						}
						else if(experimentSelected) {
							File path = new File(tree.caculateDirSelected(treePathSelected.getParentPath()), fileName);
							path.mkdirs();
							ExperimentSaveDialog.this.experimentProperty.save(path);
							ExperimentSaveDialog.this.dispose();
						}else {
							JOptionPane.showMessageDialog(ExperimentSaveDialog.this, "请选中存放目录或覆盖的实验文件");
							return;
						}						
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ExperimentSaveDialog.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
