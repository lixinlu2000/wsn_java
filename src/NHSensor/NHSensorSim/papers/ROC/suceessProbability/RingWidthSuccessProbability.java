package NHSensor.NHSensorSim.papers.ROC.suceessProbability;

import NHSensor.NHSensorSim.papers.ROC.ROCAdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "successProbability";
		String frameTitle = "RKNN VS IKNN ringWidth -> successProbability";
		String xAxisLabel = "环宽度（米）";
		String yAxisLabel = "查询成功概率";
		String chartTitle = "环宽度对查询成功率的影响";
		String adjustedVariable = "ringWidth";
		String filePath = "ROCAdaptiveGridTraverseRingEvent\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ROCAdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
