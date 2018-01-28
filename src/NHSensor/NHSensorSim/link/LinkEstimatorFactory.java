package NHSensor.NHSensorSim.link;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.experiment.LinkQualityExperiment;
import NHSensor.NHSensorSim.experiment.LinkQualityExperiments;

public class LinkEstimatorFactory {
	private static LinkEstimatorFactory instance = new LinkEstimatorFactory();

	private LinkEstimatorFactory() {

	}

	public static LinkEstimatorFactory getInstance() {
		return LinkEstimatorFactory.instance;
	}

	public static IdealLinkEstimator getIdealLinkEstimator() {
		return new IdealLinkEstimator();
	}

	public static ModelLinkEstimator getModelLinkEstimator(Network network,
			double pt) {
		double pld0 = 55;
		double d0 = 1;
		double n = 4;
		double sigma = 4;
		double pn = -105;

		double encodingRatio = 1;
		double frameLength = 50;

		LinkModel linkModel = new LinkModel(d0, n, pld0, pt, pn, sigma,
				encodingRatio, frameLength);
		return new ModelLinkEstimator(linkModel, network);

	}

	public static ArrayLinkEstimator getArrayLinkEstimator(Network network,
			double pt, int id) {
		ModelLinkEstimator mle = LinkEstimatorFactory.getModelLinkEstimator(
				network, pt);
		return mle.createArrayLinkEstimator(id);
	}

	public static ArrayLinkEstimator getArrayLinkEstimator(Network network,
			int id) {
		return LinkEstimatorFactory.getArrayLinkEstimator(network, 0, id);
	}

	public static ModelLinkEstimator getModelLinkEstimator(Network network) {
		return LinkEstimatorFactory.getModelLinkEstimator(network, 0);
	}

	public static ModelLinkEstimator getModelLinkEstimator1(Network network) {
		double pt = 25;
		double pld0 = 55;
		double d0 = 1;
		double n = 4;
		double sigma = 4;
		double pn = -105;

		double encodingRatio = 1;
		double frameLength = 50;

		LinkModel linkModel = new LinkModel(d0, n, pld0, pt, pn, sigma,
				encodingRatio, frameLength);
		return new ModelLinkEstimator(linkModel, network);
	}

	public static ArrayLinkEstimator getArrayLinkEstimator() {
		Vector experiments = LinkQualityExperiments
				.readExperimentsFromFile(new File(
						"./dataset/dataset/indoor-ceiling-array/2003_05_05/datafile-mica1-lab-power-pktsize50.txt"));
		LinkQualityExperiment lqe = (LinkQualityExperiment) experiments
				.elementAt(0);
		return new ArrayLinkEstimator(lqe.getLinkQuality());

	}

	public static ArrayLinkEstimator createArrayLinkEstimatorFromFile(File file) {
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String curLine = br.readLine();
			int nodes;

			if (curLine != null && !curLine.equals("")) {
				nodes = Integer.parseInt(curLine);
			} else {
				return null;
			}

			double[][] linkPRRs = new double[nodes][nodes];
			String[] links = new String[nodes];
			for (int i = 0; i < nodes; i++) {
				curLine = br.readLine();
				links = curLine.split("\\s+");
				if (links.length != nodes)
					return null;
				else {
					for (int j = 0; j < nodes; j++) {
						linkPRRs[i][j] = Double.parseDouble(links[j]);
					}
				}
			}
			return new ArrayLinkEstimator(linkPRRs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayLinkEstimator getArrayLinkEstimatorFromANRGFile1() {
		File file = new File("./dataset/ANRG/linkQuality/dataset1/ppr.txt");
		return LinkEstimatorFactory.createArrayLinkEstimatorFromFile(file);
	}

	public static ArrayLinkEstimator getArrayLinkEstimatorFromANRGFile2() {
		File file = new File("./dataset/ANRG/linkQuality/dataset2/ppr.txt");
		return LinkEstimatorFactory.createArrayLinkEstimatorFromFile(file);
	}

}
