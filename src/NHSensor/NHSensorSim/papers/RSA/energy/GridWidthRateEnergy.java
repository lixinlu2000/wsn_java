package NHSensor.NHSensorSim.papers.RSA.energy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网格宽度占通信半径的比率对能量的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
