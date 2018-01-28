/************************************************************************************
 *																*					
 *   "Copyright (c) 2004, 2005 The University of Southern California"						
 *   All rights reserved.					
 *																*					
 *   Permission to use, copy, modify, and distribute all components of 
 *   this software and its documentation for any purpose, without fee, 
 *   and without written agreement is hereby granted, 
 *   provided that the above copyright notice, the following two paragraphs 
 *   and the names in the credits appear in all copies of this software.		
 *											
 *   NO REPRESENTATIONS ARE MADE ABOUT THE SUITABILITY OF THE SCRIPT FOR ANY		
 *   PURPOSE. IT IS PROVIDED "AS IS" WITHOUT EXPRESS OR IMPLIED WARRANTY. NO 
 *   LIABILITY IS ASSUMED BY THE DEVELOPERS.
 *											
 *   Author:		Marco Zuniga
 *   Director: Prof. Bhaskar Krishnamachari
 *   Autonomous Networks Reseach Group, University of Southern California
 *   http://ceng.usc.edu/~anrg
 *   Contact: marcozun@usc.edu
 *
 *   Previous Version: 1.0  2004/10/04
 *   Current  Version: 1.1
 *   Date last modified:	2005/12/17 marcozun						
 *																*			
 *											
 *   Description:									
 *		This file contains the code that generates an instance of 
 *		the Link Layer Model						
 *											
 *************************************************************************************/
package NHSensor.NHSensorSim.link;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

/***********************************************************
 * 
 * Class Name: InputVariables
 * 
 * Description: contains the values of the parameters from the input file.
 * 
 ************************************************************/

class InputVariables {

	// Channel parameters
	double n; // path loss exponent
	double sigma; // standard deviation shadowing variance
	double pld0; // distance reference
	double d0; // reference distance
	// Radio parameters
	int mod; // modulation scheme
	int enc; // encoding scheme
	double pout; // radio output power
	double pn; // noise floor
	double pre; // preamble length
	double fra; // frame length
	// Topology parameters
	int numNodes; // number of nodes
	int top; // topolgy option
	double grid; // grid unit
	double Xterr; // X dimension of Terrain
	double Yterr; // Y dimension of Terrain
	String topFile; // file name with nodes' position
	// output data
	int matlab; // output in MATLAB format
	// data directly derived from input file
	double area; // area of the terrain
	// covariance matrix
	double s11;
	double s12;
	double s21;
	double s22;

	InputVariables() { // Constructor
		n = 0;
		sigma = 0;
		pld0 = 0;
		d0 = 0;
		mod = 0;
		enc = 0;
		pout = 0;
		pn = 0;
		pre = 0;
		fra = 0;
		numNodes = 0;
		top = 0;
		Xterr = 0;
		Yterr = 0;
		topFile = "";
		matlab = 0;
		area = 0;
		s11 = 0;
		s12 = 0;
		s21 = 0;
		s22 = 0;
	}

}

/***********************************************************
 * 
 * Class Name: OutputVariables
 * 
 * Description: contains the values of the results printed in output file:
 * nodes' position and packet reception rate
 * 
 ************************************************************/

class OutputVariables {

	double[] nodePosX; // X coordinate
	double[] nodePosY; // Y coordinate
	double[] outputpower; // output power
	double[] noisefloor; // noise floor
	double[][] gen; // general double dimensional array for rssi, Pe and prr

	OutputVariables(int numNodes) { // Constructor

		nodePosX = new double[numNodes];
		nodePosY = new double[numNodes];
		outputpower = new double[numNodes];
		noisefloor = new double[numNodes];
		gen = new double[numNodes][numNodes];
	}

}

/***********************************************************
 * 
 * Class Name: LinkLayerModel
 * 
 * Description: creates an instance of the Link Layer model
 * 
 ************************************************************/

class LinkLayerModel {

