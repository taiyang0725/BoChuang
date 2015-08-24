package edu.feicui.assistant.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.BatteryManager;

/**
 * ����һ����صĹ㲥�� ������̳г�����BroadcastReceiver���������ṩ����Ķ���
 * */
public class BatteryBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"NAME", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		/**
		 * ��ص�ѹ
		 * */
		editor.putInt("V", intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0));
		/**
		 * ����¶�
		 * */
		editor.putInt("T",
				intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0));
		/**
		 * ��ص�ǰ����
		 * */
		editor.putInt("L", intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0));
		/**
		 * ���״̬
		 * */
		editor.putInt("Status",
				intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0));
		/**
		 * ���ʹ�����
		 * */
		editor.putInt("H", intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0));
		/**
		 * ���������
		 * */
		editor.putInt("Scale",
				intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0));
		editor.commit();
	}
}
