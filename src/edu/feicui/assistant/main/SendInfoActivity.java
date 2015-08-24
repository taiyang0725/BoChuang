package edu.feicui.assistant.main;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.feicui.assistant.R;
import edu.feicui.assistant.mode.Person;
import edu.feicui.assistant.util.UserInfoUtil;

/**
 * 这是一个发送短信息界面的类
 * */
public class SendInfoActivity extends BaseOneActivity implements
		OnClickListener {
	/**
	 * 发送短信的按钮
	 * */
	private Button btn;
	/**
	 * 
	 * 号码框
	 */
	private EditText edtTel;
	/**
	 * 信息框
	 * */
	private EditText edtMsg;
	/**
	 * 人类对象
	 * */
	private Person person;
	/**
	 * 用户信息工具类对象
	 * */
	private UserInfoUtil userInfoUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sendmsg);

		initData();

		initView();
	}

	private void initData() {

		Intent intent = getIntent();
		person = (Person) intent.getSerializableExtra("userInfo");

	}

	private void initView() {

		btn = (Button) findViewById(R.id.btn_send_msg_content);
		btn.setOnClickListener(this);

		edtTel = (EditText) findViewById(R.id.edt_send_tel_number);
		edtTel.setText(person.getTelNum());

		edtMsg = (EditText) findViewById(R.id.edt_send_msg);

	}

	@Override
	public void onClick(View v) {
		if (!PhoneNumberUtils.isGlobalPhoneNumber(edtTel.getText().toString())) {

			edtTel.setError(getResources().getString(R.string.number_yes_no));

		} else {
			SmsManager smsManager = SmsManager.getDefault();

			Intent intent = new Intent(this, MsgBroadcast.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,
					intent, 0);

			smsManager.sendTextMessage(edtTel.getText().toString(), null,
					edtMsg.getText().toString(), pendingIntent, null);

			finish();

			jump(AddContactsActivity.class);
		}

	}

	/**
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls) {

		Intent it = new Intent(SendInfoActivity.this, cls);

		startActivity(it);
	}
}
