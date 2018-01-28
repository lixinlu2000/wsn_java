package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.packetFrameNum.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "Query message size(byte)";
		String yAxisLabel = "The total umber of data frames sent";
		String chartTitle = "查询消息大小对发送数据包数目的影响";
		String adjustedVariable = "queryMessageSize";
		String filePath = "RESA_LSA_IWQE\\packetFrameNum\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
