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
 * ����һ����ؼ����
 **/
public class BatteryDetectionActivity extends BaseOneActivity implements
		OnSharedPreferenceChangeListener {
	private ImageView imgBattery;
	/**
	 * �㲥������
	 * */
	private BatteryBroadcast myBroadcast;
	/**
	 * ��ص�ǰ����
	 * */
	private TextView txtL;
	/**
	 * ���״̬
	 * */
	private TextView txtS;
	/**
	 * ����¶�
	 * */
	private TextView txtT;
	/**
	 * ��ص�ѹ
	 * */
	private TextView txtV;
	/**
	 * ���ʹ�����
	 * */
	private TextView txtH;
	/**
	 * ���ͼƬ
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
	 * ��ʼ���ؼ�(�ַ�)
	 * */
	private void initView() {

		int maxS = sharedPreferences.getInt("Scale", 0);// ���������
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
		txtT.setText(sharedPreferences.getInt("T", 0) / 10 + "��");

		txtV = (TextView) findViewById(R.id.voltage);
		txtV.setText(sharedPreferences.getInt("V", 0) + "mv");

		txtH = (TextView) findViewById(R.id.health);
		int health = sharedPreferences.getInt("H", 0);
		String[] str1 = getResources().getStringArray(R.array.battery_health);
		txtH.setText(str1[health]);
	}

	/**
	 * ��ʼ���ؼ�(ͼƬ)
	 * */
	private void initDrawable() {
		imgBD = (ImageView) findViewById(R.id.img_battery);
		int b = sharedPreferences.getInt("L", 0);// ��ǰ����
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
	 * �㲥�����������ã��������Ϣ
	 * */
	private void mesgBroadcast() {

		myBroadcast = new BatteryBroadcast();// �㲥������

		IntentFilter filter = new IntentFilter();// ��������ѡ���ԵĽ��չ㲥��Ϣ

		filter.addAction(Intent.ACTION_BATTERY_CHANGED);// ���չ㲥������

		registerReceiver(myBroadcast, filter);// ע��㲥������

		sharedPreferences = getSharedPreferences("NAME",
				BatteryDetectionActivity.MODE_PRIVATE);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		animationDrawable = (AnimationDrawable) getResources().getDrawable(
				R.anim.battery_anim);

	}

	/**
	 * ��Activity��onDestory�йرչ㲥
	 * */
	@Override
	protected void onDestroy() {

		super.onDestroy();

		unregisterReceiver(myBroadcast);// �رչ㲥
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
