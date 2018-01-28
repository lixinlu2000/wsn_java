package NHSensor.NHSensorSim.papers.RESALifeTime.timesDiffInitialEnergy;

import NHSensor.NHSensorSim.papers.RESALifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] {true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "感知数据消息大小（字节）";
		String yAxisLabel = "执行的查询个数";
		String chartTitle = "感知数据消息大小对网络生命周期的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RESA\\lifeTime\\timesDiffInitialEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
