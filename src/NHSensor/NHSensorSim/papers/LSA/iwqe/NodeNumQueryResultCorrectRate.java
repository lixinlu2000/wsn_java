package NHSensor.NHSensorSim.papers.LSA.iwqe;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "IWQE";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询结果正确性";
		String chartTitle = "网络节点数目对查询结果正确性的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "LSA\\iwqe";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogIWQEQueryCorrectnessRate(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
