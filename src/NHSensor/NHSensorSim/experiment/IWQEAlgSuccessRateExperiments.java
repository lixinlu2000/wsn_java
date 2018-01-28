package NHSensor.NHSensorSim.experiment;

import java.util.Hashtable;
import java.util.Vector;

public class IWQEAlgSuccessRateExperiments {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * AllParam properties: int networkID; int nodeNum; double
		 * queryRegionRate; double gridWidthRate; int queryMessageSize; int
		 * answerMessageSize; double networkWidth = 450; double networkHeight =
		 * 450; int queryAndPatialAnswerSize; int resultSize;
		 */

		AllParam param;
		IWQEAlgSuccessRateExperiment experiment;

		Class paramClass = AllParam.class;

		IntSequence networkIDs = new IntSequence(1, 10, 2);
		IntSequence nodeNums = new IntSequence(400, 800, 200);
		DoubleSequence queryRegionRates = new DoubleSequence(
				Constants.IWQE_GRID_WIDTH_RATE);
		DoubleSequence gridWidthRates = new DoubleSequence(
				Constants.IWQE_GRID_WIDTH_RATE);
		IntSequence queryMessageSizes = new IntSequence(1, 10, 2);
		IntSequence answerMessageSizes = new IntSequence(1, 10, 2);
		IntSequence queryAndPatialAnswerSizes = new IntSequence(1, 10, 2);
		IntSequence resultSizes = new IntSequence(1, 10, 2);

		Hashtable sequences = new Hashtable();
		sequences.put("networkID", networkIDs.getData());
		sequences.put("nodeNum", nodeNums.getData());
		sequences.put("queryRegionRate", queryRegionRates.getData());
		sequences.put("gridWidthRate", gridWidthRates.getData());
		sequences.put("queryMessageSize", queryMessageSizes.getData());
		sequences.put("answerMessageSize", answerMessageSizes.getData());
		sequences.put("queryAndPatialAnswerSize", queryAndPatialAnswerSizes
				.getData());
		sequences.put("resultSize", resultSizes.getData());

		try {
			ParamsGenerator paramsGenerator = new ParamsGenerator(paramClass,
					sequences);
			Vector params = paramsGenerator.getParams();
			for (int i = 0; i < params.size(); i++) {
				param = (AllParam) params.elementAt(i);
				experiment = new IWQEAlgSuccessRateExperiment(param);
				experiment.run();
				System.out.println(experiment.isSuccess());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
