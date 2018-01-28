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

		//网络宽度，单位为米
		int width = 400;
		//网络高度，单位为米
		int height = 400;
		//网络阶段数目，单位为个
		int nodeNum = 500;
		
		//生成一个网络宽度为400米，高度为400米，节点数目为500的随机均匀分布的网络，随机数为1
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				1);

		//新建模拟器
		Simulator simulator = new Simulator();
		simulator.addHandleAndTraceEventListener();
		//新建参数类
		Param param = new Param();
		//新建算法结果统计类
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
