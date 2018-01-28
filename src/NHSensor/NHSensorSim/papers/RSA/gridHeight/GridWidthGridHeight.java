package NHSensor.NHSensorSim.papers.RSA.gridHeight;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthGridHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "result";
		String frameTitle = "RSA gridWith -> gridHeight";
		String xAxisLabel = "网格宽度占通信半径的比例";
		String yAxisLabel = "动态生成的网格高度平均值";
		String chartTitle = "网格宽度占通信半径的比例对动态生成的网格高度平均值的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\gridHeight";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridHeight(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
