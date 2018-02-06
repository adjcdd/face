package com.grgbanking.framework.domains.manager.role.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/18.
 */
public class RoleDeleteParam implements Serializable {

	private Long id;

	private String deleteIgnoreRelationship;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeleteIgnoreRelationship() {
		return deleteIgnoreRelationship;
	}

	public void setDeleteIgnoreRelationship(String deleteIgnoreRelationship) {
		this.deleteIgnoreRelationship = deleteIgnoreRelationship;
	}
}
