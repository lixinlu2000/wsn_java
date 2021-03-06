package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.queryResultCorrectRate.English;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "The quality of query result";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对查询结果质量的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RESA_LSA_IWQE\\queryResultCorrectRate\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
