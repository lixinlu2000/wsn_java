package NHSensor.NHSensorSim.papers.RESALifeTime.timesDiffInitialEnergy;

import NHSensor.NHSensorSim.papers.RESALifeTime.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeTimes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "queryNodeNum";
		ExperimentUtil.getDefaultUtil().setAlgRunOrNot(
				new boolean[] {true, true, true, true, true});
		String frameTitle = ExperimentUtil.getDefaultUtil().getVsString();
		String xAxisLabel = "��֪������Ϣ��С���ֽڣ�";
		String yAxisLabel = "ִ�еĲ�ѯ����";
		String chartTitle = "��֪������Ϣ��С�������������ڵ�Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "RESA\\lifeTime\\timesDiffInitialEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergyWithFail(
				adjustedVariable, absoluteFilePath, xFieldName, yFieldName,
				frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
