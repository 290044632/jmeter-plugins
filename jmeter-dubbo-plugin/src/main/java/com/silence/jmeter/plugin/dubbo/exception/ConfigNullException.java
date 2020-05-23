package com.silence.jmeter.plugin.dubbo.exception;

public class ConfigNullException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigNullException() {
		super();
	}

	public ConfigNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigNullException(String message) {
		super(message);
	}

	public ConfigNullException(Throwable cause) {
		super(cause);
	}

}
