package NHSensor.NHSensorSim.papers.RSA.successProbability;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeFailProbabilitySuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeFailProbability";
		String yFieldName = "successProbability";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "节点失效概率";
		String yAxisLabel = "查询成功的概率";
		String chartTitle = "节点失效概率对查询成功的概率的影响";
		String adjustedVariable = "nodeFailProbability";
		String filePath = "RSA\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
