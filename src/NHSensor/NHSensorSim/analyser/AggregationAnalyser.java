package NHSensor.NHSensorSim.analyser;

import java.util.Enumeration;
import java.util.Hashtable;

import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollection;
import NHSensor.NHSensorSim.util.ReflectUtil;

/*
 * NOTE: field value should be numeric!!!
 *       by default filterNonSuccessed is true
 */
public class AggregationAnalyser implements Analyser {
	ExperimentParamResultPairCollection pairCollection;
	String fieldName1;
	String fieldName2;
	Hashtable xys = new Hashtable();
	Hashtable xSize = new Hashtable();
	int type;
	public static final int SUM = 0;
	public static final int COUNT = 1;
	public static final int AVERAGE = 2;
	public static final int MAX = 3;
	public static final int MIN = 4;
	boolean filterNonSuccessed = true;

	public boolean isFilterNonSuccessed() {
		return filterNonSuccessed;
	}

	public void setFilterNonSuccessed(boolean filterNonSuccessed) {
		this.filterNonSuccessed = filterNonSuccessed;
	}

	public AggregationAnalyser(int type,
			ExperimentParamResultPairCollection pairCollection,
			String fieldName1, String fieldName2) {
		this(type, pairCollection, fieldName1, fieldName2, true);
	}

	public AggregationAnalyser(int type,
			ExperimentParamResultPairCollection pairCollection,
			String fieldName1, String fieldName2, boolean filterNonSuccessed) {
		this.type = type;
		this.pairCollection = pairCollection;
		this.fieldName1 = fieldName1;
		this.fieldName2 = fieldName2;
		this.filterNonSuccessed = filterNonSuccessed;
	}

	protected void addPairToResult(Double x, Double y) {
		Double yOld = (Double) this.xys.get(x);
		Double xSizeOld = (Double) this.xSize.get(x);

		if (yOld == null) {
			this.xys.put(x, y);
			this.xSize.put(x, new Double(1));
		} else {
			Double yNew = new Double(yOld.doubleValue() + y.doubleValue());
			this.xys.put(x, yNew);
			Double xSizeNew = new Double(xSizeOld.doubleValue() + 1);
			this.xSize.put(x, xSizeNew);
		}
	}

	public void analyse() {
		ExperimentParamResultPair pair;
		double x, y;
		int isSuccess = 0;

		try {
			for (int i = 0; i < pairCollection.size(); i++) {
				pair = this.pairCollection.get(i);

				// filter non successed result
				if (this.isFilterNonSuccessed()) {
					isSuccess = ((Number) (ReflectUtil.getFieldValueEx(pair
							.getResult(), "isSuccess"))).intValue();
					if (isSuccess == 0)
						continue;
				}

				if (ReflectUtil.hasField(pair.getParam(), fieldName1)) {
					x = ((Number) (ReflectUtil.getFieldValueEx(pair.getParam(),
							fieldName1))).doubleValue();
				} else if (ReflectUtil.hasField(pair.getResult(), fieldName1)) {
					x = ((Number) (ReflectUtil.getFieldValueEx(
							pair.getResult(), fieldName1))).doubleValue();
				} else {
					throw new Exception("field " + fieldName1
							+ " doesnot exist");
				}

				if (ReflectUtil.hasField(pair.getParam(), fieldName2)) {
					y = ((Number) (ReflectUtil.getFieldValueEx(pair.getParam(),
							fieldName2))).doubleValue();
				} else if (ReflectUtil.hasField(pair.getResult(), fieldName2)) {
					y = ((Number) (ReflectUtil.getFieldValueEx(
							pair.getResult(), fieldName2))).doubleValue();
				} else {
					throw new Exception("field " + fieldName2
							+ " doesnot exist");
				}

				this.addPairToResult(new Double(x), new Double(y));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Hashtable getResult() {
		Hashtable result = new Hashtable();

		Enumeration e = this.xys.keys();
		Double x, y, xSize;
		Double newY = new Double(0);

		while (e.hasMoreElements()) {
			x = (Double) e.nextElement();
			y = (Double) this.xys.get(x);
			xSize = (Double) this.xSize.get(x);
			if (this.type == AggregationAnalyser.AVERAGE) {
				newY = new Double(y.doubleValue() / xSize.doubleValue());
			}
			result.put(x, newY);
		}
		return result;
	}
}
