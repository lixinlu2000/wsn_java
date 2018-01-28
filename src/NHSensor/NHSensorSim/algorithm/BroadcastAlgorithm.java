package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;

public class BroadcastAlgorithm extends AbstractTreeAlg {

	public BroadcastAlgorithm(Query query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public BroadcastAlgorithm(Query query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public BroadcastAlgorithm(Query query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, statistics);
		// TODO Auto-generated constructor stub
	}

	public BroadcastAlgorithm() {
		// TODO Auto-generated constructor stub
	}

	public boolean run() {
		//获得网络中的ID为0的节点
		Node node0 = this.getNetwork().getNode(0);
		NodeAttachment cur = (NodeAttachment) node0
				.getAttachment(this.getName());
		int id = (Integer)(node0.getNeighborIDs().elementAt(1));
		
		//获得网络中的ID为1的节点
		Node node1 = this.getNetwork().getNode(id);
		NodeAttachment neighbor = (NodeAttachment) node1
				.getAttachment(this.getName());
		
		//新建一个消息包，包的大小为100个字节
		Message message = new Message(100, null);
		
		//新建一个node0广播message的广播事件，
		BroadcastEvent event = new BroadcastEvent(cur,neighbor, message, this);
		
		try {
			//调用算法仿真模块处理该事件
			this.getSimulator().handle(event);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//算法结束返回
		return true;		
	}

}
