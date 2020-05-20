package com.silence.jmeter.plugin.dubbo.util;

import java.util.ResourceBundle;

import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmeterResUtils {

	private static Logger logger = LoggerFactory.getLogger(JmeterResUtils.class);

	private static final String PACKAGE_NAME = "com.silence.jmeter.plugin.dubbo.resources.messages";

	private static volatile ResourceBundle bundle;

	public static ResourceBundle getBundle() {
		if (null == bundle) {
			bundle = ResourceBundle.getBundle(PACKAGE_NAME, JMeterUtils.getLocale());
			if (null == bundle) {
				logger.info("未读取到国际化配置信息：{},{}", PACKAGE_NAME, JMeterUtils.getLocale().getLanguage());
			}
		}
		return bundle;
	}

	public static String getResString(final String key) {
		return getResString(key, null);
	}

	public static String getResString(final String key, final String defaultValue) {
		String res = null;
		ResourceBundle resourceBundle = bundle;
		if (null == defaultValue) {
			res = "[" + key + "]";
		}
		if (resourceBundle == null) {
			resourceBundle = getBundle();
			if (resourceBundle == null) {
				return res;
			}
		}
		String value = resourceBundle.getString(key);
		return null == value ? res : value;
	}
}
