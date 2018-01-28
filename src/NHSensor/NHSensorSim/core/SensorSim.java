package NHSensor.NHSensorSim.core;

import java.util.Enumeration;
import java.util.Hashtable;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.AlgorithmFactory;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.SensornetExperimentParam;
import NHSensor.NHSensorSim.query.HistogramInfo;
import NHSensor.NHSensorSim.query.HistogramQuery;
import NHSensor.NHSensorSim.query.KNNQuery;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.util.Util;

public class SensorSim {
	private QueryBase query;
	private Network network;
	private Simulator simulator;
	private Param param;
	private Hashtable algs = new Hashtable();

	public SensorSim(QueryBase query, Network network, Simulator simulator,
			Param param) {
		this.query = query;
		this.network = network;
		this.simulator = simulator;
		this.param = param;
	}

	public SensorSim(Network network) {
		this.network = network;
		this.query = null;
		this.simulator = new Simulator();
		this.param = new Param();
	}

	public static SensorSim createSensorSim(int networkID, double width,
			double height, int nodeNum, double queryRegionRate) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}
	
	public static SensorSim createGridSensorSim(double width,
			double height, int xgap, int ygap, double queryRegionRate) {
		Network network = Network.createGridNetwork(width, height, xgap, ygap);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}
	
	public static SensorSim createGridSensorSim(double width,
			double height, int xgap, int ygap, int nodeNum, double queryRegionRate) {
		Network network = Network.createGridNetwork(width, height, xgap, ygap);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}



	public static SensorSim createSensorSim(Network network, Query query) {
		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createIntelLabSensorSim(double queryRegionRate) {
		Network network = Network.intelLabNetwork();
		Node orig = network.get2LRTNode();
		double width = network.getWidth();
		double height = network.getHeigth();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createSensorSim(int networkID, double width,
			double height, int nodeNum) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(null, network, simulator, param);
	}

	public static SensorSim createKNNSensorSim(int networkID, double width,
			double height, int nodeNum, int k) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Node orig = network.get2LRTNode();

		QueryBase query = new KNNQuery(orig, network.getRect().getCentre(), k);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createSensorSim(int networkID, double width,
			double height, int nodeNum, double queryRegionRate,
			double radioRange) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		return new SensorSim(query, network, simulator, param);
	}
	
	public static SensorSim createSensorSim(int networkID, double width,
			double height, int nodeNum, double queryRegionRate,
			double radioRange, double initialEnergy) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		param.setINIT_ENERGY(initialEnergy);
		return new SensorSim(query, network, simulator, param);
	}


	public static SensorSim createSensorSimWithHoles(int networkID, double width,
			double height, int nodeNum, double queryRegionRate,
			double radioRange, 	int holeNumber, double maxHoleRadius, int holeModelID) {
		Network network = Network.createRandomNetworkWithHole(width, height, nodeNum,
				networkID, holeNumber, maxHoleRadius, holeModelID);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createSensorSimHasAMiddleTopNode(int networkID,
			double width, double height, int nodeNum, double queryRegionRate,
			double radioRange, double initialEnergy) {
		Network network = Network.createRandomNetworkHasAMiddleTopNode(width,
				height, nodeNum, networkID);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		param.setINIT_ENERGY(initialEnergy);
		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createSensorSimHasAMiddleTopNode(int networkID,
			double width, double height, int nodeNum, double queryRegionRate,
			double radioRange) {
		Network network = Network.createRandomNetworkHasAMiddleTopNode(width,
				height, nodeNum, networkID);
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createSensorSim(NHSensor.NHSensorSim.papers.LKNN.TraverseRingExperimentParam seParam) {
		double width = seParam.getNetworkWidth();
		double height = seParam.getNetworkHeight();
		int networkID = seParam.getNetworkID();
		int nodeNum = seParam.getNodeNum();
		int holeNumber = seParam.getHoleNumber();
		double maxHoleRadius = seParam.getMaxHoleRadius();
		int holeModelID = seParam.getHoleModelID();

		Network network = Network.createRandomNetworkWithHole(width, height, nodeNum,
				networkID, holeNumber, maxHoleRadius, holeModelID);

		Simulator simulator = new Simulator();
		Param param = new Param();
		param.setQUERY_MESSAGE_SIZE(seParam.getQueryMessageSize());
		param.setANSWER_SIZE(seParam.getAnswerMessageSize());
		param.setRADIO_RANGE(seParam.getRadioRange());
		return new SensorSim(null, network, simulator, param);
	}

	public static SensorSim createSensorSim(SensornetExperimentParam seParam) {
		Network network = Network.createRandomNetwork(
				seParam.getNetworkWidth(), seParam.getNetworkHeight(), seParam
						.getNodeNum(), seParam.getNetworkID());
		Simulator simulator = new Simulator();
		Param param = new Param();
		param.setQUERY_MESSAGE_SIZE(seParam.getQueryMessageSize());
		param.setANSWER_SIZE(seParam.getAnswerMessageSize());
		return new SensorSim(null, network, simulator, param);
	}
	
	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.HSA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.E2STA.lifetime.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		double initialEnergy = param.getInitialEnergy();
		return SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange, initialEnergy);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.PEVA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.E2STA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		int queryPositonID = param.getNodeFailModelID();
		SensorSim sensorSim = SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange);
		Query query = (Query)sensorSim.getQuery();
		Node orig = sensorSim.getNetwork().getNodeAboveRect(query.getRect(), queryPositonID);
		query.setOrig(orig);
		return sensorSim;
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.EST.lifeTime.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		double initialEnergy = param.getInitialEnergy();
		return SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange, initialEnergy);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.EST.totalDataSize.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.EST.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSimHasAMiddleTopNode(networkID, width,
				height, nodeNum, queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.ESA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.CSA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSimWithHoles(networkID, width, height, nodeNum,
				queryRegionRate, radioRange, param.getHoleNumber(), param.getMaxHoleRadius(),
				param.getHoleModelID());
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.EDC.CDA_GBA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.LSA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.RESA.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange);
	}
	
	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.RESALifeTime.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		double initialEnergy = param.getInitialEnergy();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange, initialEnergy);
	}

	public static SensorSim createSensorSim(
			NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		double queryRegionRate = param.getQueryRegionRate();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		return SensorSim.createSensorSim(networkID, width, height, nodeNum,
				queryRegionRate, radioRange);
	}

	public static SensorSim createKNNSensorSim(AllParam param) {
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		int k = param.getK();
		int queryMessageSize = param.getQueryMessageSize();
		int answerMessageSize = param.getAnswerMessageSize();
		return SensorSim.createKNNSensorSim(networkID, width, height, nodeNum,
				k, queryMessageSize, answerMessageSize, radioRange);
	}

	public static SensorSim createKNNSensorSim(int networkID, double width,
			double height, int nodeNum, int k, int queryMessageSize,
			int answerMessageSize, double radioRange) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Node orig = network.get2LRTNode();

		KNNQuery query = new KNNQuery(orig, network.getRect().getCentre(), k);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		param.setQUERY_MESSAGE_SIZE(queryMessageSize);
		param.setANSWER_SIZE(answerMessageSize);
		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createHistogramSensorSim(int networkID,
			double width, double height, int nodeNum,
			HistogramInfo histogramInfo, int sampleEpoch, int sampleTimes,
			double radioRange) {
		Network network = Network.createRandomNetwork(width, height, nodeNum,
				networkID);
		Node orig = network.get2LRTNode();

		HistogramQuery query = new HistogramQuery(orig, histogramInfo,
				sampleEpoch, sampleTimes);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		return new SensorSim(query, network, simulator, param);
	}

	/*
	public static SensorSim createGridSensorSim(double width, double height,
			int xgap, int ygap, double queryRegionRate) {
		Network network = Network.createGridNetwork(width, height, xgap, ygap);
		Node orig = network.get2LRTNode();

		double queryRegionArea = network.getRect().area() * queryRegionRate;
		double queryRegionSize = Math.sqrt(queryRegionArea);
		Rect queryRect = new Rect((width - queryRegionSize) / 2,
				(width + queryRegionSize) / 2, (height - queryRegionSize) / 2,
				(height + queryRegionSize) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSim(query, network, simulator, param);
	}
	*/

	public static SensorSim createSensorSim(String sceneName) {
		Network network = null;
		Node orig = null;
		Rect queryRect = null;
		Query query = null;
		Simulator simulator;
		Param param;

		simulator = new Simulator();

		param = new Param();

		if (sceneName.equalsIgnoreCase("Grid1")) {
			network = Network.createGridNetwork(400, 400, 1, 1);
			orig = network.get2LRTNode();
			queryRect = new Rect(100, 200, 100, 200);
		} else if (sceneName.equalsIgnoreCase("Line1")) {
			network = Network.createMiddleLineNetwork(1000, 1000, 11);
			orig = network.getFirstNode();
			queryRect = new Rect(200, 600, 400, 600);
		} else if (sceneName.equalsIgnoreCase("Random1")) {
			network = Network.createRandomNetwork(4, 4, 25);
			orig = network.getLBNode();
			queryRect = new Rect(0, 4, 0, 4);
		} else if (sceneName.equalsIgnoreCase("Random2")) {
			network = Network.createRandomNetwork(500, 400, 250);
			orig = network.getLBNode();
			queryRect = new Rect(0, 0, 0, 0);
		} else if (sceneName.equalsIgnoreCase("GPSRTest")) {
			network = Network.createGPSRTestNetwork(800, 600, 200, 9,
					new Position(400, 300), 30);
			param.setRADIO_RANGE(100);
			orig = network.getLBNode();
			queryRect = new Rect(300, 500, 300, 500);
		} else if (sceneName.equalsIgnoreCase("RandomWithCenterHole")) {
			network = Network.createRandomNetworkWithHole(800, 600, 200,
					new Position(400, 300), 50);
			param.setRADIO_RANGE(100);
			orig = network.getLBNode();
			queryRect = new Rect(300, 500, 300, 500);
		} else {
		}
		query = new Query(orig, queryRect);

		return new SensorSim(query, network, simulator, param);
	}

	public static SensorSim createKNNSensorSim(String sceneName) {
		Network network = null;
		Node orig = null;
		KNNQuery kNNQuery = null;
		Position centre = null;
		int k = 1;
		Simulator simulator;
		Param param;

		simulator = new Simulator();
		param = new Param();

		if (sceneName.equalsIgnoreCase("KNNTest")) {
			network = Network.createRandomNetwork(800, 600, 1000);
			orig = network.getLBNode();
			centre = new Position(300, 300);
			k = 29;
		} else {
		}
		kNNQuery = new KNNQuery(orig, centre, k);
		return new SensorSim(kNNQuery, network, simulator, param);

	}

	public Algorithm addAlgorithm(String algName) {
		return this.addAlgorithm(algName, algName);
	}

	public Algorithm generateAlgorithm(String algName, String name) {
		Statistics statistics = new Statistics(name);
		return AlgorithmFactory.getInstance().createAlgorithm(algName, name,
				query, network, simulator, param, statistics);
	}

	public Algorithm generateAlgorithm(String algName) {
		return this.generateAlgorithm(algName, algName);
	}

	public Algorithm addAlgorithm(String algName, String name) {
		Hashtable ht = (Hashtable) algs.get(algName);
		if (ht == null) {
			ht = new Hashtable();
			algs.put(algName, ht);
		}

		Algorithm alg = (Algorithm) ht.get(name);
		if (alg == null) {
			Statistics statistics = new Statistics(name);
			alg = AlgorithmFactory.getInstance().createAlgorithm(algName, name,
					query, network, simulator, param, statistics);
			if (alg != null)
				ht.put(name, alg);
		}
		return alg;
	}
	
	public void run() {
		Hashtable ht;
		Algorithm alg;
		for (Enumeration e1 = algs.elements(); e1.hasMoreElements();) {
			ht = (Hashtable) e1.nextElement();
			for (Enumeration e2 = ht.elements(); e2.hasMoreElements();) {
				alg = (Algorithm) e2.nextElement();
				alg.init();
				alg.run();
			}
		}
	}

	public void printStatistic() {
		Hashtable ht;
		Algorithm alg;
		for (Enumeration e1 = algs.elements(); e1.hasMoreElements();) {
			ht = (Hashtable) e1.nextElement();
			for (Enumeration e2 = ht.elements(); e2.hasMoreElements();) {
				alg = (Algorithm) e2.nextElement();
				alg.getStatistics().printResult();
			}
		}
	}

	public String getAnswers() {
		Hashtable ht;
		Algorithm alg;
		StringBuffer sb = new StringBuffer("Answers{");

		for (Enumeration e1 = algs.elements(); e1.hasMoreElements();) {
			ht = (Hashtable) e1.nextElement();
			for (Enumeration e2 = ht.elements(); e2.hasMoreElements();) {
				alg = (Algorithm) e2.nextElement();
				sb.append("\n");
				sb.append(alg.getName());
				sb.append("{\n");
				sb.append(Util.toString(alg.getAns()));
				sb.append("}");
			}
		}
		sb.append("\n}");
		return sb.toString();
	}

	public boolean addAll() {
		return true;
	}

	public Algorithm getAlgorithm(String algName) {
		return this.getAlgorithm(algName, algName);
	}

	public Algorithm getAlgorithm(String algName, String name) {
		Hashtable ht = (Hashtable) this.algs.get(algName);
		if (ht != null) {
			return (Algorithm) ht.get(name);
		}
		return null;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public QueryBase getQuery() {
		return query;
	}

	public void setQuery(QueryBase query) {
		this.query = query;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public Hashtable getAlgs() {
		return algs;
	}
}
