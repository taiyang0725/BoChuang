package edu.feicui.assistant.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import edu.feicui.assistant.R;
import edu.feicui.assistant.mode.Person;
import edu.feicui.assistant.util.UserInfoUtil;

/**
 * ����һ���޸��û���Ϣ����
 * */
public class AlterInfoActivity extends BaseOneActivity implements
		OnClickListener {
	/**
	 * �޸İ�ť
	 * */
	private Button btnAlter;
	/**
	 * �����޸İ�ť
	 * */
	private Button btnSaveAlter;
	/**
	 * ɾ����ť��ť
	 * */
	private Button btnDel;
	/**
	 * ���ذ�ť
	 * */
	private Button btnReturn;
	/**
	 * �Ի���
	 * */
	private AlertDialog.Builder builder;
	/**
	 * �޸��û���Ϣ��ť����
	 * */
	private LinearLayout layoutAlterMenu;

	/**
	 * ��绰��ť
	 * */
	private ImageButton imgCall;
	/**
	 * �����Ű�ť
	 * */
	private ImageButton imgSend;
	/**
	 * ��ϵ��ͼ��
	 * */
	private ImageView imgPicture;
	/**
	 * �༭������
	 * */
	private EditText[] edt;
	/**
	 * �������
	 * */
	private Person person;
	private Person person1;
	/**
	 * �û���Ϣ���������
	 * */
	private UserInfoUtil userInfoUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alterinfo);

		initData();

		initView();
	}

	private void initData() {

		Intent intent = getIntent();
		person = (Person) intent.getSerializableExtra("userInfo");

		userInfoUtil = new UserInfoUtil();

	}

	private void initView() {

		btnAlter = (Button) findViewById(R.id.btn_info_alter);
		btnAlter.setOnClickListener(this);

		btnSaveAlter = (Button) findViewById(R.id.btn_save_info_alter);
		btnSaveAlter.setOnClickListener(this);

		btnDel = (Button) findViewById(R.id.btn_info_delete);
		btnDel.setOnClickListener(this);

		btnReturn = (Button) findViewById(R.id.btn_info_return);
		btnReturn.setOnClickListener(this);

		layoutAlterMenu = (LinearLayout) findViewById(R.id.layout_info_alter_menu);

		imgCall = (ImageButton) findViewById(R.id.img_call_up);
		imgCall.setOnClickListener(this);

		imgSend = (ImageButton) findViewById(R.id.img_send_msg);
		imgSend.setOnClickListener(this);

		imgPicture = (ImageView) findViewById(R.id.img_person_picture);
		imgPicture.setBackgroundResource(person.getUserIcon());
		/**
		 * ��ʼ���༭������
		 * */
		edt = new EditText[11];
		/**
		 * �û������༭��
		 * */
		edt[0] = (EditText) findViewById(R.id.edt_user_name);
		edt[0].setText(person.getName());

		/**
		 * �绰����༭��
		 * */
		edt[1] = (EditText) findViewById(R.id.edt_tel);
		edt[1].setText(person.getTelNum());
		/**
		 * �칫�ҵ绰�༭��
		 * */
		edt[2] = (EditText) findViewById(R.id.edt_office_tel);
		edt[2].setText(person.getOfficeTel());
		/**
		 * ��ͥ�绰�༭��
		 * */
		edt[3] = (EditText) findViewById(R.id.edt_family_tel);
		edt[3].setText(person.getHomeTel());
		/**
		 * ְ��ְ�Ʊ༭��
		 * */
		edt[4] = (EditText) findViewById(R.id.edt_work_name);
		edt[4].setText(person.getWorkName());
		/**
		 * ��λ���ֱ༭��
		 * */
		edt[5] = (EditText) findViewById(R.id.edt_unit_name);
		edt[5].setText(person.getTelNum());
		/**
		 * ��ַ�༭��
		 * */
		edt[6] = (EditText) findViewById(R.id.edt_family_address);
		edt[6].setText(person.getAddress());
		/**
		 * ��������༭��
		 * */
		edt[7] = (EditText) findViewById(R.id.edt_zip_code);
		edt[7].setText(person.getPostalcode());
		/**
		 * E-mail�༭��
		 * */
		edt[8] = (EditText) findViewById(R.id.edt_e_mail);
		edt[8].setText(person.geteMail());
		/**
		 * ������ϵ��ʽ�༭��
		 * */
		edt[9] = (EditText) findViewById(R.id.edt_other_tel);
		edt[9].setText(person.getContact());
		/**
		 * ��ע�༭��
		 * */
		edt[10] = (EditText) findViewById(R.id.edt_remark);
		edt[10].setText(person.getRemark());

		for (int i = 0; i < edt.length; i++) {
			edt[i].setEnabled(false);

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_info_alter:// �޸�
			alterUserInfo();
			break;
		case R.id.btn_save_info_alter:// �����޸�

			saveAalter();
			finish();
			jump(AddressListActivity.class);
			break;

		case R.id.btn_info_delete:// ɾ��
			isDelete();
			break;
		case R.id.btn_info_return:// ����
			finish();

			break;
		case R.id.img_call_up:// ��绰
			call();
			break;
		case R.id.img_send_msg:// ������
			finish();
			jump(SendInfoActivity.class, person);
			break;

		default:
			break;
		}

	}

	/**
	 * �����޸�
	 * */
	private void saveAalter() {

		userInfoUtil.update(getPerson(), AlterInfoActivity.this, person);
		btnSaveAlter.setVisibility(View.GONE);
		btnAlter.setVisibility(View.VISIBLE);
		for (int i = 0; i < edt.length; i++) {
			edt[i].setEnabled(false);
		}

	}

	/**
	 * ��ȡ�༭�������
	 * */
	private Person getPerson() {
		person1 = new Person();

		person1.setName(edt[0].getText().toString());

		person1.setTelNum(edt[1].getText().toString());

		person1.setOfficeTel(edt[2].getText().toString());

		person1.setHomeTel(edt[3].getText().toString());

		person1.setWorkName(edt[4].getText().toString());

		person1.setUnitName(edt[5].getText().toString());

		person1.setAddress(edt[6].getText().toString());

		person1.setPostalcode(edt[7].getText().toString());

		person1.seteMail(edt[8].getText().toString());

		person1.setContact(edt[9].getText().toString());

		person1.setRemark(edt[10].getText().toString());
		return person1;
	}

	/** �޸��û���Ϣ */
	private void alterUserInfo() {

		for (int i = 0; i < edt.length; i++) {
			edt[i].setEnabled(true);
		}

		btnSaveAlter.setVisibility(View.VISIBLE);
		btnAlter.setVisibility(View.GONE);

	}

	/**
	 * ��绰�ķ���
	 * */
	private void call() {
		String dial = "tel" + ":" + person.getTelNum();
		Intent intent = new Intent();
		/**
		 * Intent.ACTION_DIAL,����ҪȨ��,���Ž��棬��Ҫ������� Intent.ACTION_CALL.��ҪȨ�ޣ����н���
		 * */
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(dial));
		startActivity(intent);

	}

	/**
	 * ѡ��˵���������
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:// ���ؼ�
			finish();
			break;
		case KeyEvent.KEYCODE_MENU:// �˵���
			settingGone(layoutAlterMenu);

			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �ؼ���������ʾ�ķ���
	 * */
	private void settingGone(View view) {
		switch (view.getVisibility()) {
		case View.GONE:
			view.setVisibility(View.VISIBLE);
			break;
		case View.VISIBLE:
			view.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	/**
	 * �Ƿ�ɾ���Ի���ķ���
	 * */
	private void isDelete() {
		builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.is_delete_one);
		builder.setPositiveButton(R.string.con_firm,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						userInfoUtil.delete(AlterInfoActivity.this,
								person.getRawContactsId());
						finish();
						jump(AddressListActivity.class);
					}
				});
		builder.setNegativeButton(R.string.can_cel, null);
		builder.create().show();
	}

	/**
	 * ��ת��������ķ���
	 * */
	private void jump(Class<?> cls, Person person) {
		Intent it = new Intent(AlterInfoActivity.this, cls);
		it.putExtra("userInfo", person);
		startActivity(it);
	}

	/**
	 * ��ת��������ķ���
	 * */
	private void jump(Class<?> cls) {
		Intent it = new Intent(AlterInfoActivity.this, cls);

		startActivity(it);
	}

}
