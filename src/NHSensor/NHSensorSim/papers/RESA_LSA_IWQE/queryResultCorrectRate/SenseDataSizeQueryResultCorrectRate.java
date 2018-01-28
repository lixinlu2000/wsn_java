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
		String xAxisLabel = "感知数据大小（字节）";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "感知数据大小对查询结果质量的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RESA_LSA_IWQE\\queryResultCorrectRate\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
