package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ���������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RESA_LSA_IWQE\\queryResultCorrectRate\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
