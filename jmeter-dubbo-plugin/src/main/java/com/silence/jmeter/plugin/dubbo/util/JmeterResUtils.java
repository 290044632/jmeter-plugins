package com.silence.jmeter.plugin.dubbo.util;

import java.util.ResourceBundle;

import org.apache.jmeter.util.JMeterUtils;

public class JmeterResUtils {

	private static final String PACKAGE_NAME = "com.silence.jmeter.plugin.dubbo.resources.messages";

	public static String getResString(final String key) {
		return getResString(key, null);
	}

	public static String getResString(final String key, final String defaultValue) {
		String res = null;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(PACKAGE_NAME, JMeterUtils.getLocale());
		if (null == defaultValue) {
			res = "[" + key + "]";
		}
		if (resourceBundle == null) {
			return res;
		}
		String value = resourceBundle.getString(key);
		return null == value ? res : value;
	}
}
