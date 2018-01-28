package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.papers.CSA.AllParam;
import NHSensor.NHSensorSim.papers.CSA.CSACDAEnergyExperiment;

public class CSACDADemo extends AlgorithmDemo {

	public CSACDADemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		boolean showAnimator = false;
		String paramString = "answerMessageSize(40) nodeNum(800) queryMessageSize(20) networkHeight(100.0) networkID(1) nodeFailModelID(0) maxHoleRadius(10.0) networkWidth(100.0) gridWidthRate(0.8660254037844386) holeNumber(4) resultSize(40) queryAndPatialAnswerSize(60) failNodeNum(0) holeModelID(4) queryRegionRate(0.5) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		System.out.println(param);

		CSACDAEnergyExperiment e = new CSACDAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		//
		return e.getAlgorithm();
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		boolean showAnimator = false;
		String paramString = "answerMessageSize(40) nodeNum(800) queryMessageSize(20) networkHeight(100.0) networkID(1) nodeFailModelID(0) maxHoleRadius(10.0) networkWidth(100.0) gridWidthRate(0.8660254037844386) holeNumber(4) resultSize(40) queryAndPatialAnswerSize(60) failNodeNum(0) holeModelID(4) queryRegionRate(0.5) radioRange(50.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		System.out.println(param);
		param.setNetworkHeight(network.getHeigth());
		param.setNetworkWidth(network.getWidth());
		param.setNodeNum(network.getNodeNum());	

		CSACDAEnergyExperiment e = new CSACDAEnergyExperiment(param, network);
		e.getAlgorithm().setNetwork(network);
		e.setShowAnimator(showAnimator);
		e.run();
		//
		return e.getAlgorithm();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name;
		String description = "CSACDA";
		String classFullName = "NHSensor.NHSensorSim.algorithm.CSAUseCDAFailureAwareAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.CSACDADemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "NHSensor.NHSensorSim.papers.CSA.CSACDAEnergyExperiment";
		String experimentParamClassFullName ="NHSensor.NHSensorSim.papers.CSA.AllParam";

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
