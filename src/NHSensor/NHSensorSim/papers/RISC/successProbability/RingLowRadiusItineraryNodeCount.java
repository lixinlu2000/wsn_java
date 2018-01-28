package NHSensor.NHSensorSim.papers.RISC.successProbability;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingLowRadiusItineraryNodeCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringLowRadius";
		String yFieldName = "itineraryNodeCount";
		String frameTitle = "GKNN VS IKNN ringLowRadius -> itineraryNodeCount";
		String xAxisLabel = "��СԲ�뾶���ף�";
		String yAxisLabel = "��ѯ�ڵ���Ŀ������";
		String chartTitle = "��СԲ�뾶�Բ�ѯ�ڵ���Ŀ��Ӱ��";
		String adjustedVariable = "ringLowRadius";
		String filePath = "GridTraverseRing\\successProbability";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogRobustness(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
