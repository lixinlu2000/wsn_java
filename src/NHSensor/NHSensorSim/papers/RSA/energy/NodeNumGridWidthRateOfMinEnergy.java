package NHSensor.NHSensorSim.papers.RSA.energy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class NodeNumGridWidthRateOfMinEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "����ڵ���Ŀ������";
		String yAxisLabel = "���������ȣ��ף�";
		String chartTitle = "����ڵ���Ŀ�����������ȵ�Ӱ��";
		String filePath = "RSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil()
				.showAndLogNodeNumGridWidthRateMinimizeEnergy(absoluteFilePath,
						frameTitle, xAxisLabel, yAxisLabel, chartTitle);
	}

}
