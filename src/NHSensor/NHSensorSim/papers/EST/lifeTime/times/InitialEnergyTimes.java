package NHSensor.NHSensorSim.papers.EST.lifeTime.times;

import NHSensor.NHSensorSim.papers.EST.lifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class InitialEnergyTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "initialEnergy";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, false, false, false, true,
						false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "节点初始能量（mJ）";
		String yAxisLabel = "执行的查询个数";
		String chartTitle = "节点初始能量对网络生命周期的影响";
		String adjustedVariable = "initialEnergy";
		String filePath = "EST\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
