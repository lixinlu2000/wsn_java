package NHSensor.NHSensorSim.experiment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;

import NHSensor.NHSensorSim.util.ReflectUtil;

/*
 * ParamGenerator can configure the fields 
 * in the super class of a param class
 * It generates only one param
 */
public class ParamGenerator {
	Class paramClass;

	Hashtable paramValueMap;

	Field fields[];

	Method[] setMethods;

	Object[] values;

	Object param;

	public Object getParam() {
		return param;
	}

	public ParamGenerator(Class paramClass, Hashtable paramValueMap)
			throws Exception {
		this.paramClass = paramClass;
		this.paramValueMap = paramValueMap;
		this.init();
		this.generate();
	}

	protected void init() throws Exception {
		Field field;
		String fieldName;

		fields = ReflectUtil.getAllFields(this.paramClass);
		setMethods = new Method[fields.length];
		values = new Object[fields.length];

		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			fieldName = field.getName();
			values[i] = paramValueMap.get(fieldName);
			if(values[i]==null) {
				System.out.println("Field: "+ fieldName +" do not have value");
				continue;
			}
			setMethods[i] = ReflectUtil.getFieldSetMethod(this.paramClass,
					field);
		}
	}

	protected void generate() throws Exception {
		this.param = this.paramClass.getConstructor(new Class[] {})
				.newInstance(new Object[] {});
		for (int i = 0; i < this.fields.length; i++) {
			if (values[i] != null&& setMethods[i]!=null) {
				setMethods[i].invoke(param, new Object[] { values[i] });
			}
		}
	}
}
