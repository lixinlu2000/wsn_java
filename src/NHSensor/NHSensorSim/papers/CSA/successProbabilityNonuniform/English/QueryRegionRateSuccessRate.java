package NHSensor.NHSensorSim.papers.CSA.successProbabilityNonuniform.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "Query success rate";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\bytesNonuniform\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
