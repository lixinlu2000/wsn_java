package NHSensor.NHSensorSim.papers.EST.lifeTime.times;

import NHSensor.NHSensorSim.papers.EST.lifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, false, false, false, true,
						true });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "查询子区域数目";
		String yAxisLabel = "执行的查询个数";
		String chartTitle = "查询子区域数目对对网络生命周期的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "EST\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
