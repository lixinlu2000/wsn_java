package NHSensor.NHSensorSim.papers.RSA.successRate;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "isSuccess";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "节点数目";
		String yAxisLabel = "查询线路联通性";
		String chartTitle = "节点数目对查询线路联通性的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\successRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
