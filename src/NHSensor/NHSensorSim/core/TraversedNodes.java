package NHSensor.NHSensorSim.core;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * query result nodes
 */
public class TraversedNodes {
	private Vector nodes;
	private Vector rates;

	public TraversedNodes(Vector nodes, Vector rates) {
		super();
		this.nodes = nodes;
	}

	public TraversedNodes() {
		nodes = new Vector();
		rates = new Vector();
	}

	public Vector getNodes() {
		return nodes;
	}

	public void setNodes(Vector nodes) {
		this.nodes = nodes;
	}

	public boolean add(Node node) {
		return nodes.add(node);
	}

	public boolean addAttachment(Attachment attachment) {
		if (!nodes.contains(attachment.getNode())) {
			rates.add(new Double(1));
			return nodes.add(attachment.getNode());
		} else
			return false;
	}

	public boolean addAttachment(Attachment attachment, double rate) {
		if (!nodes.contains(attachment.getNode())) {
			rates.add(new Double(rate));
			return nodes.add(attachment.getNode());
		} else
			return false;
	}

	public static TraversedNodes getTraversedNodes(Network network, Shape shape) {
		Vector networkNodes = network.getNodes();
		Vector nodes = new Vector();
		Vector rates = new Vector();
		Node node;

		for (int i = 0; i < networkNodes.size(); i++) {
			node = (Node) networkNodes.elementAt(i);
			if (shape.contains(node.getPos())) {
				nodes.add(node);
				rates.add(new Double(1));
			}
		}
		return new TraversedNodes(nodes, rates);
	}

	public static TraversedNodes getTraversedNodes(Network network, Query query) {
		return TraversedNodes.getTraversedNodes(network, query.getRect());
	}

	public boolean equals(TraversedNodes tn1) {
		if (this == tn1)
			return true;

		Vector nodes1 = new Vector();
		nodes1.addAll(tn1.getNodes());
		Vector nodes0 = new Vector();
		nodes0.addAll(this.getNodes());

		NodeIDComparator c = new NodeIDComparator();
		Collections.sort(nodes0, c);
		Collections.sort(nodes1, c);

		if (nodes0.size() != nodes1.size())
			return false;

		for (int i = 0; i < nodes1.size(); i++) {
			if (c.compare(nodes0.elementAt(i), nodes1.elementAt(i)) != 0)
				return false;
		}
		return true;
	}

	public static boolean equals(TraversedNodes tn1, TraversedNodes tn2) {
		return tn1.equals(tn2);
	}

	public QueryResultCorrectness compare(TraversedNodes real) {
		// traversed node which has query result
		double correctResultSize = 0;
		// not traversed node which has query result
		double lossResultSize = 0;

		if (this == real)
			return new QueryResultCorrectness(real.getRealResultSize(), 0, real
					.getRealResultSize());

		Vector nodes1 = new Vector();
		nodes1.addAll(real.getNodes());
		Vector nodes0 = new Vector();
		nodes0.addAll(this.getNodes());

		NodeIDComparator c = new NodeIDComparator();
		Collections.sort(nodes0, c);
		Collections.sort(nodes1, c);

		int i1 = 0;
		int i0 = 0;
		while (i1 < nodes1.size()) {
			if (i0 >= nodes0.size()) {
				lossResultSize += nodes1.size() - i1;
				break;
			} else if (c.compare(nodes0.elementAt(i0), nodes1.elementAt(i1)) == 0) {
				correctResultSize += ((Double) this.rates.elementAt(this.nodes
						.indexOf(nodes0.elementAt(i0)))).doubleValue();
				i0++;
				i1++;
			} else if (c.compare(nodes0.elementAt(i0), nodes1.elementAt(i1)) < 0) {
				i0++;
			} else {
				i1++;
				lossResultSize++;
			}
		}
		return new QueryResultCorrectness(correctResultSize, lossResultSize,
				real.getRealResultSize());
	}

	public Vector caculateLostNodes(TraversedNodes real) {
		Vector lostNodes = new Vector();
		Vector nodes1 = new Vector();
		nodes1.addAll(real.getNodes());
		Vector nodes0 = new Vector();
		nodes0.addAll(this.getNodes());

		NodeIDComparator c = new NodeIDComparator();
		Collections.sort(nodes0, c);
		Collections.sort(nodes1, c);

		int i1 = 0;
		int i0 = 0;
		while (i1 < nodes1.size()) {
			if (i0 >= nodes0.size()) {
				for (int i = i1; i < nodes1.size(); i++) {
					lostNodes.add(nodes1.elementAt(i));
				}
				break;
			} else if (c.compare(nodes0.elementAt(i0), nodes1.elementAt(i1)) == 0) {
				i0++;
				i1++;
			} else if (c.compare(nodes0.elementAt(i0), nodes1.elementAt(i1)) < 0) {
				i0++;
			} else {
				lostNodes.add(nodes1.elementAt(i1));
				i1++;
			}
		}
		return lostNodes;
	}

	public int getRealResultSize() {
		return this.getNodes().size();
	}
}
