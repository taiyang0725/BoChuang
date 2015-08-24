package edu.feicui.assistant.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import edu.feicui.assistant.R;

/**
 * 这是一个电池检测类
 **/
public class BatteryDetectionActivity extends BaseOneActivity implements
		OnSharedPreferenceChangeListener {
	private ImageView imgBattery;
	/**
	 * 广播接收器
	 * */
	private BatteryBroadcast myBroadcast;
	/**
	 * 电池当前电量
	 * */
	private TextView txtL;
	/**
	 * 电池状态
	 * */
	private TextView txtS;
	/**
	 * 电池温度
	 * */
	private TextView txtT;
	/**
	 * 电池电压
	 * */
	private TextView txtV;
	/**
	 * 电池使用情况
	 * */
	private TextView txtH;
	/**
	 * 电池图片
	 * */
	private ImageView imgBD;
	private SharedPreferences sharedPreferences;
	private AnimationDrawable animationDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battery_detection);

		mesgBroadcast();

		initView();
		initDrawable();
	}

	/**
	 * 初始化控件(字符)
	 * */
	private void initView() {

		int maxS = sharedPreferences.getInt("Scale", 0);// 电池最大电量
		int nowS = sharedPreferences.getInt("L", 0);
		System.out.println(maxS + "qqqqqqqqqqqq");
		txtL = (TextView) findViewById(R.id.level);
		if (maxS > 0) {
			txtL.setText(nowS * 100 / maxS + "%");
		} else {
			txtL.setText(nowS + "%");
		}

		txtS = (TextView) findViewById(R.id.status);
		int status = sharedPreferences.getInt("Status", 0);
		String[] str = getResources().getStringArray(R.array.battery_status);
		txtS.setText(str[status]);

		txtT = (TextView) findViewById(R.id.temperature);
		txtT.setText(sharedPreferences.getInt("T", 0) / 10 + "℃");

		txtV = (TextView) findViewById(R.id.voltage);
		txtV.setText(sharedPreferences.getInt("V", 0) + "mv");

		txtH = (TextView) findViewById(R.id.health);
		int health = sharedPreferences.getInt("H", 0);
		String[] str1 = getResources().getStringArray(R.array.battery_health);
		txtH.setText(str1[health]);
	}

	/**
	 * 初始化控件(图片)
	 * */
	private void initDrawable() {
		imgBD = (ImageView) findViewById(R.id.img_battery);
		int b = sharedPreferences.getInt("L", 0);// 当前电量
		int s = sharedPreferences.getInt("Status", 0);
		if (s == 3) {
			animationDrawable.stop();
			if (b <= 10) {
				imgBD.setBackgroundResource(R.drawable.bt10);
			} else if (b >= 10 && b <= 20) {
				imgBD.setBackgroundResource(R.drawable.bt20);
			} else if (b >= 20 && b <= 30) {
				imgBD.setBackgroundResource(R.drawable.bt30);
			} else if (b >= 30 && b <= 50) {
				imgBD.setBackgroundResource(R.drawable.bt50);
			} else if (b >= 50 && b <= 70) {
				imgBD.setBackgroundResource(R.drawable.bt70);
			} else if (b >= 70 && b <= 80) {
				imgBD.setBackgroundResource(R.drawable.bt80);
			} else if (b >= 80 && b <= 90) {
				imgBD.setBackgroundResource(R.drawable.bt90);
			} else if (b >= 90 && b <= 100) {
				imgBD.setBackgroundResource(R.drawable.bt100);
			}
		} else if (s == 2) {

			imgBD.setBackgroundDrawable(animationDrawable);
			animationDrawable.start();
		}

	}

	/**
	 * 广播接收器，作用：检测电池信息
	 * */
	private void mesgBroadcast() {

		myBroadcast = new BatteryBroadcast();// 广播接收器

		IntentFilter filter = new IntentFilter();// 过滤器，选择性的接收广播信息

		filter.addAction(Intent.ACTION_BATTERY_CHANGED);// 接收广播的条件

		registerReceiver(myBroadcast, filter);// 注册广播接收者

		sharedPreferences = getSharedPreferences("NAME",
				BatteryDetectionActivity.MODE_PRIVATE);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		animationDrawable = (AnimationDrawable) getResources().getDrawable(
				R.anim.battery_anim);

	}

	/**
	 * 在Activity的onDestory中关闭广播
	 * */
	@Override
	protected void onDestroy() {

		super.onDestroy();

		unregisterReceiver(myBroadcast);// 关闭广播
	}

	/**
	 * 
	 * OnSharedPreferenceChangeListener
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		initView();
		initDrawable();

	}

}