	public static void main(String args[]) {

		// input parameters use inVar
		InputVariables inVar = new InputVariables();
		// parse input file
		readFile(args[0], inVar);
		// output parameters use outVar
		OutputVariables outVar = new OutputVariables(inVar.numNodes);
		// create topology
		System.out.print("Topology ...\t\t\t");
		obtainTopology(inVar, outVar);
		System.out.println("done");
		// obtain output power and noise floor
		System.out.print("Radio Pt and Pn ...\t\t");
		obtainRadioPtPn(inVar, outVar);
		System.out.println("done");
		// based on distance, obtain rssi for all the links
		System.out.print("Received Signal Strength ...\t");
		obtainRSSI(inVar, outVar);
		System.out.println("done");
		// based on rssi, obtain prob. of error for all the links
		System.out.print("Probability of Error ...\t");
		obtainProbError(inVar, outVar);
		System.out.println("done");
		// based on prob. of error, obtain packet reception rate for all the
		// links
		System.out.print("Packet Reception Rate ...\t");
		obtainPRR(inVar, outVar);
		System.out.println("done");
		// provide Matrix result
		System.out.print("Printing Output File ...\t");
		printFile(args[1], inVar, outVar);
		System.out.println("done");
	}

	/********************************************************
	 * Method Name: readFile
	 * 
	 * Description: parse input file and store values in inVar, do some security
	 * checks
	 *********************************************************/

	static boolean readFile(String inputFile, InputVariables var) {

		String thisLine;
		StringTokenizer st;

		// open input file
		try {
			FileInputStream fin = new FileInputStream(inputFile);
			try {
				// DataInputStream myInput = new DataInputStream(fin);
				BufferedReader myInput = new BufferedReader(
						new InputStreamReader(fin));

				try {
					// parse the file
					while ((thisLine = myInput.readLine()) != null) {

						if (!thisLine.equals("") && !thisLine.startsWith("%")) {
							st = new StringTokenizer(thisLine, " =;\t");
							String key = st.nextToken();
							String value = st.nextToken();

							if (key.equals("PATH_LOSS_EXPONENT")) {
								var.n = Double.valueOf(value).doubleValue();
								if (var.n < 0) {
									System.out
											.println("Error: value of PATH_LOSS_EXPONENT must be positive");
									System.exit(1);
								}
							} else if (key
									.equals("SHADOWING_STANDARD_DEVIATION")) {
								var.sigma = Double.valueOf(value).doubleValue();
								if (var.sigma < 0) {
									System.out
											.println("Error: value of SHADOWING_STANDARD_DEVIATION must be positive");
									System.exit(1);
								}
							} else if (key.equals("PL_D0")) {
								var.pld0 = Double.valueOf(value).doubleValue();
								if (var.pld0 < 0) {
									System.out
											.println("Error: value of PL_D0 must be positive");
									System.exit(1);
								}
							} else if (key.equals("D0")) {
								var.d0 = Double.valueOf(value).doubleValue();
								if (var.pld0 <= 0) {
									System.out
											.println("Error: value of D0 must be greater equal than zero");
									System.exit(1);
								}
							} else if (key.equals("MODULATION")) {
								var.mod = Integer.parseInt(value);
							} else if (key.equals("ENCODING")) {
								var.enc = Integer.parseInt(value);
							} else if (key.equals("OUTPUT_POWER")) {
								var.pout = Double.valueOf(value).doubleValue();
							} else if (key.equals("NOISE_FLOOR")) {
								var.pn = Double.valueOf(value).doubleValue();
							} else if (key.equals("S11")) {
								var.s11 = Double.valueOf(value).doubleValue();
							} else if (key.equals("S12")) {
								var.s12 = Double.valueOf(value).doubleValue();
							} else if (key.equals("S21")) {
								var.s21 = Double.valueOf(value).doubleValue();
							} else if (key.equals("S22")) {
								var.s22 = Double.valueOf(value).doubleValue();
							} else if (key.equals("PREAMBLE_LENGTH")) {
								var.pre = Integer.parseInt(value);
								if (var.pre < 0) {
									System.out
											.println("Error: value of PREAMBLE_LENGTH must be positive");
									System.exit(1);
								}
							} else if (key.equals("FRAME_LENGTH")) {
								var.fra = Integer.parseInt(value);
								if (var.fra < 0) {
									System.out
											.println("Error: value of FRAME_LENGTH must be positive");
									System.exit(1);
								}
							} else if (key.equals("NUMBER_OF_NODES")) {
								var.numNodes = Integer.parseInt(value);
								if (var.numNodes < 0) {
									System.out
											.println("Error: value of NUMBER_OF_NODES must be positive");
									System.exit(1);
								}
							} else if (key.equals("TOPOLOGY")) {
								var.top = Integer.parseInt(value);
							} else if (key.equals("GRID_UNIT")) {
								var.grid = Double.valueOf(value).doubleValue();
							} else if (key.equals("TOPOLOGY_FILE")) {
								var.topFile = value;
							} else if (key.equals("TERRAIN_DIMENSIONS_X")) {
								var.Xterr = Double.valueOf(value).doubleValue();
							} else if (key.equals("TERRAIN_DIMENSIONS_Y")) {
								var.Yterr = Double.valueOf(value).doubleValue();
								var.area = var.Xterr * var.Yterr;
							} else if (key.equals("MATLAB_FORMAT")) {
								var.matlab = Integer.parseInt(value);
								if ((var.matlab < 0) | (var.matlab > 1)) {
									System.out
											.println("Error: value of MATLAB_FORMAT must be either 0 or 1");
									System.exit(1);
								}
							} else {
								System.out
										.println("Error: undefined parameter "
												+ key
												+ ", please review your input file");
								System.exit(1);
							}
						}
					} // end while loop
				} catch (Exception e) {
					System.out.println("Error1: " + e);
					System.exit(1);
				}

			} // end try
			catch (Exception e) {
				System.out.println("Error2: " + e);
				System.exit(1);
			}

		} // end try
		catch (Exception e) {
			System.out.println("Error Failed to Open file " + inputFile + e);
			System.exit(1);
		}

		return true;

	}

