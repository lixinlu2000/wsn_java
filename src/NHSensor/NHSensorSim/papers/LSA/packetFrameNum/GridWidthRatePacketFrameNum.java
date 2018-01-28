package NHSensor.NHSensorSim.papers.LSA.packetFrameNum;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class GridWidthRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "gridWidthRate";
		String yFieldName = "packetFrameNum";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "网格宽度占通信半径的比率";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "网格宽度占通信半径的比率对发送的数据帧数目的影响";
		String adjustedVariable = "gridWidthRate";
		String filePath = "LSA\\packetFrameNum";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
