package NHSensor.NHSensorSim.papers.ALLRISC.energy;

import NHSensor.NHSensorSim.papers.ALLRISC.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "GKNN VS IKNN";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "ETDC\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
