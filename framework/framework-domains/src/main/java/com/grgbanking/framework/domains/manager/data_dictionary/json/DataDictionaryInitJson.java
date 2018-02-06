package com.grgbanking.framework.domains.manager.data_dictionary.json;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/11/7.
 */
public class DataDictionaryInitJson implements Serializable {

	private String code;

	private String name;

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
}
