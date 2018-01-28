package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class AlgorithmResultPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AlgorithmResultPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Algorithms") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Mac");
						node_1.add(new DefaultMutableTreeNode("S-MAC"));
						node_1.add(new DefaultMutableTreeNode("T-MAC"));
						node_1.add(new DefaultMutableTreeNode("ARC-MAC"));
						node_1.add(new DefaultMutableTreeNode("Wise-MAC"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Routing");
						node_1.add(new DefaultMutableTreeNode("GPSR"));
						node_1.add(new DefaultMutableTreeNode("AODV"));
						node_1.add(new DefaultMutableTreeNode("Flood"));
						node_1.add(new DefaultMutableTreeNode("LEACH"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Data collection");
						node_1.add(new DefaultMutableTreeNode("CTP"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Query processing");
						node_1.add(new DefaultMutableTreeNode("IWQE"));
						node_1.add(new DefaultMutableTreeNode("FullFlood"));
						node_1.add(new DefaultMutableTreeNode("TAG"));
						node_1.add(new DefaultMutableTreeNode("SWIF"));
						node_1.add(new DefaultMutableTreeNode("ESA"));
						node_1.add(new DefaultMutableTreeNode("ASA"));
						node_1.add(new DefaultMutableTreeNode("RSA"));
						node_1.add(new DefaultMutableTreeNode("RESTA"));
						node_1.add(new DefaultMutableTreeNode("IKNN"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Localization");
						node_1.add(new DefaultMutableTreeNode("TOA"));
						node_1.add(new DefaultMutableTreeNode("RSSI"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Time synchronization");
						node_1.add(new DefaultMutableTreeNode("RBS"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Privacy");
						node_1.add(new DefaultMutableTreeNode("PDA"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Security");
						node_1.add(new DefaultMutableTreeNode(""));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Other");
						node_1.add(new DefaultMutableTreeNode("CVD"));
						node_1.add(new DefaultMutableTreeNode("DDFS"));
					add(node_1);
				}
			}
		));
		scrollPane.setViewportView(tree);
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		rightPanel.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("Property", new AlgorithmPropertyPanel());
		tabbedPane.addTab("Demo", new AlgorithmDemoPanel());
		tabbedPane.addTab("Demo result", new AlgorithmResultPanel());
	}

}
