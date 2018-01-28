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
		//网络宽度，单位为米
		int width = 400;
		//网络高度，单位为米
		int height = 400;
		//网络阶段数目，单位为个
		int nodeNum = 400;
		
		//生成一个网络宽度为400米，高度为400米，节点数目为400的随机均匀分布的网络，随机数为1
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				1);

		//新建模拟器
		Simulator simulator = new Simulator();
		//新建参数类
		Param param = new Param();
		simulator.addHandleAndTraceEventListener();
		//新建算法结果统计类
		Statistics statistics = new Statistics("BroadcastAlgorithm");

		//以算法将运行的网络、模拟器、参数、算法名、算法结果统计为参数，构造一个算法实例
		BroadcastAlgorithm alg = new BroadcastAlgorithm(null, network,
				simulator, param, "BroadcastAlgorithm", statistics);
		
		//对算法进行初始化
		alg.init();
		//运行算法
		alg.run();
		
		//Animator类是算法演示类，以算法实例为参数构造一个算法演示类
		Animator animator = new Animator(alg);
		//开始演示算法
		animator.start();
	}

}
