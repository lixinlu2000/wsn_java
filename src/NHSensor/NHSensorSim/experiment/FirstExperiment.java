package NHSensor.NHSensorSim.experiment;

import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.papers.RSA.DGSANewAlgAverageGridHeightExperiment;

public class FirstExperiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * int networkID; int nodeNum; double queryRegionRate; double
		 * gridWidthRate = Math.sqrt(2)/2;
		 */

		AllParam param;
		DGSANewAlgAverageGridHeightExperiment experiment;

		Class paramClass = NI_NN_QRR_GW_Param.class;

		IntSequence networkIDs = new IntSequence(1, 10, 2);
		IntSequence nodeNums = new IntSequence(400, 800, 200);
		DoubleSequence queryRegionRates = new DoubleSequence(0.1, 1, 0.2);
		DoubleSequence gridWidthRates = new DoubleSequence(0.5, 1, 0.2);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());

		try {
			ParamsGenerator paramsGenerator = new ParamsGenerator(paramClass,
					sequences);
			Vector params = paramsGenerator.getParams();
			for (int i = 0; i < params.size(); i++) {
				param = (AllParam) params.elementAt(i);
				experiment = new DGSANewAlgAverageGridHeightExperiment(param);
				experiment.run();
				System.out.println(experiment.getAverageGridHeight());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
