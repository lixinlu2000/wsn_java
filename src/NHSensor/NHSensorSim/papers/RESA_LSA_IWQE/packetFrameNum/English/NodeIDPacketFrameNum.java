package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.packetFrameNum.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "Network ID";
		String yAxisLabel = "The total umber of data frames sent";
		String chartTitle = "����ID�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "RESA_LSA_IWQE\\packetFrameNum\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
