package NHSensor.NHSensorSim.papers.EST.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EST.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "网络节点数目对查询结果质量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "EST\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
