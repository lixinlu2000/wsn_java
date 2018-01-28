package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.packetFrameNum;

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
		String xAxisLabel = "��ѯ��Ϣ��С���ֽڣ�";
		String yAxisLabel = "���͵����ݰ���Ŀ������";
		String chartTitle = "��ѯ��Ϣ��С�Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "queryMessageSize";
		String filePath = "RESA_LSA_IWQE\\packetFrameNum\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
