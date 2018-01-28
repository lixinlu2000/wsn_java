package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.RestaFarAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;

public class RestaFarDemo extends AlgorithmDemo {

	public RestaFarDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RestaFarAlg.NAME);
		RestaFarAlg alg = (RestaFarAlg) sensorSim
				.getAlgorithm(RestaFarAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		alg.initSectorInRectsBySita(15);
		sensorSim.run();
		//
		return alg;
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RestaFarAlg.NAME);
		RestaFarAlg alg = (RestaFarAlg) sensorSim
				.getAlgorithm(RestaFarAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		alg.initSectorInRectsBySita(15);
		sensorSim.run();
		//
		return alg;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "RestaFar";
		String classFullName = "NHSensor.NHSensorSim.algorithm.RestaFarAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.RestaFarDemo";
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
			File file = new File("./Algorithms/Routing/" + name + "/config");
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
