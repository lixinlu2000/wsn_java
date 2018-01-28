package NHSensor.NHSensorSim.papers.ROC.suceessProbability;

import NHSensor.NHSensorSim.papers.ROC.ROCAdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeFailProbabilitySuccessProbability {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeFailProbability";
		String yFieldName = "successProbability";
		String frameTitle = "RKNN VS IKNN nodeFailProbability -> successProbability";
		String xAxisLabel = "�ڵ�ʧЧ����";
		String yAxisLabel = "��ѯ�ɹ�����";
		String chartTitle = "�ڵ�ʧЧ���ʶԲ�ѯ�ɹ��ʵ�Ӱ��";
		String adjustedVariable = "nodeFailProbability";
		String filePath = "ROCAdaptiveGridTraverseRingEvent\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ROCAdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogRobustness(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
