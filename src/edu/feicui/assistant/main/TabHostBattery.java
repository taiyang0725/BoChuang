package edu.feicui.assistant.main;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import edu.feicui.assistant.R;

public class TabHostBattery extends TabActivity implements OnTabChangeListener {
	private TabHost tabHost;
	private int tabHostId;
	private View viewD;
	private View viewM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		viewD = LayoutInflater.from(TabHostBattery.this).inflate(
				R.layout.detection, null);
		viewM = LayoutInflater.from(TabHostBattery.this).inflate(
				R.layout.maintain, null);

		/** µç³Ø¼ì²â */
		tabHost.addTab(tabHost
				.newTabSpec("battery_detection")

				.setIndicator(viewD)
				.setContent(
						new Intent(TabHostBattery.this,
								BatteryDetectionActivity.class)));
		/** µç³ØÎ¬»¤ */
		tabHost.addTab(tabHost
				.newTabSpec("battery_maintain")

				.setIndicator(viewM)
				.setContent(
						new Intent(TabHostBattery.this,
								BatteryMaintainActivity.class)));

	}

	@Override
	public void onTabChanged(String tabId) {
		if (tabHostId == getTabIndex(tabId)) {
			return;
		}
		View view = tabHost.getTabWidget().getChildAt(getTabIndex(tabId));
		view.setBackgroundResource(R.drawable.img_backgra);
		View view1 = tabHost.getTabWidget().getChildAt(tabHostId);
		view1.setBackgroundResource(R.drawable.backimg_fouce);
		tabHostId = getTabIndex(tabId);

	}

	private int getTabIndex(String tabId) {
		int index = 0;
		if (tabId.equals("battery_detection")) {
			index = 0;
		}
		if (tabId.equals("battery_maintain")) {
			index = 1;
		}
		return index;
	}
}
