package edu.feicui.assistant.main;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.assistant.R;
import edu.feicui.assistant.adapter.AddressListAdapter;
import edu.feicui.assistant.mode.Person;
import edu.feicui.assistant.util.UserInfoUtil;

/**
 * ����һ��ͨѶ¼��
 * */
public class AddressListActivity extends BaseOneActivity implements
		OnItemClickListener, OnClickListener,

		OnItemLongClickListener, TextWatcher {
	/**
	 * ListView�ؼ�
	 * */
	private ListView lst;
	/**
	 * ��ʾ��ѯ����
	 * */
	private TextView txtNum;
	/**
	 * ��ϵ����Ϣ�ļ���
	 * */
	private ArrayList<Person> list;
	/**
	 * ��ѯ��ϵ����Ϣ�ļ���
	 * */
	private ArrayList<Person> querylist;

	/**
	 * ���������
	 * */
	private UserInfoUtil userInfoUtil;

	/**
	 * �˵�����ؼ�����
	 * */
	private ImageView[] img;
	/**
	 * ��������ʾ��ͼƬ
	 * */
	private ImageView imgNoData;
	/**
	 * �˵������ܲ���
	 * */
	private LinearLayout linearLayoutMenu;
	/**
	 * �Ƿ�ѡ�пؼ�
	 * */
	private CheckBox chb;

	/**
	 * �Ի���
	 * */
	private AlertDialog.Builder builder;
	/**
	 * �Ի������
	 * */
	private AlertDialog alertDialog;
	/**
	 * View
	 * */
	private View view;
	/**
	 * �˵����ҿ�
	 * */
	private EditText edt;
	/**
	 * תȦ���ط�
	 * */
	private ProgressDialog progressDialog;

	/**
	 * ��ѯ����������ַ���
	 * */
	private String info;

	private int telId;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				progressDialog.cancel();
				initAadapter(querylist);
				break;
			case 2:

				initAadapter(querylist);

			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_book);

		initData();

		initView();

	}

	/**
	 * ��ʼ����Դ
	 * */
	private void initData() {
		userInfoUtil = new UserInfoUtil();

		querylist = new ArrayList<Person>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				list = userInfoUtil.getPersonInfo(AddressListActivity.this);
				querylist = list;
				handler.sendEmptyMessage(1);
			}
		}).start();

	}

	/**
	 * ��ʼ��������
	 * */
	private void initAadapter(ArrayList<Person> infoList) {
		lst = (ListView) findViewById(R.id.lst_address);
		lst.setOnItemClickListener(this);
		lst.setOnItemLongClickListener(this);
		AddressListAdapter adapter = new AddressListAdapter(this, infoList);
		lst.setAdapter(adapter);
	}

	/**
	 * �ؼ���ʼ��
	 * */
	private void initView() {

		initAadapter(querylist);

		linearLayoutMenu = (LinearLayout) findViewById(R.id.visibility_menu);

		img = new ImageView[5];
		/** ���� */
		img[0] = (ImageView) findViewById(R.id.img_add);
		img[0].setOnClickListener(this);
		/** ���� */
		img[1] = (ImageView) findViewById(R.id.img_find);
		img[1].setOnClickListener(this);
		/** ͨѶ */
		img[2] = (ImageView) findViewById(R.id.img_communication);
		img[2].setOnClickListener(this);
		/** �˵� */
		img[3] = (ImageView) findViewById(R.id.img_menu_menu);
		img[3].setOnClickListener(this);
		/** �˳� */
		img[4] = (ImageView) findViewById(R.id.img_exit);
		img[4].setOnClickListener(this);

		imgNoData = (ImageView) findViewById(R.id.img_no_data);

		edt = (EditText) findViewById(R.id.edt_find);
		edt.addTextChangedListener(this);

		txtNum = (TextView) findViewById(R.id.txt_find_num);

		progressDialog = ProgressDialog.show(AddressListActivity.this, null,
				getResources().getString(R.string.defy_death));

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
			ViewGone(linearLayoutMenu);

			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �ؼ���������ʾ�ķ���
	 * */
	private void ViewGone(View view) {
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
	 * lst.setOnItemClickListener(this)����¼����Ƿ��绰������Ϣ
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		chb = (CheckBox) view.findViewById(R.id.chb_is_select);

		chb.setChecked(!chb.isChecked());

		querylist.get(position).setCheck(chb.isChecked());

	}

	/**
	 * lst.setOnItemLongClickListener(this);�����¼����޸���Ϣ
	 * */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		jump(AlterInfoActivity.class, position);

		return true;
	}

	/**
	 * �˵�����ص�����
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_add:// ����

			jump(AddContactsActivity.class);

			break;
		case R.id.img_find:// ����
			ViewGone(edt);

			break;
		case R.id.img_communication:// ͨѶ
			selectNum();
			break;
		case R.id.img_menu_menu:// �˵�
			settingMenuMenu();
			break;
		case R.id.img_exit:// ����
			finish();
			jump(MenuActivity.class);
			break;
		case R.id.img_show_all:// ��ʾ����
			querylist = list;
			initAadapter(querylist);
			lst.setVisibility(View.VISIBLE);
			imgNoData.setVisibility(View.GONE);
			txtNum.setText(R.string.app_name);
			alertDialog.cancel();

			break;
		case R.id.img_delete_all:// ɾ������

			isDeleteAll();
			break;
		case R.id.img_retreat:// �˳�
			alertDialog.cancel();
			break;

		default:
			break;
		}
	}

	/**
	 * �Ƿ�ɾ��ȫ�����жԻ���ķ���
	 * */
	private void isDeleteAll() {
		builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.is_delete_all);
		builder.setPositiveButton(R.string.con_firm,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.cancel();
						querylist = list;
						querylist.clear();
						handler.sendEmptyMessage(2);

					}
				});
		builder.setNegativeButton(R.string.can_cel, null);
		builder.create().show();
	}

	/**
	 * ������ʾ���С�ɾ�����жԻ���ķ���
	 * */
	private void settingMenuMenu() {

		builder = new AlertDialog.Builder(this);
		view = getLayoutInflater().inflate(R.layout.menumenu, null);
		builder.setView(view);
		alertDialog = builder.create();
		alertDialog.show();

		img = new ImageView[3];
		/** ��ʾ���� */
		img[0] = (ImageView) view.findViewById(R.id.img_show_all);
		img[0].setOnClickListener(this);
		/** ɾ������ */
		img[1] = (ImageView) view.findViewById(R.id.img_delete_all);
		img[1].setOnClickListener(this);
		/** �˳� */
		img[2] = (ImageView) view.findViewById(R.id.img_retreat);
		img[2].setOnClickListener(this);

	}

	/**
	 * ����ѡ���绰�����ŵķ���
	 * */
	private void settingCommunication() {

		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.select);
		builder.setItems(R.array.communication_array,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							call();

							break;
						case 1:
							finish();
							jump(SendInfoActivity.class, telId);
							break;

						default:
							break;

						}

					}
				});
		builder.setNegativeButton(R.string.can_cel, null);
		builder.create().show();
	}

	/**
	 * ��ת��������ķ���(������Ϣ����ת��
	 * */
	private void jump(Class<?> cls, int index) {

		Intent it = new Intent(AddressListActivity.this, cls);
		it.putExtra("userInfo", querylist.get(index));
		startActivity(it);
	}

	/**
	 * ��ת��������ķ���
	 * */
	private void jump(Class<?> cls) {

		Intent it = new Intent(AddressListActivity.this, cls);

		startActivity(it);
	}

	/**
	 * ��绰�ķ���
	 * */
	private void call() {
		String dial = "tel" + ":" + querylist.get(telId).getTelNum();
		Intent intent = new Intent();
		/**
		 * Intent.ACTION_DIAL,����ҪȨ��,���Ž��棬��Ҫ������� Intent.ACTION_CALL.��ҪȨ�ޣ����н���
		 * */
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(dial));
		startActivity(intent);

	}

	/**
	 * �ж�ѡ�м�������
	 * */
	private void selectNum() {
		int count = 0;
		String[] telCount = getResources().getStringArray(
				R.array.number_select_many);
		for (int i = 0; i < querylist.size(); i++) {

			if (querylist.get(i).isCheck()) {

				count++;
				if (count == 1) {
					telId = i;
				}

			}
		}

		if (count == 0) {
			Toast.makeText(AddressListActivity.this, telCount[0],
					Toast.LENGTH_SHORT).show();
		} else if (count == 1) {
			settingCommunication();
		} else if (count > 1) {
			Toast.makeText(AddressListActivity.this, telCount[1],
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * EditText��TextWatcherδʵ�ֵķ���
	 * */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String[] find = getResources().getStringArray(R.array.find_how_mang);
		info = s.toString();
		querylist = userInfoUtil.query(list, info);
		if (querylist.size() > 0) {
			txtNum.setText(find[0] + querylist.size() + find[1]);
			lst.setVisibility(View.VISIBLE);
			imgNoData.setVisibility(View.GONE);
			handler.sendEmptyMessage(2);
		} else if (querylist.size() == 0) {
			txtNum.setText(find[2]);
			lst.setVisibility(View.GONE);
			imgNoData.setVisibility(View.VISIBLE);
			handler.sendEmptyMessage(2);
		}

	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
