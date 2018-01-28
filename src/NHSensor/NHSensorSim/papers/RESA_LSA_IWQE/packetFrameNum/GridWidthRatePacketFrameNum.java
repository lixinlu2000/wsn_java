package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.packetFrameNum;

import NHSensor.NHSensorSim.papers.RESA.ExperimentUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "packetFrameNum";
		String frameTitle = "RESA VS LSA VS IWQE";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "网格宽度占通信半径的比率对发送数据包数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RESA_LSA_IWQE\\packetFrameNum\\Chinese";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