	/********************************************************
	 * Method Name: obtainTopology
	 * 
	 * Description: create topology
	 *********************************************************/

	static boolean obtainTopology(InputVariables inVar, OutputVariables outVar) {

		Random rand = new Random();
		int i, j;
		int sqrtNumNodes, nodesX;
		double cellArea, cellLength;
		double Xdist, Ydist, dist;
		boolean wrongPlacement;

		switch (inVar.top) {
		case 1: // GRID
			if (inVar.grid < inVar.d0) {
				System.out
						.println("Error: value of GRID_UNIT must be greater than D0");
				System.exit(1);
			}
			sqrtNumNodes = (int) Math.sqrt(inVar.numNodes);
			if (sqrtNumNodes != Math.sqrt(inVar.numNodes)) {
				System.out
						.println("Error: on GRID topology, NUMBER_OF_NODES should be the square of a natural number");
				System.exit(1);
			}
			for (i = 0; i < inVar.numNodes; i = i + 1) {
				outVar.nodePosX[i] = (i % sqrtNumNodes) * inVar.grid;
				outVar.nodePosY[i] = (i / sqrtNumNodes) * inVar.grid;
			}
			break;
		case 2: // UNIFORM
			if ((inVar.Xterr < 0) | (inVar.Yterr < 0)) {
				System.out
						.println("Error: values of TERRAIN_DIMENSIONS must be positive");
				System.exit(1);
			}
			cellLength = Math.sqrt(inVar.area / inVar.numNodes);
			nodesX = (int) Math.ceil(inVar.Xterr / cellLength);
			cellLength = inVar.Xterr / nodesX;
			// 1.4 (below) is an arbitrary number chosen to decrease the
			// probability
			// that nodes get closer than D0 to one another.
			if (cellLength < (inVar.d0 * 1.4)) {
				System.out
						.println("Error: on UNIFORM topology, density is too high, increase physical terrain");
				System.exit(1);
			}
			for (i = 0; i < inVar.numNodes; i = i + 1) {
				outVar.nodePosX[i] = (i % nodesX) * cellLength
						+ rand.nextDouble() * cellLength;
				outVar.nodePosY[i] = (i / nodesX) * cellLength
						+ rand.nextDouble() * cellLength;
				wrongPlacement = true;
				while (wrongPlacement) {
					for (j = 0; j < i; j = j + 1) {
						Xdist = outVar.nodePosX[i] - outVar.nodePosX[j];
						Ydist = outVar.nodePosY[i] - outVar.nodePosY[j];
						// distance between a given pair of nodes
						dist = Math.pow((Xdist * Xdist + Ydist * Ydist), 0.5);
						if (dist < inVar.d0) {
							outVar.nodePosX[i] = (i % nodesX) * cellLength
									+ rand.nextDouble() * cellLength;
							outVar.nodePosY[i] = (i / nodesX) * cellLength
									+ rand.nextDouble() * cellLength;
							wrongPlacement = true;
							break;
						}
					}
					if (j == i) {
						wrongPlacement = false;
					}
				}
			}
			break;
		case 3: // RANDOM
			if ((inVar.Xterr < 0) | (inVar.Yterr < 0)) {
				System.out
						.println("Error: values of TERRAIN_DIMENSIONS must be positive");
				System.exit(1);
			}
			cellLength = Math.sqrt(inVar.area / inVar.numNodes);
			// 1.4 (below) is an arbitrary number chosen to decrease the
			// probability
			// that nodes get closer than D0 to one another.
			if (cellLength < (inVar.d0 * 1.4)) {
				System.out
						.println("Error: on RANDOM topology, density is too high, increase physical terrain");
				System.exit(1);
			}
			for (i = 0; i < inVar.numNodes; i = i + 1) {
				outVar.nodePosX[i] = rand.nextDouble() * inVar.Xterr;
				outVar.nodePosY[i] = rand.nextDouble() * inVar.Yterr;
				wrongPlacement = true;
				while (wrongPlacement) {
					for (j = 0; j < i; j = j + 1) {
						Xdist = outVar.nodePosX[i] - outVar.nodePosX[j];
						Ydist = outVar.nodePosY[i] - outVar.nodePosY[j];
						// distance between a given pair of nodes
						dist = Math.pow((Xdist * Xdist + Ydist * Ydist), 0.5);
						if (dist < inVar.d0) {
							outVar.nodePosX[i] = rand.nextDouble()
									* inVar.Xterr;
							outVar.nodePosY[i] = rand.nextDouble()
									* inVar.Yterr;
							wrongPlacement = true;
							break;
						}
					}
					if (j == i) {
						wrongPlacement = false;
					}
				}
			}
			break;
		case 4: // FILE
			readTopologyFile(inVar.topFile, inVar, outVar);
			correctTopology(inVar, outVar);
			break;
		default:
			System.out
					.println("Error: topology is not correct, please check TOPOLOGY in the input file");
			System.exit(1);
		}

		return true;
	}

