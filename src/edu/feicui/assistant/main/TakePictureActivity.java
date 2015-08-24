package edu.feicui.assistant.main;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import edu.feicui.assistant.R;

/**
 * 这是一个拍照完成展示图片类
 * */
public class TakePictureActivity extends BaseActivity implements
		OnClickListener {
	/**
	 * 删除图片按钮
	 * */
	private ImageView imgDel;
	/**
	 * 保存图片按钮
	 * */
	private ImageView imgSave;
	/**
	 * 展示图片
	 * */
	private ImageView imgPhoto;

	/**
	 * 图片路径字符窜
	 * */
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takepicture);

		receivePath();

		initView();
	}

	/**
	 * 接收图片路径的方法
	 * */
	private void receivePath() {
		Intent intent = getIntent();
		path = intent.getStringExtra("picture");
		Log.d("aaaaaaaa", path);

	}

	/**
	 * 初始化控件
	 * */
	private void initView() {

		imgPhoto = (ImageView) findViewById(R.id.photo);
		imgPhoto.setImageURI(Uri.parse(path));

		imgDel = (ImageView) findViewById(R.id.del);
		imgDel.setOnClickListener(this);

		imgSave = (ImageView) findViewById(R.id.save);
		imgSave.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			Toast.makeText(TakePictureActivity.this, R.string.save_scuu,
					Toast.LENGTH_SHORT).show();

			finish();
			break;
		case R.id.del:
			File file = new File(path);
			file.delete();
			Toast.makeText(TakePictureActivity.this, R.string.del_scuu,
					Toast.LENGTH_SHORT).show();

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
		Intent it = new Intent(TakePictureActivity.this, cls);
		startActivity(it);
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
