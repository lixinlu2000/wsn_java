package NHSensor.NHSensorSim.papers.LSA.successRate;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "isSuccess";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "感知数据消息大小（字节）";
		String yAxisLabel = "查询成功率";
		String chartTitle = "感知数据消息大小对查询成功率的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "LSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogSuccessRate(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
