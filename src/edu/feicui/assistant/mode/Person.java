package edu.feicui.assistant.mode;

import java.io.Serializable;

/**
 * 这是一个人类
 * */
public class Person implements Serializable {

	/**
	 * 姓名
	 * */
	private String name;
	/**
	 * 电话号码
	 * */
	private String telNum;
	/**
	 * 用户图标
	 * */
	private int userIcon;
	/**
	 * 办公室电话
	 * */
	private String officeTel;
	/** 家庭电话 */
	private String homeTel;
	/** 职务职称 */
	private String workName;
	/** 单位名称 */
	private String unitName;
	/** 地址 */
	private String address;
	/** 邮政编码 */
	private String postalcode;
	/** E-mail */
	private String eMail;
	/** 其他联系方式 */
	private String contact;
	/** 备注 */
	private String remark;
	/** 是否选中 */
	private boolean check;
	/** rawContactsId */
	private String rawContactsId;
	/** idContacts */
	private String idContacts;

	public String getRawContactsId() {
		return rawContactsId;
	}

	public void setRawContactsId(String rawContactsId) {
		this.rawContactsId = rawContactsId;
	}

	public String getIdContacts() {
		return idContacts;
	}

	public void setIdContacts(String idContacts) {
		this.idContacts = idContacts;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(int userIcon) {
		this.userIcon = userIcon;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", telNum=" + telNum + ", userIcon="
				+ userIcon + ", officeTel=" + officeTel + ", homeTel="
				+ homeTel + ", workName=" + workName + ", unitName=" + unitName
				+ ", address=" + address + ", postalcode=" + postalcode
				+ ", eMail=" + eMail + ", contact=" + contact + ", remark="
				+ remark + ", check=" + check + "]";
	}

}
