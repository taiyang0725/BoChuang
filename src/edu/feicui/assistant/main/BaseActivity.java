package edu.feicui.assistant.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * 这是一个全屏类
 **/
public class BaseActivity extends Activity {
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
		 * 设置状态栏为隐藏的方法
		 * */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
