package tcr;

import com.nttdocomo.device.CodeReader;

public class CodeParser {
	private final static Object[][] FORMAT_NAME_MAP = {
			{ new Integer(-2), "UNSUPPORTED" }, { new Integer(-1), "UNKNOWN" },
			{ new Integer(0), "AUTO" }, { new Integer(1), "JAN8" },
			{ new Integer(2), "JAN13" },
			{ new Integer(3), "QR" },
			{ new Integer(4), "OCR" },
			// {new Integer(5), "?"},
			{ new Integer(6), "MICRO_QR" }, { new Integer(7), "NW7" },
			{ new Integer(8), "CODE39" }, { new Integer(9), "ER" },
			{ new Integer(10), "FP" } };

	private final static Object[][] TYPE_NAME_MAP = {
			{ new Integer(-1), "UNKNOWN" }, { new Integer(0), "BINARY" },
			{ new Integer(1), "NUMBER" }, { new Integer(2), "ASCII" },
			{ new Integer(3), "STRING" } };

	private CodeParser() {
	}

	public static Code parse(int type, int format, byte[] data) {
		Object value = null;

		switch (type) {
		case CodeReader.TYPE_ASCII:
		case CodeReader.TYPE_STRING:
		case CodeReader.TYPE_NUMBER:
			value = new String(data);
			break;
		case CodeReader.TYPE_BINARY:
		case CodeReader.TYPE_UNKNOWN:
		default:
			value = data;
			break;
		}

		return value == null ? null : new Code(type, format, value);
	}

	public static Object[] getFormat(int code) {
		for (int i = 0, m = FORMAT_NAME_MAP.length; i < m; ++i) {
			if (((Integer) FORMAT_NAME_MAP[i][0]).intValue() == code) {
				return FORMAT_NAME_MAP[i];
			}
		}
		return null;
	}

	public static String getFormatName(int code) {
		for (int i = 0, m = FORMAT_NAME_MAP.length; i < m; ++i) {
			if (((Integer) FORMAT_NAME_MAP[i][0]).intValue() == code) {
				return (String) FORMAT_NAME_MAP[i][1];
			}
		}
		return "???";
	}

	public static String getTypeName(int type) {
		for (int i = 0, m = TYPE_NAME_MAP.length; i < m; ++i) {
			if (((Integer) TYPE_NAME_MAP[i][0]).intValue() == type) {
				return (String) TYPE_NAME_MAP[i][1];
			}
		}
		return "???";
	}

	public static String getString(Code code) {
		String str = null;
		switch (code.type) {
		case CodeReader.TYPE_ASCII:
		case CodeReader.TYPE_STRING:
		case CodeReader.TYPE_NUMBER:
			str = (String) code.value;
			break;
		case CodeReader.TYPE_BINARY:
		case CodeReader.TYPE_UNKNOWN:
		default:
			if (code.value != null) {
				str = new String((byte[]) code.value);
			}
			break;
		}
		return str == null ? "null" : str;
	}
}