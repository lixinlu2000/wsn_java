package NHSensor.NHSensorSim.link;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;

public class ModelLinkEstimator extends LinkEstimator {
	private LinkModel linkModel;
	private Network network;

	public ModelLinkEstimator(LinkModel linkModel, Network network) {
		super();
		this.linkModel = linkModel;
		this.network = network;
	}

	public static ModelLinkEstimator getLinkEstimator(Network network) {
		double pt = 0;
		double pld0 = 55;
		double d0 = 1;
		double n = 4;
		double sigma = 4;
		double pn = -105;

		double encodingRatio = 1;
		double frameLength = 50;

		LinkModel linkModel = new LinkModel(d0, n, pld0, pt, pn, sigma,
				encodingRatio, frameLength);
		return new ModelLinkEstimator(linkModel, network);
	}

	public ArrayLinkEstimator createArrayLinkEstimator(int id) {
		int nodeNum = this.network.getNodeNum();
		double[][] linkPRRs = new double[nodeNum][nodeNum];

		for (int i = 0; i < nodeNum; i++) {
			for (int j = 0; j < nodeNum; j++) {
				if (i == j) {
					linkPRRs[i][j] = 1;
				} else {
					linkPRRs[i][j] = this.getLinkPRR(id, i, j);
				}
			}
		}
		return new ArrayLinkEstimator(linkPRRs);
	}

	public LinkModel getLinkModel() {
		return linkModel;
	}

	public void setLinkModel(LinkModel linkModel) {
		this.linkModel = linkModel;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	/*
	 * nodeID1 --> nodeID2 packet reception rate
	 * 
	 * @see NHSensor.NHSensorSim.link.LinkEstimator#getLinkPRR(int, int)
	 */
	public double getLinkPRR(int nodeID1, int nodeID2) {
		// if(nodeID1<linkPRRs.length&&nodeID2<linkPRRs.length)
		Node node1 = this.network.getNode(nodeID1);
		Node node2 = this.network.getNode(nodeID2);
		double distance = node1.getPos().distance(node2.getPos());
		return linkModel.prr(0, distance);
	}

	/*
	 * nodeID1 --> nodeID2 packet reception rate
	 * 
	 * @see NHSensor.NHSensorSim.link.LinkEstimator#getLinkPRR(int, int)
	 */
	public double getLinkPRR(int id, int nodeID1, int nodeID2) {
		// if(nodeID1<linkPRRs.length&&nodeID2<linkPRRs.length)
		Node node1 = this.network.getNode(nodeID1);
		Node node2 = this.network.getNode(nodeID2);
		double distance = node1.getPos().distance(node2.getPos());
		return linkModel.prr(id, distance);
	}

}
