package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.suceessProbability;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SitaSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "sita";
		String yFieldName = "successProbability";
		String frameTitle = "RKNN VS IKNN sita -> successProbability";
		String xAxisLabel = "�����μнǣ�����)";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "�����μнǶԲ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "sita";
		String filePath = "AdaptiveGridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
