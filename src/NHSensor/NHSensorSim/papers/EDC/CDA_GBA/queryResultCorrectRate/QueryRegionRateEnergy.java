package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对查询结果质量的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSACDA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
