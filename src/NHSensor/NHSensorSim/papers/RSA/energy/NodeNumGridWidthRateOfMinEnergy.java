package NHSensor.NHSensorSim.papers.RSA.energy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumGridWidthRateOfMinEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "最优网格宽度（米）";
		String chartTitle = "网络节点数目对最优网格宽度的影响";
		String filePath = "RSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil()
				.showAndLogNodeNumGridWidthRateMinimizeEnergy(absoluteFilePath,
						frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
