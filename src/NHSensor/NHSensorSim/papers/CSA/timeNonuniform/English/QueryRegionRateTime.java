package NHSensor.NHSensorSim.papers.CSA.timeNonuniform.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "communicationTime";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "Time latency (s)";
		String chartTitle = "��ѯ�������ռ���縲������ı�����������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\bytesNonuniform\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
