package NHSensor.NHSensorSim.papers.RSA.gridHeight;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumGridHeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "result";
		String frameTitle = "RSA nodeNum -> gridHeight";
		String xAxisLabel = "网格节点数目（个）";
		String yAxisLabel = "动态生成的网格高度平均值";
		String chartTitle = "网格节点数目对动态生成的网格高度平均值的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\gridHeight";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageGridHeight(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
