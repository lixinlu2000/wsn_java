package NHSensor.NHSensorSim.papers.RESA.queryResultCorrectRate.English;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "Number of sensors";
		String yAxisLabel = "The quality of query result";
		String chartTitle = "网络节点数目对查询结果质量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "RESA\\queryResultCorrectRate\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
