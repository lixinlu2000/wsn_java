package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.energy;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "查询节点数目";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对查询节点数目的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSACDA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
