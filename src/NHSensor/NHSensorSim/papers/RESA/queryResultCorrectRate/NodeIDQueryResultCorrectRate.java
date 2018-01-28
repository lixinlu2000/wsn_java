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
		String xAxisLabel = "网络ID";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "网络ID对查询结果质量的影响";
		String adjustedVariable = "networkID";
		String filePath = "RESA\\queryResultCorrectRate\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
