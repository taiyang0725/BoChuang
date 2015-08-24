package edu.feicui.assistant.exception;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// CrashHandler.getInstance().init(getApplicationContext());

	}

}
