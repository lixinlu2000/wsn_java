package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.suceessProbability;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumAverageBackupNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "averageBackupNodeCount";
		String frameTitle = "RKNN VS IKNN nodeNum -> averageBackupNodeCount";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "ƽ�����ݽڵ���Ŀ������";
		String chartTitle = "�ڵ���Ŀ��ƽ�����ݽڵ���Ŀ��Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "AdaptiveGridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
