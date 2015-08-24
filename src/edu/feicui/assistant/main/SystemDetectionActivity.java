package edu.feicui.assistant.main;

import android.os.Bundle;
import android.widget.ExpandableListView;
import edu.feicui.assistant.R;
import edu.feicui.assistant.adapter.SystemAdapter;
import edu.feicui.assistant.util.AppEntityUtil;

/**
 * 这是一个系统检测类
 * */
public class SystemDetectionActivity extends BaseOneActivity {

	private ExpandableListView els;
	/**
	 * 主组信息
	 * */
	String[] groupView;

	/**
	 * 子组信息
	 * */
	String[][] childView;
	/**
	 * 子组内容
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
	 * 初始化资源
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
	 * 初始化控件
	 * */
	private void initView() {

		els = (ExpandableListView) findViewById(R.id.els_sys);
		SystemAdapter sAdapter = new SystemAdapter(
				SystemDetectionActivity.this, groupView, childView, childInfo);
		els.setAdapter(sAdapter);

	}

}
