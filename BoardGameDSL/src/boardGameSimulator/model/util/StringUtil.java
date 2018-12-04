package boardGameSimulator.model.util;

public class StringUtil {

	/*
	 * checks if a Function is an integer
	 */
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		if (str.isEmpty()) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (str.length() == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static boolean isDouble(String str) {
		if (str == null) {
			return false;
		}
		if (str.isEmpty()) {
			return false;
		}
		
		int i = 0;
		if (str.charAt(0) == '-') {
			if (str.length() == 1) {
				return false;
			}
			i = 1;
		}
		boolean pointSeen = false;
		for (; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				if(pointSeen || c != '.'){
					return false;
				} else{
					pointSeen = true;
				}
			}
		}
		return true;
	}

}
