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

public class JAlgorithmTree extends JTree {

	private  JAlgorithmTreeNode rootNode;
	private DefaultTreeModel jAlgorithmTreeModel;
	private boolean containsFile;// 显示文件或不显示文件
	private static File networkTopDir = new File("./Algorithms");
	private static String algorithmPropertyFileName = "config";
	private File file;
	

	public JAlgorithmTree() {
		this(networkTopDir, false);
	}

	public JAlgorithmTree(File file) {
		this(file, false);
	}

	public JAlgorithmTree(File file, boolean containsFile) {
		this.containsFile = containsFile;
		this.file = file;

		rootNode = new JAlgorithmTreeNode(file);
		rootNode.expand();
		jAlgorithmTreeModel = new DefaultTreeModel(rootNode);
		setModel(jAlgorithmTreeModel);
		addTreeExpansionListener(new JTreeExpansionListener());
	}
	
	public void reInit()  {
		rootNode = new JAlgorithmTreeNode(file);
		rootNode.expand();
		jAlgorithmTreeModel = new DefaultTreeModel(rootNode);
		setModel(jAlgorithmTreeModel);	
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
	
	public String caculateAlgorithmPropertyFileSelected(TreePath treePath) {
		StringBuffer sb = new StringBuffer("./");
		Object[] paths = treePath.getPath();
		
		for(int i=0;i<paths.length;i++) {
			sb.append("/");
			sb.append(paths[i]);
		}
		sb.append("/");
		sb.append(algorithmPropertyFileName);
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
		if (o instanceof JAlgorithmTreeNode) {
			return ((JAlgorithmTreeNode) o).file.getAbsolutePath();
		}
		return null;
	}

	protected class JAlgorithmTreeNode extends DefaultMutableTreeNode {

		protected File file;
		protected boolean isDirectory;

		public JAlgorithmTreeNode(File file) {
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
					if (f.isFile()&&f.getName().equals(algorithmPropertyFileName)) {
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
					this.add(new JAlgorithmTreeNode(f));
				}
			}
			return true;
		}
	}

	protected class JTreeExpansionListener implements TreeExpansionListener {

		public void treeExpanded(TreeExpansionEvent e) {
			JAlgorithmTreeNode fileNode = (JAlgorithmTreeNode) e.getPath()
					.getLastPathComponent();
			if (fileNode != null) {

				new FileNodeExpansion(fileNode).execute();
			}
		}

		public void treeCollapsed(TreeExpansionEvent e) {
		}
	}

	protected class FileNodeExpansion extends SwingWorker<Boolean, Void> {
		private JAlgorithmTreeNode node;

		public FileNodeExpansion(JAlgorithmTreeNode node) {
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
					jAlgorithmTreeModel.reload(node);
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(JAlgorithmTree.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (ExecutionException ex) {
				Logger.getLogger(JAlgorithmTree.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}
}