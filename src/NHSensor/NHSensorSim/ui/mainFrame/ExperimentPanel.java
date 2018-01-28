package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.AlgorithmDemo;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.papers.CSA.AllParam;
import NHSensor.NHSensorSim.papers.CSA.CSACDAEnergyExperiment;
import NHSensor.NHSensorSim.tools.FileTools;
import NHSensor.NHSensorSim.ui.AnimatorPanel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

public class ExperimentPanel extends JPanel {
	private final double DIVIDER_LOCATION_RATE = 0.15;
	JSplitPane splitPane = new JSplitPane();
	JExperimentTree experimentTree = new JExperimentTree();
	TreePath treePathSelected;
	JPanel rightPanel = new JPanel();
	JClosableTabbedPane rightTabbedPane = new JClosableTabbedPane();
	ExperimentProperty selectedExperimentProperty;
	
	private Hashtable experimentTabHashtable = new Hashtable();
	private int networkAndAlgorithmTabLocation = JTabbedPane.BOTTOM;
	private int popupMenuForGroupX, popupMenuForGroupY;
	private JPopupMenu popupMenuForAlgorithmTree = new JPopupMenu();
	private static int tempExperimentID = 0;
	private static final String tempExperimentNamePrefiex = "Experiment";


	/**
	 * Create the panel.
	 */
	public ExperimentPanel() {
		setLayout(new BorderLayout(0, 0));
		
		add(splitPane, BorderLayout.CENTER);
		experimentTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				treePathSelected = experimentTree.getSelectionPath();
			}
		});
		experimentTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2) {
					TreePath treePathSelected = experimentTree.getPathForLocation(e.getX(), e.getY());
					ExperimentPanel.this.openExperiment(treePathSelected);
				}
				else if(e.getClickCount()==1) {
					treePathSelected = experimentTree.getPathForLocation(e.getX(), e.getY());
				}
			}
				
			public void mouseReleased(MouseEvent e){
				if(e.isPopupTrigger()) {
					popupMenuForGroupX = e.getX();
					popupMenuForGroupY = e.getY();
					popupMenuForAlgorithmTree.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});
		
		JScrollPane scrollPane = new JScrollPane(experimentTree);
		splitPane.setLeftComponent(scrollPane);				
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		rightPanel.add(rightTabbedPane, BorderLayout.CENTER);
		rightTabbedPane.addTab("实验", new JPanel());
		
		JMenuItem mi1 = new JMenuItem("创建组");
		JMenuItem mi2 = new JMenuItem("删除");
		popupMenuForAlgorithmTree.add(mi1);
		popupMenuForAlgorithmTree.add(mi2);
		
		mi1.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = experimentTree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {
					JOptionPane.showMessageDialog(ExperimentPanel.this, "无法在实验项上创建组", "创建实验组失败", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					String groupName = JOptionPane.showInputDialog(ExperimentPanel.this, "输入所创建实验组的组名");
					if(groupName==null||groupName.equals("")) return;
					File path = new File(experimentTree.caculateDirSelected(treePathSelected));
					File file = new File(path, groupName);
					try {
						file.mkdir();
						experimentTree.reInit();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(ExperimentPanel.this, "无法创建实验组", "创建实验组失败", JOptionPane.INFORMATION_MESSAGE);
					}
				}				
			}
		});

		mi2.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = experimentTree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {
					int option = JOptionPane.showConfirmDialog(ExperimentPanel.this, "确定删除选中的实验项："+lastTreeNodeSelected.toString()+"？", "删除实验项", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						File file = new File(experimentTree.caculateDirSelected(treePathSelected));
						try {
							FileTools.deleteDir(file);
							((DefaultMutableTreeNode)lastTreeNodeSelected.getParent()).remove(lastTreeNodeSelected);
							experimentTree.reInit();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(ExperimentPanel.this, "无法创建删除选中的实验项："+lastTreeNodeSelected.toString(), "删除实验项失败", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						return;
					}
				}
				else {
					int option = JOptionPane.showConfirmDialog(ExperimentPanel.this, "确定删除选中的实验组："+lastTreeNodeSelected.toString()+"？", "删除实验组", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						File file = new File(experimentTree.caculateDirSelected(treePathSelected));
						try {
							FileTools.deleteDir(file);
							((DefaultMutableTreeNode)lastTreeNodeSelected.getParent()).remove(lastTreeNodeSelected);
							experimentTree.reInit();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(ExperimentPanel.this, "无法删除选中的实验组："+lastTreeNodeSelected.toString(), "删除实验组失败", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						return;
					}
				}				
			}
		});

	}
	
	public void updateSplitPane() {
		splitPane.setDividerLocation(DIVIDER_LOCATION_RATE);
	}
		
	public void showSelectedExperiment(String experimentPathName) {
		this.showExperiment(experimentPathName, selectedExperimentProperty);
	}
	
	public void showTempExperiment(ExperimentProperty experimentPropertye) {
		tempExperimentID++;
		this.showExperiment(tempExperimentNamePrefiex+tempExperimentID, experimentPropertye);
	}
	
	public void openExperiment(TreePath treePathSelected) {
		if(treePathSelected==null) return;
		DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
		if(lastTreeNodeSelected.isLeaf()) {
			try {
				File path = new File(experimentTree.caculateExperimentPropertyPathSelected(treePathSelected));
				selectedExperimentProperty = ExperimentProperty.read(path);
				showSelectedExperiment(path.getName());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}					
		}
	}
	
	public void showExperiment(String experimentPathName, ExperimentProperty experimentProperty) {
		String algorithmClassFullName = experimentProperty.getAlgorithmProperty().getClassFullName();		
		String demoClassFullName = experimentProperty.getAlgorithmProperty().getDemoClassFullName();
		
		Network network = experimentProperty.getNetwork();
		NetworkShowPanel networkShowPanel = new NetworkShowPanel();
		networkShowPanel.setNetwork(network);
		int index;
		
		if(experimentTabHashtable.get(experimentPathName)!=null ) {
			index = rightTabbedPane.indexOfTab(experimentPathName);
			if(index>0) {
				rightTabbedPane.setSelectedIndex(index);
				return;
			}
		}
		
		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo)Class.forName(demoClassFullName).newInstance();
			Algorithm alg = algorithmDemo.getRunnedAlgorithmInstance(network);
			AnimatorPanel animatorPanel = new AnimatorPanel(alg);
			JTabbedPane tp = new JTabbedPane(networkAndAlgorithmTabLocation);
			tp.add("实验网络", networkShowPanel);
			tp.add("实验算法", animatorPanel);
			experimentTabHashtable.put(experimentPathName,experimentPathName);
			rightTabbedPane.addTab(experimentPathName, tp);
			index =  rightTabbedPane.getTabCount()-1;
			rightTabbedPane.setSelectedIndex(index);
			networkShowPanel.updateSplitPane();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSelectedExperimentProperty(
			ExperimentProperty selectedExperimentProperty) {
		this.selectedExperimentProperty = selectedExperimentProperty;
	}

	public ExperimentProperty getSelectedExperimentProperty() {
		return selectedExperimentProperty;
	}

	public TreePath getTreePathSelected() {
		return treePathSelected;
	}
}