	/***************************************************************
	 * Method Name: correctTopology
	 * 
	 * Description: checks that created/provided topology does not have
	 * inter_node distances less than D0 meter
	 ****************************************************************/

	static boolean correctTopology(InputVariables inVar, OutputVariables outVar) {
		Random rand = new Random();
		int i, j;
		double Xdist, Ydist, dist, avgDecay;

		for (i = 0; i < inVar.numNodes; i = i + 1) {
			for (j = i + 1; j < inVar.numNodes; j = j + 1) {
				Xdist = outVar.nodePosX[i] - outVar.nodePosX[j];
				Ydist = outVar.nodePosY[i] - outVar.nodePosY[j];
				// distance between a given pair of nodes
				dist = Math.pow((Xdist * Xdist + Ydist * Ydist), 0.5);
				if (dist < inVar.d0) {
					System.out.println("Error: file " + inVar.topFile
							+ " contains inter_node distances less than one.");
					System.exit(1);
				}
			}
		}
		return true;
	}

	/***************************************************************
	 * Method Name: obtainPtPn
	 * 
	 * Description: obtain output power and noise floor for all nodes in the
	 * network
	 ****************************************************************/

	static boolean obtainRadioPtPn(InputVariables inVar, OutputVariables outVar) {
		Random rand = new Random();
		int i, j;
		double t11, t12, t21, t22;
		double rn1, rn2;

		t11 = Math.sqrt(inVar.s11);
		t12 = inVar.s12 / Math.sqrt(inVar.s11);
		t21 = 0;
		t22 = Math.sqrt((inVar.s11 * inVar.s22 - Math.pow(inVar.s12, 2))
				/ inVar.s11);

		for (i = 0; i < inVar.numNodes; i = i + 1) {
			rn1 = rand.nextGaussian();
			rn2 = rand.nextGaussian();
			outVar.noisefloor[i] = inVar.pn + t11 * rn1;
			outVar.outputpower[i] = inVar.pout + t12 * rn1 + t22 * rn2;
		}
		return true;
	}

	/***************************************************************
	 * Method Name: obtainRSSI
	 * 
	 * Description: obtain rssi for all the links in the network use topology
	 * information and channel parameters
	 ****************************************************************/

