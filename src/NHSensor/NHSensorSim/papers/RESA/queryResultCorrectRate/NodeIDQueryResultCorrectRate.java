package NHSensor.NHSensorSim.papers.RESA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "����ID�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "RESA\\queryResultCorrectRate\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
