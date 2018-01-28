package NHSensor.NHSensorSim.papers.EST.lifeTime.times;

import NHSensor.NHSensorSim.papers.EST.lifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, false, false, false, true,
						false });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "网络节点数目（个）";
		String yAxisLabel = "执行的查询个数";
		String chartTitle = "网络节点数目对网络生命周期的影响";
		String adjustedVariable = "nodeNum";
		String filePath = "EST\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
