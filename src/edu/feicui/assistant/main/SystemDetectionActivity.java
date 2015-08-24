package edu.feicui.assistant.main;

import android.os.Bundle;
import android.widget.ExpandableListView;
import edu.feicui.assistant.R;
import edu.feicui.assistant.adapter.SystemAdapter;
import edu.feicui.assistant.util.AppEntityUtil;

/**
 * ����һ��ϵͳ�����
 * */
public class SystemDetectionActivity extends BaseOneActivity {

	private ExpandableListView els;
	/**
	 * ������Ϣ
	 * */
	String[] groupView;

	/**
	 * ������Ϣ
	 * */
	String[][] childView;
	/**
	 * ��������
	 * */
	String[][] childInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_detection);

		initData();

		initView();
	}

	/**
	 * ��ʼ����Դ
	 * */
	private void initData() {
		AppEntityUtil util = new AppEntityUtil();
		groupView = getResources().getStringArray(R.array.system_detection);

		childView = new String[][] {
				getResources().getStringArray(R.array.base_info),
				getResources().getStringArray(R.array.CPU),
				getResources().getStringArray(R.array.RAM),
				getResources().getStringArray(R.array.resolution_ratio),
				getResources().getStringArray(R.array.pixel),
				getResources().getStringArray(R.array.WIFI),

		};
		childInfo = new String[][] { util.getBaseInfo(this),
				util.getCpuInfo(this), util.getRamInfo(this),
				util.getDisplayMetricsInfo(this), util.getPixelInfo(this),
				util.getWifiInfo(this) };

	}

	/**
	 * ��ʼ���ؼ�
	 * */
	private void initView() {

		els = (ExpandableListView) findViewById(R.id.els_sys);
		SystemAdapter sAdapter = new SystemAdapter(
				SystemDetectionActivity.this, groupView, childView, childInfo);
		els.setAdapter(sAdapter);

	}

}
