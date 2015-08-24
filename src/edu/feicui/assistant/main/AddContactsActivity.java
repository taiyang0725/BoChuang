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
 * 这是一个添加联系人信息的类
 * */
public class AddContactsActivity extends BaseOneActivity implements
		OnClickListener {

	/**
	 * 联系人图标
	 * */
	private ImageView imgPicture;
	/**
	 * Gallery
	 * */
	private Gallery glr;
	/**
	 * 用户图标数组
	 * */
	private int[] selpic;

	/**
	 * 保存用户信息按钮
	 * */
	private Button btnSave;
	/**
	 * 返回按钮
	 * */
	private Button btnReturn;
	/**
	 * 用户图标ID
	 * */
	private int picID;
	/**
	 * 用户信息工具类
	 * */
	private UserInfoUtil userInfoUtil;

	private ImageSwitcher switcher;
	/**
	 * 编辑框数组
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
	 * 初始化用户图标资源
	 * */
	private void initData() {
		userInfoUtil = new UserInfoUtil();

		selpic = userInfoUtil.userIcon();

	}

	/**
	 * 控件初始化
	 * */
	private void initView() {

		imgPicture = (ImageView) findViewById(R.id.img_person_picture);
		imgPicture.setOnClickListener(this);

		btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(this);

		btnReturn = (Button) findViewById(R.id.btn_re_turn);
		btnReturn.setOnClickListener(this);

		/**
		 * 初始化编辑框数组
		 * */
		edt = new EditText[11];

		/**
		 * 用户姓名编辑框
		 * */
		edt[0] = (EditText) findViewById(R.id.edt_user_name);

		/**
		 * 电话号码编辑框
		 * */
		edt[1] = (EditText) findViewById(R.id.edt_tel);

		/**
		 * 办公室电话编辑框
		 * */
		edt[2] = (EditText) findViewById(R.id.edt_office_tel);

		/**
		 * 家庭电话编辑框
		 * */
		edt[3] = (EditText) findViewById(R.id.edt_family_tel);

		/**
		 * 职务职称编辑框
		 * */
		edt[4] = (EditText) findViewById(R.id.edt_work_name);

		/**
		 * 单位名字编辑框
		 * */
		edt[5] = (EditText) findViewById(R.id.edt_unit_name);

		/**
		 * 地址编辑框
		 * */
		edt[6] = (EditText) findViewById(R.id.edt_family_address);

		/**
		 * 邮政编码编辑框
		 * */
		edt[7] = (EditText) findViewById(R.id.edt_zip_code);

		/**
		 * E-mail编辑框
		 * */
		edt[8] = (EditText) findViewById(R.id.edt_e_mail);

		/**
		 * 其他联系方式编辑框
		 * */
		edt[9] = (EditText) findViewById(R.id.edt_other_tel);

		/**
		 * 备注编辑框
		 * */
		edt[10] = (EditText) findViewById(R.id.edt_remark);

	}

	/**
	 * 获取编辑框的内容
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
		case R.id.img_person_picture:// 用户图标
			selPic();
			break;
		case R.id.btn_save:// 保存用户信息按钮
			addUserInfo();
			break;
		case R.id.btn_re_turn:// 返回按钮
			finish();
			break;

		default:
			break;
		}

	}

	/** 新增联系人信息 */
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
	 * 用户图标选择对话框的方法
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
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls) {

		Intent it = new Intent(AddContactsActivity.this, cls);

		startActivity(it);
	}
}
