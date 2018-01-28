package NHSensor.NHSensorSim.papers.CRISC.other;

import NHSensor.NHSensorSim.papers.CRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumBackupNodeNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "backupNodeNum";
		String frameTitle = "CRISC VS ERISC VS IC";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "��ѯ�ڵ���ͷ�ڵ���Ŀ";
		String chartTitle = "���ݽڵ���Ŀ";
		String adjustedVariable = "nodeNum";
		String filePath = "CRISC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}
}
