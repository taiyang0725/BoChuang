package edu.feicui.assistant.main;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import edu.feicui.assistant.R;

public class TabHostSpeed extends TabActivity implements OnTabChangeListener {
	private TabHost tabHost;
	private int tabHostId;
	private View viewCall;
	private View viewSystem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		viewCall = LayoutInflater.from(TabHostSpeed.this).inflate(
				R.layout.call, null);
		viewSystem = LayoutInflater.from(TabHostSpeed.this).inflate(
				R.layout.system, null);

		/** 手机加速 */
		tabHost.addTab(tabHost.newTabSpec("Speed_go")

		.setIndicator(viewCall)
				.setContent(new Intent(TabHostSpeed.this, SpeedActivity.class)));
		/** 系统检测 */
		tabHost.addTab(tabHost
				.newTabSpec("system_detection")

				.setIndicator(viewSystem)
				.setContent(
						new Intent(TabHostSpeed.this,
								SystemDetectionActivity.class)));

	}

	@Override
	public void onTabChanged(String tabId) {
		View view = tabHost.getTabWidget().getChildAt(getTabIndex(tabId));

		view.setBackgroundResource(R.drawable.img_backgra);
	}

	private int getTabIndex(String tabId) {

		return tabHost.getCurrentTab();
	}
}
