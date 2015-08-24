package edu.feicui.assistant.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

/**
 * 这是一个隐藏标题拦的类
 * */
public class BaseOneActivity extends Activity {
	/**
	 * 屏宽
	 * */
	protected int screenW;
	/**
	 * 屏高
	 * */
	protected int screenH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setFullScreen();

		initScreen();
	}

	/**
	 * 设置全屏的方法
	 * */
	private void setFullScreen() {

		/**
		 * 设置标题栏为隐藏的方法
		 * */
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	/**
	 * 获取屏幕大小
	 * */
	private void initScreen() {

		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		screenW = dm.widthPixels;
		screenH = dm.heightPixels;

	}

}
