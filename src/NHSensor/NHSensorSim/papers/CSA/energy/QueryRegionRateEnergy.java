package NHSensor.NHSensorSim.papers.CSA.energy;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "consumedEnergy";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "��ѯ�������ռ���縲������ı�����������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
