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
 * ����һ���������
 **/
public class CameraActivity extends BaseActivity implements OnClickListener,
		Callback, OnSeekBarChangeListener, PictureCallback,
		android.content.DialogInterface.OnClickListener, AutoFocusCallback {
	/**
	 * ���� surfaceView
	 * */
	private SurfaceView surfaceView;
	/**
	 * �м���
	 * */
	private SurfaceHolder holder;
	/**
	 * ���������
	 * */
	private Camera camera;
	/**
	 * ���忪�أ��Ƿ��������
	 * */
	private boolean isPreview;
	/**
	 * ���ذ�ť
	 * */
	private ImageView imgBack;
	/**
	 * ����ƿ��ư�ť
	 * */
	private ImageView imgLight;
	/**
	 * ���հ�ť
	 * */
	private ImageView imgCamera;
	/**
	 * ����ͷѡ��ť
	 * */
	private ImageView imgChooseCam;
	/**
	 * �������
	 * */
	private SeekBar skb;
	/**
	 * ����ܼң������������
	 * */
	private Parameters parameters;
	/**
	 * ��������
	 * */
	public static final int CAMERA_FACING_BACK = 0;
	/**
	 * ǰ������
	 * */
	public static final int CAMERA_FACING_FRONT = 1;
	private int numberC;
	private BufferedOutputStream stream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ���ú���

		setContentView(R.layout.activity_camera);

		initView();
	}

	/**
	 * ��ʼ���ؼ�
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
	 * ��ʼ����
	 * */
	private void takePic() {

		camera.takePicture(null, null, null, this);
		isPreview = false;
	}

	/**
	 * ��ת��������ķ���
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

		camera = Camera.open(numberC);// �����

		parameters = camera.getParameters();// ��ȡ�������
		int z = parameters.getMaxZoom();// ��ȡ��󽹾�
		skb.setMax(z);
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.autoFocus(this);// �Զ��Խ�
		camera.startPreview();// ��ʼԤ��
		camera.autoFocus(this);// �Զ��Խ�

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
				camera.autoFocus(this);// �Զ��Խ�
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
	 * ��������Ƶķ���
	 * */
	private void setFlashlight() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CameraActivity.this);

		builder.setNegativeButton(R.string.automatic, this);// �Զ�
		builder.setNeutralButton(R.string.open, this);// ��
		builder.setPositiveButton(R.string.close, this);// ��

		AlertDialog ad = builder.create();
		ad.show();
	}

	/**
	 * ����ǰ������ͷ�ķ���
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
		camera.autoFocus(this);// �Զ��Խ�
		parameters.setZoom(progress);
		camera.autoFocus(this);// �Զ��Խ�
		camera.setParameters(parameters);// �����������
		camera.autoFocus(this);// �Զ��Խ�
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

		if (Environment.getExternalStorageState().equals(// �ж�sd���Ƿ����
				Environment.MEDIA_MOUNTED)) {

			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// ��ȡͼƬ

			File file = new File(Environment.getExternalStorageDirectory()// ����·��

					+ File.separator + "cike");
			if (!file.exists()) {// �ļ����Ƿ����
				file.mkdirs();// �����ļ���
			}
			File file1 = new File(file, System.currentTimeMillis() + ".jpg");
			try {
				stream = new BufferedOutputStream(new FileOutputStream(file1));
				bitmap.compress(CompressFormat.JPEG, 80, stream);// ѹ��ͼƬ���ļ���

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
			String path = file1.getAbsolutePath();// ��ȡ·��
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
		case DialogInterface.BUTTON_NEGATIVE:// �Զ�

			parameters = camera.getParameters();// ��ȡ�������

			parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);

			imgLight.setBackgroundResource(R.drawable.light_auto);

			camera.setParameters(parameters);// �����������

			break;

		case DialogInterface.BUTTON_NEUTRAL:// ��

			parameters = camera.getParameters();// ��ȡ�������

			parameters.setFlashMode(Parameters.FLASH_MODE_ON);

			imgLight.setBackgroundResource(R.drawable.light_on);

			camera.setParameters(parameters);// �����������

			break;

		case DialogInterface.BUTTON_POSITIVE:// ��

			parameters = camera.getParameters();// ��ȡ�������

			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);

			imgLight.setBackgroundResource(R.drawable.light_off);

			camera.setParameters(parameters);// �����������

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
	 * ����ֻ����ؼ��ķ���
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
