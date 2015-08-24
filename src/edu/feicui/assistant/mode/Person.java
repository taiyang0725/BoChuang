package edu.feicui.assistant.mode;

import java.io.Serializable;

/**
 * ����һ������
 * */
public class Person implements Serializable {

	/**
	 * ����
	 * */
	private String name;
	/**
	 * �绰����
	 * */
	private String telNum;
	/**
	 * �û�ͼ��
	 * */
	private int userIcon;
	/**
	 * �칫�ҵ绰
	 * */
	private String officeTel;
	/** ��ͥ�绰 */
	private String homeTel;
	/** ְ��ְ�� */
	private String workName;
	/** ��λ���� */
	private String unitName;
	/** ��ַ */
	private String address;
	/** �������� */
	private String postalcode;
	/** E-mail */
	private String eMail;
	/** ������ϵ��ʽ */
	private String contact;
	/** ��ע */
	private String remark;
	/** �Ƿ�ѡ�� */
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
