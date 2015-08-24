package edu.feicui.assistant.main;

import android.content.Intent;
import android.os.Bundle;
import edu.feicui.assistant.interf.OnLogoEndListener;
import edu.feicui.assistant.view.LogoView;

/** ����һ��LOGO������ */
public class LogoActivity extends BaseActivity implements OnLogoEndListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		LogoView lv = new LogoView(LogoActivity.this, screenW, screenH);
		lv.setOnLogoEndLinener(this);

		setContentView(lv);
	}

	@Override
	public void OnLogoEnd() {

		Intent it = new Intent(LogoActivity.this, MenuActivity.class);
		startActivity(it);
		finish();
	}

}
