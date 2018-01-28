package NHSensor.NHSensorSim.papers.CRISC.energy;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class FailNodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "failNodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "失效节点数目（个）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "失效节点数目对能量的影响";
		String adjustedVariable = "failNodeNum";
		String filePath = "CRISC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

}
