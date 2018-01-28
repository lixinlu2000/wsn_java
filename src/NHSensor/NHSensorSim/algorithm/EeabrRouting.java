package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;

public class EeabrRouting extends GPSRAlg {

	public EeabrRouting(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public EeabrRouting() {
		// TODO Auto-generated constructor stub
	}

	public EeabrRouting(QueryBase query, Network network, Simulator simulator, Param param, String name,
			Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public EeabrRouting(QueryBase query, Network network, Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, statistics);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		
		Node node0 = this.getNetwork().getNode(0);
		NodeAttachment cur = (NodeAttachment) node0.getAttachment(this.getName());
		
		Node node1 = this.getNetwork().getNode(1);
		NodeAttachment ngb = (NodeAttachment)node1.getAttachment(this.getName());
		
		Message message = new Message(100,null);
		
		GreedyForwardToPointEvent event = new GreedyForwardToPointEvent(cur,node1.getPos(),message,this);
		
		try {
			this.getSimulator().handle(event);
		} catch (SensornetBaseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return true;
	}

}
