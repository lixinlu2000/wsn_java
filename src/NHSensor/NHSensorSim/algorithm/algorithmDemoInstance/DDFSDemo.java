package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.end.CVDAlg;
import NHSensor.NHSensorSim.end.DDFSAlg;
import NHSensor.NHSensorSim.network.NetworkUtil;
import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.FullFloodEnergyExperiment;
import NHSensor.NHSensorSim.query.Query;

public class DDFSDemo extends AlgorithmDemo {

	public DDFSDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		Network network = NetworkUtil.createNetwork1();
		Node orig = network.getNode(9);
		Query query = new Query(orig, network.getRect());

		SensorSim sensorSim = SensorSim.createSensorSim(network, query);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(DDFSAlg.NAME);
		DDFSAlg alg = (DDFSAlg) sensorSim.getAlgorithm(DDFSAlg.NAME);

		sensorSim.run();
		return alg;
	}

	public Algorithm getRunnedAlgorithmInstance(Network network) {
		Node orig = network.getNode(9);
		Query query = new Query(orig, network.getRect());

		SensorSim sensorSim = SensorSim.createSensorSim(network, query);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(DDFSAlg.NAME);
		DDFSAlg alg = (DDFSAlg) sensorSim.getAlgorithm(DDFSAlg.NAME);

		sensorSim.run();
		return alg;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "基于深度优先搜索的割点检测算法";
		String classFullName = "NHSensor.NHSensorSim.end.DDFSAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.DDFSDemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "";
		String experimentParamClassFullName ="";

		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo) Class.forName(
					demoClassFullName).newInstance();
			name = algorithmDemo.getRunnedAlgorithmInstance().getName();

			AlgorithmProperty ap = new AlgorithmProperty(name, description,
					classFullName, demoClassFullName, isSystemAlgorithm,
					experimentClassFullName, experimentParamClassFullName);
			File file = new File("./Algorithms/Other/" + name + "/config");
			if (!file.getParentFile().exists()) {
				System.out.println("目标文件所在路径不存在，准备创建。。。");
				if (!file.getParentFile().mkdirs()) {
					System.out.println("创建目录文件所在的目录失败！");
					return;
				}
			}
			ap.save(file);
			System.out.println("结束！！！！！！！！！！！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
