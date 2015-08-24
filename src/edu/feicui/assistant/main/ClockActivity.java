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
 * ����һ��������
 **/
public class ClockActivity extends BaseActivity implements OnClickListener,
		OnTimeChangedListener, android.content.DialogInterface.OnClickListener {
	/**
	 * ʱ��
	 * */
	private TextView txtTime;
	/**
	 * ʱ��
	 * */
	private ImageView imgClock;
	/**
	 * ���ڡ�����
	 * */
	private TextView txtData;
	/**
	 * ��������
	 * */
	private Button btnClock;
	/**
	 * ����ʱ��
	 **/
	private Button btnTime;
	/**
	 * ʱ�����ÿؼ�
	 * */
	private TimePicker tpk;
	/**
	 * ���õ�ʱ��
	 * */
	private String time;
	/**
	 * ���õ�ʱ���Сʱ
	 * */
	private int h;
	/**
	 * ���õ�ʱ��ķ���
	 * */
	private int min;
	/**
	 * ��ǰʱ��
	 * */
	private long nowTime;
	/**
	 * ���ӹ�����
	 * */
	private AlarmManager manager;
	/**
	 * ��������
	 * */
	private Calendar calendar;
	/**
	 * ���Ӳ�����
	 * */
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_clock);

		initView();
	}

	/**
	 * �ؼ���ʼ��
	 * */
	private void initView() {

		String time = "%1$tH:%1$tM";// 24Сʱʱ����
		String data = "%1$tY-%1$tm-%1$td  %1$tA";// ���ں�����
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
	 * �������ӶԻ���
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
		builder.setPositiveButton(R.string.con_firm, this);// ȷ��
		builder.setNegativeButton(R.string.can_cel, null);// ȡ��

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
	 * ��������
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
	 * �ر����ӵķ���
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
