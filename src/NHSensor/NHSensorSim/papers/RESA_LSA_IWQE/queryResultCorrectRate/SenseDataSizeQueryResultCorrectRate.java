package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "��֪���ݴ�С���ֽڣ�";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��֪���ݴ�С�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RESA_LSA_IWQE\\queryResultCorrectRate\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
