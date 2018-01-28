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
		//��������е�IDΪ0�Ľڵ�
		Node node0 = this.getNetwork().getNode(0);
		NodeAttachment cur = (NodeAttachment) node0
				.getAttachment(this.getName());
		int id = (Integer)(node0.getNeighborIDs().elementAt(1));
		
		//��������е�IDΪ1�Ľڵ�
		Node node1 = this.getNetwork().getNode(id);
		NodeAttachment neighbor = (NodeAttachment) node1
				.getAttachment(this.getName());
		
		//�½�һ����Ϣ�������Ĵ�СΪ100���ֽ�
		Message message = new Message(100, null);
		
		//�½�һ��node0�㲥message�Ĺ㲥�¼���
		BroadcastEvent event = new BroadcastEvent(cur,neighbor, message, this);
		
		try {
			//�����㷨����ģ�鴦����¼�
			this.getSimulator().handle(event);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�㷨��������
		return true;		
	}

}
