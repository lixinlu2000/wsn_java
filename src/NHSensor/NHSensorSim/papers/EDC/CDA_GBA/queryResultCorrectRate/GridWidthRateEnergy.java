package NHSensor.NHSensorSim.papers.EDC.CDA_GBA.queryResultCorrectRate;

import NHSensor.NHSensorSim.papers.EDC.CDA_GBA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRateEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryResultCorrectRate";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "������ռͨ�Ű뾶�ı���";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "������ռͨ�Ű뾶�ı��ʶԲ�ѯ���������Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "CSACDA\\queryResultCorrectRate";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
