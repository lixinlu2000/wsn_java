package NHSensor.NHSensorSim.papers.LKNN.packetFrameNumUniform;

import NHSensor.NHSensorSim.papers.LKNN.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class RingWidthEnergy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "ringWidth";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LAC VS IC";
		String xAxisLabel = "The width of ring";
		String yAxisLabel = "The total number of data frames sent";
		String chartTitle = "����ȶ�������Ӱ��";
		String adjustedVariable = "ringWidth";
		String filePath = "LKNN\\English\\packetFrameNumUniform";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
