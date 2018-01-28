package NHSensor.NHSensorSim.papers.LSA.packetFrameNumNeed;

import NHSensor.NHSensorSim.papers.LSA.LSAUtil;
import NHSensor.NHSensorSim.papers.RSA.RSAUtil;
import NHSensor.NHSensorSim.util.Util;

public class QueryRegionRatePacketFrameNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xFieldName = "queryRegionRate";
		String yFieldName = "packetFrameNumNeed";
		String frameTitle = "LSA VS IWQE";
		String xAxisLabel = "查询区域面积占网络覆盖区域的比例";
		String yAxisLabel = "发送的数据包数目（个）";
		String chartTitle = "查询区域面积占网络覆盖区域的比例对发送数据包数目的影响";
		String adjustedVariable = "queryRegionRate";
		String filePath = "LSA\\packetFrameNumNeed";
		String absoluteFilePath = Util.generateFilePath(filePath);
		LSAUtil.getDefaultLSAUtil().showAndLogEnergy(adjustedVariable,
				absoluteFilePath, xFieldName, yFieldName, frameTitle,
				xAxisLabel, yAxisLabel, chartTitle);
	}

}
