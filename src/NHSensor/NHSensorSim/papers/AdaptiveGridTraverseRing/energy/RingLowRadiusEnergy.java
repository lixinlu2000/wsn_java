package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.energy;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "��СԲ�뾶���ף�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��СԲ�뾶��������Ӱ��";
		String adjustedVariable = "ringLowRadius";
		String filePath = "AdaptiveGridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
