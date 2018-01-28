package NHSensor.NHSensorSim.papers.CRISC.energy;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "consumedEnergy";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "环宽度（米）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "环宽度对能量的影响";
		String adjustedVariable = "ringWidth";
		String filePath = "CRISC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
