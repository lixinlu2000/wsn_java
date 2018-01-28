package NHSensor.NHSensorSim.ui.mainFrame;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import NHSensor.NHSensorSim.ui.mainFrame.JNetworkTree.JFileTreeNode;

public class JExperimentTree extends JTree {

	private  JExperimentTreeNode rootNode;
	private DefaultTreeModel jExperimentTreeModel;
	private boolean containsFile;// 显示文件或不显示文件
	private static File networkTopDir = new File("./Experiments");
	private static String experimentPropertyFileName = "config";
	private File file;

	public JExperimentTree() {
		this(networkTopDir, false);
	}

	public JExperimentTree(File file) {
		this(file, false);
	}

	public JExperimentTree(File file, boolean containsFile) {
		this.containsFile = containsFile;
		this.file = file;

		rootNode = new JExperimentTreeNode(file);
		rootNode.expand();
		jExperimentTreeModel = new DefaultTreeModel(rootNode);
		setModel(jExperimentTreeModel);
		addTreeExpansionListener(new JTreeExpansionListener());
		this.expandRow(1);
	}
	
	public void reInit()  {
		rootNode = new JExperimentTreeNode(file);
		rootNode.expand();
		jExperimentTreeModel = new DefaultTreeModel(rootNode);
		setModel(jExperimentTreeModel);
		this.expandRow(1);
	}

	public String caculateDirSelected(TreePath treePath) {
		StringBuffer sb = new StringBuffer("./");
		Object[] paths = treePath.getPath();
		
		for(int i=0;i<paths.length;i++) {
			sb.append("/");
			sb.append(paths[i]);
		}
		return sb.toString();
	}
	
	public String caculateExperimentPropertyFileSelected(TreePath treePath) {
		StringBuffer sb = new StringBuffer("./");
		Object[] paths = treePath.getPath();
		
		for(int i=0;i<paths.length;i++) {
			sb.append("/");
			sb.append(paths[i]);
		}
		sb.append("/");
		sb.append(experimentPropertyFileName);
		return sb.toString();
	}
	
	public String caculateExperimentPropertyPathSelected(TreePath treePath) {
		StringBuffer sb = new StringBuffer("./");
		Object[] paths = treePath.getPath();
		
		for(int i=0;i<paths.length;i++) {
			sb.append("/");
			sb.append(paths[i]);
		}
		return sb.toString();
	}


	
	public String caculateFileSelected(TreePath treePath, String fileName) {
		StringBuffer sb = new StringBuffer("./");
		Object[] paths = treePath.getPath();
		
		for(int i=0;i<paths.length;i++) {
			sb.append("/");
			sb.append(paths[i]);
		}
		
		sb.append("/");
		sb.append(fileName);
		return sb.toString();
	}

	
	public String getPathName(TreePath path) {
		Object o = path.getLastPathComponent();
		if (o instanceof JExperimentTreeNode) {
			return ((JExperimentTreeNode) o).file.getAbsolutePath();
		}
		return null;
	}

	protected class JExperimentTreeNode extends DefaultMutableTreeNode {

		protected File file;
		protected boolean isDirectory;

		public JExperimentTreeNode(File file) {
			this.file = file;
			isDirectory = file.isDirectory();
			setUserObject(file);
		}

		@Override
		public boolean isLeaf() {
			if (file == null) {
				return false;
			}
			if(isDirectory) {
				File[] files = file.listFiles();
				if (files == null) {
					return false;
				}
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					/**
					 * 文件夹必添加 文件则受系统文件树model控制
					 */
					if (f.isFile()&&f.getName().equals(experimentPropertyFileName)) {
						return true;
					}
				}
				return false;
			}		
			return false;
		}
		

		@Override
		public String toString() {
				return file.getName();
		}

		@Override
		public boolean getAllowsChildren() {
			return isDirectory;
		}

		public boolean expand() {
			this.removeAllChildren();
			File[] files = file.listFiles();
			if (files == null) {
				return false;
			}
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				/**
				 * 文件夹必添加 文件则受系统文件树model控制
				 */
				if (f.isDirectory()) {
					this.add(new JExperimentTreeNode(f));
				}
			}
			return true;
		}
	}

	protected class JTreeExpansionListener implements TreeExpansionListener {

		public void treeExpanded(TreeExpansionEvent e) {
			JExperimentTreeNode fileNode = (JExperimentTreeNode) e.getPath()
					.getLastPathComponent();
			if (fileNode != null) {

				new FileNodeExpansion(fileNode).execute();
			}
		}

		public void treeCollapsed(TreeExpansionEvent e) {
		}
	}

	protected class FileNodeExpansion extends SwingWorker<Boolean, Void> {
		private JExperimentTreeNode node;

		public FileNodeExpansion(JExperimentTreeNode node) {
			this.node = node;
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			return node.expand();
		}

		@Override
		protected void done() {
			try {
				// 节点可以展开
				if (get()) {
					jExperimentTreeModel.reload(node);
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(JExperimentTree.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (ExecutionException ex) {
				Logger.getLogger(JExperimentTree.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}
}