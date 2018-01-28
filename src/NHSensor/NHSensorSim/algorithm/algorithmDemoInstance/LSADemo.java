package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.LSAAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;

public class LSADemo extends AlgorithmDemo {

	public LSADemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 600, 400, 300, 0.64);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(LSAAlg.NAME);
		LSAAlg lsa = (LSAAlg) sensorSim.getAlgorithm(LSAAlg.NAME);
		lsa.getParam().setANSWER_SIZE(30);
		lsa.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		Network network = lsa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));

		double gridWidth = Math.sqrt(3) / 2 * lsa.getParam().getRADIO_RANGE();
		lsa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		//
		return lsa;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.64);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(LSAAlg.NAME);
		LSAAlg lsa = (LSAAlg) sensorSim.getAlgorithm(LSAAlg.NAME);
		lsa.getParam().setANSWER_SIZE(30);
		lsa.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));

		double gridWidth = Math.sqrt(3) / 2 * lsa.getParam().getRADIO_RANGE();
		lsa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		//
		return lsa;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "LSA";
		String classFullName = "NHSensor.NHSensorSim.algorithm.LSAAlg"; // contains
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.LSADemo";
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
			File file = new File("./Algorithms/Query processing/" + name + "/config");
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
