package NHSensor.NHSensorSim.papers.RESA;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.experiment.Constants;
import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGeneratorEx;

public class ParamsFactory {
	int nodeNum = 320;
	// int nodeNumBegin = 240;
	//
	int nodeNumBegin = 160;
	int nodeNumEnd = 560;
	int nodeNumStep = 80;

	double queryRegionRate = 0.64;
	// double[] queryRegionRateArray = new double[]{0.36,0.49,0.64,0.81,0.9};
	//
	double[] queryRegionRateArray = new double[] { 0.4, 0.45, 0.5, 0.55, 0.6,
			0.65, 0.7, 0.75 };
	// double[] queryRegionRateArray = new
	// double[]{0.2,0.25,0.3,0.35,0.4,0.45,0.5
	// ,0.55,0.6,0.65,0.7,0.75,0.8,0.85,0.9,0.95,0.96,0.97,0.98,0.99};

	double gridWidthRate = Constants.IWQE_GRID_WIDTH_RATE;
	double gridWidthRatesBegin = 1 / 11.0;
	double gridWidthRatesEnd = 10 / 11.0;
	double gridWidthRatesStep = 1 / 11.0;

	double radioRange = 11;
	double radioRangesBegin = 10;
	double radioRangesEnd = 20;
	double radioRangesStep = 1;

	int queryMessageSize = 22;
	int queryMessageSizeBegin = 22;
	int queryMessageSizeEnd = 110;
	int queryMessageSizeStep = 22;

	int senseDataSize = 110;
	int senseDataSizeBegin = 110;
	int senseDataSizeEnd = 550;
	int senseDataSizeStep = 110;

	double networkWidth = 100;
	double networkHeight = 100;

	int networkIDBegin = 1;
	int networkIDEnd = 10;
	int networkIDStep = 1;

	int linkIDBegin = 1;
	int linkIDEnd = 20;
	int linkIDStep = 1;

	double nodeFailProbability = 0.04;
	double nodeFailProbabilityBegin = 0.01;
	double nodeFailProbabilityEnd = 0.3;
	double nodeFailProbabilityStep = 0.05;

	int k = 30;
	int kBegin = 10;
	int kEnd = 60;
	int kStep = 10;

	double pt = 0;
	double ptBegin = -20;
	double ptEnd = 5;
	double ptStep = 5;

	double pld0 = 55;
	double pld0Begin = 55;
	double pld0End = 55;
	double pld0Step = 55;

	double d0 = 1;
	double d0Begin = 1;
	double d0End = 1;
	double d0Step = 1;

	double n = 4;
	double nBegin = 4;
	double nEnd = 4;
	double nStep = 4;

	double sigma = 4;
	double sigmaBegin = 4;
	double sigmaEnd = 4;
	double sigmaStep = 4;

	double pn = -105;
	double pnBegin = -105;
	double pnEnd = -105;
	double pnStep = -105;

	double encodingRatio = 1;
	double encodingRatioBegin = 1;
	double encodingRatioEnd = 1;
	double encodingRatioStep = 1;

	double frameLength = 50;
	double frameLengthBegin = 50;
	double frameLengthEnd = 50;
	double frameLengthStep = 50;

	double preambleLength = 28;
	double preambleLengthBegin = 28;
	double preambleLengthEnd = 28;
	double preambleLengthStep = 28;
	
	double initialEnergy = 800;
	double initialEnergyBegin = 800;
	double initialEnergyEnd = 1600;
	double initialEnergyStep = 200;


	public ParamsFactory() {

	}

	public Vector variable(String variableName) throws Exception {
		String methodName = variableName + "Variable";
		Class classType = this.getClass();
		Method method = classType.getMethod(methodName, new Class[] {});
		return (Vector) (method.invoke(this, new Object[] {}));
	}

