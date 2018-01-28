package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.suceessProbability;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumItineraryNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "itineraryNodeCount";
		String frameTitle = "RKNN VS IKNN nodeNum -> itineraryNodeCount";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ڵ���Ŀ������";
		String chartTitle = "�ڵ���Ŀ�Բ�ѯ�ڵ���Ŀ��Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "AdaptiveGridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
