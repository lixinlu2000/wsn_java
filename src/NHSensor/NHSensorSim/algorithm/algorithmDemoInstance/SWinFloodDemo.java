package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.SWinFloodAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;

public class SWinFloodDemo extends AlgorithmDemo {

	public SWinFloodDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 600, 400, 300,1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(SWinFloodAlg.NAME);
		SWinFloodAlg alg = (SWinFloodAlg) sensorSim
				.getAlgorithm(SWinFloodAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		// double subQueryRegionWidth =
		// Math.sqrt(3)/2*csa.getParam().getRADIO_RANGE();
		sensorSim.run();
		return alg;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network,1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(SWinFloodAlg.NAME);
		SWinFloodAlg alg = (SWinFloodAlg) sensorSim
				.getAlgorithm(SWinFloodAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		// double subQueryRegionWidth =
		// Math.sqrt(3)/2*csa.getParam().getRADIO_RANGE();
		sensorSim.run();
		return alg;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "SWinFlood";
		String classFullName = "NHSensor.NHSensorSim.algorithm.SWinFloodAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.SWinFloodDemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "NHSensor.NHSensorSim.papers.E2STA.SWinFloodEnergyExperiment";
		String experimentParamClassFullName ="NHSensor.NHSensorSim.papers.E2STA.AllParam";

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
