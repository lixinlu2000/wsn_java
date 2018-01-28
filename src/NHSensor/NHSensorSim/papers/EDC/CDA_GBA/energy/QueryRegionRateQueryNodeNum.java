package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.energy;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRateQueryNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "queryNodeNum";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "��ѯ�������ռ���縲������ı���";
		String yAxisLabel = "��ѯ�ڵ���Ŀ";
		String chartTitle = "��ѯ�������ռ���縲������ı����Բ�ѯ�ڵ���Ŀ��Ӱ��";
		String adjustedVariable = "queryRegionRate";
		String filePath = "CSACDA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
