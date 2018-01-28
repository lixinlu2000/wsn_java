package NHSensor.NHSensorSim.papers.PEVA.energy;

import NHSensor.NHSensorSim.papers.PEVA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizeEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "consumedEnergy";
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "查询消息大小（字节）";
		String yAxisLabel = "消耗的能量（mJ）";
		String chartTitle = "查询消息大小对能量的影响";
		String adjustedVariable = "queryMessageSize";
		String filePath = "PEVA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