	static boolean obtainRSSI(InputVariables inVar, OutputVariables outVar) {
		Random rand = new Random();
		int i, j;
		double Xdist, Ydist, dist, avgDecay;

		for (i = 0; i < inVar.numNodes; i = i + 1) {
			for (j = i + 1; j < inVar.numNodes; j = j + 1) {
				Xdist = outVar.nodePosX[i] - outVar.nodePosX[j];
				Ydist = outVar.nodePosY[i] - outVar.nodePosY[j];
				// distance between a given pair of nodes
				dist = Math.pow((Xdist * Xdist + Ydist * Ydist), 0.5);
				// mean decay dependent on distance
				avgDecay = -inVar.pld0 - 10 * inVar.n
						* (Math.log(dist / inVar.d0) / Math.log(10.0))
						+ (rand.nextGaussian() * inVar.sigma);
				// assymetric links are given by running two different
				// R.V.s for each unidirectional link.
				// NOTE: this approach is not accurate, assymetry is due mainly
				// to
				// to hardware imperfections and not for assymetric paths
				outVar.gen[i][j] = outVar.outputpower[i] + avgDecay;
				outVar.gen[j][i] = outVar.outputpower[j] + avgDecay;
			}
		}
		return true;

	}

	/********************************************************
	 * Method Name: obtainProbError
	 * 
	 * Description: probability of error is obtained based on rssi and
	 * modulation scheme
	 *********************************************************/

	static boolean obtainProbError(InputVariables inVar, OutputVariables outVar) {
		int i, j;

		for (i = 0; i < inVar.numNodes; i = i + 1) {
			for (j = 0; j < inVar.numNodes; j = j + 1) {

				if (i == j) {
					outVar.gen[i][j] = 0.00;
				} else {
					// convert SNR from dBm to Watts
					double snr = (Math.pow(10,
							((outVar.gen[i][j] - outVar.noisefloor[j]) / 10))) / .64;
					// division by .64 above (snr) converts from Eb/No to RSSI
					// this is specific for each radio (read paper: Data-rate(R)
					// / Bandwidth-noise(B))
					switch (inVar.mod) {
					case 1: // NCASK
						outVar.gen[i][j] = 0.5 * (Math.exp(-0.5 * snr) + Q(Math
								.sqrt(snr)));
						break;
					case 2: // ASK
						outVar.gen[i][j] = Q(Math.sqrt(snr / 2));
						break;
					case 3: // NCFSK
						outVar.gen[i][j] = 0.5 * Math.exp(-0.5 * snr);
						break;
					case 4: // FSK
						outVar.gen[i][j] = Q(Math.sqrt(snr));
						break;
					case 5: // BPSK
						outVar.gen[i][j] = Q(Math.sqrt(2 * snr));
						break;
					case 6: // DPSK
						outVar.gen[i][j] = 0.5 * Math.exp(-snr);
						break;
					default:
						System.out
								.println("Error: modulation is not correct, please check MODULATION in the input file");
						System.exit(1);
					}
				}
			}
		}
		return true;
	}

	/********************************************************
	 * Method Name: obtainPRR
	 * 
	 * Description: obtain packet reception rate based on probability of error
	 * and encoding scheme
	 *********************************************************/

	static boolean obtainPRR(InputVariables inVar, OutputVariables outVar) {
		int i, j;
		double preSeq;

		for (i = 0; i < inVar.numNodes; i = i + 1) {
			for (j = 0; j < inVar.numNodes; j = j + 1) {

				if (i == j) {
					outVar.gen[i][j] = 1.00;
				} else {
					preSeq = Math.pow((1 - outVar.gen[i][j]), 8 * inVar.pre);
					switch (inVar.enc) {
					case 1: // NRZ
						outVar.gen[i][j] = preSeq
								* Math.pow((1 - outVar.gen[i][j]),
										8 * (inVar.fra - inVar.pre));
						break;
					case 2: // 4B5B
						outVar.gen[i][j] = preSeq
								* Math.pow((1 - outVar.gen[i][j]),
										8 * (inVar.fra - inVar.pre) * 1.25);
						break;
					case 3: // Manchester
						outVar.gen[i][j] = preSeq
								* Math.pow((1 - outVar.gen[i][j]),
										8 * (inVar.fra - inVar.pre) * 2.0);
						break;
					case 4: // SECDED
						outVar.gen[i][j] = preSeq
								* Math
										.pow((Math.pow((1 - outVar.gen[i][j]),
												8) + 8
												* outVar.gen[i][j]
												* Math.pow(
														(1 - outVar.gen[i][j]),
														7)),
												(inVar.fra - inVar.pre) * 3.0);
						break;
					default:
						System.out
								.println("Error: encoding is not correct, please check ENCODING in the input file");
						System.exit(1);
					}
				}
			}
		}

		return true;
	}

