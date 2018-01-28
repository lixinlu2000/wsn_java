package NHSensor.NHSensorSim.papers.RESA.packetFrameNum;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "radioRange";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "ͨ�Ű뾶";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "����ڵ���Ŀ�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "radioRange";
		String filePath = "RESA\\packetFrameNum\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}