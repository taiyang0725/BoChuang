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
 * ����һ�����Ͷ���Ϣ�������
 * */
public class SendInfoActivity extends BaseOneActivity implements
		OnClickListener {
	/**
	 * ���Ͷ��ŵİ�ť
	 * */
	private Button btn;
	/**
	 * 
	 * �����
	 */
	private EditText edtTel;
	/**
	 * ��Ϣ��
	 * */
	private EditText edtMsg;
	/**
	 * �������
	 * */
	private Person person;
	/**
	 * �û���Ϣ���������
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
	 * ��ת��������ķ���
	 * */
	private void jump(Class<?> cls) {

		Intent it = new Intent(SendInfoActivity.this, cls);

		startActivity(it);
	}
}
