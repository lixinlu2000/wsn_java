package NHSensor.NHSensorSim.papers.RSA.gridArea;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthGridArea {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "result";
		String frameTitle = "RSA gridWidthRate -> gridArea";
		String xAxisLabel = "网格宽度占通信半径的比例";
		String yAxisLabel = "动态生成的网格面积平均值";
		String chartTitle = "网格宽度占通信半径的比例对动态生成的网格面积平均值的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\gridArea";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridArea(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
