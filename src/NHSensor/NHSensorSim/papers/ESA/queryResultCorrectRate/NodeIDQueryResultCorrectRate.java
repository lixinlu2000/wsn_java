package NHSensor.NHSensorSim.papers.ESA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "ESA VS IWQE";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "����ID�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "ESA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
