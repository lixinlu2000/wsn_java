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
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;

public class AlgorithmChooserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JAlgorithmTree tree = new JAlgorithmTree();
	private JTextField algTextField;
	private TreePath treePathSelected;
	private DefaultMutableTreeNode lastTreeNodeSelected;
	private AlgorithmProperty selectedAlgorithmProperty;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AlgorithmChooserDialog dialog = new AlgorithmChooserDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AlgorithmChooserDialog() {
		setTitle("\u7B97\u6CD5\u9009\u62E9\u5BF9\u8BDD\u6846");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				treePathSelected = e.getNewLeadSelectionPath();
				lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {							
					String algName = lastTreeNodeSelected.toString();
					algTextField.setText(algName);
				}
				else {
					algTextField.setText("");
				}

			}
		});
		contentPanel.add(new JScrollPane(tree));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblNewLabel = new JLabel("\u9009\u4E2D\u7684\u7B97\u6CD5\uFF1A");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel.gridx = 0;
				gbc_lblNewLabel.gridy = 0;
				panel.add(lblNewLabel, gbc_lblNewLabel);
			}
			{
				algTextField = new JTextField();
				algTextField.setEditable(false);
				GridBagConstraints gbc_algTextField = new GridBagConstraints();
				gbc_algTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_algTextField.gridx = 1;
				gbc_algTextField.gridy = 0;
				panel.add(algTextField, gbc_algTextField);
				algTextField.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(40);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(algTextField.getText().equals("")) {
							JOptionPane.showMessageDialog(AlgorithmChooserDialog.this, "未选中算法", "无法运行算法", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							try {
								selectedAlgorithmProperty = AlgorithmProperty.read(new File(tree.caculateAlgorithmPropertyFileSelected(treePathSelected)));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}		
						AlgorithmChooserDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AlgorithmChooserDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public AlgorithmProperty getSelectedAlgorithmProperty() {
		return selectedAlgorithmProperty;
	}

}
