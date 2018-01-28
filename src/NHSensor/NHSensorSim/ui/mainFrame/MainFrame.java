package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.experiment.ExperimentConfig;
import NHSensor.NHSensorSim.tools.LicenseTool;
import NHSensor.NHSensorSim.tools.UTool;

import com.verhas.licensor.License;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private AlgorithmPanel algorithmPanel = new AlgorithmPanel();
	private NetworkPanel networkPanel = new NetworkPanel();
	private ExperimentPanel experimentPanel = new ExperimentPanel();
	private AlgorithmComparePanel algorithmComparePanel = new AlgorithmComparePanel();
	private JFrame frame = this;
	private JPanel panel = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	private final String NETWORK = "network";
	private final String ALGORITHM = "algorithm";
	private final String EXPERIMENT = "experiment";
	private final String ALGORITHMCOMPARE = "algorithmCompare";
	private final static int FONTSIZE = 14;
	private JButton btnNetwork;
	private JButton btnAlgorithm;
	private JButton btnExperiment;
	private JButton btnAlgorithmCompare;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(new SubstanceMistSilverLookAndFeel());
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
//					UIManager
//					.setLookAndFeel(new SubstanceMistSilverLookAndFeel());
//			JFrame.setDefaultLookAndFeelDecorated(true);
//			JDialog.setDefaultLookAndFeelDecorated(true);
//			SubstanceLookAndFeel
//					.setCurrentTheme(new SubstanceTerracottaTheme());
					// SubstanceLookAndFeel.setSkin(new EmeraldDuskSkin());
					// SubstanceLookAndFeel.setCurrentButtonShaper(new
					// ClassicButtonShaper());
					// SubstanceLookAndFeel.setCurrentWatermark(new
					// SubstanceBubblesWatermark());
					// SubstanceLookAndFeel.setCurrentBorderPainter(new
					// StandardBorderPainter());
					// SubstanceLookAndFeel.setCurrentGradientPainter(new
					// StandardGradientPainter());
					// SubstanceLookAndFeel.setCurrentTitlePainter(new
					// FlatTitePainter());

					Font font = new Font("Dialog", Font.PLAIN, FONTSIZE);
					java.util.Enumeration keys = UIManager.getDefaults().keys();
					while (keys.hasMoreElements()) {
						Object key = keys.nextElement();
						Object value = UIManager.get(key);
						if (value instanceof javax.swing.plaf.FontUIResource) {
							UIManager.put(key, font);
						}
					}

					LicenseTool licenseTool = LicenseTool.getLicenseToolInstance();
					if(!(licenseTool.checkDateAndVersionValidity())) {
						JOptionPane.showMessageDialog(null, "试用期已过，请购买正式版", "Atos-SensorSim", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					
					MainFrame frame = new MainFrame();
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();
					frame.setLocation(0, 0);
					frame.setSize(screenSize.width, screenSize.height);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
					frame.getNetworkPanel().updateSplitPane();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		UTool.checkKey();
		
		setTitle("NHSensorSim");
		Toolkit tool = this.getToolkit(); 
		this.setIconImage(tool.getImage("images/splash.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNetwork = new JMenu("    \u7F51\u7EDC    ");
		menuBar.add(mnNetwork);

		JMenuItem mntmOpen = new JMenuItem("\u6253\u5F00");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NetworkOpenDialog dialog = new NetworkOpenDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModal(true);
				dialog.setVisible(true);
				MainFrame.this.showNetworkPanel();
				MainFrame.this.getNetworkPanel().getNetworkModel().setNetwork(dialog.getNetwork());
				MainFrame.this.getNetworkPanel().getTree().setSelectionRow(4);
			}
		});
		mnNetwork.add(mntmOpen);

		JMenuItem mntmSaveAs = new JMenuItem("\u4FDD\u5B58\u4E3A");

		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Network network = networkPanel.getNetworkModel().getNetwork();
				if (network == null || network.getNodeNum() == 0) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"网络为空，无法保存,请先生成网络再保存");
					return;
				}
				NetworkSaveDialog dialog = new NetworkSaveDialog(network);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModal(true);
				dialog.show(true);
			}
		});
		mnNetwork.add(mntmSaveAs);

		JSeparator separator = new JSeparator();
		mnNetwork.add(separator);
		
		JMenuItem mntmRunAlgorithm = new JMenuItem("\u8FD0\u884C\u7B97\u6CD5");
		mntmRunAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Network network = networkPanel.getNetworkModel().getNetwork();
				if(network==null||network.getNodeNum()==0) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"网络无效，无法运行算法，请先选择网络再运行算法");
					return;					
				}
				AlgorithmChooserDialog acd = new AlgorithmChooserDialog();
				acd.setModal(true);
				acd.setVisible(true);
				if(acd.getSelectedAlgorithmProperty()!=null) {
					ExperimentProperty experimentProperty = new ExperimentProperty(networkPanel.getNetworkModel().getNetwork(), acd.getSelectedAlgorithmProperty(), new ExperimentConfig());
					MainFrame.this.getExperimentPanel().setSelectedExperimentProperty(experimentProperty);
					MainFrame.this.getExperimentPanel().showTempExperiment(experimentProperty);
					MainFrame.this.showExperimentPanel();
				}

			}
		});
		mnNetwork.add(mntmRunAlgorithm);

		JMenu mnExperiment = new JMenu("    \u5B9E\u9A8C    ");
		menuBar.add(mnExperiment);

		JMenuItem mntmOpen_1 = new JMenuItem("\u6253\u5F00");
		mnExperiment.add(mntmOpen_1);

		JMenuItem mntmSaveAs_1 = new JMenuItem("\u4FDD\u5B58\u4E3A");
		mntmSaveAs_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExperimentProperty experimentProperty = experimentPanel.getSelectedExperimentProperty();
				if (experimentProperty == null) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"实验为空，无法保存");
					return;
				}
				ExperimentSaveDialog dialog = new ExperimentSaveDialog(experimentProperty);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModal(true);
				dialog.setVisible(true);
			}
		});
		mnExperiment.add(mntmSaveAs_1);

		JSeparator separator_1 = new JSeparator();
		mnExperiment.add(separator_1);

		JMenuItem mntmRun = new JMenuItem("\u8FD0\u884C");
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath treePathSelected = experimentPanel.getTreePathSelected();
				if(treePathSelected==null) {
					JOptionPane.showMessageDialog(MainFrame.this, "未选择将运行的实验", "运行试验失败", JOptionPane.INFORMATION_MESSAGE);
					return;				
				}
				DefaultMutableTreeNode lastTreeNodeSelected = (DefaultMutableTreeNode)treePathSelected.getLastPathComponent();
				if(lastTreeNodeSelected==null||!lastTreeNodeSelected.isLeaf()) {
					JOptionPane.showMessageDialog(MainFrame.this, "未选择将运行的实验", "运行试验失败", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				experimentPanel.openExperiment(experimentPanel.getTreePathSelected());
			}
		});
		mnExperiment.add(mntmRun);

		JMenu mnHelp = new JMenu("    \u5E2E\u52A9    ");
		menuBar.add(mnHelp);
		
		JMenuItem menuItem = new JMenuItem("\u5173\u4E8E");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog ad = new AboutDialog();
				ad.setModal(true);
				ad.setVisible(true);
			}
		});
		mnHelp.add(menuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		btnNetwork = new JButton("\u7F51\u7EDC");
		btnNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNetworkPanel();
			}
		});
		toolBar.add(btnNetwork);

		btnAlgorithm = new JButton("\u7B97\u6CD5");
		btnAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAlgorithmPanel();
			}
		});
		toolBar.add(btnAlgorithm);

		btnExperiment = new JButton("\u5B9E\u9A8C");
		btnExperiment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showExperimentPanel();
			}
		});
		toolBar.add(btnExperiment);
		
		btnAlgorithmCompare = new JButton("\u7B97\u6CD5\u9A8C\u8BC1");
		btnAlgorithmCompare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAlgorithmComparePanel();
			}
		});
		toolBar.add(btnAlgorithmCompare);

		panel.setLayout(cardLayout);
		this.networkPanel.setMainFrame(this);
		panel.add(this.NETWORK, this.networkPanel);
		panel.add(this.ALGORITHM, this.algorithmPanel);
		panel.add(this.EXPERIMENT, this.experimentPanel);
		panel.add(this.ALGORITHMCOMPARE, this.algorithmComparePanel);
		contentPane.add(panel, BorderLayout.CENTER);
	}

	public NetworkPanel getNetworkPanel() {
		return networkPanel;
	}

	public ExperimentPanel getExperimentPanel() {
		return experimentPanel;
	}
	
	public void showExperimentPanel() {
		cardLayout.show(panel, EXPERIMENT);
		experimentPanel.updateSplitPane();
		btnExperiment.setSelected(true);
		btnNetwork.setSelected(false);
		btnAlgorithm.setSelected(false);
		btnAlgorithmCompare.setSelected(false);
		
	}
	
	public void showNetworkPanel() {
		cardLayout.show(panel, NETWORK);
		networkPanel.updateSplitPane();
		btnExperiment.setSelected(false);
		btnNetwork.setSelected(true);
		btnAlgorithm.setSelected(false);
		btnAlgorithmCompare.setSelected(false);
	}
	
	public void showAlgorithmPanel() {
		cardLayout.show(panel, ALGORITHM);
		algorithmPanel.updateSplitPane();
		btnExperiment.setSelected(false);
		btnNetwork.setSelected(false);
		btnAlgorithm.setSelected(true);
		btnAlgorithmCompare.setSelected(false);
	}
	
	
	public void showAlgorithmComparePanel() {
		cardLayout.show(panel, ALGORITHMCOMPARE);
		algorithmComparePanel.updateSplitPane();
		btnExperiment.setSelected(false);
		btnNetwork.setSelected(false);
		btnAlgorithm.setSelected(false);
		btnAlgorithmCompare.setSelected(true);
	}

}
