package com.model2.mvc.service.domain;

public class Code {
	private String groupId;
	private String code;
	private String codeName;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code.trim();
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	@Override
	public String toString() {
		return "Code [groupId=" + groupId + ", code=" + code + ", codeName=" + codeName + "]";
	}
}
