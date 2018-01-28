package NHSensor.NHSensorSim.papers.RESA.queryResultCorrectRate.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "Sensed data size (byte)";
		String yAxisLabel = "The quality of query result";
		String chartTitle = "��֪���ݴ�С�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RESA\\queryResultCorrectRate\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
