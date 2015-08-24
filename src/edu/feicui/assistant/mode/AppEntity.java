package edu.feicui.assistant.mode;

import android.graphics.drawable.Drawable;

/**
 * ����һ��Ӧ�ó�������ʵ����
 * */
public class AppEntity {
	/**
	 * Ӧ�ó�������
	 * */
	private CharSequence name;
	/**
	 * Ӧ�ó���ͼ��
	 * */
	private Drawable icon;
	/**
	 * Ӧ�ó�����
	 * */
	private int vCode;
	/**
	 * Ӧ�ó������
	 * */
	private String packageName;
	/**
	 * Ӧ�ó���汾����
	 * */
	private String editionName;
	/**
	 * ���̷�������
	 * */
	private int serveNum;
	/**
	 * �������������ڴ�
	 * */
	private String oneRAM;
	/**
	 * �����ڴ�
	 * */
	private float appRAM;
	/**
	 * �Ƿ�ѡ��
	 * */
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getServeNum() {
		return serveNum;
	}

	public void setServeNum(int serveNum) {
		this.serveNum = serveNum;
	}

	public String getOneRAM() {
		return oneRAM;
	}

	public void setOneRAM(String oneRAM) {
		this.oneRAM = oneRAM;
	}

	public float getAppRAM() {
		return appRAM;
	}

	public void setAppRAM(float appRAM) {
		this.appRAM = appRAM;
	}

	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public int getvCode() {
		return vCode;
	}

	public void setvCode(int vCode) {
		this.vCode = vCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

}
