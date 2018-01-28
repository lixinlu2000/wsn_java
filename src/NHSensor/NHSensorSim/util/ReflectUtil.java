package NHSensor.NHSensorSim.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ParamGenerator;

public class ReflectUtil {

	public static Object copy(Object object) throws Exception {

		Class classType = object.getClass();
		System.out.println("Class:" + classType.getName());
		Object objectCopy = classType.getConstructor(new Class[] {})
				.newInstance(new Object[] {});
		Field fields[] = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			String setMethodName = "set" + firstLetter + fieldName.substring(1);
			Method getMethod = classType.getMethod(getMethodName,
					new Class[] {});
			Method setMethod = classType.getMethod(setMethodName,
					new Class[] { field.getType() });
			Object value = getMethod.invoke(object, new Object[] {});
			System.out.println(fieldName + ":" + value);
			setMethod.invoke(objectCopy, new Object[] { value });
		}
		return objectCopy;
	}

	public static String[][] getAllFields(Object object) throws Exception {

		Class classType = object.getClass();
		Field fields[] = classType.getDeclaredFields();
		String[][] fieldNameValuePair = new String[fields.length][2];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			Method getMethod = classType.getMethod(getMethodName,
					new Class[] {});
			Object value = getMethod.invoke(object, new Object[] {});
			fieldNameValuePair[i][0] = fieldName;
			fieldNameValuePair[i][1] = value.toString();
		}
		return fieldNameValuePair;
	}

	/*
	 * get all fields including the fields from super class
	 */
	public static String[][] getAllFieldsEx(Object object) throws Exception {

		Class classType = object.getClass();
		Field fields[] = ReflectUtil.getAllFields(classType);
		String[][] fieldNameValuePair = new String[fields.length][2];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			Method getMethod = ReflectUtil.getFieldGetMethod(classType, field);
			Object value = getMethod.invoke(object, new Object[] {});
			fieldNameValuePair[i][0] = fieldName;
			fieldNameValuePair[i][1] = value.toString();
		}
		return fieldNameValuePair;
	}

	public static Hashtable getAllFieldsValues(Object object) throws Exception {
		Hashtable hashtable = new Hashtable();
		Class classType = object.getClass();
		Field fields[] = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			Method getMethod = classType.getMethod(getMethodName,
					new Class[] {});
			Object value = getMethod.invoke(object, new Object[] {});
			hashtable.put(fieldName, value);
		}
		return hashtable;
	}

	public static Object stringToObject(String str, Class c) throws Exception {
		Hashtable paramValueMap = new Hashtable();
		String splits[] = str.split("\\s+");
		String paramValueString, param, value, type;
		int parenthesesBegin, parenthesesEnd;
		for (int i = 0; i < splits.length; i++) {
			paramValueString = splits[i];
			parenthesesBegin = paramValueString.indexOf('(');
			parenthesesEnd = paramValueString.indexOf(')');
			param = paramValueString.substring(0, parenthesesBegin);
			value = paramValueString.substring(parenthesesBegin + 1,
					parenthesesEnd);
			type = ReflectUtil.getField(c, param).getType().getName();
			if (type.equalsIgnoreCase("double")) {
				paramValueMap.put(param, Double.valueOf(value));
			} else if (type.equalsIgnoreCase("int")) {
				paramValueMap.put(param, Integer.valueOf(value));
			}
		}
		ParamGenerator pg = new ParamGenerator(c, paramValueMap);
		return pg.getParam();
	}

	public static String objectToString(Object o) throws Exception {
		Hashtable fieldValueMap = ReflectUtil.getAllFieldsValuesEx(o);
		Enumeration e = fieldValueMap.keys();
		Object key, value;
		StringBuffer sb = new StringBuffer();
		while (e.hasMoreElements()) {
			key = e.nextElement();
			value = fieldValueMap.get(key);
			sb.append(key);
			sb.append("(");
			sb.append(value);
			sb.append(") ");
		}
		return sb.toString();
	}

	/*
	 * get all fields including the fields from super class
	 */
	public static Hashtable getAllFieldsValuesEx(Object object)
			throws Exception {
		Hashtable hashtable = new Hashtable();
		Class classType = object.getClass();
		Field fields[] = ReflectUtil.getAllFields(classType);
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			Method getMethod = ReflectUtil.getFieldGetMethod(classType, field);
			if (getMethod != null) {
				Object value = getMethod.invoke(object, new Object[] {});
				hashtable.put(fieldName, value);
			} else {
				// System.out.println("Field " + fieldName
				// +"'s value is not gotten");
			}
		}
		return hashtable;
	}

	public static Object getFieldValue(Object object, String fieldName)
			throws Exception {
		Hashtable hashtable = ReflectUtil.getAllFieldsValues(object);
		return hashtable.get(fieldName);
	}

	/*
	 * get field value including the fields from super class
	 */
	public static Object getFieldValueEx(Object object, String fieldName)
			throws Exception {
		Hashtable hashtable = ReflectUtil.getAllFieldsValuesEx(object);
		return hashtable.get(fieldName);
	}

	/*
	 * get field value including the fields from super class
	 */
	public static Object getFieldValueEx(ExperimentParamResultPair pair,
			String fieldName) throws Exception {
		Object object = null;

		if (ReflectUtil.hasField(pair.getParam(), fieldName)) {
			object = ReflectUtil.getFieldValueEx(pair.getParam(), fieldName);
		} else if (ReflectUtil.hasField(pair.getResult(), fieldName)) {
			object = ReflectUtil.getFieldValueEx(pair.getResult(), fieldName);
		} else {
			throw new Exception("field " + fieldName + " doesnot exist");
		}
		return object;
	}

	public static boolean hasField(Object object, String fieldName) {
		Field[] allFields = ReflectUtil.getAllFields(object.getClass());
		for (int i = 0; i < allFields.length; i++) {
			if (fieldName.equals(allFields[i].getName()))
				return true;
		}
		return false;
	}

	public static Field[] getAllFields(Class c) {
		Class[] allClasses = ReflectUtil.getAllClasses(c);
		Vector allFields = new Vector();
		Field[] allFieldsArray;

		for (int i = 0; i < allClasses.length; i++) {
			allFields.addAll(Util.arrayToVector(allClasses[i]
					.getDeclaredFields()));
		}

		allFieldsArray = new Field[allFields.size()];
		for (int i = 0; i < allFieldsArray.length; i++) {
			allFieldsArray[i] = (Field) allFields.elementAt(i);
		}
		return allFieldsArray;
	}

	public static Field getField(Class c, String fieldName) {
		Field[] allFieldsArray = ReflectUtil.getAllFields(c);

		for (int i = 0; i < allFieldsArray.length; i++) {
			if (allFieldsArray[i].getName().equalsIgnoreCase(fieldName))
				return allFieldsArray[i];
		}
		System.out.println("Field " + fieldName + " cannot find");
		return null;
	}

	public static Method getFieldSetMethod(Class c, Field field) {
		String fieldName = field.getName();
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String setMethodName = "set" + firstLetter + fieldName.substring(1);

		Class[] allClasses = ReflectUtil.getAllClasses(c);
		Method setMethod = null;

		for (int i = 0; i < allClasses.length; i++) {
			try {
				setMethod = allClasses[i].getMethod(setMethodName,
						new Class[] { field.getType() });
			} catch (SecurityException e) {
				continue;
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
		return setMethod;
	}

	public static Method getFieldGetMethod(Class c, Field field) {
		String fieldName = field.getName();
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getMethodName = "get" + firstLetter + fieldName.substring(1);

		Class[] allClasses = ReflectUtil.getAllClasses(c);
		Method getMethod = null;

		for (int i = 0; i < allClasses.length; i++) {
			try {
				getMethod = allClasses[i].getMethod(getMethodName,
						new Class[] {});
			} catch (SecurityException e) {
				continue;
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
		return getMethod;
	}

	public static Class[] getAllClasses(Class c) {
		Vector allClasses = new Vector();
		allClasses.add(c);

		Class superClass = c.getSuperclass();
		while (superClass != null) {
			allClasses.add(superClass);
			superClass = superClass.getSuperclass();
		}

		Class[] allClassesArray = new Class[allClasses.size()];
		for (int i = 0; i < allClassesArray.length; i++) {
			allClassesArray[i] = (Class) allClasses.elementAt(i);
		}
		return allClassesArray;
	}

	public static String schemeString(Class classType, String tableName) {
		StringBuffer sb = new StringBuffer();
		Field fields[] = classType.getDeclaredFields();
		String fieldName;
		String fieldType;

		sb.append(tableName + "( ");
		for (int i = 0; i < fields.length - 1; i++) {
			fieldName = fields[i].getName();
			// fieldType = fields[i].getType().getSimpleName();
			fieldType = fields[i].getType().getName();
			sb.append(fieldName + " " + fieldType + ", ");
		}

		int j = fields.length - 1;
		if (j >= 0) {
			fieldName = fields[j].getName();
			// fieldType = fields[j].getType().getSimpleName();
			fieldType = fields[j].getType().getName();
			sb.append(fieldName + " " + fieldType);
		}

		sb.append(")");
		return sb.toString();
	}

	/*
	 * public static void main(String[] args) throws Exception{ Param param =
	 * new Param(); String[][] s = ReflectUtil.getAllFields(param); for(int
	 * i=0;i<s.length;i++) { for(int j=0;j<s[0].length;j++) {
	 * System.out.println(s[i][j]); } } }
	 */

	public static void main(String[] args) throws Exception {
		AllParam param = new AllParam();
		// System.out.println(ReflectUtil.schemeString(param.getClass(),param.
		// getClass().getSimpleName()));
		System.out.println(ReflectUtil.schemeString(param.getClass(), param
				.getClass().getName()));
		System.out.println(String.valueOf(Types.CHAR));
	}

}
