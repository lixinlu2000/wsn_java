package NHSensor.NHSensorSim.experiment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.util.ReflectUtil;
import NHSensor.NHSensorSim.util.Util;

/*
 * ParamsGeneratorEx can configure the fields 
 * in the super class of a param class
 */
public class ParamsGeneratorEx {
	public static final Integer DEFAULT_GROUP_ID = new Integer(1);

	Class paramClass;

	Hashtable sequences;

	Hashtable fieldGroup = new Hashtable();

	Field fields[];

	Method[] setMethods;

	Vector[] sequenceArray;

	Hashtable groupIndex = new Hashtable();

	Integer[] group;

	Vector params = new Vector();

	public Vector getParams() {
		return params;
	}

	public ParamsGeneratorEx(Class paramClass, Hashtable sequences)
			throws Exception {
		this.paramClass = paramClass;
		this.sequences = sequences;
		this.init();
		this.generate();
	}

	public ParamsGeneratorEx(Class paramClass, Hashtable sequences,
			Hashtable fieldGroup) throws Exception {
		this.paramClass = paramClass;
		this.sequences = sequences;
		this.fieldGroup = fieldGroup;
		this.init();
		this.generate();
	}

	public ParamsGeneratorEx(String paramsFileName) throws Exception {
		params = (Vector) Util.readObject(paramsFileName);
	}

	protected void init() throws Exception {
		Field field;
		String fieldName;

		fields = ReflectUtil.getAllFields(this.paramClass);
		setMethods = new Method[fields.length];
		group = new Integer[fields.length];
		sequenceArray = new Vector[fields.length];

		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			fieldName = field.getName();
			sequenceArray[i] = (Vector) sequences.get(fieldName);
			setMethods[i] = ReflectUtil.getFieldSetMethod(this.paramClass,
					field);
			group[i] = (Integer) fieldGroup.get(fieldName);
		}
	}

	protected Vector generate() throws Exception {
		Vector sequenceArrayIndexes = new Vector();
		for (int i = 0; i < this.fields.length; i++) {
			sequenceArrayIndexes.add(new Integer(-1));
		}
		this.loop(0, new Hashtable(), sequenceArrayIndexes);
		return this.getParams();

	}

	protected void loop(int layer, Hashtable groupIndex,
			Vector sequenceArrayIndexes) throws Exception {
		if (layer >= this.fields.length) {
			Object param = this.paramClass.getConstructor(new Class[] {})
					.newInstance(new Object[] {});

			int index;
			for (int i = 0; i < fields.length; i++) {
				index = ((Integer) sequenceArrayIndexes.elementAt(i))
						.intValue();
				if (index != -1) {
					setMethods[i].invoke(param, new Object[] { sequenceArray[i]
							.elementAt(index) });
				}
			}
			this.params.add(param);
			return;
		}

		if (sequenceArray[layer] == null || sequenceArray[layer].isEmpty()) {
			sequenceArrayIndexes.set(layer, new Integer(-1));
			loop(layer + 1, (Hashtable) groupIndex.clone(),
					(Vector) sequenceArrayIndexes.clone());
		} else {
			Integer currentGroup = this.group[layer];
			if (currentGroup == null) {
				for (int i = 0; i < sequenceArray[layer].size(); i++) {
					sequenceArrayIndexes.set(layer, new Integer(i));
					loop(layer + 1, (Hashtable) groupIndex.clone(),
							(Vector) sequenceArrayIndexes.clone());
				}
			} else {
				Integer currentGroupIndex = (Integer) groupIndex
						.get(currentGroup);
				if (currentGroupIndex == null) {
					for (int i = 0; i < sequenceArray[layer].size(); i++) {
						sequenceArrayIndexes.set(layer, new Integer(i));
						groupIndex.put(currentGroup, new Integer(i));
						loop(layer + 1, (Hashtable) groupIndex.clone(),
								(Vector) sequenceArrayIndexes.clone());
					}
				} else {
					sequenceArrayIndexes.set(layer, new Integer(
							currentGroupIndex.intValue()));
					loop(layer + 1, (Hashtable) groupIndex.clone(),
							(Vector) sequenceArrayIndexes.clone());
				}
			}
		}
	}
}
