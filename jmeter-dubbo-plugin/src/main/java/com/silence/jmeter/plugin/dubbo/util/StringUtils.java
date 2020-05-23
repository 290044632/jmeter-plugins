package com.silence.jmeter.plugin.dubbo.util;

public class StringUtils {

	public static boolean isNotBlank(String value) {
		return null != value && !value.isEmpty();
	}

	public static boolean isBlank(String value) {
		return null == value || value.isEmpty();
	}

	public static boolean isAnyBlank(String... values) {
		if (null == values || values.length == 0) {
			return true;
		}
		for (String value : values) {
			if (isBlank(value)) {
				return true;
			}
		}
		return false;
	}

}
