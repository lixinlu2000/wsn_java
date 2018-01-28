package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;

public class BuildTreeAlgorithm extends AbstractTreeAlg {

	public BuildTreeAlgorithm(Query query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public BuildTreeAlgorithm(Query query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public BuildTreeAlgorithm(Query query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, statistics);
		// TODO Auto-generated constructor stub
	}

	public BuildTreeAlgorithm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean run() {
		//获得网络中ID为0的节点
		Node node0 = this.getNetwork().getNode(0);
		NodeAttachment cur = (NodeAttachment) node0
				.getAttachment(this.getName());
		
		//获得建树的范围
		Rect rect = this.getSpatialQuery().getRect();
		
		//构造大小为100字节的消息
		Message message = new Message(100,null);

		//构造以0号节点为起始节点，建树范围为rect的建树事件，该建树事件在建树过程中发送的消息大小为100
		BuildTreeEvent bte = new BuildTreeEvent(cur, rect, message, this);

		//运行建树事件bte，进行建树操作
		try {
			this.getSimulator().handle(bte);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
