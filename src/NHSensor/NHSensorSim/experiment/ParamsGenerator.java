package NHSensor.NHSensorSim.experiment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.util.Util;

public class ParamsGenerator {
	public static final Integer DEFAULT_GROUP_ID = new Integer(1);

	Class paramClass;

	Hashtable sequences;

	Hashtable fieldGroup = new Hashtable();

	Field fields[];

	Method[] setMethods;

	Vector[] sequenceArray;

	Integer[] group;

	int[] indexes;

	Vector params = new Vector();

	public Vector getParams() {
		return params;
	}

	public ParamsGenerator(Class paramClass, Hashtable sequences)
			throws Exception {
		this.paramClass = paramClass;
		this.sequences = sequences;
		this.init();
		this.generate();
	}

	public ParamsGenerator(Class paramClass, Hashtable sequences,
			Hashtable fieldGroup) throws Exception {
		this.paramClass = paramClass;
		this.sequences = sequences;
		this.fieldGroup = fieldGroup;
		this.init();
		this.generate();
	}

	public ParamsGenerator(String paramsFileName) throws Exception {
		params = (Vector) Util.readObject(paramsFileName);
	}

	protected void init() throws Exception {
		Field field;
		String fieldName;
		String setMethodName;
		String firstLetter;

		fields = this.paramClass.getDeclaredFields();
		setMethods = new Method[fields.length];
		indexes = new int[fields.length];
		group = new Integer[fields.length];
		sequenceArray = new Vector[fields.length];

		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			fieldName = field.getName();
			sequenceArray[i] = (Vector) sequences.get(fieldName);
			firstLetter = fieldName.substring(0, 1).toUpperCase();
			setMethodName = "set" + firstLetter + fieldName.substring(1);
			setMethods[i] = this.paramClass.getMethod(setMethodName,
					new Class[] { field.getType() });
			group[i] = (Integer) fieldGroup.get(fieldName);
		}
	}

	protected Vector generate() throws Exception {

		this.loop(0, new Hashtable());
		return this.getParams();

	}

	protected void loop(int layer, Hashtable groupIndex) throws Exception {
		if (layer >= this.fields.length) {
			Object param = this.paramClass.getConstructor(new Class[] {})
					.newInstance(new Object[] {});

			int index;
			for (int i = 0; i < fields.length; i++) {
				index = this.indexes[i];
				if (index != -1) {
					setMethods[i].invoke(param, new Object[] { sequenceArray[i]
							.elementAt(index) });
				}
			}
			this.params.add(param);
			// bug fixed
			groupIndex.clear();

			return;
		}

		if (sequenceArray[layer] == null || sequenceArray[layer].isEmpty()) {
			indexes[layer] = -1;
			loop(layer + 1, (Hashtable) groupIndex.clone());
		} else {
			Integer currentGroup = this.group[layer];
			if (currentGroup == null) {
				for (int i = 0; i < sequenceArray[layer].size(); i++) {
					indexes[layer] = i;
					loop(layer + 1, (Hashtable) groupIndex.clone());
				}
			} else {
				Integer currentGroupIndex = (Integer) groupIndex
						.get(currentGroup);
				if (currentGroupIndex == null) {
					for (int i = 0; i < sequenceArray[layer].size(); i++) {
						indexes[layer] = i;
						groupIndex.put(currentGroup, new Integer(i));
						loop(layer + 1, (Hashtable) groupIndex.clone());
					}
				} else {
					indexes[layer] = currentGroupIndex.intValue();
					loop(layer + 1, (Hashtable) groupIndex.clone());
				}
			}
		}
	}
}
