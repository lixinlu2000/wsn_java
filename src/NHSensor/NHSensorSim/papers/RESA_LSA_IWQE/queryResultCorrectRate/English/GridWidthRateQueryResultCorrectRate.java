package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.queryResultCorrectRate.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "The ratio of grid width to wireless range";
		String yAxisLabel = "The quality of query result";
		String chartTitle = "网格宽度占通信半径的比率对查询结果质量的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RESA_LSA_IWQE\\queryResultCorrectRate\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
