package NHSensor.NHSensorSim.papers.EST.totalDataSize.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EST.totalDataSize.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, true, false, false, false, true,
						true });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "执行的查询个数";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对网络生命周期的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "EST\\totalDataSize\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
