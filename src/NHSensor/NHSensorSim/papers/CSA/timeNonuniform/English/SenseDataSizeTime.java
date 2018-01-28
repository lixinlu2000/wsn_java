package NHSensor.NHSensorSim.papers.CSA.timeNonuniform.English;

import NHSensor.NHSensorSim.papers.CSA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class SenseDataSizeTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "answerMessageSize";
		String yFieldName = "communicationTime";
		String frameTitle = "ESA-GBA VS CSA-CDA";
		String xAxisLabel = "Sensed data size (bytes)";
		String yAxisLabel = "Time latency (s)";
		String chartTitle = "��֪������Ϣ��С���������ĵ�Ӱ��";
		String adjustedVariable = "answerMessageSize";
		String filePath = "CSA\\bytesNonuniform\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
