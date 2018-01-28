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

import NHSensor.NHSensorSim.ui.mainFrame.JAlgorithmTree.JAlgorithmTreeNode;

public class JNetworkTree extends JTree {

	private  JFileTreeNode rootNode;
	private DefaultTreeModel jFileTreeModel;
	private boolean containsFile;// 显示文件或不显示文件
	private static File networkTopDir = new File("./Networks");
	private File file;

	public JNetworkTree() {
		this(networkTopDir, true);
	}

	public JNetworkTree(File file) {
		this(file, true);
	}

	public JNetworkTree(File file, boolean containsFile) {
		this.containsFile = containsFile;
		this.file = file;
		
		rootNode = new JFileTreeNode(file);
		rootNode.expand();
		jFileTreeModel = new DefaultTreeModel(rootNode);
		setModel(jFileTreeModel);
		addTreeExpansionListener(new JTreeExpansionListener());
		this.expandRow(1);
	}

	
	public void reInit()  {
		rootNode = new JFileTreeNode(file);
		rootNode.expand();
		jFileTreeModel = new DefaultTreeModel(rootNode);
		setModel(jFileTreeModel);	
		this.expandRow(1);
	}

	public String caculateFileSelected(TreePath treePath) {
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
		if (o instanceof JFileTreeNode) {
			return ((JFileTreeNode) o).file.getAbsolutePath();
		}
		return null;
	}

	protected class JFileTreeNode extends DefaultMutableTreeNode {

		protected File file;
		protected boolean isDirectory;
		

		public JFileTreeNode(File file) {
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
				return file.list()==null||file.list().length==0;
			}
			
			return !isDirectory;
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
				if (f.isDirectory() || containsFile) {
					JFileTreeNode fileTreeNode = new JFileTreeNode(f);
					this.add(fileTreeNode);
					fileTreeNode.expand();
				}
			}
			return true;
		}
	}

	protected class JTreeExpansionListener implements TreeExpansionListener {

		public void treeExpanded(TreeExpansionEvent e) {
			JFileTreeNode fileNode = (JFileTreeNode) e.getPath()
					.getLastPathComponent();
			if (fileNode != null) {

				new FileNodeExpansion(fileNode).execute();
			}
		}

		public void treeCollapsed(TreeExpansionEvent e) {
		}
	}

	protected class FileNodeExpansion extends SwingWorker<Boolean, Void> {
		private JFileTreeNode node;

		public FileNodeExpansion(JFileTreeNode node) {
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
					jFileTreeModel.reload(node);
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(JNetworkTree.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (ExecutionException ex) {
				Logger.getLogger(JNetworkTree.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}
}