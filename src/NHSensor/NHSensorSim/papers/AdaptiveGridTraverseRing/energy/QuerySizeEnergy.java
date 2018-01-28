package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.energy;

import NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing.AdaptiveGridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "��ѯ��Ϣ��С���ֽڣ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��ѯ��Ϣ��С��������Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "AdaptiveGridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		AdaptiveGridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);

	}

}