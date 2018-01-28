package NHSensor.NHSensorSim.papers.RSA.averageEnergy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "nodeNum";
		String yFieldName = "consumedEnergy";
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "����ڵ���Ŀ��������Ӱ��";
		String adjustedVariable = "nodeNum";
		String filePath = "RSA\\averageEnergy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil().showAndLogAverageEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);

	}

}
