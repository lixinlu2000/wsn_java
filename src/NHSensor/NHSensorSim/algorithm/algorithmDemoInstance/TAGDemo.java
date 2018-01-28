package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.papers.PEVA.AllParam;
import NHSensor.NHSensorSim.papers.PEVA.TAGEnergyExperiment;

public class TAGDemo extends AlgorithmDemo {

	public TAGDemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		boolean showAnimator = false;
		String paramString = "answerMessageSize(2) nodeNum(320) nodeFailProbability(0.0) queryMessageSize(4) networkHeight(400.0) networkID(0) k(0) nodeFailModelID(0) networkWidth(600.0) gridWidthRate(17.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(1) radioRange(50.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		System.out.println(param);

		TAGEnergyExperiment e = new TAGEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		return e.getAlgorithm();
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		boolean showAnimator = false;
		String paramString = "answerMessageSize(2) nodeNum(320) nodeFailProbability(0.0) queryMessageSize(4) networkHeight(400.0) networkID(0) k(0) nodeFailModelID(0) networkWidth(600.0) gridWidthRate(17.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(1) radioRange(50.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		System.out.println(param);

		TAGEnergyExperiment e = new TAGEnergyExperiment(param, network);
		e.setShowAnimator(showAnimator);
		e.run();
		return e.getAlgorithm();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "TAG";
		String classFullName = "NHSensor.NHSensorSim.algorithm.TAGNewAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.TAGDemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "NHSensor.NHSensorSim.papers.PEVA.TAGEnergyExperiment";
		String experimentParamClassFullName ="NHSensor.NHSensorSim.papers.PEVA.AllParam";

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
