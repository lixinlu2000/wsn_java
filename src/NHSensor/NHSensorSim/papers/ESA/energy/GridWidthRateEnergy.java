package NHSensor.NHSensorSim.papers.ESA.energy;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS IWQE";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网格宽度占通信半径的比率对能量的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "ESA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
