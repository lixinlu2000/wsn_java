package NHSensor.NHSensorSim.papers.ROC.suceessProbability;

import NHSensor.NHSensorSim.papers.ROC.ROCAdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NullSuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "k";
		String yFieldName = "successProbability";
		String frameTitle = "RKNN VS IKNN k -> successProbability";
		String xAxisLabel = "kֵ";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "k��Ŀ�Բ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "null";
		String filePath = "ROCAdaptiveGridTraverseRingEvent\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ROCAdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
