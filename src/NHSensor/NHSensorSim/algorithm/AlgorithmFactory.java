package NHSensor.NHSensorSim.algorithm;

import javax.swing.JOptionPane;
import NHSensor.NHSensorSim.cds.CDSAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.core.TAGAlg;
import NHSensor.NHSensorSim.end.CVDAlg;
import NHSensor.NHSensorSim.end.DDFSAlg;
import NHSensor.NHSensorSim.end.EndAlg;
import NHSensor.NHSensorSim.query.HistogramQuery;
import NHSensor.NHSensorSim.query.KNNQuery;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.tools.LicenseTool;
import NHSensor.NHSensorSim.tools.UTool;

public class AlgorithmFactory {
	private static AlgorithmFactory af = null;

	private AlgorithmFactory() {
	}

	public static AlgorithmFactory getInstance() {
		if (af == null) {
			af = new AlgorithmFactory();
			return af;
		} else
			return af;
	}

	public Algorithm createSimpleAlgorithm(String algName, String name,
			QueryBase query, Network network) {
		Simulator simulator = new Simulator();
		simulator.addHandleEventListener();
		Param param = new Param();
		Statistics statistics = new Statistics(name);
		return this.createAlgorithm(algName, name, query, network, simulator,
				param, statistics);
	}

	public Algorithm createGuiAlgorithm(String algName, String name,
			QueryBase query, Network network) {
		Simulator simulator = new Simulator();
		simulator.addHandleAndTraceEventListener();
		Param param = new Param();
		Statistics statistics = new Statistics(name);
		return this.createAlgorithm(algName, name, query, network, simulator,
				param, statistics);
	}

