package NHSensor.NHSensorSim.papers.EST.partitionStrategy;

import NHSensor.NHSensorSim.papers.EST.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KQueryResultCorrectRate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryResultCorrectRate";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, false, false, false, false, false, true,
						true });
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ��������Ŀ";
		String yAxisLabel = "��ѯ�������";
		String chartTitle = "��ѯ��������Ŀ�Բ�ѯ���������Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "EST\\partitionStrategy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
