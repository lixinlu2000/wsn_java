package NHSensor.NHSensorSim.papers.E2STA.energy;

import NHSensor.NHSensorSim.papers.E2STA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "consumedEnergy";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, true, false, false,  false, false, true, false, false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "查询子区域数目";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "查询子区域数目对能量的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "E2STA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
