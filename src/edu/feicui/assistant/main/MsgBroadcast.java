package edu.feicui.assistant.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import edu.feicui.assistant.R;

/**
 * 这是一个短信回执的类
 * */
public class MsgBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, R.string.msg_succeed, Toast.LENGTH_SHORT)
				.show();

	}

}
