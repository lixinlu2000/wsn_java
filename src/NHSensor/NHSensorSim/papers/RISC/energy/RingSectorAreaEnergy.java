package NHSensor.NHSensorSim.papers.RISC.energy;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingSectorAreaEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringSectorArea";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "�����������m2��";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�����������������Ӱ��";
		String adjustedVariable = "sita";
		String filePath = "GridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogEnergy(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
