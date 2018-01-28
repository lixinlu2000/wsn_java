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

public class SimpleRoutingAlgorithm extends GPSRAlg {

	public SimpleRoutingAlgorithm(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public SimpleRoutingAlgorithm() {
		// TODO Auto-generated constructor stub
	}

	public SimpleRoutingAlgorithm(QueryBase query, Network network,
			Simulator simulator, Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public SimpleRoutingAlgorithm(QueryBase query, Network network,
			Simulator simulator, Param param, Statistics statistics) {
		super(query, network, simulator, param, statistics);
		// TODO Auto-generated constructor stub
	}

	public boolean run() {
		//��������е�IDΪ0�Ľڵ�
		Node node0 = this.getNetwork().getNode(0);
		NodeAttachment cur = (NodeAttachment) node0
				.getAttachment(this.getName());
		
		//��������е�IDΪ1�Ľڵ�
		Node node1 = this.getNetwork().getNode(1);
		
		//�½�һ����Ϣ�������Ĵ�СΪ100���ֽ�
		Message message = new Message(100, null);
		
		//�½�һ��GreedyForwardToPointEvent�¼���������Ϣ����Դ�ڵ�Ϊcur����node0������Ϣ����Ŀ�ĵ�ַ��node1���ڵ�λ��
		//���͵���Ϣ��СΪ100���ֽ�
		GreedyForwardToPointEvent event = new GreedyForwardToPointEvent(cur,node1.getPos(), message, this);
		
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
