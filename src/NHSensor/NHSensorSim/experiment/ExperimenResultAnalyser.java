package NHSensor.NHSensorSim.experiment;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.jfree.data.xy.XYDataset;

public abstract class ExperimenResultAnalyser {
	ExperimentParamResultPairCollections pairCollections;
	boolean filterNonSuccessed = true;

	public boolean isFilterNonSuccessed() {
		return filterNonSuccessed;
	}

	public void setFilterNonSuccessed(boolean filterNonSuccessed) {
		this.filterNonSuccessed = filterNonSuccessed;
	}

	public ExperimenResultAnalyser(
			ExperimentParamResultPairCollections pairCollections) {
		this(pairCollections, true);
	}

	public ExperimenResultAnalyser(
			ExperimentParamResultPairCollections pairCollections,
			boolean filterNonSuccessed) {
		this.pairCollections = pairCollections;
		this.filterNonSuccessed = filterNonSuccessed;
	}

	public ExperimentParamResultPairCollections getPairCollections() {
		return pairCollections;
	}

	public void setPairCollection(
			ExperimentParamResultPairCollections pairCollections) {
		this.pairCollections = pairCollections;
	}

	/*
	 * filter non-success experiment result
	 */
	public void pretreatment() {
		if (!this.filterNonSuccessed)
			return;

		ExperimentParamResultPairCollection pairCollection;
		SortedSet ss = new TreeSet();

		for (int i = 0; i < this.pairCollections.size(); i++) {
			pairCollection = this.pairCollections.get(i);
			ss.addAll(pairCollection.getNonSuccessPairIndexs());
		}

		Iterator i = ss.iterator();
		Vector indexs = new Vector();
		Integer index;
		while (i.hasNext()) {
			index = (Integer) i.next();
			indexs.insertElementAt(index, 0);
		}
		System.out.println(indexs);

		for (int j = 0; j < indexs.size(); j++) {
			index = (Integer) indexs.elementAt(j);
			for (int k = 0; k < this.pairCollections.size(); k++) {
				pairCollection = this.pairCollections.get(k);
				pairCollection.removeElementAt(index.intValue());
			}
		}

	}

	/*
	 * filter non-success experiment result
	 */
	public void pretreatmentOld() {
		if (!this.filterNonSuccessed)
			return;

		ExperimentParamResultPairCollection pairCollection;
		SortedSet ss = new TreeSet();

		for (int i = 0; i < this.pairCollections.size(); i++) {
			pairCollection = this.pairCollections.get(i);
			ss.addAll(pairCollection.getNonSuccessPairIndexs());
		}

		Iterator i = ss.iterator();
		Vector indexs = new Vector();
		Integer index;
		while (i.hasNext()) {
			index = (Integer) i.next();
			indexs.insertElementAt(index, 0);
		}

		for (int j = 0; j < indexs.size(); j++) {
			index = (Integer) indexs.elementAt(j);
			for (int k = 0; k < this.pairCollections.size(); k++) {
				pairCollection = this.pairCollections.get(k);
				pairCollection.removeElementAt(index.intValue());
			}
		}

	}

	public abstract XYDataset generateXYDataset() throws Exception;
}
