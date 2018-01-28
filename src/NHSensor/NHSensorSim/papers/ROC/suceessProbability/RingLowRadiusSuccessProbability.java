package NHSensor.NHSensorSim.papers.ROC.suceessProbability;

import NHSensor.NHSensorSim.papers.ROC.ROCAdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "successProbability";
		String frameTitle = "RKNN VS IKNN ringLowRadius -> successProbability";
		String xAxisLabel = "��СԲ�뾶���ף�";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "��СԲ�뾶�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "ringLowRadius";
		String filePath = "ROCAdaptiveGridTraverseRingEvent\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ROCAdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
