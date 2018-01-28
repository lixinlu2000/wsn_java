package NHSensor.NHSensorSim.experiment;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import org.jfree.data.xy.XYSeries;

import NHSensor.NHSensorSim.util.FormatUtil;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class ExperimentParamResultPairCollection {
	private String name;
	private Vector paramResultPairs = new Vector();

	public ExperimentParamResultPairCollection(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void add(ExperimentParamResultPair pair) {
		this.paramResultPairs.add(pair);
	}

	public void add(ExperimentParam param, ExperimentResult result) {
		this.paramResultPairs.add(new ExperimentParamResultPair(param, result));
	}

	public ExperimentParamResultPair get(int index) {
		return (ExperimentParamResultPair) this.paramResultPairs.get(index);
	}

	public int size() {
		return this.paramResultPairs.size();
	}

	public XYSeries generateXYSeries(String xFieldName, String yFieldName)
			throws Exception {
		String seriesName = this.getName();
		XYSeries xYSeries = new XYSeries(seriesName);
		ExperimentParamResultPair pair;
		Number xValue;
		Number yValue;

		for (int i = 0; i < this.size(); i++) {
			pair = this.get(i);
			xValue = (Number) ReflectUtil.getFieldValue(pair.getParam(),
					xFieldName);
			yValue = (Number) ReflectUtil.getFieldValue(pair.getResult(),
					yFieldName);
			xYSeries.add(xValue, yValue);
		}
		return xYSeries;
	}

	public SortedSet getNonSuccessPairIndexs() {
		SortedSet sortedSet = new TreeSet();
		ExperimentParamResultPair pair;
		int isSuccess;

		try {
			for (int i = 0; i < this.size(); i++) {
				pair = this.get(i);

				isSuccess = ((Number) (ReflectUtil.getFieldValueEx(pair
						.getResult(), "isSuccess"))).intValue();
				if (isSuccess == 0)
					sortedSet.add(new Integer(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sortedSet;
	}

	private class ValueCounter {
		double value;
		int counter;

		public ValueCounter(double value, int counter) {
			this.value = value;
			this.counter = counter;
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		public int getCounter() {
			return counter;
		}

		public void setCounter(int counter) {
			this.counter = counter;
		}

		public void increaseCounter() {
			this.counter++;
		}

		public void addValue(double v) {
			this.value += v;
		}

		public double getAverageValue() {
			return this.value / this.counter;
		}
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public XYSeries generateXYSeries(String xFieldName, String yFieldName,
			String minimizeFieldName, boolean filterNonSuccessed)
			throws Exception {
		String seriesName = this.getName();
		XYSeries xYSeries = new XYSeries(seriesName);
		ExperimentParamResultPair pair;
		Number xValue;
		Number yValue;
		Number minYValue;
		Number zValue;
		Number minZValue;
		Hashtable xHashtable = new Hashtable();
		Hashtable yHashtable;
		ValueCounter valueCounter;
		int isSuccess;

		for (int i = 0; i < this.size(); i++) {
			pair = this.get(i);

			if (filterNonSuccessed) {
				isSuccess = ((Number) (ReflectUtil.getFieldValueEx(pair,
						"isSuccess"))).intValue();
				if (isSuccess == 0)
					continue;
			}

			xValue = (Number) ReflectUtil.getFieldValueEx(pair, xFieldName);
			yValue = (Number) ReflectUtil.getFieldValueEx(pair, yFieldName);
			zValue = (Number) ReflectUtil.getFieldValueEx(pair,
					minimizeFieldName);

			yHashtable = (Hashtable) xHashtable.get(xValue);
			if (yHashtable == null) {
				yHashtable = new Hashtable();
				xHashtable.put(xValue, yHashtable);
				valueCounter = new ValueCounter(zValue.doubleValue(), 1);
				yHashtable.put(yValue, valueCounter);
			} else {
				valueCounter = (ValueCounter) yHashtable.get(yValue);
				if (valueCounter == null) {
					valueCounter = new ValueCounter(zValue.doubleValue(), 1);
					yHashtable.put(yValue, valueCounter);
				} else {
					valueCounter.addValue(zValue.doubleValue());
					valueCounter.increaseCounter();
				}
			}
		}

		for (Enumeration e = xHashtable.keys(); e.hasMoreElements();) {
			xValue = (Number) e.nextElement();
			yHashtable = (Hashtable) xHashtable.get(xValue);
			minYValue = new Double(Double.MAX_VALUE);
			minZValue = new Double(Double.MAX_VALUE);
			for (Enumeration e1 = yHashtable.keys(); e1.hasMoreElements();) {
				yValue = (Number) e1.nextElement();
				valueCounter = (ValueCounter) yHashtable.get(yValue);
				zValue = new Double(valueCounter.getAverageValue());
				if (zValue.doubleValue() < minZValue.doubleValue()) {
					minYValue = yValue;
					minZValue = zValue;
				}
			}
			xYSeries.add(xValue, minYValue);
		}
		return xYSeries;
	}

	public void removeElementAt(int index) {
		paramResultPairs.removeElementAt(index);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		ExperimentParamResultPair pair;
		Hashtable paramFieldValues;
		Hashtable resultFieldValues;
		Vector columnNames;
		Vector tuple;

		if (this.size() == 0) {
			return "";
		}

		sb.append(this.getName());
		sb.append("\n");
		try {
			for (int i = 0; i < this.size(); i++) {
				pair = this.get(i);
				paramFieldValues = ReflectUtil.getAllFieldsValuesEx(pair
						.getParam());
				resultFieldValues = ReflectUtil.getAllFieldsValuesEx(pair
						.getResult());

				if (i == 0) {
					columnNames = FormatUtil.keyStrings(paramFieldValues,
							resultFieldValues);
					sb.append(FormatUtil.toString(columnNames));
				}
				tuple = FormatUtil.valueStringsIncludeKey(paramFieldValues,
						resultFieldValues);
				sb.append("\n");
				sb.append(FormatUtil.toString(tuple));
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
