package NHSensor.NHSensorSim.papers.EST.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EST.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ���������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "EST\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
