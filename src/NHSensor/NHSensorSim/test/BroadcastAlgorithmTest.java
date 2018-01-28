package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.algorithm.BroadcastAlgorithm;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.ui.Animator;

public class BroadcastAlgorithmTest {

	public BroadcastAlgorithmTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		//�����ȣ���λΪ��
		int width = 400;
		//����߶ȣ���λΪ��
		int height = 400;
		//����׶���Ŀ����λΪ��
		int nodeNum = 400;
		
		//����һ��������Ϊ400�ף��߶�Ϊ400�ף��ڵ���ĿΪ400��������ȷֲ������磬�����Ϊ1
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				1);

		//�½�ģ����
		Simulator simulator = new Simulator();
		//�½�������
		Param param = new Param();
		simulator.addHandleAndTraceEventListener();
		//�½��㷨���ͳ����
		Statistics statistics = new Statistics("BroadcastAlgorithm");

		//���㷨�����е����硢ģ�������������㷨�����㷨���ͳ��Ϊ����������һ���㷨ʵ��
		BroadcastAlgorithm alg = new BroadcastAlgorithm(null, network,
				simulator, param, "BroadcastAlgorithm", statistics);
		
		//���㷨���г�ʼ��
		alg.init();
		//�����㷨
		alg.run();
		
		//Animator�����㷨��ʾ�࣬���㷨ʵ��Ϊ��������һ���㷨��ʾ��
		Animator animator = new Animator(alg);
		//��ʼ��ʾ�㷨
		animator.start();
	}

}
