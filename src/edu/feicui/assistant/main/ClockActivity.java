package edu.feicui.assistant.main;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import edu.feicui.assistant.R;

/**
 * 这是一个闹钟类
 **/
public class ClockActivity extends BaseActivity implements OnClickListener,
		OnTimeChangedListener, android.content.DialogInterface.OnClickListener {
	/**
	 * 时间
	 * */
	private TextView txtTime;
	/**
	 * 时钟
	 * */
	private ImageView imgClock;
	/**
	 * 日期、星期
	 * */
	private TextView txtData;
	/**
	 * 开启闹钟
	 * */
	private Button btnClock;
	/**
	 * 设置时间
	 **/
	private Button btnTime;
	/**
	 * 时间设置控件
	 * */
	private TimePicker tpk;
	/**
	 * 设置的时间
	 * */
	private String time;
	/**
	 * 设置的时间的小时
	 * */
	private int h;
	/**
	 * 设置的时间的分钟
	 * */
	private int min;
	/**
	 * 当前时间
	 * */
	private long nowTime;
	/**
	 * 闹钟管理者
	 * */
	private AlarmManager manager;
	/**
	 * 日历对象
	 * */
	private Calendar calendar;
	/**
	 * 闹钟操作者
	 * */
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_clock);

		initView();
	}

	/**
	 * 控件初始化
	 * */
	private void initView() {

		String time = "%1$tH:%1$tM";// 24小时时间制
		String data = "%1$tY-%1$tm-%1$td  %1$tA";// 日期和星期
		nowTime = System.currentTimeMillis();

		// txtTime = (TextView) findViewById(R.id.txt_time);
		// txtTime.setText(String.format(time, System.currentTimeMillis()));

		txtData = (TextView) findViewById(R.id.txt_year);
		txtData.setText(String.format(data, nowTime));

		btnClock = (Button) findViewById(R.id.start_clock);
		btnClock.setOnClickListener(this);

		btnTime = (Button) findViewById(R.id.setting_time);
		btnTime.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_clock:
			closeClock();
			break;
		case R.id.setting_time:
			settingClockDialog();

			break;

		default:
			break;
		}

	}

	/**
	 * 设置闹钟对话框
	 * */
	private void settingClockDialog() {

		View view = getLayoutInflater().inflate(R.layout.settingtime, null);
		tpk = (TimePicker) view.findViewById(R.id.tpk);
		tpk.setOnTimeChangedListener(this);

		tpk.setIs24HourView(true);
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(nowTime);
		tpk.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		tpk.setCurrentMinute(calendar.get(Calendar.MINUTE));

		AlertDialog.Builder builder = new AlertDialog.Builder(
				ClockActivity.this);
		builder.setIcon(R.drawable.clock);
		builder.setTitle(R.string.setting);
		builder.setView(view);
		builder.setPositiveButton(R.string.con_firm, this);// 确定
		builder.setNegativeButton(R.string.can_cel, null);// 取消

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	/**
	 * OnTimeChangedListener
	 * */
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		time = String.format("%1$02d:%2$02d", hourOfDay, minute);
		h = hourOfDay;
		min = minute;

	}

	/**
	 * android.content.DialogInterface.OnClickListener
	 * */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		btnTime.setText(time);
		btnTime.setEnabled(false);
		btnClock.setText(R.string.close_clock);
		settingClock();
		Toast toast = Toast.makeText(ClockActivity.this, "", Toast.LENGTH_LONG);
		View view = getLayoutInflater().inflate(R.layout.clock, null);
		txtTime = (TextView) view.findViewById(R.id.txt_time);
		txtTime.setText(time);
		toast.setView(view);
		toast.show();

	}

	/**
	 * 设置闹钟
	 * */
	private void settingClock() {

		manager = (AlarmManager) getSystemService(ClockActivity.ALARM_SERVICE);

		Intent intents = new Intent(this, TimeOutActivity.class);

		pendingIntent = PendingIntent.getActivity(this, 1, intents, 0);

		calendar.setTimeInMillis(nowTime);
		calendar.set(Calendar.HOUR_OF_DAY, h);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);

		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);

		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), 60000, pendingIntent);

	}

	/**
	 * 关闭闹钟的方法
	 * */
	private void closeClock() {
		if (manager != null) {
			manager.cancel(pendingIntent);
			btnClock.setText(R.string.start_clock);
			btnTime.setText(R.string.setting_time);
			btnTime.setEnabled(true);
			manager = null;
		} else {
			Toast.makeText(this, getResources().getString(R.string.no_clock),
					Toast.LENGTH_LONG).show();
		}

	}
}
