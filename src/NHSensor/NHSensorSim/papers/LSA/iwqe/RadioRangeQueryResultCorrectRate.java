package NHSensor.NHSensorSim.papers.LSA.iwqe;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangeQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "IWQE";
		String xAxisLabel = "通信半径";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "网络节点数目对查询结果质量的影响";
		String adjustedVariable = "radioRange";
		String filePath = "LSA\\iwqe";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogIWQEQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
