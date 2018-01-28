package NHSensor.NHSensorSim.papers.CSA.energy.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "The ratio of grid width to wireless range";
		String yAxisLabel = "Number of cluster head nodes";
		String chartTitle = "网格宽度占通信半径的比率对查询节点数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "CSA\\energy\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
