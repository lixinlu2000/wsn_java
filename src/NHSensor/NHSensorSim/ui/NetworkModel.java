package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.shape.Rect;

public class NetworkModel {
	private Vector networkViews = new Vector();
	private Network network = null;
	private String description = "";
	private double networkCanvasSizeScale = 0;
	private Vector selectedNodes = new Vector();


	public NetworkModel(Network network) {
		this.network = network;
	}

	public Vector getSelectedNodes() {
		return selectedNodes;
	}
	
	public Node getSelectedNode() {
		return (Node)selectedNodes.elementAt(0);
	}

	public void repaintCanvases() {
		for (int i = 0; i < this.networkViews.size(); i++) {
			NetworkView networkView = (NetworkView) this.networkViews
					.elementAt(i);
			networkView.updateNetworkView();
		}
	}

	public Vector getNodes() {
		return network.getNodes();
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
		this.repaintCanvases();
	}
	
	public void addNode(int x, int y) {
		this.network.addNode(x, y);
		this.repaintCanvases();
	}
	
	public void deleteSelectedNodes() {
		this.network.deleteNodes(this.getSelectedNodes());
		this.clearSelectedNodes();
		this.repaintCanvases();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Vector getNetworkViews() {
		return networkViews;
	}

	public void addNetworkView(NetworkView networkView) {
		this.getNetworkViews().add(networkView);
	}
	
	public void setNetworkCanvasSizeScale(double scale) {
		this.networkCanvasSizeScale = scale;
		for (int i = 0; i < this.networkViews.size(); i++) {
			NetworkView networkView = (NetworkView) this.networkViews
					.elementAt(i);
			networkView.setNetworkViewScale(Math.pow(2, scale));
		}
	}
	
	public void setNodeRadioRange(Node node, double radioRange) {
		this.getNetwork().setNodeRadioRange(node, radioRange);
		this.repaintCanvases();
	}
	
	public void setRadioRange(double radioRange) {
		this.getNetwork().setRadioRange(radioRange);
		this.repaintCanvases();
	}

	
	public void setSelectedNodes(Vector selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	
	public void clearSelectedNodes() {
		this.selectedNodes.clear();
	}

	public void setSelectedNodes(int x, int y, int w, int h) {
		Rect rect = new Rect(x-w/2,x+w/2,y-h/2,y+h/2);
		selectedNodes = this.network.getNodesInRect(rect);
		this.repaintCanvases();
	}

	public double getNetworkCanvasSizeScale() {
		return networkCanvasSizeScale;
	}	
}