	public Vector networkIDVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector radioRangeVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRangesBegin,
				this.radioRangesEnd, this.radioRangesStep);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}
	
	public Vector initialEnergyVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergyBegin,
				this.initialEnergyEnd, this.initialEnergyStep);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}


	public Vector nodeNumVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNumBegin,
				this.nodeNumEnd, this.nodeNumStep);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector gridWidthRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryRegionRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRateArray);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector queryMessageSizeVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Vector queryMessageSizes = new Vector();
		Vector answerMessageSizes = new Vector();
		Vector queryAndPatialAnswerSizes = new Vector();
		Vector resultSizes = new Vector();

		for (int qs = this.queryMessageSizeBegin; qs <= this.queryMessageSizeEnd; qs += this.queryMessageSizeStep) {
			ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
					this.senseDataSize, qs);
			queryMessageSizes.addElement(new Integer(itineraryAlgParam
					.getQuerySize()));
			answerMessageSizes.addElement(new Integer(itineraryAlgParam
					.getAnswerSize()));
			queryAndPatialAnswerSizes.addElement(new Integer(itineraryAlgParam
					.getQueryAndPatialAnswerSize()));
			resultSizes.addElement(new Integer(itineraryAlgParam
					.getResultSize()));
		}

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes);
		sequences.put("answerMessageSize", answerMessageSizes);
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes);
		sequences.put("resultSize", resultSizes);
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("queryMessageSize", ParamsGeneratorEx.DEFAULT_GROUP_ID);
		fieldGroup.put("answerMessageSize", ParamsGeneratorEx.DEFAULT_GROUP_ID);
		fieldGroup.put("queryAndPatialAnswerSize",
				ParamsGeneratorEx.DEFAULT_GROUP_ID);
		fieldGroup.put("resultSize", ParamsGeneratorEx.DEFAULT_GROUP_ID);

		sequences.put("initialEnergy", initialEnergys.getData());
		
		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences,
					fieldGroup);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector answerMessageSizeVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		Vector queryMessageSizes = new Vector();
		Vector answerMessageSizes = new Vector();
		Vector queryAndPatialAnswerSizes = new Vector();
		Vector resultSizes = new Vector();

		for (int sds = this.senseDataSizeBegin; sds <= this.senseDataSizeEnd; sds += this.senseDataSizeStep) {
			ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(sds,
					this.queryMessageSize);
			queryMessageSizes.addElement(new Integer(itineraryAlgParam
					.getQuerySize()));
			answerMessageSizes.addElement(new Integer(itineraryAlgParam
					.getAnswerSize()));
			queryAndPatialAnswerSizes.addElement(new Integer(itineraryAlgParam
					.getQueryAndPatialAnswerSize()));
			resultSizes.addElement(new Integer(itineraryAlgParam
					.getResultSize()));
		}

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes);
		sequences.put("answerMessageSize", answerMessageSizes);
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes);
		sequences.put("resultSize", resultSizes);
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());

		Hashtable fieldGroup = new Hashtable();
		fieldGroup.put("queryMessageSize", ParamsGeneratorEx.DEFAULT_GROUP_ID);
		fieldGroup.put("answerMessageSize", ParamsGeneratorEx.DEFAULT_GROUP_ID);
		fieldGroup.put("queryAndPatialAnswerSize",
				ParamsGeneratorEx.DEFAULT_GROUP_ID);
		fieldGroup.put("resultSize", ParamsGeneratorEx.DEFAULT_GROUP_ID);
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences,
					fieldGroup);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeFailProbabilityVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(this.gridWidthRate);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbabilityBegin, this.nodeFailProbabilityEnd,
				this.nodeFailProbabilityStep);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nodeNumGridWidthRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNumBegin,
				this.nodeNumEnd, this.nodeNumStep);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector radioRangeGridWidthRateVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector ptVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.ptBegin, this.ptEnd,
				this.ptStep);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector pld0Variable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0Begin, this.pld0End,
				this.pld0Step);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector d0Variable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0Begin, this.d0End,
				this.d0Step);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector nVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.nBegin, this.nEnd,
				this.nStep);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector sigmaVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigmaBegin,
				this.sigmaEnd, this.sigmaStep);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector pnVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pnBegin, this.pnEnd,
				this.pnStep);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector encodingRatioVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(
				this.encodingRatioBegin, this.encodingRatioEnd,
				this.encodingRatioStep);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLength);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

	public Vector frameLengthVariable() {
		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(this.networkIDBegin,
				this.networkIDEnd, this.networkIDStep);
		IntSequence linkIDs = new IntSequence(this.linkIDBegin, this.linkIDEnd,
				this.linkIDStep);
		IntSequence nodeNums = new IntSequence(this.nodeNum);
		DoubleSequence queryRegionRates = new DoubleSequence(
				this.queryRegionRate);
		DoubleSequence gridWidthRates = new DoubleSequence(
				this.gridWidthRatesBegin, this.gridWidthRatesEnd,
				this.gridWidthRatesStep);
		DoubleSequence radioRanges = new DoubleSequence(this.radioRange);
		DoubleSequence nodeFailProbabilitys = new DoubleSequence(
				this.nodeFailProbability);
		IntSequence ks = new IntSequence(this.k);
		DoubleSequence networkWidths = new DoubleSequence(this.networkWidth);
		DoubleSequence networkHeights = new DoubleSequence(this.networkHeight);
		DoubleSequence initialEnergys = new DoubleSequence(this.initialEnergy);

		// link mode
		DoubleSequence pts = new DoubleSequence(this.pt);
		DoubleSequence pld0s = new DoubleSequence(this.pld0);
		DoubleSequence d0s = new DoubleSequence(this.d0);
		DoubleSequence ns = new DoubleSequence(this.n);
		DoubleSequence sigmas = new DoubleSequence(this.sigma);
		DoubleSequence pns = new DoubleSequence(this.pn);
		DoubleSequence encodingRatios = new DoubleSequence(this.encodingRatio);
		DoubleSequence frameLengths = new DoubleSequence(this.frameLengthBegin,
				this.frameLengthEnd, this.frameLengthStep);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(
				this.senseDataSize, this.queryMessageSize);
		IntSequence queryMessageSizes = new IntSequence(itineraryAlgParam
				.getQuerySize());
		IntSequence answerMessageSizes = new IntSequence(itineraryAlgParam
				.getAnswerSize());
		IntSequence queryAndPatialAnswerSizes = new IntSequence(
				itineraryAlgParam.getQueryAndPatialAnswerSize());
		IntSequence resultSizes = new IntSequence(itineraryAlgParam
				.getResultSize());

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("linkID", linkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());
		sequences.put("nodeFailProbability", nodeFailProbabilitys.getData());
		sequences.put("k", ks.getData());
		sequences.put("radioRange", radioRanges.getData());
		sequences.put("networkWidth", networkWidths.getData());
		sequences.put("networkHeight", networkHeights.getData());

		// link mode
		sequences.put("pt", pts.getData());
		sequences.put("pld0", pld0s.getData());
		sequences.put("d0", d0s.getData());
		sequences.put("n", ns.getData());
		sequences.put("sigma", sigmas.getData());
		sequences.put("pn", pns.getData());
		sequences.put("encodingRatio", encodingRatios.getData());
		sequences.put("frameLength", frameLengths.getData());
		DoubleSequence preambleLengths = new DoubleSequence(this.preambleLength);
		sequences.put("preambleLength", preambleLengths.getData());
		sequences.put("initialEnergy", initialEnergys.getData());

		ParamsGeneratorEx paramsGenerator;
		try {
			paramsGenerator = new ParamsGeneratorEx(paramClass, sequences);
			return paramsGenerator.getParams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Vector();
	}

}
