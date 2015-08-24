package edu.feicui.assistant.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import edu.feicui.assistant.R;

/**
 * 这是一个照相机类
 **/
public class CameraActivity extends BaseActivity implements OnClickListener,
		Callback, OnSeekBarChangeListener, PictureCallback,
		android.content.DialogInterface.OnClickListener, AutoFocusCallback {
	/**
	 * 定义 surfaceView
	 * */
	private SurfaceView surfaceView;
	/**
	 * 中间人
	 * */
	private SurfaceHolder holder;
	/**
	 * 定义照相机
	 * */
	private Camera camera;
	/**
	 * 定义开关，是否拍照完成
	 * */
	private boolean isPreview;
	/**
	 * 返回按钮
	 * */
	private ImageView imgBack;
	/**
	 * 闪光灯控制按钮
	 * */
	private ImageView imgLight;
	/**
	 * 拍照按钮
	 * */
	private ImageView imgCamera;
	/**
	 * 摄像头选择按钮
	 * */
	private ImageView imgChooseCam;
	/**
	 * 焦距控制
	 * */
	private SeekBar skb;
	/**
	 * 相机管家，设置相机属性
	 * */
	private Parameters parameters;
	/**
	 * 后置摄像
	 * */
	public static final int CAMERA_FACING_BACK = 0;
	/**
	 * 前置摄像
	 * */
	public static final int CAMERA_FACING_FRONT = 1;
	private int numberC;
	private BufferedOutputStream stream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 设置横屏

		setContentView(R.layout.activity_camera);

		initView();
	}

	/**
	 * 初始化控件
	 * */
	private void initView() {

		surfaceView = (SurfaceView) findViewById(R.id.sfv);
		holder = surfaceView.getHolder();
		holder.addCallback(this);

		imgBack = (ImageView) findViewById(R.id.back);
		imgBack.setOnClickListener(this);

		imgLight = (ImageView) findViewById(R.id.light);
		imgLight.setOnClickListener(this);

		imgCamera = (ImageView) findViewById(R.id.photo);
		imgCamera.setOnClickListener(this);

		imgChooseCam = (ImageView) findViewById(R.id.chooseCam);
		imgChooseCam.setOnClickListener(this);

		skb = (SeekBar) findViewById(R.id.skb);
		skb.setOnSeekBarChangeListener(this);
	}

	/**
	 * 开始拍照
	 * */
	private void takePic() {

		camera.takePicture(null, null, null, this);
		isPreview = false;
	}

	/**
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls) {
		Intent it = new Intent(CameraActivity.this, cls);
		startActivity(it);
	}

	/**
	 * SurfaceHolder.Callback
	 * */

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (camera != null) {
			camera.release();
			camera = null;
		}

		camera = Camera.open(numberC);// 打开相机

		parameters = camera.getParameters();// 获取相机参数
		int z = parameters.getMaxZoom();// 获取最大焦距
		skb.setMax(z);
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.autoFocus(this);// 自动对焦
		camera.startPreview();// 开始预览
		camera.autoFocus(this);// 自动对焦

		isPreview = true;

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (camera != null) {
			if (isPreview) {
				camera.autoFocus(this);// 自动对焦
				camera.stopPreview();
			}
			camera.release();
			camera = null;
		}
	}

	/**
	 * ImageView
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo:
			takePic();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.light:
			setFlashlight();
			break;
		case R.id.chooseCam:
			setCamera();
			break;

		default:
			break;
		}

	}

	/**
	 * 设置闪光灯的方法
	 * */
	private void setFlashlight() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CameraActivity.this);

		builder.setNegativeButton(R.string.automatic, this);// 自动
		builder.setNeutralButton(R.string.open, this);// 开
		builder.setPositiveButton(R.string.close, this);// 关

		AlertDialog ad = builder.create();
		ad.show();
	}

	/**
	 * 设置前后摄像头的方法
	 * */

	private void setCamera() {

		if (numberC == Camera.CameraInfo.CAMERA_FACING_BACK) {
			numberC = Camera.CameraInfo.CAMERA_FACING_FRONT;
		} else if (numberC == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			numberC = Camera.CameraInfo.CAMERA_FACING_BACK;
		}
	}

	/**
	 * SeekBar
	 * */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		camera.autoFocus(this);// 自动对焦
		parameters.setZoom(progress);
		camera.autoFocus(this);// 自动对焦
		camera.setParameters(parameters);// 设置相机参数
		camera.autoFocus(this);// 自动对焦
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	/**
	 * PictureCallBack
	 * */
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {

		if (Environment.getExternalStorageState().equals(// 判断sd卡是否存在
				Environment.MEDIA_MOUNTED)) {

			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// 获取图片

			File file = new File(Environment.getExternalStorageDirectory()// 储存路径

					+ File.separator + "cike");
			if (!file.exists()) {// 文件夹是否存在
				file.mkdirs();// 创建文件夹
			}
			File file1 = new File(file, System.currentTimeMillis() + ".jpg");
			try {
				stream = new BufferedOutputStream(new FileOutputStream(file1));
				bitmap.compress(CompressFormat.JPEG, 80, stream);// 压缩图片到文件流

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}
			String path = file1.getAbsolutePath();// 获取路径
			Intent intent = new Intent(CameraActivity.this,
					TakePictureActivity.class);
			intent.putExtra("picture", path);
			startActivity(intent);

		} else {
			Toast.makeText(CameraActivity.this, R.string.sd_remove,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * DialogInterface.OnClickListener
	 * */
	@Override
	public void onClick(DialogInterface dialog, int which) {

		switch (which) {
		case DialogInterface.BUTTON_NEGATIVE:// 自动

			parameters = camera.getParameters();// 获取相机参数

			parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);

			imgLight.setBackgroundResource(R.drawable.light_auto);

			camera.setParameters(parameters);// 设置相机参数

			break;

		case DialogInterface.BUTTON_NEUTRAL:// 开

			parameters = camera.getParameters();// 获取相机参数

			parameters.setFlashMode(Parameters.FLASH_MODE_ON);

			imgLight.setBackgroundResource(R.drawable.light_on);

			camera.setParameters(parameters);// 设置相机参数

			break;

		case DialogInterface.BUTTON_POSITIVE:// 关

			parameters = camera.getParameters();// 获取相机参数

			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);

			imgLight.setBackgroundResource(R.drawable.light_off);

			camera.setParameters(parameters);// 设置相机参数

			break;

		default:
			break;
		}

	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		Log.d(getResources().getString(R.string.camera), "success:" + success
				+ "  " + "camera:" + camera);
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