	public Algorithm createAlgorithm(String algName, String name,
			QueryBase queryBase, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		LicenseTool licenseTool = LicenseTool.getLicenseToolInstance();
		if(!(licenseTool.checkDateAndVersionValidity())) {
			JOptionPane.showMessageDialog(null, "试用期已过，请购买正式版", "Atos-SensorSim", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0); 
		}
		UTool.checkKey();

		if (algName.equalsIgnoreCase(SWinFloodAlg.NAME)) {
			Query query = (Query) queryBase;
			return new SWinFloodAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(FullFloodAlg.NAME)) {
			Query query = (Query) queryBase;
			return new FullFloodAlg(query, network, simulator, param, name,
					statistics);
		}  else if (algName.equalsIgnoreCase(GRTAlg.NAME)) {
			Query query = (Query) queryBase;
			return new GRTAlg(query, network, simulator, param, name,
					statistics);
		}else if (algName.equalsIgnoreCase(SWinDepthAlg.NAME)) {
			Query query = (Query) queryBase;
			return new SWinDepthAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(EcstaAlg.NAME)) {
			Query query = (Query) queryBase;
			return new EcstaAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(TAGAlg.NAME)) {
			Query query = (Query) queryBase;
			return new TAGAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(GreedyTAGAlg.NAME)) {
			Query query = (Query) queryBase;
			return new GreedyTAGAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(IWQEAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(GStarAlg.NAME)) {
			Query query = (Query) queryBase;
			return new GStarAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(PEVAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new PEVAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(TAGNewAlg.NAME)) {
			Query query = (Query) queryBase;
			return new TAGNewAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(E2STAExAlg.NAME)) {
			Query query = (Query) queryBase;
			return new E2STAExAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(E2STAFinalAlg.NAME)) {
			Query query = (Query) queryBase;
			return new E2STAFinalAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(E2STAHyperbolaAlg.NAME)) {
			Query query = (Query) queryBase;
			return new E2STAHyperbolaAlg(query, network, simulator, param, name,
					statistics);
		}else if (algName.equalsIgnoreCase(E2STAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new E2STAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(HSAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new HSAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(HSAApproximateAlg.NAME)) {
			Query query = (Query) queryBase;
			return new HSAApproximateAlg(query, network, simulator, param, name,
					statistics);
		}  else if (algName.equalsIgnoreCase(GPSRAlg.NAME)) {
			Query query = (Query) queryBase;
			return new GPSRAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(GSAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new GSAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(DGSAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new DGSAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(DGSANewAlg.NAME)) {
			Query query = (Query) queryBase;
			return new DGSANewAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RSAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RSAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(GPSRAttachmentAlg.NAME)) {
			Query query = (Query) queryBase;
			return new GPSRAttachmentAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(EndAlg.NAME)) {
			Query query = (Query) queryBase;
			return new EndAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(CDSAlg.NAME)) {
			Query query = (Query) queryBase;
			return new CDSAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(CVDAlg.NAME)) {
			Query query = (Query) queryBase;
			return new CVDAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(DDFSAlg.NAME)) {
			Query query = (Query) queryBase;
			return new DDFSAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(IKNNAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new IKNNAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(IKNNExAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new IKNNExAlg(query, network, simulator, param, name,
					statistics);
		}
		if (algName.equalsIgnoreCase(GKNNAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new GKNNAlg(query, network, simulator, param, name,
					statistics);
		}
		if (algName.equalsIgnoreCase(GKNNExAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new GKNNExAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName
				.equalsIgnoreCase(GKNNUseGridTraverseRingEventAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new GKNNUseGridTraverseRingEventAlg(query, network,
					simulator, param, name, statistics);
		}
		if (algName.equalsIgnoreCase(CKNNUseGBAFailureAwareAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new CKNNUseGBAFailureAwareAlg(query, network, simulator,
					param, name, statistics);
		}
		if (algName.equalsIgnoreCase(EKNNUseGBAFailureAwareAlg.NAME)) {
			KNNQuery query = (KNNQuery) queryBase;
			return new EKNNUseGBAFailureAwareAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(DefaultTreeAlg.NAME)) {
			return new DefaultTreeAlg(queryBase, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(HistogramNaiveAlg.NAME)) {
			HistogramQuery query = (HistogramQuery) queryBase;
			return new HistogramNaiveAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(HistogramWithCacheAlg.NAME)) {
			HistogramQuery query = (HistogramQuery) queryBase;
			return new HistogramWithCacheAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(LSAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new LSAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RestaFarAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RestaFarAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RestaFarMulticastQueryAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RestaFarMulticastQueryAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(RestaIDCFarMulticastQueryAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RestaIDCFarMulticastQueryAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(RestaIDCFarAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RestaIDCFarAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RestaIDCNearAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RestaIDCNearAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RESAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RESAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RESAUseGridAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RESAUseGridAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RESACAAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RESACAAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RESACAALifeTimeAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RESACAALifeTimeAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(RESACAAUseGridAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RESACAAUseGridAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(RESACAAUseGridLifeTimeAlg.NAME)) {
			Query query = (Query) queryBase;
			return new RESACAAUseGridLifeTimeAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(IWQEUseICAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEUseICAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(IWQERetransmitUseICAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQERetransmitUseICAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName.equalsIgnoreCase(IWQEIdealUseICAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEIdealUseICAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName
				.equalsIgnoreCase(IWQEIdealUseICForRESACompareAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEIdealUseICForRESACompareAlg(query, network,
					simulator, param, name, statistics);
		} else if (algName.equalsIgnoreCase(IWQEIdealUseICFailureAwareAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEIdealUseICFailureAwareAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName
				.equalsIgnoreCase(IWQEIdealUseICAndLinkAwareRoutingAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEIdealUseICAndLinkAwareRoutingAlg(query, network,
					simulator, param, name, statistics);
		} else if (algName
				.equalsIgnoreCase(IWQEIdealUseICFailureAwareForSuccessRateAlg.NAME)) {
			Query query = (Query) queryBase;
			return new IWQEIdealUseICFailureAwareForSuccessRateAlg(query,
					network, simulator, param, name, statistics);
		} else if (algName.equalsIgnoreCase(ESAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(ESAOldAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAOldAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(ESAUseGAAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAUseGAAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(ESAUseGBAFailureAwareAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAUseGBAFailureAwareAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(ESAUseGBAFailureAwareFinalAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAUseGBAFailureAwareFinalAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(CSAUseCDAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new CSAUseCDAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(CSAUseCDAFailureAwareAlg.NAME)) {
			Query query = (Query) queryBase;
			return new CSAUseCDAFailureAwareAlg(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(CSAUseCDAFailureAwareAlgOld.NAME)) {
			Query query = (Query) queryBase;
			return new CSAUseCDAFailureAwareAlgOld(query, network, simulator,
					param, name, statistics);
		} else if (algName.equalsIgnoreCase(ESAUseGBAAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAUseGBAAlg(query, network, simulator, param, name,
					statistics);
		} else if (algName.equalsIgnoreCase(ESAUseGBALinkAwareAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAUseGBALinkAwareAlg(query, network, simulator, param,
					name, statistics);
		} else if (algName
				.equalsIgnoreCase(ESAUseGBAFailureAwareForSuccessRateAlg.NAME)) {
			Query query = (Query) queryBase;
			return new ESAUseGBAFailureAwareForSuccessRateAlg(query, network,
					simulator, param, name, statistics);
		} else if (algName.equalsIgnoreCase("")) {
			// Query query = (Query)queryBase;
			// return new (query, network, simulator, param, name, statistics);
		} else {
			try {
				Class algClass = Class.forName(algName);
				Algorithm alg = (Algorithm)algClass.newInstance();
				alg.setName(name);
				alg.setNetwork(network);
				alg.setQuery(queryBase);
				alg.setSimulator(simulator);
				alg.setParam(param);
				alg.setStatistics(statistics);
				return alg;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
