package com.o2r.helper;

import java.util.Date;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1997753363232807009L;

	private String localMessage;
	private String errorCode;
	private Date errorTime;
	private int severity;

	public CustomException(String localMessage, Date errorTime, int severity,
			String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.localMessage = localMessage;
		this.errorTime = errorTime;
		this.severity = severity;
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getLocalMessage() {
		return localMessage;
	}

	public void setLocalMessage(String localMessage) {
		this.localMessage = localMessage;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "CustomException [localMessage=" + localMessage + ", errorTime="
				+ errorTime + ", severity=" + severity + "]";
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
