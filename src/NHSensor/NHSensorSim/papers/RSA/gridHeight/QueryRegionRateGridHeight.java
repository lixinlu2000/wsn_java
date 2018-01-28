package NHSensor.NHSensorSim.papers.RSA.gridHeight;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateGridHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "result";
		String frameTitle = "RSA queryRegionRate -> gridHeight";
		String xAxisLabel = "查询区域大小占网络区域的比例";
		String yAxisLabel = "动态生成的网格高度平均值";
		String chartTitle = "网格节点数目对动态生成的网格高度平均值的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RSA\\gridHeight";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridHeight(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
