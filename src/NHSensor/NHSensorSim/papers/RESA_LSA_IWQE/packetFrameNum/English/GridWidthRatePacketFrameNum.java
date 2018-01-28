package NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.packetFrameNum.English;

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
		String xAxisLabel = "The ratio of grid width to wireless range";
		String yAxisLabel = "The total umber of data frames sent";
		String chartTitle = "网格宽度占通信半径的比率对发送数据包数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "RESA_LSA_IWQE\\packetFrameNum\\English";
		String absoluteFilePath = Util.generateFilePath(filePath);
		ExperimentUtil.getDefaultUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
