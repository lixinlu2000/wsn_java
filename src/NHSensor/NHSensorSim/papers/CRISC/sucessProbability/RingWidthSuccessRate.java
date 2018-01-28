package NHSensor.NHSensorSim.papers.CRISC.sucessProbability;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "isSuccess";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "环宽度（米）";
		String yAxisLabel = "查询成功率";
		String chartTitle = "环宽度对查询成功率的影响";
		String adjustedVariable = "ringWidth";
		String filePath = "CRISC\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
