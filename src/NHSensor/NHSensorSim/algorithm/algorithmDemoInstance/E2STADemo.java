package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import java.io.File;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.papers.E2STA.AllParam;
import NHSensor.NHSensorSim.papers.E2STA.E2STAEnergyExperiment;

public class E2STADemo extends AlgorithmDemo {

	public E2STADemo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		boolean showAnimator = false;
		String paramString = "networkID(4) nodeNum(400) queryRegionRate(0.4) gridWidthRate(10) queryMessageSize(30) answerMessageSize(50) networkWidth(600.0) networkHeight(400.0) radioRange(50.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		System.out.println(param);

		E2STAEnergyExperiment e = new E2STAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		//
		return e.getAlgorithm();
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		boolean showAnimator = false;
		String paramString = "networkID(4) nodeNum(400) queryRegionRate(0.4) gridWidthRate(10) queryMessageSize(30) answerMessageSize(50) networkWidth(600.0) networkHeight(400.0) radioRange(50.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
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

		E2STAEnergyExperiment e = new E2STAEnergyExperiment(param, network);
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
		String description = "E2STA";
		String classFullName = "NHSensor.NHSensorSim.algorithm.E2STAAlg"; 
		String demoClassFullName = "NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.E2STADemo";
		boolean isSystemAlgorithm = true;
		String experimentClassFullName = "NHSensor.NHSensorSim.papers.E2STA.E2STAEnergyExperiment";
		String experimentParamClassFullName ="NHSensor.NHSensorSim.papers.E2STA.AllParam";

		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo) Class.forName(
					demoClassFullName).newInstance();
			name = algorithmDemo.getRunnedAlgorithmInstance().getName();

			AlgorithmProperty ap = new AlgorithmProperty(name, description,
					classFullName, demoClassFullName, isSystemAlgorithm,
					experimentClassFullName, experimentParamClassFullName);
			File file = new File("./Algorithms/Privacy/" + name + "/config");
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
