package NHSensor.NHSensorSim.papers.LKNN.packetFrameNumNonuniform;

import NHSensor.NHSensorSim.papers.LKNN.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class HoleNumberPacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "holeNumber";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LAC VS IC";
		String xAxisLabel = "Number of holes";
		String yAxisLabel = "The total number of data frames sent";
		String chartTitle = "����ڵ���Ŀ�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "holeNumber";
		String filePath = "LKNN\\English\\packetFrameNumNonuniform";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
