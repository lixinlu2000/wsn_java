package NHSensor.NHSensorSim.papers.LSA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "����ID�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "LSA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
