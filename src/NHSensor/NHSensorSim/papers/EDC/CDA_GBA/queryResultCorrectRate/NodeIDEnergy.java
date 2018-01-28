package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeIDEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "networkID";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "����ID";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "����ڵ���Ŀ�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "networkID";
		String filePath = "CSACDA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
