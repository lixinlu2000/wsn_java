package NHSensor.NHSensorSim.papers.EST.self.energy;

import NHSensor.NHSensorSim.papers.EST.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "consumedEnergy";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, false, true, false, true,
						false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "网络ID";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网络节点数目对能量的影响";
		String adjustedVariable = "networkID";
		String filePath = "EST\\self\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);

		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
