package edu.feicui.assistant.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.BatteryManager;

/**
 * 这是一个电池的广播， 创建类继承抽象类BroadcastReceiver，用子类提供此类的对象
 * */
public class BatteryBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"NAME", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		/**
		 * 电池电压
		 * */
		editor.putInt("V", intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0));
		/**
		 * 电池温度
		 * */
		editor.putInt("T",
				intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0));
		/**
		 * 电池当前电量
		 * */
		editor.putInt("L", intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0));
		/**
		 * 电池状态
		 * */
		editor.putInt("Status",
				intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0));
		/**
		 * 电池使用情况
		 * */
		editor.putInt("H", intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0));
		/**
		 * 电池最大电量
		 * */
		editor.putInt("Scale",
				intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0));
		editor.commit();
	}
}
