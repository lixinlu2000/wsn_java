package NHSensor.NHSensorSim.papers.RESALifeTime.timesDiffInitialEnergy;

import NHSensor.NHSensorSim.papers.RESALifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class InitialEnergyDeltaTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "initialEnergyDelta";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] {true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "节点初始能量最大差异（mJ）";
		String yAxisLabel = "执行的查询个数";
		String chartTitle = "节点初始能量最大差异对网络生命周期的影响";
		String adjustedVariable = "initialEnergyDelta";
		String filePath = "RESA\\lifeTime\\timesDiffInitialEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
