package NHSensor.NHSensorSim.test;

import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.Constants;
import NHSensor.NHSensorSim.experiment.DoubleSequence;
import NHSensor.NHSensorSim.experiment.IntSequence;
import NHSensor.NHSensorSim.experiment.ParamsGenerator;
import NHSensor.NHSensorSim.util.Util;

public class BeanToXMLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class paramClass = AllParam.class;
		AllParam param;

		IntSequence networkIDs = new IntSequence(1, 10, 1);
		IntSequence nodeNums = new IntSequence(200, 400, 200);
		DoubleSequence queryRegionRates = new DoubleSequence(0.1, 0.1, 0.1);
		DoubleSequence gridWidthRates = new DoubleSequence(
				Constants.IWQE_GRID_WIDTH_RATE);

		ItineraryAlgParam itineraryAlgParam = new ItineraryAlgParam(4);
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
			Util.writeObject("result\\RSA\\params.txt", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
