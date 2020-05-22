package com.silence.jmeter.plugin.dubbo.model;

import java.util.Map;

public class JmeterDubboInterfaceModel {

	private String className;
	
	private String method;
	
	private Map<Object, Object> args;
	
	public JmeterDubboInterfaceModel() {
	}

	public JmeterDubboInterfaceModel(String className, String method, Map<Object, Object> args) {
		super();
		this.className = className;
		this.method = method;
		this.args = args;
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

	public Map<Object, Object> getArgs() {
		return args;
	}

	public void setArgs(Map<Object, Object> args) {
		this.args = args;
	}
	
}
