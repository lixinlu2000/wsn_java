package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.energy;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "查询节点数目";
		String chartTitle = "网格宽度占通信半径的比率对查询节点数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "CSACDA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
