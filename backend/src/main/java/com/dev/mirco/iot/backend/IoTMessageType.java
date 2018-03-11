package com.dev.mirco.iot.backend;

/**
 * Created by mirco on 19/06/16.
 */
public enum IoTMessageType {
	PUT(0),
	DELETE(1),
	NOTIFICATION(2),
	DATA(3);

	private final int code;

	IoTMessageType(int code) {
		this.code=code;
	}

	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
