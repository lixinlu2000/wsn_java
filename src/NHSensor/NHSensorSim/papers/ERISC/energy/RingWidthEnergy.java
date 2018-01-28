package NHSensor.NHSensorSim.papers.ERISC.energy;

import NHSensor.NHSensorSim.papers.ERISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RKNN VS IKNN";
		String xAxisLabel = "环宽度（米）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "环宽度对能量的影响";
		String adjustedVariable = "ringWidth";
		String filePath = "EDCAdaptiveGridTraverseRingEvent\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
