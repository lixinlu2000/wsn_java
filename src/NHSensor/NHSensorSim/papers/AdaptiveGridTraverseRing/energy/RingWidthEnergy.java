package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.energy;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "����ȣ��ף�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ȶ�������Ӱ��";
		String adjustedVariable = "ringWidth";
		String filePath = "AdaptiveGridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
