package NHSensor.NHSensorSim.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.jfree.data.xy.XYSeries;

public class FormatUtil {
	public static void addHashtableToXYSeries(XYSeries xYSeries,
			Hashtable hashtable) throws Exception {
		Double x, y;
		Enumeration e = hashtable.keys();
		Object object;

		while (e.hasMoreElements()) {
			object = e.nextElement();
			if (!(object instanceof Double)) {
				throw new Exception("hashtable should be Double->Double");
			}
			x = (Double) object;

			if (!(hashtable.get(object) instanceof Double)) {
				throw new Exception("hashtable should be Double->Double");
			}
			y = (Double) hashtable.get(x);
			xYSeries.add(x, y);
		}
	}

	public static String toString(Vector vector) {
		return FormatUtil.toString(vector, " ");
	}

	public static String toString(Vector vector, String delimit) {
		StringBuffer sb = new StringBuffer();
		Object object;

		for (int i = 0; i < vector.size(); i++) {
			object = vector.elementAt(i);
			sb.append(object);
			if (i != vector.size() - 1) {
				sb.append(delimit);
			}
		}
		return sb.toString();
	}

	public static Vector keyStrings(Hashtable hashtable) {
		Vector result = new Vector();
		Enumeration e = hashtable.keys();
		Object key;

		while (e.hasMoreElements()) {
			key = e.nextElement();
			result.add(key.toString());
		}
		return result;
	}

	public static Vector keyStrings(Hashtable h1, Hashtable h2) {
		Vector h1KeyStrings = FormatUtil.keyStrings(h1);
		Vector h2KeyStrings = FormatUtil.keyStrings(h2);
		h1KeyStrings.addAll(h2KeyStrings);
		return h1KeyStrings;
	}

	public static Vector valueStrings(Hashtable h1, Hashtable h2) {
		Vector h1ValueStrings = FormatUtil.valueStrings(h1);
		Vector h2ValueStrings = FormatUtil.valueStrings(h2);
		h1ValueStrings.addAll(h2ValueStrings);
		return h1ValueStrings;
	}

	public static Vector valueStringsIncludeKey(Hashtable h1, Hashtable h2) {
		Vector h1ValueStrings = FormatUtil.valueStringsIncludeKey(h1);
		Vector h2ValueStrings = FormatUtil.valueStringsIncludeKey(h2);
		h1ValueStrings.addAll(h2ValueStrings);
		return h1ValueStrings;
	}

	public static Vector valueStringsIncludeKey(Hashtable hashtable) {
		Vector result = new Vector();
		Enumeration e = hashtable.keys();
		Object key;
		Object value;

		while (e.hasMoreElements()) {
			key = e.nextElement();
			value = hashtable.get(key);
			if (value != null)
				result.add(key + "(" + value.toString() + ")");
			else
				result.add(key + "(" + "null" + ")");
		}
		return result;
	}

	public static Vector valueStrings(Hashtable hashtable) {
		Vector result = new Vector();
		Enumeration e = hashtable.keys();
		Object key;
		Object value;

		while (e.hasMoreElements()) {
			key = e.nextElement();
			value = hashtable.get(key);
			if (value != null)
				result.add(value.toString());
			else
				result.add("null");
		}
		return result;
	}

}
