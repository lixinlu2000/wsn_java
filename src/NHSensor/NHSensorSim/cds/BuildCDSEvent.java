package NHSensor.NHSensorSim.cds;

import java.util.LinkedList;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class BuildCDSEvent extends TreeEvent {

	public BuildCDSEvent(CDSAttachment root, Rect rect, Message colorMessage,
			Algorithm alg) {
		super(root, rect, colorMessage, alg);
	}

	public CDSAttachment getCDSRoot() {
		return (CDSAttachment) root;
	}

	/*
	 * return new gray nodes
	 */
	protected Vector blackNodeBroadcast(CDSAttachment na)
			throws SensornetBaseException {
		BroadcastEvent be = new BroadcastEvent(na, null, this.getMessage(),
				this.getAlg());
		this.getAlg().getSimulator().handle(be);

		Vector neighbors = na.getNeighbors();
		Vector newGrayNodes = new Vector();
		CDSAttachment neighbor;
		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (CDSAttachment) neighbors.elementAt(i);
			if (neighbor.isWhiteNode()) {
				neighbor.setGray();
				newGrayNodes.add(neighbor);
			}
		}
		return newGrayNodes;
	}

	/*
	 * return new white nodes
	 */
	protected Vector grayNodeBroadcast(CDSAttachment na)
			throws SensornetBaseException {
		BroadcastEvent be = new BroadcastEvent(na, null, this.getMessage(),
				this.getAlg());
		this.getAlg().getSimulator().handle(be);

		Vector neighbors = na.getNeighbors();
		Vector newWhiteNodes = new Vector();
		CDSAttachment neighbor;
		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (CDSAttachment) neighbors.elementAt(i);
			if (neighbor.isWhiteNode()) {
				newWhiteNodes.add(neighbor);
			}
		}
		return newWhiteNodes;
	}

	protected Vector caculateNewBlackNodes(Vector whiteNodesCheckingBlack) {
		CDSAttachment na;
		Vector newBlackNodes = new Vector();
		for (int i = 0; i < whiteNodesCheckingBlack.size(); i++) {
			na = (CDSAttachment) whiteNodesCheckingBlack.elementAt(i);
			if (na.shouldBeBlack())
				newBlackNodes.add(na);
		}
		return newBlackNodes;
	}

	public void handle() throws SensornetBaseException {
		CDSAttachment newBlackNode, whiteNode;
		Vector newGrayNodes, newWhiteNodes;
		LinkedList newBlackNodes = new LinkedList();

		Vector whiteNodesCheckingBlack = new Vector();
		this.getCDSRoot().setBlack();
		newBlackNodes.add(this.getCDSRoot());

		do {
			while (newBlackNodes.size() > 0) {
				newBlackNode = (CDSAttachment) newBlackNodes.poll();
				newGrayNodes = blackNodeBroadcast(newBlackNode);
				whiteNodesCheckingBlack.removeAll(newGrayNodes);
				CDSAttachment newGrayNode;
				for (int i = 0; i < newGrayNodes.size(); i++) {
					newGrayNode = (CDSAttachment) newGrayNodes.elementAt(i);
					newWhiteNodes = grayNodeBroadcast(newGrayNode);
					whiteNodesCheckingBlack.addAll(newWhiteNodes);
				}
			}

			for (int i = 0; i < whiteNodesCheckingBlack.size(); i++) {
				whiteNode = (CDSAttachment) whiteNodesCheckingBlack
						.elementAt(i);
				if (whiteNode.shouldBeBlack()) {
					newBlackNodes.add(whiteNode);
				}
			}

			whiteNodesCheckingBlack.removeAll(newBlackNodes);
			for (int i = 0; i < newBlackNodes.size(); i++) {
				newBlackNode = (CDSAttachment) newBlackNodes.get(i);
				newBlackNode.setBlack();
			}
		} while (newBlackNodes.size() > 0);

	}
}
