package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmModel;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.AlgorithmDemo;
import NHSensor.NHSensorSim.importAlgorithm.ThirdPartyAlgorithmPackage;
import NHSensor.NHSensorSim.tools.FileTools;
import NHSensor.NHSensorSim.ui.AnimatorPanel;

public class AlgorithmComparePanel extends JPanel {
	private final double DIVIDER_LOCATION_RATE = 0.15;
	private JSplitPane splitPane = new JSplitPane();
	private JAlgorithmTree tree = new JAlgorithmTree();
	private String curAlgorithmClassName;
	private AlgorithmProperty selectedAlgorithmProperty;
	private AlgorithmModel algorithmModel;
	private JClosableTabbedPane tabbedPane;
	private int popupMenuForGroupX, popupMenuForGroupY;
	private JPopupMenu popupMenuForAlgorithmTree = new JPopupMenu();
	private JFileChooser fileChooser = new JFileChooser();
	private final String userAlgorithmClassPath = "./userAlgorithms";
	
	/**
	 * Create the panel.
	 */
	public AlgorithmComparePanel() {
		setLayout(new BorderLayout(0, 0));
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()!=2) return; 
				TreePath treePathSelected = tree.getPathForLocation(e.getX(), e.getY());
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {
					try {
						selectedAlgorithmProperty = AlgorithmProperty.read(new File(tree.caculateAlgorithmPropertyFileSelected(treePathSelected)));
						showSelectedAlgorithm();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
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
		
		add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		splitPane.setLeftComponent(scrollPane);
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JClosableTabbedPane(); //JTabbedPane.TOP
		rightPanel.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("�㷨��֤", new JPanel());
		
		JMenuItem mi1 = new JMenuItem("������");
		JMenuItem mi2 = new JMenuItem("ɾ��");
		JMenuItem mi3 = new JMenuItem("�����㷨");
		JMenuItem mi4 = new JMenuItem("�����㷨");

		popupMenuForAlgorithmTree.add(mi1);
		popupMenuForAlgorithmTree.add(mi2);
		popupMenuForAlgorithmTree.add(mi3);
		popupMenuForAlgorithmTree.add(mi4);
		
		mi1.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {
					JOptionPane.showMessageDialog(AlgorithmComparePanel.this, "�޷����㷨�ϴ�����", "�����㷨��ʧ��", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					String groupName = JOptionPane.showInputDialog(AlgorithmComparePanel.this, "�����������㷨�������");
					if(groupName==null||groupName.equals("")) {
						//JOptionPane.showMessageDialog(AlgorithmPanel.this, "������㷨������Ϊ��", "�����㷨��ʧ��", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					File path = new File(tree.caculateDirSelected(treePathSelected));
					File file = new File(path, groupName);
					try {
						file.mkdir();
						tree.repaint();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(AlgorithmComparePanel.this, "�޷������㷨��", "�����㷨��ʧ��", JOptionPane.INFORMATION_MESSAGE);
					}
				}				
			}
		});

		mi2.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {
					int option = JOptionPane.showConfirmDialog(AlgorithmComparePanel.this, "ȷ��ɾ��ѡ�е��㷨��"+lastTreeNodeSelected.toString()+"��", "ɾ���㷨", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						File file = new File(tree.caculateDirSelected(treePathSelected));
						try {
							FileTools.deleteDir(file);
							((DefaultMutableTreeNode)lastTreeNodeSelected.getParent()).remove(lastTreeNodeSelected);
							tree.repaint();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(AlgorithmComparePanel.this, "�޷�����ɾ��ѡ�е��㷨��"+lastTreeNodeSelected.toString(), "ɾ���㷨ʧ��", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						return;
					}
				}
				else {
					int option = JOptionPane.showConfirmDialog(AlgorithmComparePanel.this, "ȷ��ɾ��ѡ�е��㷨�飺"+lastTreeNodeSelected.toString()+"��", "ɾ���㷨��", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						File file = new File(tree.caculateDirSelected(treePathSelected));
						try {
							FileTools.deleteDir(file);
							((DefaultMutableTreeNode)lastTreeNodeSelected.getParent()).remove(lastTreeNodeSelected);
							tree.repaint();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(AlgorithmComparePanel.this, "�޷�ɾ��ѡ�е��㷨�飺"+lastTreeNodeSelected.toString(), "ɾ���㷨��ʧ��", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						return;
					}
				}				
			}
		});
		
		mi3.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				File path = new File(tree.caculateDirSelected(treePathSelected));
				File algConfigFile;
				AlgorithmProperty algorithmProperty;
				ThirdPartyAlgorithmPackage thirdPartyAlgorithmPackage;
				fileChooser.setApproveButtonText("���㷨�����ļ�");
				fileChooser.setDialogTitle("�����㷨");
				int result = fileChooser.showOpenDialog(AlgorithmComparePanel.this);
				if(result==JFileChooser.APPROVE_OPTION) {
					try {
						algConfigFile = fileChooser.getSelectedFile();
						thirdPartyAlgorithmPackage = new ThirdPartyAlgorithmPackage(algConfigFile);
						thirdPartyAlgorithmPackage.copyToPath(path);
						if(thirdPartyAlgorithmPackage.isUserAlgorithm())
							thirdPartyAlgorithmPackage.copyClassesToPath(new File(userAlgorithmClassPath));
						JOptionPane.showMessageDialog(AlgorithmComparePanel.this, "��Ч���㷨�����ļ�", "�����㷨�ɹ�", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(AlgorithmComparePanel.this, "��Ч���㷨", "�����㷨ʧ��", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			}
		});

		mi4.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = tree.getPathForLocation(popupMenuForGroupX, popupMenuForGroupY);
				if(treePathSelected==null) return;
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected.isLeaf()) {
					if(algorithmModel.getAlgorithm()!=null) {
						try {
							algorithmModel.runAlgorithm();
							AnimatorPanel animatorPanel = new AnimatorPanel(algorithmModel.getAlgorithm());
							tabbedPane.addTab(selectedAlgorithmProperty.getName(), animatorPanel);
							tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});

	}
		
	public void showSelectedAlgorithm() {
		String algorithmClassFullName = selectedAlgorithmProperty.getClassFullName();
		int index;
		
		
		if(algorithmClassFullName.equals(curAlgorithmClassName)) {
			return;
		}
		else {		
			int tabCount = tabbedPane.getTabCount();
			for(int i=tabCount-1;i>0;i--) {
				this.tabbedPane.remove(i);
			}
			curAlgorithmClassName = algorithmClassFullName;
			try {
				AlgorithmInputOutputPanel algorithmInputOutputPanel = new AlgorithmInputOutputPanel();
				algorithmModel = new AlgorithmModel(selectedAlgorithmProperty);
				algorithmInputOutputPanel.setAlgorithmModel(algorithmModel);
				tabbedPane.addTab("�㷨�������", algorithmInputOutputPanel);
				index =  tabbedPane.getTabCount()-1;
				tabbedPane.setSelectedIndex(index);
				
				NetworkShowPanel networkShowPanel = new NetworkShowPanel();
				networkShowPanel.setNetwork(algorithmModel.getNetwork());
				algorithmModel.setNetworkShowPanel(networkShowPanel);
				tabbedPane.addTab("�㷨���е�����", networkShowPanel);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateSplitPane() {
		splitPane.setDividerLocation(DIVIDER_LOCATION_RATE);
	}
}
