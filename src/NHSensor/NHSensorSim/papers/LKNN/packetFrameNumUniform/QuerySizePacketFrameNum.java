package NHSensor.NHSensorSim.papers.LKNN.packetFrameNumUniform;

import NHSensor.NHSensorSim.papers.LKNN.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QuerySizePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryMessageSize";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LAC VS IC";
		String xAxisLabel = "Query message size(byte)";
		String yAxisLabel = "The total number of data frames sent";
		String chartTitle = "��ѯ��Ϣ��С�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "LKNN\\English\\packetFrameNumUniform";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
