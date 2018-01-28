package NHSensor.NHSensorSim.papers.CSA.successProbability;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateSuccessRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "isSuccess";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "查询成功率";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对查询成功率的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\isSuccess";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogSuccessRateHasFailNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
