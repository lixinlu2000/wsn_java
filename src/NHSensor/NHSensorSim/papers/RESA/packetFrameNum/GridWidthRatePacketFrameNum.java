package NHSensor.NHSensorSim.papers.RESA.packetFrameNum;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "������ռͨ�Ű뾶�ı��ʶԷ������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RESA\\packetFrameNum\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
