package NHSensor.NHSensorSim.papers.RSA.successProbability;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "successProbability";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询成功的概率";
		String chartTitle = "网络节点数目对查询成功的概率的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogRobustness(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
