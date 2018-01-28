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
				System.out.println("Ŀ���ļ�����·�������ڣ�׼������������");
				if (!file.getParentFile().mkdirs()) {
					System.out.println("����Ŀ¼�ļ����ڵ�Ŀ¼ʧ�ܣ�");
					return;
				}
			}
			ap.save(file);
			System.out.println("��������������������������");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
