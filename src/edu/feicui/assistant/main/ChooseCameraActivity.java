package edu.feicui.assistant.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import edu.feicui.assistant.R;

/**
 * 这是一个选择照相机类
 **/
public class ChooseCameraActivity extends BaseOneActivity implements
		OnClickListener {

	private Button[] btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosecamera);
		itinView();
	}

	private void itinView() {

		btn = new Button[2];

		btn[0] = (Button) findViewById(R.id.myCamera);
		btn[0].setOnClickListener(this);

		btn[1] = (Button) findViewById(R.id.systemCamera);
		btn[1].setOnClickListener(this);

		ImageView img = (ImageView) findViewById(R.id.chooseback);
		img.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myCamera:
			jump(CameraActivity.class);
			break;
		case R.id.systemCamera:
			Intent intent = new Intent(); // 调用照相机

			intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
			startActivity(intent);

			break;
		case R.id.chooseback:
			finish();

			break;

		default:
			break;
		}

	}

	/**
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls) {
		Intent it = new Intent(ChooseCameraActivity.this, cls);
		startActivity(it);
	}
}
