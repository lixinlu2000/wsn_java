package NHSensor.NHSensorSim.papers.LKNN.packetFrameNumUniform;

import NHSensor.NHSensorSim.papers.LKNN.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LAC VS IC";
		String xAxisLabel = "Radio range(m)";
		String yAxisLabel = "The total number of data frames sent";
		String chartTitle = "�ڵ�ͨ�Ű뾶�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "radioRange";
		String filePath = "LKNN\\English\\packetFrameNumUniform";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false,  true });
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
