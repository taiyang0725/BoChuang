package edu.feicui.assistant.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import edu.feicui.assistant.R;

/**
 * ����һ�����ά������
 * */
public class BatteryMaintainActivity extends BaseOneActivity implements
		OnSharedPreferenceChangeListener {
	/**
	 * ���ͼƬ
	 * */
	private ImageView imgM;
	/**
	 * ���ٳ��С����
	 * */
	private ImageView imgBulb1;
	/**
	 * ���ٳ������
	 * */
	private TextView txtBulb1;
	/**
	 * ѭ�����С����
	 * */
	private ImageView imgBulb2;
	/**
	 * ѭ���������
	 * */
	private TextView txtBulb2;
	/**
	 * ������С����
	 * */
	private ImageView imgBulb3;
	/**
	 * ����������
	 * */
	private TextView txtBulb3;
	/**
	 * ���״̬����
	 * */
	private TextView txtM;
	/**
	 * ����
	 * */
	private AnimationDrawable animationDrawable;
	/**
	 * ��������
	 * */
	private SharedPreferences sharedPreferences;
	/**
	 * ��ǰ����
	 * */
	private int b;

	/**
	 * ��ǰ״̬
	 * */
	private int s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battery_maintain);

		initView();
		changeView();
	}

	/**
	 * �ؼ���ʼ��
	 * */
	private void initView() {

		imgM = (ImageView) findViewById(R.id.img_maintain);
		txtM = (TextView) findViewById(R.id.txt_b);

		imgBulb1 = (ImageView) findViewById(R.id.bulb1);
		imgBulb2 = (ImageView) findViewById(R.id.bulb2);
		imgBulb3 = (ImageView) findViewById(R.id.bulb3);

		txtBulb1 = (TextView) findViewById(R.id.txt_bulb1);
		txtBulb2 = (TextView) findViewById(R.id.txt_bulb2);
		txtBulb3 = (TextView) findViewById(R.id.txt_bulb3);

		sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

	}

	/**
	 * ״̬�ı䣬View�ı�
	 * */
	private void changeView() {
		b = sharedPreferences.getInt("L", 0);// ��ǰ����
		s = sharedPreferences.getInt("Status", 0);// ��ǰ״̬

		if (s == 3) {

			txtM.setText(R.string.battery_charge1);
			if (b <= 10) {
				imgM.setBackgroundResource(R.drawable.bt10);
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb1.setTextColor(Color.parseColor("#8B0000"));

			} else if (b >= 10 && b <= 20) {
				imgM.setBackgroundResource(R.drawable.bt20);
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb1.setTextColor(Color.parseColor("#FFFFFF"));
			} else if (b >= 20 && b <= 30) {
				imgM.setBackgroundResource(R.drawable.bt30);
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb1.setTextColor(Color.parseColor("#FFFFFF"));
			} else if (b >= 30 && b <= 50) {
				imgM.setBackgroundResource(R.drawable.bt50);
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb1.setTextColor(Color.parseColor("#FFFFFF"));

			} else if (b >= 50 && b <= 70) {
				imgM.setBackgroundResource(R.drawable.bt70);
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb1.setTextColor(Color.parseColor("#FFFFFF"));

			} else if (b >= 70 && b <= 80) {
				imgM.setBackgroundResource(R.drawable.bt80);
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb1.setTextColor(Color.parseColor("#FFFFFF"));

			} else if (b >= 80 && b <= 90) {
				imgM.setBackgroundResource(R.drawable.bt90);
				imgBulb2.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb2.setTextColor(Color.parseColor("#FFFFFF"));

			} else if (b >= 90 && b <= 100) {
				imgM.setBackgroundResource(R.drawable.bt100);
				imgBulb2.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb2.setTextColor(Color.parseColor("#FFFFFF"));
			} else if (b >= 100) {
				imgBulb3.setBackgroundResource(R.drawable.battery_bulb_deactive);
				txtBulb3.setTextColor(Color.parseColor("#FFFFFF"));
			}
		} else if (s == 2) {
			animationDrawable = (AnimationDrawable) getResources().getDrawable(
					R.anim.battery_anim);

			imgM.setBackgroundDrawable(animationDrawable);

			animationDrawable.start();
			txtM.setText(R.string.battery_charge);
			if (b <= 80) {
				imgBulb1.setBackgroundResource(R.drawable.battery_bulb_active);
				txtBulb1.setTextColor(Color.parseColor("#17a221"));

			} else if (b > 80 && b < 100) {
				imgBulb2.setBackgroundResource(R.drawable.battery_bulb_active);
				txtBulb2.setTextColor(Color.parseColor("#17a221"));
			} else if (b >= 100) {
				imgBulb3.setBackgroundResource(R.drawable.battery_bulb_active);
				txtBulb3.setTextColor(Color.parseColor("#17a221"));
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		initView();
		changeView();

	}

}
