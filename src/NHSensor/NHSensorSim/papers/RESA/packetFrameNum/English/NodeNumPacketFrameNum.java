package NHSensor.NHSensorSim.papers.RESA.packetFrameNum.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "Number of sensors";
		String yAxisLabel = "The total umber of data frames sent";
		String chartTitle = "����ڵ���Ŀ�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "RESA\\packetFrameNum\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
