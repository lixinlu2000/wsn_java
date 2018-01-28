package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.averageEnergy;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NetworkIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "����ID";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ID��������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "AdaptiveGridTraverseRing\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogAverageEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
