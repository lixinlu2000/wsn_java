package NHSensor.NHSensorSim.papers.RESA.packetFrameNum.English;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS IWQE";
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "The total umber of data frames sent";
		String chartTitle = "��ѯ�������ռ���縲������ı����Է������ݰ���Ŀ��Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "RESA\\packetFrameNum\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
