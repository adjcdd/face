package com.grgbanking.framework.domains.manager.data_dictionary.pojo;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/11/3.
 */
public class DataDictionaryPojo implements Serializable{

	private Long id;

	private String code;

	private String name;

	private Byte type;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