	/********************************************************
	 * Method Name: printFile
	 * 
	 * Description: provides a Matrix with the PRR
	 *********************************************************/

	static boolean printFile(String outputFile, InputVariables inVar,
			OutputVariables outVar) {

		int i, j;

		DecimalFormat posFormat = new DecimalFormat("##0.00");

		try {
			FileOutputStream fout = new FileOutputStream(outputFile);
			try {
				PrintStream myOutput = new PrintStream(fout);
				if (inVar.matlab == 0) {
					myOutput.println("NODES_PLACEMENT");
					for (i = 0; i < inVar.numNodes; i = i + 1) {
						myOutput.println(i + " = ("
								+ posFormat.format(outVar.nodePosX[i]) + ", "
								+ posFormat.format(outVar.nodePosY[i])
								+ ", 0.0);");
					}
				} else {
					myOutput.println("NODES_PLACEMENT = [");
					for (i = 0; i < inVar.numNodes; i = i + 1) {
						myOutput.println(i + " "
								+ posFormat.format(outVar.nodePosX[i]) + " "
								+ posFormat.format(outVar.nodePosY[i]));
					}
					myOutput.println("];");
				}
				myOutput.println("");
				myOutput.println("PRR_MATRIX = [ ");
				for (i = 0; i < inVar.numNodes; i = i + 1) {
					for (j = 0; j < inVar.numNodes; j = j + 1) {
						myOutput.print(posFormat.format(outVar.gen[i][j])
								+ "  ");
					}
					myOutput.println();
				}
				myOutput.println("];");
			} catch (Exception e) {
				System.out.println("Error3: " + e);
				System.exit(1);
			}
		} catch (Exception e) {
			System.out.println("Error Failed to Write file: " + outputFile + e);
			System.exit(1);
		}

		return true;
	}

	/********************************************************
	 * Method Name: readTopologyFile
	 * 
	 * Description: obtains node placement when FILE option chosen
	 *********************************************************/

	static boolean readTopologyFile(String inputFile, InputVariables inVar,
			OutputVariables outVar) {

		String thisLine;
		StringTokenizer st;
		int counter = 0;

		try {
			FileInputStream fin = new FileInputStream(inputFile);
			try {
				// DataInputStream myInput = new DataInputStream(fin);
				BufferedReader myInput = new BufferedReader(
						new InputStreamReader(fin));

				try {

					while ((thisLine = myInput.readLine()) != null) {

						if (!thisLine.equals("") && !thisLine.startsWith("%")
								&& !thisLine.startsWith("t")
								&& !thisLine.startsWith("]")) {
							st = new StringTokenizer(thisLine, " \t");
							int node = Integer.parseInt(st.nextToken());
							double x = Double.valueOf(st.nextToken())
									.doubleValue();
							double y = Double.valueOf(st.nextToken())
									.doubleValue();
							outVar.nodePosX[node] = x;
							outVar.nodePosY[node] = y;
							counter++;
						}
					}
					if (counter != inVar.numNodes) {
						System.out
								.println("Number of nodes in file "
										+ inputFile
										+ " does not agree with value entered in NUMBER_OF_NODES");
						System.exit(1);
					}
				} // end try
				catch (Exception e) {
					System.out.println("Error4: " + e);
					System.exit(1);
				}

			} // end try
			catch (Exception e) {
				System.out.println("Error5: " + e);
				System.exit(1);
			}

		} // end try
		catch (Exception e) {
			System.out.println("Error: Failed to Open TOPOLOGY_FILE "
					+ inputFile + e);
			System.exit(1);
		}

		return true;
	}

	/********************************************************
	 * Method Name: Q
	 * 
	 * Description: provides an approximation of the Q function
	 *********************************************************/

	static double Q(double z)

	{
		double a1 = 0.127414796;
		double a2 = -0.142248368;
		double a3 = 0.7107068705;
		double a4 = -0.7265760135;
		double a5 = 0.5307027145;
		double B = 0.231641888;
		double t = 1 / (1 + B * z);

		if (z >= 0) {
			return ((a1 * t + a2 * Math.pow(t, 2) + a3 * Math.pow(t, 3) + a4
					* Math.pow(t, 4) + a5 * Math.pow(t, 5)) * Math.exp(-Math
					.pow(z, 2) / 2));
		} else {
			System.out
					.println("Error in Q function: argument Z must be greater equal than 0");
			System.exit(1);
			return -1;
		}
	}

}

public class LinkAndTopologyGenerator {

}
