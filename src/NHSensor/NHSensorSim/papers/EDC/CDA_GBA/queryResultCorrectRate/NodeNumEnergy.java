package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "查询结果质量";
		String chartTitle = "网络节点数目对查询结果质量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "CSACDA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
