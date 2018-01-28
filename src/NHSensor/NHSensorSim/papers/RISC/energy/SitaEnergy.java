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
		String xAxisLabel = "环扇形夹角（弧度）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "sita对能量的影响";
		String adjustedVariable = "sita";
		String filePath = "GridTraverseRing\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		GridTraverseRingExperimentUtil.getDefaultUtil().showAndLogEnergy(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
