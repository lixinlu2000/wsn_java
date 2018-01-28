package NHSensor.NHSensorSim.experiment;

import java.util.Hashtable;
import java.util.Vector;

import org.jfree.data.xy.XYDataset;

public class ExperimentParamResultPairCollections {
	private Vector collections = new Vector();
	private Hashtable hashtable = new Hashtable();

	public ExperimentParamResultPairCollections() {
	}

	public ExperimentParamResultPairCollections(String[] collectionNames,
			boolean[] algRunOrNot) {
		ExperimentParamResultPairCollection c;

		for (int i = 0; i < collectionNames.length; i++) {
			if (algRunOrNot[i]) {
				c = new ExperimentParamResultPairCollection(collectionNames[i]);
				this.add(c);
				hashtable.put(collectionNames[i], c);
			}
		}
	}

	public ExperimentParamResultPairCollections(String[] collectionNames) {
		ExperimentParamResultPairCollection c;

		for (int i = 0; i < collectionNames.length; i++) {
			c = new ExperimentParamResultPairCollection(collectionNames[i]);
			this.add(c);
			hashtable.put(collectionNames[i], c);
		}
	}

	public ExperimentParamResultPairCollections(String collectionName1) {
		ExperimentParamResultPairCollection c;
		c = new ExperimentParamResultPairCollection(collectionName1);
		this.add(c);
		hashtable.put(collectionName1, c);
	}

	public ExperimentParamResultPairCollections(String collectionName1,
			String collectionName2) {
		String[] collectionNames = new String[] { collectionName1,
				collectionName2 };

		ExperimentParamResultPairCollection c;

		for (int i = 0; i < collectionNames.length; i++) {
			c = new ExperimentParamResultPairCollection(collectionNames[i]);
			this.add(c);
			hashtable.put(collectionNames[i], c);
		}

	}

	public ExperimentParamResultPairCollections(String collectionName1,
			String collectionName2, String collectionName3) {
		String[] collectionNames = new String[] { collectionName1,
				collectionName2, collectionName3 };

		ExperimentParamResultPairCollection c;

		for (int i = 0; i < collectionNames.length; i++) {
			c = new ExperimentParamResultPairCollection(collectionNames[i]);
			this.add(c);
			hashtable.put(collectionNames[i], c);
		}
	}

	public void add(ExperimentParamResultPairCollection c) {
		this.collections.add(c);
		this.hashtable.put(c.getName(), c);
	}

	public void add(String collectionName, ExperimentParamResultPair pair) {
		ExperimentParamResultPairCollection c = (ExperimentParamResultPairCollection) this.hashtable
				.get(collectionName);
		if (c == null)
			return;
		else {
			c.add(pair);
		}
	}

	public ExperimentParamResultPairCollection get(int index) {
		return (ExperimentParamResultPairCollection) this.collections
				.get(index);
	}

	public int size() {
		return this.collections.size();
	}

	/*
	 * fix the value of the field xFieldName and calculate the average value of
	 * the field yFieldName
	 */
	public XYDataset generateXYDataset(String xFieldName, String yFieldName)
			throws Exception {
		return this.generateXYDataset(xFieldName, yFieldName, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate the average value of
	 * the field yFieldName
	 */
	public XYDataset generateXYDataset(String xFieldName, String yFieldName,
			boolean filterNonSuccessed) throws Exception {
		AggregationExperimenResultAnalyser analyser = new AggregationExperimenResultAnalyser(
				this, xFieldName, yFieldName, filterNonSuccessed);
		return analyser.generateXYDataset();
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public XYDataset generateXYDataset(String xFieldName, String yFieldName,
			String minimizedFieldName) throws Exception {
		return this.generateXYDataset(xFieldName, yFieldName,
				minimizedFieldName, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public XYDataset generateXYDataset(String xFieldName, String yFieldName,
			String minimizedFieldName, boolean filterNonSuccessed)
			throws Exception {
		MinimizeExperimenResultAnalyser analyser = new MinimizeExperimenResultAnalyser(
				this, xFieldName, yFieldName, minimizedFieldName,
				filterNonSuccessed);
		return analyser.generateXYDataset();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		ExperimentParamResultPairCollection c;

		for (int i = 0; i < this.size(); i++) {
			c = this.get(i);
			sb.append(c.toString());
			if (i != this.size() - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public String toXYString(String xFieldName, String yFieldName) {
		return this.toXYString(xFieldName, yFieldName, true);
	}

	public String toXYString(String xFieldName, String yFieldName,
			boolean filterNonSuccessed) {
		StringBuffer sb = new StringBuffer();
		XYDataset xYDataset;

		try {
			xYDataset = this.generateXYDataset(xFieldName, yFieldName,
					filterNonSuccessed);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		for (int seryIndex = 0; seryIndex < xYDataset.getSeriesCount(); seryIndex++) {
			sb.append(xYDataset.getSeriesKey(seryIndex));
			sb.append("\n");
			for (int index = 0; index < xYDataset.getItemCount(seryIndex); index++) {
				sb.append(xYDataset.getXValue(seryIndex, index));
				sb.append(" ");
				sb.append(xYDataset.getYValue(seryIndex, index));
				if (index != xYDataset.getItemCount(seryIndex) - 1) {
					sb.append("\n");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public String toXYString(String xFieldName, String yFieldName,
			String minimizedFieldName) {
		StringBuffer sb = new StringBuffer();
		XYDataset xYDataset;

		try {
			xYDataset = this.generateXYDataset(xFieldName, yFieldName,
					minimizedFieldName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		for (int seryIndex = 0; seryIndex < xYDataset.getSeriesCount(); seryIndex++) {
			sb.append(xYDataset.getSeriesKey(seryIndex));
			sb.append("\n");
			for (int index = 0; index < xYDataset.getItemCount(seryIndex); index++) {
				sb.append(xYDataset.getXValue(seryIndex, index));
				sb.append(" ");
				sb.append(xYDataset.getYValue(seryIndex, index));
				if (index != xYDataset.getItemCount(seryIndex) - 1) {
					sb.append("\n");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
