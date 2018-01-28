package NHSensor.NHSensorSim.papers.ESA.esaGAA;

import NHSensor.NHSensorSim.papers.ESA.ESAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESAGAA VS ESAGBA";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网格宽度占通信半径的比率对能量的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "ESA\\esaGAA";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ESAUtil.getDefaultESAUtil().showAndLogESAGAAEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
