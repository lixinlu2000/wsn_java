package NHSensor.NHSensorSim.util;

public class NetworkDensityTool {
	public static double networkDensity(double networkWidth,
			double networkHeight, int totalNodeNum) {
		return totalNodeNum / (networkWidth * networkHeight);
	}

	public static double averageNeighborNodeNum(double networkWidth,
			double networkHeight, int totalNodeNum, double radioRange) {
		double p = NetworkDensityTool.networkDensity(networkWidth,
				networkHeight, totalNodeNum);
		return p * Math.PI * radioRange * radioRange;
	}

	public static double nodeNum(double networkWidth, double networkHeight,
			double radioRange, double averageNeighborNodeNum) {
		return averageNeighborNodeNum / (Math.PI * radioRange * radioRange)
				* networkWidth * networkHeight;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double networkWidth = 100;
		double networkHeight = 100;
		double radioRange = 10;

		for (int totalNodeNum = 160; totalNodeNum <= 800; totalNodeNum += 160) {
			System.out.println(NetworkDensityTool.averageNeighborNodeNum(
					networkWidth, networkHeight, totalNodeNum, radioRange));
		}

		System.out.println();

		for (double averageNeighborNodeNum = 5; averageNeighborNodeNum <= 25; averageNeighborNodeNum += 5) {
			System.out.println(NetworkDensityTool.nodeNum(networkWidth,
					networkHeight, radioRange, averageNeighborNodeNum));
		}

	}

}
