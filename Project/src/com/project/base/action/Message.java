package com.project.base.action;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String qrCoder;
	private String base64Img;
	private String returnId;
	private boolean success;
	private Object obj;

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "Message [msg=" + msg + ", success=" + success + ", obj=" + obj + "]";
	}

	public String getQrCoder() {
		return qrCoder;
	}

	public void setQrCoder(String qrCoder) {
		this.qrCoder = qrCoder;
	}

	public String getBase64Img() {
		return base64Img;
	}

	public void setBase64Img(String base64Img) {
		this.base64Img = base64Img;
	}

}
