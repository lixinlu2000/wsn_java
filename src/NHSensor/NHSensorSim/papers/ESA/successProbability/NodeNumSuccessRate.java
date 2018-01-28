package NHSensor.NHSensorSim.papers.ESA.successProbability;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA VS IWQE";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询成功率";
		String chartTitle = "网络节点数目对查询成功率的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "ESA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		
		ESAUtil.getDefaultESAUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
