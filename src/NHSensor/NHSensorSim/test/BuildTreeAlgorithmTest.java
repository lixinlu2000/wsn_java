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
		//网络宽度，单位为米
		int width = 400;
		//网络高度，单位为米
		int height = 400;
		//网络阶段数目，单位为个
		int nodeNum = 400;
		
		//生成一个网络宽度为400米，高度为400米，节点数目为400的随机均匀分布的网络，随机数为1
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				1);
		
		//生成一个查询类，该类的两个参数分别是建树的根节点信息，和所构造的树的范围
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
