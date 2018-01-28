package NHSensor.NHSensorSim.papers.RSA.gridArea;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateGridArea {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "result";
		String frameTitle = "RSA queryRegionRate -> gridArea";
		String xAxisLabel = "查询区域面积占网络区域的比例";
		String yAxisLabel = "动态生成的网格面积平均值";
		String chartTitle = "查询区域面积占网络区域的比例对动态生成的网格面积平均值的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RSA\\gridArea";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridArea(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

}
