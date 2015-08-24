package edu.feicui.assistant.main;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.assistant.R;
import edu.feicui.assistant.adapter.CallAdapter;
import edu.feicui.assistant.mode.AppEntity;
import edu.feicui.assistant.util.AppEntityUtil;

/**
 * ����һ���ֻ����ٵ���
 * */
public class SpeedActivity extends BaseOneActivity implements
		OnItemClickListener, OnClickListener {
	private ListView lst;
	/**
	 * �������еĳ���ļ���
	 * */
	private ArrayList<AppEntity> listP;
	/**
	 * �Ƿ�ѡ��
	 * */
	private CheckBox chb;
	/**
	 * һ�����ٰ�ť
	 * */
	private Button btn;
	/**
	 * Ӧ�ó������
	 * */
	private TextView txtNum;
	/**
	 * Ӧ�ó�����������ڴ�
	 * */
	private TextView txtRam;
	/**
	 * �����ڴ���ռ�ٷֱ�
	 * */
	private TextView txtApp;

	/**
	 * �����ڴ������
	 * */
	private ProgressBar pgb;
	/**
	 * תȦ���ط�
	 * */
	private ProgressDialog progressDialog;
	/**
	 * ���������
	 * */
	private AppEntityUtil appEntityUtil;

	private CallAdapter callAdapter;
	/**
	 * û����ǰ�ĳ��������
	 * */
	private int noKill;
	/**
	 * ���߳������̵߳�ͨѶ
	 * */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog.cancel();
				callAdapter = new CallAdapter(SpeedActivity.this, listP);
				lst.setAdapter(callAdapter);
				changeData();
				break;
			case 2:
				String killNum = getResources().getString(
						R.string.kill_process, noKill - listP.size());
				callAdapter = new CallAdapter(SpeedActivity.this, listP);
				lst.setAdapter(callAdapter);
				changeData();
				Toast.makeText(SpeedActivity.this, killNum, Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				break;
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speed_go);

		getData();

		initView();

	}

	/**
	 * �ؼ���ʼ��
	 * */
	private void initView() {

		callAdapter = new CallAdapter(SpeedActivity.this, listP);

		lst = (ListView) findViewById(R.id.speed_lst);
		lst.setAdapter(callAdapter);
		lst.setOnItemClickListener(SpeedActivity.this);

		btn = (Button) findViewById(R.id.Turbo_Key);
		btn.setOnClickListener(SpeedActivity.this);

		txtNum = (TextView) findViewById(R.id.txt_app_num);

		txtRam = (TextView) findViewById(R.id.txt_app_ram);

		txtApp = (TextView) findViewById(R.id.txt_app_);

		pgb = (ProgressBar) findViewById(R.id.progressbar);
		pgb.setMax(100);

		progressDialog = ProgressDialog.show(SpeedActivity.this, null,
				getResources().getString(R.string.defy_death));

	}

	/**
	 * ��ȡ���ݵķ���
	 * */
	private void getData() {
		listP = new ArrayList<AppEntity>();
		appEntityUtil = new AppEntityUtil();

		getProessNum(1);

	}

	private void getProessNum(final int from) {
		noKill = listP.size();
		new Thread(new Runnable() {

			public void run() {
				listP = appEntityUtil.getProgressCase(SpeedActivity.this);

				handler.sendEmptyMessage(from);
			}
		}).start();
	}

	/**
	 * �������ݵķ���
	 * */
	private void changeData() {

		txtNum.setText(listP.size() + getResources().getString(R.string.ge));

		long initalMem = appEntityUtil.getRAM();// ϵͳ���ڴ�
		long availMem = appEntityUtil.getAvailMemory(SpeedActivity.this);// �����ڴ�

		txtRam.setText(Formatter.formatFileSize(SpeedActivity.this, initalMem
				- availMem)
				+ "/" + Formatter.formatFileSize(SpeedActivity.this, availMem));

		txtApp.setText((initalMem - availMem) * 100 / initalMem + "%");

		pgb.setProgress((int) ((initalMem - availMem) * 100 / initalMem));

	}

	/**
	 * OnClickListener
	 * */
	@Override
	public void onClick(View v) {

		ActivityManager aManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (int i = 0; i < listP.size(); i++) {

			if (listP.get(i).isChecked()) {

				aManager.killBackgroundProcesses(listP.get(i).getPackageName());

			}

		}
		getProessNum(2);

	}

	/**
	 * OnItemClickListener
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {

		chb = (CheckBox) view.findViewById(R.id.chb_call);

		chb.setChecked(!chb.isChecked());

		listP.get(position).setChecked(chb.isChecked());

	}
}
