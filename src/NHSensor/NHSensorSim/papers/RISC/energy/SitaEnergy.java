package NHSensor.NHSensorSim.papers.RISC.energy;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SitaEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "sita";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "�����μнǣ����ȣ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "sita��������Ӱ��";
		String adjustedVariable = "sita";
		String filePath = "GridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogEnergy(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
