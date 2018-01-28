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

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;

public class NetworkSaveDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fileNameTextField;
	private JNetworkTree tree;
	private TreePath treePathSelected;
	private DefaultMutableTreeNode lastTreeNodeSelected;
	private String netwworkFileExt = ".net";
	private String treeFileNameWithoutExt;
	private Network network;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Network network = Network.createRandomNetwork(400,
					400, 400);
			IdealRadioRangeLinkEstimator le = new IdealRadioRangeLinkEstimator(
					network, 50);
			network.setLinkEstimator(le);					
			network.setDescription("A sample network");

			NetworkSaveDialog dialog = new NetworkSaveDialog(network);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NetworkSaveDialog(Network net) {
		this.network = net;
		
		setTitle("保存网络");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblNewLabel = new JLabel("\u7F51\u7EDC\u6587\u4EF6\u540D:");
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
			{
				JLabel lblNewLabel_1 = new JLabel(netwworkFileExt);
				GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
				gbc_lblNewLabel_1.weightx = 0.1;
				gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_1.gridx = 3;
				gbc_lblNewLabel_1.gridy = 0;
				panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tree = new JNetworkTree();
				tree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						treePathSelected = e.getNewLeadSelectionPath();
						lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
						if(lastTreeNodeSelected.isLeaf()) {							
							String fileName = lastTreeNodeSelected.toString();
							if(fileName.endsWith(".net")) {
								treeFileNameWithoutExt = fileName.substring(0,
										fileName.lastIndexOf(netwworkFileExt));							
								fileNameTextField.setText(treeFileNameWithoutExt);
							}
						}
					}
				});
				scrollPane.setColumnHeaderView(tree);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String fileName = fileNameTextField.getText();
						if(fileName.equals("")) {
							JOptionPane.showMessageDialog(NetworkSaveDialog.this, "文件名不能为空");
							return;
						}
						
						if(treeFileNameWithoutExt!=null&&treeFileNameWithoutExt.equals(fileName)) {
							File file = new File(tree.caculateFileSelected(treePathSelected));
							network.save(file);
							NetworkSaveDialog.this.dispose();
						}
						if(lastTreeNodeSelected!=null&&!lastTreeNodeSelected.isLeaf()) {
							File file = new File(tree.caculateFileSelected(treePathSelected, fileName+netwworkFileExt));
							network.save(file);
							NetworkSaveDialog.this.dispose();
						}
						else if(lastTreeNodeSelected!=null&&lastTreeNodeSelected.isLeaf()) {
							String fileName1 = lastTreeNodeSelected.toString();
							if(!fileName1.endsWith(".net")) {
								File file = new File(tree.caculateFileSelected(treePathSelected, fileName+netwworkFileExt));
								network.save(file);
								NetworkSaveDialog.this.dispose();
							}
						}else {
							JOptionPane.showMessageDialog(NetworkSaveDialog.this, "请选中保存的目录");
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
						NetworkSaveDialog.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
