package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICFailureAwareAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;

public class IWQEFailureAwareDemo extends AlgorithmDemo {

	public IWQEFailureAwareDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(4, 450, 450, 600, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEIdealUseICFailureAwareAlg.NAME);
		IWQEIdealUseICFailureAwareAlg iwqeFailueAware = (IWQEIdealUseICFailureAwareAlg) sensorSim
				.getAlgorithm(IWQEIdealUseICFailureAwareAlg.NAME);
		iwqeFailueAware.getParam().setANSWER_SIZE(30);

		Network network = iwqeFailueAware.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 1));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* iwqeFailueAware.getParam().getRADIO_RANGE();
		iwqeFailueAware
				.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		//
		return iwqeFailueAware;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEIdealUseICFailureAwareAlg.NAME);
		IWQEIdealUseICFailureAwareAlg iwqeFailueAware = (IWQEIdealUseICFailureAwareAlg) sensorSim
				.getAlgorithm(IWQEIdealUseICFailureAwareAlg.NAME);
		iwqeFailueAware.getParam().setANSWER_SIZE(30);

		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 100));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* iwqeFailueAware.getParam().getRADIO_RANGE();
		iwqeFailueAware
				.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		//
		return iwqeFailueAware;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "IWQEFailureAware";
		String classFullName = "NHSensor.NHSensorSim.algorithm.IWQEIdealUseICFailureAwareAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.IWQEFailureAwareDemo";
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
