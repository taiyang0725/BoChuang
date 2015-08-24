package edu.feicui.assistant.main;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.feicui.assistant.R;

public class TimeOutActivity extends BaseOneActivity implements OnClickListener {
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_out);

		setDialog();

		music();

	}

	private void music() {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();

		try {
			mediaPlayer.setDataSource(this,
					RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void setDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.clock);
		builder.setTitle(R.string.alarm_clock);
		builder.setMessage(R.string.hi_time_out);
		builder.setNegativeButton(R.string.know, this);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		mediaPlayer.stop();

		mediaPlayer = null;

		changeBtnTex();

		finish();

	}

	/**
	 * 改变ClockActivity的Button的方法
	 * */
	private void changeBtnTex() {
		View view = getLayoutInflater().inflate(R.layout.activity_clock, null);
		Button btnTime = (Button) view.findViewById(R.id.setting_time);
		btnTime.setText(R.string.setting_time);
		btnTime.setEnabled(true);
		Button btnClock = (Button) view.findViewById(R.id.start_clock);
		btnTime.setText(R.string.start_clock);
		System.out.println("aaaaaaaaaa");

	}
}
