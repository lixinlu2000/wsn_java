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

public class SensorSimWithNetwork {
	private QueryBase query;
	private Network network;
	private Simulator simulator;
	private Param param;
	private Hashtable algs = new Hashtable();

	public SensorSimWithNetwork(QueryBase query, Network network, Simulator simulator,
			Param param) {
		this.query = query;
		this.network = network;
		this.simulator = simulator;
		this.param = param;
	}

	public SensorSimWithNetwork(Network network) {
		this.network = network;
		this.query = null;
		this.simulator = new Simulator();
		this.param = new Param();
	}

	public static SensorSimWithNetwork createSensorSim(Network network, double queryRegionRate) {
		Node orig = network.get2LRTNode();

		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = network.getWidth()*sqrtQueryRegionRate;
		double queryHeight = network.getHeigth()*sqrtQueryRegionRate;
		Rect queryRect = new Rect((network.getWidth() - queryWidth) / 2,
				(network.getWidth() + queryWidth) / 2, (network.getHeigth() - queryHeight) / 2,
				(network.getHeigth() + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSimWithNetwork(query, network, simulator, param);
	}
	


	public static SensorSimWithNetwork createSensorSim(Network network, Query query) {
		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSimWithNetwork(query, network, simulator, param);
	}

	public static SensorSimWithNetwork createSensorSim(Network network) {
		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSimWithNetwork(null, network, simulator, param);
	}

	public static SensorSimWithNetwork createKNNSensorSim(Network network, int k) {
		double width = network.getWidth(); double height=network.getHeigth(); int nodeNum=network.getNodeNum();

		Node orig = network.get2LRTNode();

		QueryBase query = new KNNQuery(orig, network.getRect().getCentre(), k);

		Simulator simulator = new Simulator();
		Param param = new Param();

		return new SensorSimWithNetwork(query, network, simulator, param);
	}

	public static SensorSimWithNetwork createSensorSim(Network network, double queryRegionRate,
			double radioRange) {
		double width = network.getWidth(); double height=network.getHeigth(); int nodeNum=network.getNodeNum();
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
		return new SensorSimWithNetwork(query, network, simulator, param);
	}
	
	public static SensorSimWithNetwork createSensorSim(Network network, double queryRegionRate,
			double radioRange, double initialEnergy) {
		double width = network.getWidth(); double height=network.getHeigth(); int nodeNum=network.getNodeNum();
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
		return new SensorSimWithNetwork(query, network, simulator, param);
	}


	public static SensorSimWithNetwork createSensorSimHasAMiddleTopNode(Network network, double queryRegionRate,
			double radioRange, double initialEnergy) {
		double width = network.getWidth(); double height=network.getHeigth(); int nodeNum=network.getNodeNum();
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
		return new SensorSimWithNetwork(query, network, simulator, param);
	}

	public static SensorSimWithNetwork createSensorSimHasAMiddleTopNode(Network network, double queryRegionRate,
			double radioRange) {
		double width = network.getWidth(); double height=network.getHeigth(); int nodeNum=network.getNodeNum();
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
		return new SensorSimWithNetwork(query, network, simulator, param);
	}

	public static SensorSimWithNetwork createSensorSim(NHSensor.NHSensorSim.papers.LKNN.TraverseRingExperimentParam seParam, Network network) {
		Simulator simulator = new Simulator();
		Param param = new Param();
		param.setQUERY_MESSAGE_SIZE(seParam.getQueryMessageSize());
		param.setANSWER_SIZE(seParam.getAnswerMessageSize());
		param.setRADIO_RANGE(seParam.getRadioRange());
		return new SensorSimWithNetwork(null, network, simulator, param);
	}

	public static SensorSimWithNetwork createSensorSim(SensornetExperimentParam seParam, Network network) {
		Simulator simulator = new Simulator();
		Param param = new Param();
		param.setQUERY_MESSAGE_SIZE(seParam.getQueryMessageSize());
		param.setANSWER_SIZE(seParam.getAnswerMessageSize());
		return new SensorSimWithNetwork(null, network, simulator, param);
	}
	
	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.HSA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.E2STA.lifetime.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		double initialEnergy = param.getInitialEnergy();
		return SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange, initialEnergy);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.PEVA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.E2STA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		int queryPositonID = param.getNodeFailModelID();
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange);
		Query query = (Query)sensorSim.getQuery();
		Node orig = sensorSim.getNetwork().getNodeAboveRect(query.getRect(), queryPositonID);
		query.setOrig(orig);
		return sensorSim;
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.EST.lifeTime.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		double initialEnergy = param.getInitialEnergy();
		return SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange, initialEnergy);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.EST.totalDataSize.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.EST.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSimHasAMiddleTopNode(network, queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.ESA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.CSA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.EDC.CDA_GBA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.LSA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.RESA.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}
	
	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.RESALifeTime.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		double initialEnergy = param.getInitialEnergy();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange, initialEnergy);
	}

	public static SensorSimWithNetwork createSensorSim(
			NHSensor.NHSensorSim.papers.RESA_LSA_IWQE.AllParam param, Network network) {
		double queryRegionRate = param.getQueryRegionRate();
		double radioRange = param.getRadioRange();
		return SensorSimWithNetwork.createSensorSim(network,
				queryRegionRate, radioRange);
	}

	public static SensorSimWithNetwork createKNNSensorSim(AllParam param, Network network) {
		double radioRange = param.getRadioRange();
		int k = param.getK();
		int queryMessageSize = param.getQueryMessageSize();
		int answerMessageSize = param.getAnswerMessageSize();
		return SensorSimWithNetwork.createKNNSensorSim(network,
				k, queryMessageSize, answerMessageSize, radioRange);
	}

	public static SensorSimWithNetwork createKNNSensorSim(Network network, int k, int queryMessageSize,
			int answerMessageSize, double radioRange) {
		Node orig = network.get2LRTNode();

		KNNQuery query = new KNNQuery(orig, network.getRect().getCentre(), k);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		param.setQUERY_MESSAGE_SIZE(queryMessageSize);
		param.setANSWER_SIZE(answerMessageSize);
		return new SensorSimWithNetwork(query, network, simulator, param);
	}

	public static SensorSimWithNetwork createHistogramSensorSim(Network network, 
			HistogramInfo histogramInfo, int sampleEpoch, int sampleTimes,
			double radioRange) {
		Node orig = network.get2LRTNode();

		HistogramQuery query = new HistogramQuery(orig, histogramInfo,
				sampleEpoch, sampleTimes);

		Simulator simulator = new Simulator();
		Param param = new Param(radioRange);
		return new SensorSimWithNetwork(query, network, simulator, param);
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

	public static SensorSimWithNetwork createSensorSim(String sceneName) {
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

		return new SensorSimWithNetwork(query, network, simulator, param);
	}

	public static SensorSimWithNetwork createKNNSensorSim(String sceneName) {
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
		return new SensorSimWithNetwork(kNNQuery, network, simulator, param);

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
