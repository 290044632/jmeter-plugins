package com.silence.jmeter.plugin.dubbo.model;

import java.util.List;
import java.util.Map;

public class JmeterDubboInterfaceModel {

	private String className;
	
	private String method;
	
	private Map<String, Object> dubboConfig;
	
	private List<Map<String, Object>> args;
	public JmeterDubboInterfaceModel() {
	}

	public JmeterDubboInterfaceModel(String className, String method,List< Map<String, Object>> args,Map<String, Object> dubboConfig) {
		super();
		this.className = className;
		this.method = method;
		this.args = args;
		this.dubboConfig = dubboConfig;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setArgs(List< Map<String, Object>> args) {
		this.args = args;
	}

	public List<Map<String, Object>> getArgs() {
		return args;
	}

	public Map<String, Object> getDubboConfig() {
		return dubboConfig;
	}

	public void setDubboConfig(Map<String, Object> dubboConfig) {
		this.dubboConfig = dubboConfig;
	}
	
}
