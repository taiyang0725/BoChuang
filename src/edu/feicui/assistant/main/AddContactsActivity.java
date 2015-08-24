package edu.feicui.assistant.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import edu.feicui.assistant.R;
import edu.feicui.assistant.adapter.SelPicAdapter;
import edu.feicui.assistant.mode.Person;
import edu.feicui.assistant.util.UserInfoUtil;

/**
 * ����һ�������ϵ����Ϣ����
 * */
public class AddContactsActivity extends BaseOneActivity implements
		OnClickListener {

	/**
	 * ��ϵ��ͼ��
	 * */
	private ImageView imgPicture;
	/**
	 * Gallery
	 * */
	private Gallery glr;
	/**
	 * �û�ͼ������
	 * */
	private int[] selpic;

	/**
	 * �����û���Ϣ��ť
	 * */
	private Button btnSave;
	/**
	 * ���ذ�ť
	 * */
	private Button btnReturn;
	/**
	 * �û�ͼ��ID
	 * */
	private int picID;
	/**
	 * �û���Ϣ������
	 * */
	private UserInfoUtil userInfoUtil;

	private ImageSwitcher switcher;
	/**
	 * �༭������
	 * */
	private EditText[] edt;

	private Person person;

	private int num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_adduserinfo);

		initData();

		initView();
	}

	/**
	 * ��ʼ���û�ͼ����Դ
	 * */
	private void initData() {
		userInfoUtil = new UserInfoUtil();

		selpic = userInfoUtil.userIcon();

	}

	/**
	 * �ؼ���ʼ��
	 * */
	private void initView() {

		imgPicture = (ImageView) findViewById(R.id.img_person_picture);
		imgPicture.setOnClickListener(this);

		btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(this);

		btnReturn = (Button) findViewById(R.id.btn_re_turn);
		btnReturn.setOnClickListener(this);

		/**
		 * ��ʼ���༭������
		 * */
		edt = new EditText[11];

		/**
		 * �û������༭��
		 * */
		edt[0] = (EditText) findViewById(R.id.edt_user_name);

		/**
		 * �绰����༭��
		 * */
		edt[1] = (EditText) findViewById(R.id.edt_tel);

		/**
		 * �칫�ҵ绰�༭��
		 * */
		edt[2] = (EditText) findViewById(R.id.edt_office_tel);

		/**
		 * ��ͥ�绰�༭��
		 * */
		edt[3] = (EditText) findViewById(R.id.edt_family_tel);

		/**
		 * ְ��ְ�Ʊ༭��
		 * */
		edt[4] = (EditText) findViewById(R.id.edt_work_name);

		/**
		 * ��λ���ֱ༭��
		 * */
		edt[5] = (EditText) findViewById(R.id.edt_unit_name);

		/**
		 * ��ַ�༭��
		 * */
		edt[6] = (EditText) findViewById(R.id.edt_family_address);

		/**
		 * ��������༭��
		 * */
		edt[7] = (EditText) findViewById(R.id.edt_zip_code);

		/**
		 * E-mail�༭��
		 * */
		edt[8] = (EditText) findViewById(R.id.edt_e_mail);

		/**
		 * ������ϵ��ʽ�༭��
		 * */
		edt[9] = (EditText) findViewById(R.id.edt_other_tel);

		/**
		 * ��ע�༭��
		 * */
		edt[10] = (EditText) findViewById(R.id.edt_remark);

	}

	/**
	 * ��ȡ�༭�������
	 * */
	private Person getPerson() {
		person = new Person();
		person.setName(edt[0].getText().toString());
		person.setTelNum(edt[1].getText().toString());
		person.setOfficeTel(edt[2].getText().toString());
		person.setHomeTel(edt[3].getText().toString());
		person.setWorkName(edt[4].getText().toString());
		person.setUnitName(edt[5].getText().toString());
		person.setAddress(edt[6].getText().toString());
		person.setPostalcode(edt[7].getText().toString());
		person.seteMail(edt[8].getText().toString());
		person.setContact(edt[9].getText().toString());
		person.setRemark(edt[10].getText().toString());
		return person;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_person_picture:// �û�ͼ��
			selPic();
			break;
		case R.id.btn_save:// �����û���Ϣ��ť
			addUserInfo();
			break;
		case R.id.btn_re_turn:// ���ذ�ť
			finish();
			break;

		default:
			break;
		}

	}

	/** ������ϵ����Ϣ */
	private void addUserInfo() {
		if (TextUtils.isEmpty(edt[0].getText().toString())) {
			edt[0].setError(getResources().getString(R.string.name_null)
					.toString());
		} else {
			userInfoUtil.insert(getPerson(), this);

			Toast.makeText(this, R.string.save_scuu, Toast.LENGTH_SHORT).show();
			finish();
			jump(AddressListActivity.class);

		}
	}

	/**
	 * �û�ͼ��ѡ��Ի���ķ���
	 * */
	private void selPic() {

		View view = getLayoutInflater().inflate(R.layout.pictruegallery, null);

		switcher = (ImageSwitcher) view.findViewById(R.id.imageswitcher);

		glr = (Gallery) view.findViewById(R.id.glr_select_picture);
		SelPicAdapter adapter = new SelPicAdapter(this, selpic);
		glr.setAdapter(adapter);
		glr.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switcher.setBackgroundResource(selpic[position]);
				picID = position;

			}
		});
		glr.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switcher.setBackgroundResource(selpic[position]);
				picID = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.sel_pic);
		builder.setView(view);
		builder.setPositiveButton(R.string.con_firm,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						imgPicture.setBackgroundResource(selpic[picID]);
					}
				});
		builder.setNegativeButton(R.string.can_cel, null);
		builder.create().show();

	}

	/**
	 * ��ת��������ķ���
	 * */
	private void jump(Class<?> cls) {

		Intent it = new Intent(AddContactsActivity.this, cls);

		startActivity(it);
	}
}
