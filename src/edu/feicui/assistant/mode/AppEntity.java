package edu.feicui.assistant.mode;

import android.graphics.drawable.Drawable;

/**
 * 这是一个应用程序属性实体类
 * */
public class AppEntity {
	/**
	 * 应用程序名字
	 * */
	private CharSequence name;
	/**
	 * 应用程序图标
	 * */
	private Drawable icon;
	/**
	 * 应用程序编号
	 * */
	private int vCode;
	/**
	 * 应用程序包名
	 * */
	private String packageName;
	/**
	 * 应用程序版本名称
	 * */
	private String editionName;
	/**
	 * 进程服务数量
	 * */
	private int serveNum;
	/**
	 * 单个程序运行内存
	 * */
	private String oneRAM;
	/**
	 * 运行内存
	 * */
	private float appRAM;
	/**
	 * 是否被选中
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
