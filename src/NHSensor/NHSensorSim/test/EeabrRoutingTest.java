package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.algorithm.EeabrRouting;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.ui.Animator;

public class EeabrRoutingTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//�����ȣ���λΪ��
		int width = 400;
		//����߶ȣ���λΪ��
		int height = 400;
		//����׶���Ŀ����λΪ��
		int nodeNum = 500;
		
		//����һ��������Ϊ400�ף��߶�Ϊ400�ף��ڵ���ĿΪ500��������ȷֲ������磬�����Ϊ1
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				1);

		//�½�ģ����
		Simulator simulator = new Simulator();
		simulator.addHandleAndTraceEventListener();
		//�½�������
		Param param = new Param();
		//�½��㷨���ͳ����
		Statistics statistics = new Statistics("EeabrRouting");

		//
		EeabrRouting alg = new EeabrRouting(null, network,
				simulator, param, "EeabrRouting", statistics);
		
		alg.init();
		alg.run();
		
		Animator animator = new Animator(alg);
		animator.start();
	}

}
