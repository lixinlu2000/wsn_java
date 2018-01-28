package NHSensor.NHSensorSim.papers.RSA.averageEnergy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "查询消息大小（字节）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "查询消息大小对能量的影响";
		String adjustedVariable = "queryMessageSize";
		String filePath = "RSA\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
