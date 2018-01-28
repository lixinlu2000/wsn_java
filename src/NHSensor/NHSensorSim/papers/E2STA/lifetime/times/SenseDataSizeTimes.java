package NHSensor.NHSensorSim.papers.E2STA.lifetime.times;

import NHSensor.NHSensorSim.papers.E2STA.lifetime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] { false, true, true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��֪������Ϣ��С���ֽڣ�";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��֪������Ϣ��С�������������ڵ�Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "E2STA\\lifeTime\\times";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
