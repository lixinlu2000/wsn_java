package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ���������Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSACDA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
