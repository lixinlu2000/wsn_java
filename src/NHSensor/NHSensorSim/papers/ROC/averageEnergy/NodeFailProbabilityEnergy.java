package NHSensor.NHSensorSim.papers.ROC.averageEnergy;

import NHSensor.NHSensorSim.papers.ROC.ROCAdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeFailProbabilityEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeFailProbability";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "�ڵ�ʧЧ����";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�ڵ�ʧЧ���ʶ�������Ӱ��";
		String adjustedVariable = "nodeFailProbability";
		String filePath = "ROCAdaptiveGridTraverseRingEvent\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ROCAdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogAverageEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
