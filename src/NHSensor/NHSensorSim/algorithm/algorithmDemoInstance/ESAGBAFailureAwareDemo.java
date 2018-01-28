package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareFinalAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;

public class ESAGBAFailureAwareDemo extends AlgorithmDemo {

	public ESAGBAFailureAwareDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(6, 600, 450, 500, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGBAFailureAwareFinalAlg.NAME);
		ESAUseGBAFailureAwareFinalAlg esa = (ESAUseGBAFailureAwareFinalAlg) sensorSim
				.getAlgorithm(ESAUseGBAFailureAwareFinalAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);

		Network network = esa.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 10));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		//
		return esa;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGBAFailureAwareFinalAlg.NAME);
		ESAUseGBAFailureAwareFinalAlg esa = (ESAUseGBAFailureAwareFinalAlg) sensorSim
				.getAlgorithm(ESAUseGBAFailureAwareFinalAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);

		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 10));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		//
		return esa;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "ESAGBAFailureAware";
		String classFullName = "NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareFinalAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.ESAGBAFailureAwareDemo";
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
