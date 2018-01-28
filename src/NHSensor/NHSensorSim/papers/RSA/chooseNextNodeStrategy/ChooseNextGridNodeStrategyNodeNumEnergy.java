package NHSensor.NHSensorSim.papers.RSA.chooseNextNodeStrategy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class ChooseNextGridNodeStrategyNodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网络节点数目对能量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\chooseNextNodeStrategy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogEnergyChooseNextNode(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
