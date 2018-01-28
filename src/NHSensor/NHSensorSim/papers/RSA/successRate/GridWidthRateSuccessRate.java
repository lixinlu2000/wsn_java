package NHSensor.NHSensorSim.papers.RSA.successRate;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "isSuccess";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "网格宽度占通信半径的比例";
		String yAxisLabel = "查询线路联通性";
		String chartTitle = "网格宽度占通信半径的比例对查询线路联通性的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\successRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
