package tcr.util;

import java.util.Enumeration;
import java.util.Vector;

public class VectorUtil {
	private VectorUtil() {

	}

	public static String join(Vector vec, String delim) {
		if (vec == null) {
			throw new NullPointerException();
		}
		if (delim == null) {
			delim = "";
		}
		StringBuffer sb = new StringBuffer();
		for (Enumeration en = vec.elements(); en.hasMoreElements();) {
			sb.append(en.nextElement() + delim);
		}
		return sb.length() <= 0 ? "" : sb.toString().substring(0,
				sb.length() - delim.length());
	}
}
