package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.BuildTreeAlgorithm;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.ui.Animator;

public class BuildTreeAlgorithmTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");
		//�����ȣ���λΪ��
		int width = 400;
		//����߶ȣ���λΪ��
		int height = 400;
		//����׶���Ŀ����λΪ��
		int nodeNum = 400;
		
		//����һ��������Ϊ400�ף��߶�Ϊ400�ף��ڵ���ĿΪ400��������ȷֲ������磬�����Ϊ1
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				1);
		
		//����һ����ѯ�࣬��������������ֱ��ǽ����ĸ��ڵ���Ϣ��������������ķ�Χ
		Query query = new Query(network.getNode(0), network.getRect());


		Simulator simulator = new Simulator();
		Param param = new Param();
		simulator.addHandleAndTraceEventListener();
		Statistics statistics = new Statistics("BroadcastAlgorithm");

		BuildTreeAlgorithm alg = new BuildTreeAlgorithm(query, network,
				simulator, param, "BuildTreeAlgorithm", statistics);
		

		// double subQueryRegionWidth =
		// Math.sqrt(3)/2*csa.getParam().getRADIO_RANGE();
		alg.init();
		alg.run();
		
		Animator animator = new Animator(alg);
		animator.start();
	}

}
