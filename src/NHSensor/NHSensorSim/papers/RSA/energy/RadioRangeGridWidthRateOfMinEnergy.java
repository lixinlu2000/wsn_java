package NHSensor.NHSensorSim.papers.RSA.energy;

import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class RadioRangeGridWidthRateOfMinEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String frameTitle = "RSA VS IWQE";
		String xAxisLabel = "�ڵ�ͨ�Ű뾶���ף�";
		String yAxisLabel = "���ĵ�������mJ��";
		String chartTitle = "�ڵ�ͨ�Ű뾶��������Ӱ��";
		String filePath = "RSA\\energy";
		String absoluteFilePath = Util.generateFilePath(filePath);
		RSAUtil.getDefaultRSAUtil()
				.showAndLogRadioRangeGridWidthRateMinimizeEnergy(
						absoluteFilePath, frameTitle, xAxisLabel, yAxisLabel,
						chartTitle);
	}

}
