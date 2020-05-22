package com.silence.jmeter.plugin.dubbo.util;

import com.alibaba.fastjson.JSON;

public class JmeterJSONUtils {

	public static <T> String toJSONString(T o) {
		return JSON.toJSONString(o);
	}

	public static <T> T toObject(String value, Class<T> tClass) {
		return JSON.parseObject(value, tClass);
	}
}
