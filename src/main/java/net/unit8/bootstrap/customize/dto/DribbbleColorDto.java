package net.unit8.bootstrap.customize.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DribbbleColorDto implements Serializable {
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
