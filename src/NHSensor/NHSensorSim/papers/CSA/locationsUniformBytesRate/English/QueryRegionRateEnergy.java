package NHSensor.NHSensorSim.papers.CSA.locationsUniformBytesRate.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "maintainLocationsFrameNumRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { true, true, false });
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "The ratio of query region to area covered";
		String yAxisLabel = "Cost for maintaining neighbors' locations";
		String chartTitle = "��ѯ�������ռ���縲������ı�����������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\locationsUniformBytes\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
