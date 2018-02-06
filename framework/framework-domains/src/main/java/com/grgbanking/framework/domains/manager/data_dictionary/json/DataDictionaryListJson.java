package com.grgbanking.framework.domains.manager.data_dictionary.json;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/11/3.
 */
public class DataDictionaryListJson implements Serializable{

	private String code;

	private String name;

	private Byte type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
}
