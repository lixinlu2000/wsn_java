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
		//���������IDΪ0�Ľڵ�
		Node node0 = this.getNetwork().getNode(0);
		NodeAttachment cur = (NodeAttachment) node0
				.getAttachment(this.getName());
		
		//��ý����ķ�Χ
		Rect rect = this.getSpatialQuery().getRect();
		
		//�����СΪ100�ֽڵ���Ϣ
		Message message = new Message(100,null);

		//������0�Žڵ�Ϊ��ʼ�ڵ㣬������ΧΪrect�Ľ����¼����ý����¼��ڽ��������з��͵���Ϣ��СΪ100
		BuildTreeEvent bte = new BuildTreeEvent(cur, rect, message, this);

		//���н����¼�bte�����н�������
		try {
			this.getSimulator().handle(bte);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
