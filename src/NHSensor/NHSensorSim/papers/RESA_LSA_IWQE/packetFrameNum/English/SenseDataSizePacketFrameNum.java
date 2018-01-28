package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.packetFrameNum.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "Sensed data size (byte)";
		String yAxisLabel = "The total umber of data frames sent";
		String chartTitle = "感知数据消息大小对发送数据包数目的影响";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RESA_LSA_IWQE\\packetFrameNum\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
