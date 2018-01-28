package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.FullFloodAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;

public class FullFloodDemo extends AlgorithmDemo {

	public FullFloodDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 600, 400, 250, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(FullFloodAlg.NAME);
		FullFloodAlg alg = (FullFloodAlg) sensorSim
				.getAlgorithm(FullFloodAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		sensorSim.run();
		return alg;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(FullFloodAlg.NAME);
		FullFloodAlg alg = (FullFloodAlg) sensorSim
				.getAlgorithm(FullFloodAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		sensorSim.run();
		return alg;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "FullFlood";
		String classFullName = "NHSensor.NHSensorSim.algorithm.FullFlood"; // contains
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.FullFloodDemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "NHSensor.NHSensorSim.papers.E2STA.FullFloodEnergyExperiment";
		String experimentParamClassFullName ="NHSensor.NHSensorSim.papers.E2STA.AllParam";

		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo) Class.forName(
					demoClassFullName).newInstance();
			name = algorithmDemo.getRunnedAlgorithmInstance().getName();

			AlgorithmProperty ap = new AlgorithmProperty(name, description,
					classFullName, demoClassFullName, isSystemAlgorithm,
					experimentClassFullName, experimentParamClassFullName);
			File file = new File("./Algorithms/Data collection/" + name + "/config");
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
