package NHSensor.NHSensorSim.papers.E2STA.energy;

import NHSensor.NHSensorSim.papers.E2STA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
//				new boolean[] { true, true, true, true, true,  true, true });
				new boolean[] { false, false, true, false, false,  false, false, true, false, false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "网络节点数目对能量的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "E2STA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
