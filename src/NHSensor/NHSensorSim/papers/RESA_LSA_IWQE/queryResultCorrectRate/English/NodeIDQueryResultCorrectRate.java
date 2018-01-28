package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.queryResultCorrectRate.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "Network ID";
		String yAxisLabel = "The quality of query result";
		String chartTitle = "����ID�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "RESA_LSA_IWQE\\queryResultCorrectRate\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
