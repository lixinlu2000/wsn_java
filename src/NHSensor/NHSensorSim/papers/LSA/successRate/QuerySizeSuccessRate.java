package NHSensor.NHSensorSim.papers.LSA.successRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "isSuccess";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "查询消息大小（字节）";
		String yAxisLabel = "查询成功率";
		String chartTitle = "查询消息大小对查询成功率的影响";
		String adjustedVariable = "queryMessageSize";
		String filePath = "LSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogSuccessRate(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
