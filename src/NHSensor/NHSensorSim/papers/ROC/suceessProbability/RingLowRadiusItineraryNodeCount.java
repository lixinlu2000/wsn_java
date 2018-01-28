package NHSensor.NHSensorSim.papers.ROC.suceessProbability;

import NHSensor.NHSensorSim.papers.ROC.ROCAdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusItineraryNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "itineraryNodeCount";
		String frameTitle = "RKNN VS IKNN ringLowRadius -> itineraryNodeCount";
		String xAxisLabel = "��СԲ�뾶���ף�";
		String yAxisLabel = "��ѯ�ڵ���Ŀ������";
		String chartTitle = "��СԲ�뾶�Բ�ѯ�ڵ���Ŀ��Ӱ��";
		String adjustedVariable = "ringLowRadius";
		String filePath = "ROCAdaptiveGridTraverseRingEvent\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ROCAdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
