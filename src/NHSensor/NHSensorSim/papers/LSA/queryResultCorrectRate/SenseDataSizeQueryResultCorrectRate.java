package NHSensor.NHSensorSim.papers.LSA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "感知数据大小（字节）";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "感知数据大小对查询结果质量的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "LSA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
