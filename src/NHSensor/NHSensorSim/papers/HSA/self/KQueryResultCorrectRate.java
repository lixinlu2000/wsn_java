package NHSensor.NHSensorSim.papers.HSA.self;

import NHSensor.NHSensorSim.papers.HSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "subAreaNum";
		String yFieldName = "queryResultCorrectRate";

		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, false, true});
		String xAxisLabel = "查询子区域数目";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "查询子区域数目对查询结果质量的影响";
		String adjustedVariable = "subAreaNum";
		String filePath = "HSA\\self";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
