package NHSensor.NHSensorSim.papers.RSA.chooseNextNodeStrategy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class ChooseNextGridNodeStrategyNodeNumAverageEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\chooseNextNodeStrategy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageEnergyChooseNextNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
