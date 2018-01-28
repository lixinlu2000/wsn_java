package NHSensor.NHSensorSim.papers.RISC.averageEnergy;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SitaEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "sita";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RISC VS IC";
		String xAxisLabel = "�����μнǣ����ȣ�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "sita��������Ӱ��";
		String adjustedVariable = "sita";
		String filePath = "GridTraverseRing\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogAverageEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
