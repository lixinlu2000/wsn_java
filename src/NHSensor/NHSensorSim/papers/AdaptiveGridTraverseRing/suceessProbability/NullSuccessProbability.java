package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.suceessProbability;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NullSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "k";
		String yFieldName = "successProbability";
		String frameTitle = "RKNN VS IKNN k -> successProbability";
		String xAxisLabel = "k值";
		String yAxisLabel = "查询成功概率";
		String chartTitle = "k数目对查询成功率的影响";
		String adjustedVariable = "null";
		String filePath = "AdaptiveGridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
