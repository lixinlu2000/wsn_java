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
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对查询结果质量的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "EST\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
