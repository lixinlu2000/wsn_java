package NHSensor.NHSensorSim.experiment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;

import NHSensor.NHSensorSim.shape.Position;

public class LinkQualityExperiment {
	private String name;
	private String date;
	private int totalPacketNum;
	private int packetSize;
	private int periodTime; // in miliseconds
	private int powerSetting; // in units
	private double linkQuality[][];
	private double recvPacketsNums[][];
	private double conns[][];
	private int lastSeqnos[][];
	private Position positions[];
	private int nodeNum;

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public double[][] getRecvPacketsNums() {
		return recvPacketsNums;
	}

	public void setRecvPacketsNums(double[][] recvPacketsNums) {
		this.recvPacketsNums = recvPacketsNums;
	}

	public int[][] getLastSeqnos() {
		return lastSeqnos;
	}

	public void setLastSeqnos(int[][] lastSeqnos) {
		this.lastSeqnos = lastSeqnos;
	}

	public Position[] getPositions() {
		return positions;
	}

	public void setPositions(Position[] positions) {
		this.positions = positions;
	}

	public LinkQualityExperiment() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTotalPacketNum() {
		return totalPacketNum;
	}

	public void setTotalPacketNum(int totalPacketNum) {
		this.totalPacketNum = totalPacketNum;
	}

	public int getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	public int getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(int periodTime) {
		this.periodTime = periodTime;
	}

	public int getPowerSetting() {
		return powerSetting;
	}

	public void setPowerSetting(int powerSetting) {
		this.powerSetting = powerSetting;
	}

	public double[][] getLinkQuality() {
		return linkQuality;
	}

	public String toString() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw
				.println("************************************************************");
		pw.println("EXPERIMENT: " + this.getName());
		pw.println("DATE: " + this.date);
		pw.println("PARAMETERS:");
		pw.println("  total number of packets: " + this.getTotalPacketNum());
		pw.println("  packet size: " + this.getPacketSize() + " bytes");
		pw.println("  period time: " + this.getPeriodTime() + "  miliseconds");
		pw.println("  power setting: " + this.getPowerSetting() + " units");
		pw
				.println("************************************************************");

		for (int i = 0; i < this.recvPacketsNums.length; i++) {
			int recvNodeID = i + 1;
			double x = this.positions[i].getX();
			double y = this.positions[i].getY();
			pw.println("");
			pw.println("NODE " + recvNodeID + " (" + x + " " + y + ")" + ":");
			pw.println("--------------------------------------");
			for (int j = 0; j < this.recvPacketsNums.length; j++) {
				if (i != j) {
					int sendNodeID = j + 1;
					double recvPacketsNum = this.recvPacketsNums[i][j];
					double conn = this.conns[i][j];
					double lastSeqno = this.lastSeqnos[i][j];
					pw.println("[" + sendNodeID + "] " + recvPacketsNum + " "
							+ conn + " " + lastSeqno);
				}
			}
		}
		return sw.toString();
	}

	public double[][] getConns() {
		return conns;
	}

	public void setConns(double[][] conns) {
		this.conns = conns;
	}

	public void readExperiment(BufferedReader br, String curLine) {
		readExperimentHeader(br, curLine);
		this.init();
		readNodes(br);
	}

	public void init() {
		recvPacketsNums = new double[nodeNum][nodeNum];
		conns = new double[nodeNum][nodeNum];
		lastSeqnos = new int[nodeNum][nodeNum];
		positions = new Position[nodeNum];
		for (int i = 0; i < nodeNum; i++) {
			positions[i] = new Position();
		}
	}

	public boolean readNode(BufferedReader br) {
		try {
			String lineData = null;
			lineData = br.readLine();
			if (lineData != null && lineData.equals("")) {
				lineData = br.readLine();
				if (lineData == null) {
					return false;
				}
				if (!lineData.startsWith("NODE"))
					return false;
			}

			if (lineData == null) {
				return false;
			}

			int nodeNO = readNodeHeader(lineData);
			br.readLine();// ------------------------------
			if (lineData.startsWith("NODE"))
				readNodeTestInfo(br, nodeNO);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void readNodeTestInfo(BufferedReader br, int nodeNO) {
		try {
			String data;
			data = br.readLine();
			// [1] 193 96.50 200
			while (data != null && !data.equals("")) {
				// System.out.println(data);
				int beg = data.indexOf("[") + 1;
				int end = data.indexOf("]");
				int srcNO = Integer.parseInt(data.substring(beg, end));
				String info = data.substring(end + 1, data.length());
				StringTokenizer st = new StringTokenizer(info);
				recvPacketsNums[nodeNO - 1][srcNO - 1] = Double.parseDouble(st
						.nextToken().trim());
				conns[nodeNO - 1][srcNO - 1] = Double.parseDouble(st
						.nextToken().trim());

				try {
					lastSeqnos[nodeNO - 1][srcNO - 1] = Integer.parseInt(st
							.nextToken().trim());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

				data = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int readNodeHeader(String lineData) {
		int preIndex = lineData.indexOf("NODE");
		int sufIndex = lineData.indexOf("(");
		int nodeNo = Integer.parseInt(lineData
				.substring(preIndex + 4, sufIndex).trim());
		String positionStr = lineData.substring(sufIndex + 1,
				lineData.length() - 2);
		StringTokenizer st = new StringTokenizer(positionStr);
		Position position = new Position();
		position.setX(Double.parseDouble(st.nextToken().trim()));
		position.setY(Double.parseDouble(st.nextToken().trim()));
		positions[nodeNo - 1] = position;
		return nodeNo;
	}

	public void readNodes(BufferedReader br) {
		for (int i = 0; i < nodeNum; i++) {
			System.out.println("Read Nodes");
			boolean hasNode = readNode(br);
			if (!hasNode)
				break;
		}
	}

	public void readExperimentHeader(BufferedReader br, String curLine) {
		try {
			String lineData = curLine;
			this.setName(getExperimentParam(lineData, ":", null));
			lineData = br.readLine();
			this.setDate(getExperimentParam(lineData, ":", null));
			lineData = br.readLine();// PARAMETERS:
			lineData = br.readLine();
			this.setTotalPacketNum(Integer.parseInt(getExperimentParam(
					lineData, ":", null).trim()));
			lineData = br.readLine();
			this.setPacketSize(Integer.parseInt(getExperimentParam(lineData,
					":", "bytes").trim()));
			lineData = br.readLine();
			this.setPeriodTime(Integer.parseInt(getExperimentParam(lineData,
					":", "miliseconds").trim()));
			lineData = br.readLine();
			this.setPowerSetting(Integer.parseInt(getExperimentParam(lineData,
					":", "units").trim()));
			lineData = br.readLine();
			this.setNodeNum(Integer.parseInt(getExperimentParam(lineData, ":",
					null).trim()));
			lineData = br.readLine();
			/** **************************** */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getExperimentParam(String lineData, String prefix,
			String suffix) {
		int preIndex = lineData.indexOf(prefix);
		if (suffix != null) {
			int sufIndex = lineData.lastIndexOf(suffix);
			// System.out.println(lineData.substring(preIndex+1, sufIndex));
			return lineData.substring(preIndex + 1, sufIndex);
		} else {
			// System.out.println(lineData.substring(preIndex+1,
			// lineData.length()));
			return lineData.substring(preIndex + 1, lineData.length());
		}
	}
}
