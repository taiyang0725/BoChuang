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
 * 这是一个修改用户信息的类
 * */
public class AlterInfoActivity extends BaseOneActivity implements
		OnClickListener {
	/**
	 * 修改按钮
	 * */
	private Button btnAlter;
	/**
	 * 保存修改按钮
	 * */
	private Button btnSaveAlter;
	/**
	 * 删除按钮按钮
	 * */
	private Button btnDel;
	/**
	 * 返回按钮
	 * */
	private Button btnReturn;
	/**
	 * 对话框
	 * */
	private AlertDialog.Builder builder;
	/**
	 * 修改用户信息按钮布局
	 * */
	private LinearLayout layoutAlterMenu;

	/**
	 * 打电话按钮
	 * */
	private ImageButton imgCall;
	/**
	 * 发短信按钮
	 * */
	private ImageButton imgSend;
	/**
	 * 联系人图标
	 * */
	private ImageView imgPicture;
	/**
	 * 编辑框数组
	 * */
	private EditText[] edt;
	/**
	 * 人类对象
	 * */
	private Person person;
	private Person person1;
	/**
	 * 用户信息工具类对象
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
		 * 初始化编辑框数组
		 * */
		edt = new EditText[11];
		/**
		 * 用户姓名编辑框
		 * */
		edt[0] = (EditText) findViewById(R.id.edt_user_name);
		edt[0].setText(person.getName());

		/**
		 * 电话号码编辑框
		 * */
		edt[1] = (EditText) findViewById(R.id.edt_tel);
		edt[1].setText(person.getTelNum());
		/**
		 * 办公室电话编辑框
		 * */
		edt[2] = (EditText) findViewById(R.id.edt_office_tel);
		edt[2].setText(person.getOfficeTel());
		/**
		 * 家庭电话编辑框
		 * */
		edt[3] = (EditText) findViewById(R.id.edt_family_tel);
		edt[3].setText(person.getHomeTel());
		/**
		 * 职务职称编辑框
		 * */
		edt[4] = (EditText) findViewById(R.id.edt_work_name);
		edt[4].setText(person.getWorkName());
		/**
		 * 单位名字编辑框
		 * */
		edt[5] = (EditText) findViewById(R.id.edt_unit_name);
		edt[5].setText(person.getTelNum());
		/**
		 * 地址编辑框
		 * */
		edt[6] = (EditText) findViewById(R.id.edt_family_address);
		edt[6].setText(person.getAddress());
		/**
		 * 邮政编码编辑框
		 * */
		edt[7] = (EditText) findViewById(R.id.edt_zip_code);
		edt[7].setText(person.getPostalcode());
		/**
		 * E-mail编辑框
		 * */
		edt[8] = (EditText) findViewById(R.id.edt_e_mail);
		edt[8].setText(person.geteMail());
		/**
		 * 其他联系方式编辑框
		 * */
		edt[9] = (EditText) findViewById(R.id.edt_other_tel);
		edt[9].setText(person.getContact());
		/**
		 * 备注编辑框
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
		case R.id.btn_info_alter:// 修改
			alterUserInfo();
			break;
		case R.id.btn_save_info_alter:// 保存修改

			saveAalter();
			finish();
			jump(AddressListActivity.class);
			break;

		case R.id.btn_info_delete:// 删除
			isDelete();
			break;
		case R.id.btn_info_return:// 返回
			finish();

			break;
		case R.id.img_call_up:// 打电话
			call();
			break;
		case R.id.img_send_msg:// 发短信
			finish();
			jump(SendInfoActivity.class, person);
			break;

		default:
			break;
		}

	}

	/**
	 * 保存修改
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
	 * 获取编辑框的内容
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

	/** 修改用户信息 */
	private void alterUserInfo() {

		for (int i = 0; i < edt.length; i++) {
			edt[i].setEnabled(true);
		}

		btnSaveAlter.setVisibility(View.VISIBLE);
		btnAlter.setVisibility(View.GONE);

	}

	/**
	 * 打电话的方法
	 * */
	private void call() {
		String dial = "tel" + ":" + person.getTelNum();
		Intent intent = new Intent();
		/**
		 * Intent.ACTION_DIAL,不需要权限,拨号界面，需要按发射键 Intent.ACTION_CALL.需要权限，呼叫界面
		 * */
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(dial));
		startActivity(intent);

	}

	/**
	 * 选项菜单创建方法
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:// 返回键
			finish();
			break;
		case KeyEvent.KEYCODE_MENU:// 菜单键
			settingGone(layoutAlterMenu);

			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 控件隐藏与显示的方法
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
	 * 是否删除对话框的方法
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
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls, Person person) {
		Intent it = new Intent(AlterInfoActivity.this, cls);
		it.putExtra("userInfo", person);
		startActivity(it);
	}

	/**
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls) {
		Intent it = new Intent(AlterInfoActivity.this, cls);

		startActivity(it);
	}

}
