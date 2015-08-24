package edu.feicui.assistant.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

/**
 * ����һ�����ر���������
 * */
public class BaseOneActivity extends Activity {
	/**
	 * ����
	 * */
	protected int screenW;
	/**
	 * ����
	 * */
	protected int screenH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setFullScreen();

		initScreen();
	}

	/**
	 * ����ȫ���ķ���
	 * */
	private void setFullScreen() {

		/**
		 * ���ñ�����Ϊ���صķ���
		 * */
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	/**
	 * ��ȡ��Ļ��С
	 * */
	private void initScreen() {

		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		screenW = dm.widthPixels;
		screenH = dm.heightPixels;

	}

}
