package NHSensor.NHSensorSim.papers.LSA.energy;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "consumedEnergy";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "通信半径";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "通信半径对能量的影响";
		String adjustedVariable = "radioRange";
		String filePath = "LSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
