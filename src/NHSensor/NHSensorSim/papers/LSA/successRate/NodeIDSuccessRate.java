package NHSensor.NHSensorSim.papers.LSA.successRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "isSuccess";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�ɹ���";
		String chartTitle = "����ID�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "LSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogSuccessRate(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
