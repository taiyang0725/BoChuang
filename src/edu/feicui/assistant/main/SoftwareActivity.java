package edu.feicui.assistant.main;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import edu.feicui.assistant.R;
import edu.feicui.assistant.adapter.GridViewSoftwareAdapter;
import edu.feicui.assistant.adapter.ListViewSoftwareAdapter;
import edu.feicui.assistant.mode.AppEntity;
import edu.feicui.assistant.util.AppEntityUtil;

/**
 * ����һ������ܼ���
 **/
public class SoftwareActivity extends BaseOneActivity implements
		OnClickListener, OnItemClickListener {
	/**
	 * �������
	 * */
	private TextView txtNum;

	/**
	 * �б�򹬸�
	 * */
	private ImageView img;
	/**
	 * ������ʽ
	 * */
	private GridView grd;
	/**
	 * �б���ʽ
	 * */
	private ListView lst;
	/**
	 * ��װ��������ϵͳ��ť
	 * */
	private ImageButton imgbtnS;
	/**
	 * ��װ���������û���ť
	 * */
	private ImageButton imgbtnU;
	/**
	 * ��װ��������ϵͳ
	 * */
	private ArrayList<AppEntity> listSys;
	/**
	 * ��װ���������û�
	 * */
	private ArrayList<AppEntity> listUser;
	/**
	 * �ж�Ӧ�ó�������ʲô�ط�
	 * */
	private boolean isAppSys;
	/**
	 * 
	 * */
	private AppEntity appEntity;
	/**
	 * ����
	 * */
	private Animation animation;
	/**
	 * ������
	 * */
	private static final int REQUEST_CODE = 2;
	private int index;
	/**
	 * תȦ���ط�
	 * */
	private ProgressDialog progressDialog;
	/**
	 * 
	 * */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				showAppSys();

				progressDialog.cancel();

				break;
			case 2:
				if (isAppSys) {
					showAppUser();
				} else {
					showAppSys();
				}

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_software);

		initData();

		initView();

	}

	/**
	 * ��ʼ��������Դ
	 * */
	private void initData() {
		listSys = new ArrayList<AppEntity>();
		listUser = new ArrayList<AppEntity>();

		sonThread(1);

	}

	/**
	 * ���߳������̵߳�ͨѶ
	 * */
	private void sonThread(final int num) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				AppEntityUtil appEntityUtil = new AppEntityUtil();

				listSys = appEntityUtil.getSystemApp(SoftwareActivity.this);

				listUser = appEntityUtil.getUserApp(SoftwareActivity.this);
				handler.sendEmptyMessage(num);

			}
		}).start();
	}

	/**
	 * ��ʼ���ؼ�
	 * */
	private void initView() {

		grd = (GridView) findViewById(R.id.gridview);

		grd.setOnItemClickListener(this);

		lst = (ListView) findViewById(R.id.listview);

		lst.setOnItemClickListener(this);

		img = (ImageView) findViewById(R.id.imgGrd);
		img.setOnClickListener(this);

		imgbtnS = (ImageButton) findViewById(R.id.sys_app);
		imgbtnS.setOnClickListener(this);

		imgbtnU = (ImageButton) findViewById(R.id.user_app);
		imgbtnU.setOnClickListener(this);

		txtNum = (TextView) findViewById(R.id.txt_num);

		progressDialog = ProgressDialog.show(SoftwareActivity.this, null,
				getResources().getString(R.string.defy_death));

	}

	/**
	 * OnClickListener
	 * */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imgGrd:
			showGrdAndLst();
			break;
		case R.id.sys_app:
			showAppSys();
			break;
		case R.id.user_app:
			showAppUser();
			break;

		default:
			break;
		}
	}

	/**
	 * ��ʼ������ķ���
	 * */
	/**
	 * @param list
	 *            Ӧ�ó�����Դ����
	 * @param resid
	 *            �û���ťͼƬ��Դid
	 * @param resid1
	 *            ϵͳ��ťͼƬ��Դid
	 * @param id
	 *            ���������û�����ϵͳ����id
	 * @param size
	 *            ����ĸ���
	 */
	private void initAdapter(ArrayList<AppEntity> list, int resid, int resid1,
			int id, long size) {

		GridViewSoftwareAdapter adapterGrd = new GridViewSoftwareAdapter(
				SoftwareActivity.this, list);
		ListViewSoftwareAdapter adapterLst = new ListViewSoftwareAdapter(
				SoftwareActivity.this, list);

		grd.setAdapter(adapterGrd);
		lst.setAdapter(adapterLst);

		imgbtnU.setBackgroundResource(resid);
		imgbtnS.setBackgroundResource(resid1);

		txtNum.setText(getResources().getString(id) + size);
		txtNum.setTextSize(20);

	}

	/**
	 * չʾ�û���װ����ķ���
	 * */
	private void showAppUser() {
		initAdapter(listUser, R.drawable.btn_bg, Color.TRANSPARENT,
				R.string.sw_num_user, listUser.size());

		isAppSys = true;

	}

	/**
	 * չʾϵͳ��װ����
	 * */
	private void showAppSys() {

		initAdapter(listSys, Color.TRANSPARENT, R.drawable.btn_bg,
				R.string.sw_num_sys, listSys.size());

		isAppSys = false;

	}

	/**
	 * �б��빬��ķ���
	 * */
	private void showGrdAndLst() {

		if (grd.getVisibility() == View.VISIBLE) {
			grd.setVisibility(View.GONE);
			lst.setVisibility(View.VISIBLE);
			img.setBackgroundResource(R.drawable.list);

		} else if (lst.getVisibility() == View.VISIBLE) {
			lst.setVisibility(View.GONE);
			grd.setVisibility(View.VISIBLE);
			img.setBackgroundResource(R.drawable.grids);
		}

	}

	/**
	 * OnItemClickListener
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		index = position;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SoftwareActivity.this);

		builder.setTitle(R.string.select);
		builder.setItems(R.array.sel_word,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							appDetailedness(position);
							break;
						case 1:
							uninstall(position);
							break;

						default:
							break;
						}
					}

				});
		builder.setNegativeButton(R.string.can_cel, null);

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	/**
	 * ж������ķ���
	 * */
	private void uninstall(final int position) {

		appEntity = isAppSys ? listUser.get(position) : listSys.get(position);

		String packageName = appEntity.getPackageName();

		Intent intent = new Intent();
		intent.setData(Uri.parse("package:" + packageName));
		intent.setAction(Intent.ACTION_DELETE);
		startActivityIfNeeded(intent, REQUEST_CODE);

	}

	/**
	 * �Ի�����ϸ��Ϣ
	 * */
	private void appDetailedness(int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SoftwareActivity.this);

		appEntity = isAppSys ? listUser.get(position) : listSys.get(position);
		builder.setIcon(appEntity.getIcon());
		builder.setTitle(R.string.detailedness);
		String app = (getResources().getString(R.string.a_name) + appEntity
				.getName())
				+ "\n"
				+ (getResources().getString(R.string.app_packageName) + appEntity
						.getPackageName())
				+ "\n"
				+ (getResources().getString(R.string.versions_number) + appEntity
						.getvCode())
				+ "\n"
				+ getResources().getString(R.string.versions_name)
				+ appEntity.getEditionName();
		builder.setMessage(app);
		builder.setNegativeButton(R.string.con_firm, null);

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE:
			sonThread(2);
			break;

		default:
			break;
		}
	}

	/**
	 * ����ֻ����ؼ��ķ���
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();

			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
