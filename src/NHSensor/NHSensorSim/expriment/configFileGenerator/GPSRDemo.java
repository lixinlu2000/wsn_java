package NHSensor.NHSensorSim.expriment.configFileGenerator;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.AlgorithmDemo;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.experiment.ExperimentConfig;
import NHSensor.NHSensorSim.link.IdealRadioRangeLinkEstimator;
import NHSensor.NHSensorSim.ui.mainFrame.ExperimentProperty;

public class GPSRDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "位置路由协议GPSR";
		String classFullName = "NHSensor.NHSensorSim.routing.gpsr.GPSRAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.GPSRDemo";
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

			double radioRange = 50;
			Network network = Network.createRandomNetwork(800, 400, 300, radioRange);
			IdealRadioRangeLinkEstimator le = new IdealRadioRangeLinkEstimator(
					network, radioRange);
			network.setLinkEstimator(le);
			System.out.println(network);
			ExperimentConfig experimentConfig = new ExperimentConfig();
			
			ExperimentProperty ep = new ExperimentProperty(network, ap, experimentConfig);
			
			File file = new File("./Experiments/" + name);
			if (!file.getParentFile().exists()) {
				System.out.println("目标文件所在路径不存在，准备创建。。。");
				if (!file.getParentFile().mkdirs()) {
					System.out.println("创建目录文件所在的目录失败！");
					return;
				}
			}
			ep.save(file);
			System.out.println("结束！！！！！！！！！！！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
