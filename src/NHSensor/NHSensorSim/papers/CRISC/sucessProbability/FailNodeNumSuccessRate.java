package NHSensor.NHSensorSim.papers.CRISC.sucessProbability;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class FailNodeNumSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "failNodeNum";
		String yFieldName = "isSuccess";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "失效节点数目（个）";
		String yAxisLabel = "查询成功率";
		String chartTitle = "失效节点数目对查询成功率的影响";
		String adjustedVariable = "failNodeNum";
		String filePath = "CRISC\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
