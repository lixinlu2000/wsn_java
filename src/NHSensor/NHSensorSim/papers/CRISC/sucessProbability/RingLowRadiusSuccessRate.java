package NHSensor.NHSensorSim.papers.CRISC.sucessProbability;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "isSuccess";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "环小圆半径（米）";
		String yAxisLabel = "查询成功率";
		String chartTitle = "环小圆半径对查询成功率的影响";
		String adjustedVariable = "ringLowRadius";
		String filePath = "CRISC\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
