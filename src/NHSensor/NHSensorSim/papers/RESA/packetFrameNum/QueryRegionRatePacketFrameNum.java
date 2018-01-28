package NHSensor.NHSensorSim.papers.RESA.packetFrameNum;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对发送数据包数目的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RESA\\packetFrameNum\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
