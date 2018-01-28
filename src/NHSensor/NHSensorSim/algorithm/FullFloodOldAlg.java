package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;

public class FullFloodOldAlg extends Algorithm {
	public final static String NAME = "FullFlood";

	public FullFloodOldAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public FullFloodOldAlg(Query query) {
		super(query);
	}

	public FullFloodOldAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, FullFloodOldAlg.NAME,
				statistics);
	}

	public FullFloodOldAlg() {
	}

	public Query getSpatialQuery() {
		return (Query) this.getQuery();
	}

	public void initAttachment() {
		Vector nodes = network.getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new NodeAttachment(node, this, param.getINIT_ENERGY(), param
					.getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		this.initNeighbors();

	}

	public void initNeighbors() {
		Vector nodes = network.getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (NodeAttachment) node.getAttachment(this.getName());
			na.initNeighbors();
		}
	}

	public void broadcast() throws SensornetBaseException {
		LinkedList path = new LinkedList();

		NodeAttachment cur = (NodeAttachment) this.getSpatialQuery().getOrig()
				.getAttachment(this.getName());

		cur.setReceivedQID(query.getId());
		cur.setHopCount(0);
		cur.setParent(null);

		path.addLast(cur);
		Vector neighbors;
		NodeAttachment neighbor;
		Event event;
		Message msg;

		while (!path.isEmpty()) {
			cur = (NodeAttachment) path.getFirst();
			path.removeFirst();
			neighbors = cur.getNeighbors();

			msg = new Message(param.getQUERY_MESSAGE_SIZE(), query);
			event = new BroadcastEvent(cur, null, msg, this);
			this.getSimulator().handle(event);

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getReceivedQID() == -1) {
					neighbor.setReceivedQID(query.getId());
					neighbor.setHopCount(cur.getHopCount() + 1);
					neighbor.setParent(cur);

					path.addLast(neighbor);
					if (neighbor.getNode().getPos().in(
							this.getSpatialQuery().getRect())) {
						ans.add(neighbor);
					}
				}
			}

		}
	}

	public void collect() throws SensornetBaseException {
		NodeAttachment cur;
		Message msg;
		Event event;

		for (Iterator it = ans.iterator(); it.hasNext();) {
			cur = (NodeAttachment) it.next();
			while (cur.getParent() != null) {
				msg = new Message(param.getANSWER_SIZE(), null);
				event = new BroadcastEvent(cur, cur.getParent(), msg, this);
				this.getSimulator().handle(event);
				cur = cur.getParent();
			}
		}
	}

	public boolean run() {
		try {
			this.broadcast();
			this.collect();
		} catch (SensornetBaseException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
