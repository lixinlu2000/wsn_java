package NHSensor.NHSensorSim.papers.RISC.averageEnergy;

import NHSensor.NHSensorSim.papers.RISC.GridTraverseRingExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "����ȣ��ף�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ȶ�������Ӱ��";
		String adjustedVariable = "ringWidth";
		String filePath = "GridTraverseRing\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil()
				.showAndLogAverageEnergy(adjustedVariable, absoluteFilePath,
						xFieldName, yFieldName, frameTitle, xAxisLabel,
						yAxisLabel, chartTitle);
	}

}
