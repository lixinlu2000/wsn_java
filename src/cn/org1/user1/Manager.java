package cn.org1.user1;

import java.io.File;
import NHSensor.NHSensorSim.algorithm.AlgorithmProperty;
import NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.AlgorithmDemo;

public class Manager {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String className = "ThirdPartyAlg1";
		String description = "UserAlg";
		String packageName = "cn.org1.user1";
		String classFullName = packageName+'.'+className; // contains
		String demoClassFullName = classFullName+"Demo";
		boolean isSystemAlgorithm = false;
		String experimentClassFullName = classFullName+"EnergyExperiment";
		String experimentParamClassFullName =packageName+'.'+"AlgParam";
		
		String name;

		try {
			AlgorithmDemo algorithmDemo = (AlgorithmDemo) Class.forName(
					demoClassFullName).newInstance();
			name = algorithmDemo.getRunnedAlgorithmInstance().getName();

			AlgorithmProperty ap = new AlgorithmProperty(name, description,
					classFullName, demoClassFullName, isSystemAlgorithm,
					experimentClassFullName, experimentParamClassFullName);
			File file = new File("./userWorkspace/" + name + "/config");
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
