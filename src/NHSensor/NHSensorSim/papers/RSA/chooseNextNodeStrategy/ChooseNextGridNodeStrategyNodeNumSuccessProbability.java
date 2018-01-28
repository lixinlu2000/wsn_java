package NHSensor.NHSensorSim.papers.RSA.chooseNextNodeStrategy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class ChooseNextGridNodeStrategyNodeNumSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "successProbability";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ɹ��ĸ���";
		String chartTitle = "����ڵ���Ŀ�Բ�ѯ�ɹ��ĸ��ʵ�Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\chooseNextNodeStrategy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustnessChooseNextNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
