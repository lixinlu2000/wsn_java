package NHSensor.NHSensorSim.papers.CSA.energy;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "The ratio of grid width to wireless range";
		String yAxisLabel = "Energy Consumption (mJ)Energy Consumption (mJ)";
		String chartTitle = "网格宽度占通信半径的比率对能量的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "CSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
