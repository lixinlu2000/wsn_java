package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.RESACAAAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;

public class RESACAADemo extends AlgorithmDemo {

	public RESACAADemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 600, 450, 400, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RESACAAAlg.NAME);
		RESACAAAlg resa = (RESACAAAlg) sensorSim.getAlgorithm(RESACAAAlg.NAME);
		resa.getParam().setANSWER_SIZE(30);
		resa.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		Network network = resa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));
		resa.setConsiderLinkQuality(true);

		double gridWidth = Math.sqrt(3) / 2 * resa.getParam().getRADIO_RANGE();
		resa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		//
		return resa;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RESACAAAlg.NAME);
		RESACAAAlg resa = (RESACAAAlg) sensorSim.getAlgorithm(RESACAAAlg.NAME);
		resa.getParam().setANSWER_SIZE(30);
		resa.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));
		resa.setConsiderLinkQuality(true);

		double gridWidth = Math.sqrt(3) / 2 * resa.getParam().getRADIO_RANGE();
		resa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		//
		return resa;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "RESACAA";
		String classFullName = "NHSensor.NHSensorSim.algorithm.RESACAAAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.RESACAADemo";
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
