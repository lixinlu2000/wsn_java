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
public class ParamsGeneratorExOld {
	public static final Integer DEFAULT_GROUP_ID = new Integer(1);

	Class paramClass;

	Hashtable sequences;

	Hashtable fieldGroup = new Hashtable();

	Field fields[];

	Method[] setMethods;

	Vector[] sequenceArray;

	Hashtable groupIndex = new Hashtable();

	Integer[] group;

	int[] indexes;

	Vector params = new Vector();

	public Vector getParams() {
		return params;
	}

	public ParamsGeneratorExOld(Class paramClass, Hashtable sequences)
			throws Exception {
		this.paramClass = paramClass;
		this.sequences = sequences;
		this.init();
		this.generate();
	}

	public ParamsGeneratorExOld(Class paramClass, Hashtable sequences,
			Hashtable fieldGroup) throws Exception {
		this.paramClass = paramClass;
		this.sequences = sequences;
		this.fieldGroup = fieldGroup;
		this.init();
		this.generate();
	}

	public ParamsGeneratorExOld(String paramsFileName) throws Exception {
		params = (Vector) Util.readObject(paramsFileName);
	}

	protected void init() throws Exception {
		Field field;
		String fieldName;

		fields = ReflectUtil.getAllFields(this.paramClass);
		setMethods = new Method[fields.length];
		indexes = new int[fields.length];
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

		this.loop(0);
		return this.getParams();

	}

	protected void loop(int layer) throws Exception {
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
			this.groupIndex.clear();
			return;
		}

		if (sequenceArray[layer] == null || sequenceArray[layer].isEmpty()) {
			indexes[layer] = -1;
			loop(layer + 1);
		} else {
			Integer currentGroup = this.group[layer];
			if (currentGroup == null) {
				for (int i = 0; i < sequenceArray[layer].size(); i++) {
					indexes[layer] = i;
					loop(layer + 1);
				}
			} else {
				Integer currentGroupIndex = (Integer) this.groupIndex
						.get(currentGroup);
				if (currentGroupIndex == null) {
					for (int i = 0; i < sequenceArray[layer].size(); i++) {
						indexes[layer] = i;
						this.groupIndex.put(currentGroup, new Integer(i));
						loop(layer + 1);
					}
				} else {
					indexes[layer] = currentGroupIndex.intValue();
					loop(layer + 1);
				}
			}
		}
	}
}
