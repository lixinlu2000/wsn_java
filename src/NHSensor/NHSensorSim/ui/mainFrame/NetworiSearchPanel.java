package NHSensor.NHSensorSim.ui.mainFrame;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

public class NetworiSearchPanel extends JPanel {
	private JTextField nodeNum;
	private JTextField description;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public NetworiSearchPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane verticalSplitPane = new JSplitPane();
		verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		rightPanel.add(verticalSplitPane, BorderLayout.CENTER);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		verticalSplitPane.setLeftComponent(topPanel);
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_topPanel.rowHeights = new int[]{0, 0, 0};
		gbl_topPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_topPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);
		
		JLabel label = new JLabel("Node num:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		topPanel.add(label, gbc_label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"<=", "<", "=", ">=", ">"}));
		comboBox.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		topPanel.add(comboBox, gbc_comboBox);
		
		nodeNum = new JTextField();
		nodeNum.setColumns(10);
		GridBagConstraints gbc_nodeNum = new GridBagConstraints();
		gbc_nodeNum.fill = GridBagConstraints.HORIZONTAL;
		gbc_nodeNum.insets = new Insets(0, 0, 5, 5);
		gbc_nodeNum.gridx = 2;
		gbc_nodeNum.gridy = 0;
		topPanel.add(nodeNum, gbc_nodeNum);
		
		JButton button = new JButton("Search");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridheight = 2;
		gbc_button.gridx = 3;
		gbc_button.gridy = 0;
		topPanel.add(button, gbc_button);
		
		JLabel label_1 = new JLabel("Description contains:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridwidth = 2;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		topPanel.add(label_1, gbc_label_1);
		
		description = new JTextField();
		description.setColumns(10);
		GridBagConstraints gbc_description = new GridBagConstraints();
		gbc_description.fill = GridBagConstraints.HORIZONTAL;
		gbc_description.insets = new Insets(0, 0, 5, 5);
		gbc_description.gridx = 2;
		gbc_description.gridy = 1;
		topPanel.add(description, gbc_description);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "100", "100", "100", "10", "10", "Uniform", "A simple uniform network"},
				{"2", "200", "200", "400", "30", "100", "Grid", null},
				{"3", null, null, null, null, null, null, null},
				{"4", null, null, null, null, null, null, null},
				{"5", null, null, null, null, null, null, null},
				{"6", null, null, null, null, null, null, null},
				{"7", null, null, null, null, null, null, null},
				{"8", null, null, null, null, null, null, null},
			},
			new String[] {
				"No", "Width", "Height", "Node Number", "Default Radio Range", "Default Initial Energy", "Network Type", "Description"
			}
		));
		table.getColumnModel().getColumn(3).setPreferredWidth(93);
		verticalSplitPane.setRightComponent(new JScrollPane(table));
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Network types") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Uniform");
						node_1.add(new DefaultMutableTreeNode(""));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Grid");
						node_1.add(new DefaultMutableTreeNode(""));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("non-Uniform");
						node_1.add(new DefaultMutableTreeNode(""));
					add(node_1);
				}
			}
		));
		scrollPane.setColumnHeaderView(tree);

	}

}
