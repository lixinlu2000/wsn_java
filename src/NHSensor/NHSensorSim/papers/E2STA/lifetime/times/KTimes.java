package NHSensor.NHSensorSim.papers.E2STA.lifetime.times;

import NHSensor.NHSensorSim.papers.E2STA.lifetime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class KTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��ѯ��������Ŀ";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��ѯ��������Ŀ�Զ������������ڵ�Ӱ��";
		String adjustedVariable = "gridWidthRate";
		String filePath = "E2STA\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}
}
