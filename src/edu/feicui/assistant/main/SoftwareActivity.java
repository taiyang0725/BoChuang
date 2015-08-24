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
 * 这是一个软件管家类
 **/
public class SoftwareActivity extends BaseOneActivity implements
		OnClickListener, OnItemClickListener {
	/**
	 * 程序个数
	 * */
	private TextView txtNum;

	/**
	 * 列表或宫格
	 * */
	private ImageView img;
	/**
	 * 宫格形式
	 * */
	private GridView grd;
	/**
	 * 列表形式
	 * */
	private ListView lst;
	/**
	 * 安装程序来自系统按钮
	 * */
	private ImageButton imgbtnS;
	/**
	 * 安装程序来自用户按钮
	 * */
	private ImageButton imgbtnU;
	/**
	 * 安装程序来自系统
	 * */
	private ArrayList<AppEntity> listSys;
	/**
	 * 安装程序来自用户
	 * */
	private ArrayList<AppEntity> listUser;
	/**
	 * 判断应用程序来自什么地方
	 * */
	private boolean isAppSys;
	/**
	 * 
	 * */
	private AppEntity appEntity;
	/**
	 * 动画
	 * */
	private Animation animation;
	/**
	 * 请求码
	 * */
	private static final int REQUEST_CODE = 2;
	private int index;
	/**
	 * 转圈加载符
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
	 * 初始化程序资源
	 * */
	private void initData() {
		listSys = new ArrayList<AppEntity>();
		listUser = new ArrayList<AppEntity>();

		sonThread(1);

	}

	/**
	 * 子线程与主线程的通讯
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
	 * 初始化控件
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
	 * 初始化适配的方法
	 * */
	/**
	 * @param list
	 *            应用程序资源集合
	 * @param resid
	 *            用户按钮图片资源id
	 * @param resid1
	 *            系统按钮图片资源id
	 * @param id
	 *            程序来自用户还是系统文字id
	 * @param size
	 *            程序的个数
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
	 * 展示用户安装程序的方法
	 * */
	private void showAppUser() {
		initAdapter(listUser, R.drawable.btn_bg, Color.TRANSPARENT,
				R.string.sw_num_user, listUser.size());

		isAppSys = true;

	}

	/**
	 * 展示系统安装程序
	 * */
	private void showAppSys() {

		initAdapter(listSys, Color.TRANSPARENT, R.drawable.btn_bg,
				R.string.sw_num_sys, listSys.size());

		isAppSys = false;

	}

	/**
	 * 列表与宫格的方法
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
	 * 卸载软件的方法
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
	 * 对话框详细信息
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
	 * 监测手机返回键的方法
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
