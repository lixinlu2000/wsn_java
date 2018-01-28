package NHSensor.NHSensorSim.papers.RSA.energy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangeGridWidthRateOfMinEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "节点通信半径（米）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "节点通信半径对能量的影响";
		String filePath = "RSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil()
				.showAndLogRadioRangeGridWidthRateMinimizeEnergy(
						absoluteFilePath, frameTitle, xAxisLabel, yAxisLabel,
						chartTitle);
	}

}
