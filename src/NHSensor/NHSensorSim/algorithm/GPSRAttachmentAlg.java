package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.routing.gpsr.GraphType;

public class GPSRAttachmentAlg extends Algorithm {
	public final static String NAME = "GPSRAttachmentAlg";

	int graphType = GraphType.RNG;

	public int getGraphType() {
		return graphType;
	}

	public void setGraphType(int graphType) {
		this.graphType = graphType;
	}

	public GPSRAttachmentAlg(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public GPSRAttachmentAlg() {

	}

	public GPSRAttachmentAlg(QueryBase query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public GPSRAttachmentAlg(QueryBase query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, GPSRAttachmentAlg.NAME,
				statistics);
		// TODO Auto-generated constructor stub
	}

	public void initAttachment() {
		// TODO Auto-generated method stub
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		GPSRAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new GPSRAttachment(node, this, this.getParam()
					.getINIT_ENERGY(), this.getParam().getRADIO_RANGE());
			na.setGraphType(this.getGraphType());
			node.attach(this.getName(), na);
		}
		this.initNeighbors();
	}

	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		GPSRAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (GPSRAttachment) node.getAttachment(this.getName());
			// NOTE initial neighbors and lives
			na.initNeighbors();
		}
	}

	public boolean run() {
		// TODO Auto-generated method stub
		return true;
	}
}
