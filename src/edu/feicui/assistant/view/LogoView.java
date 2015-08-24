package edu.feicui.assistant.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import edu.feicui.assistant.R;
import edu.feicui.assistant.interf.OnLogoEndListener;

/**
 * ����һ��logo��Դ��
 * */
public class LogoView extends View implements Runnable {
	/**
	 * �߳̿���
	 * */
	private boolean isrun;
	/**
	 * �߳�����ʱ��
	 * */
	private static final long TIME = 2000l;
	/**
	 * ԭͼƬ
	 * */
	private Bitmap bitmap;
	/**
	 * ������ͼƬͼƬ
	 * */
	private Bitmap bitmap1;

	private OnLogoEndListener onLogoEndLinener;

	public LogoView(Context context, int screenW, int screenH) {

		super(context);

		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		bitmap1 = Bitmap.createScaledBitmap(bitmap, screenW, screenH, true);

		new Thread(this).start();
		isrun = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		/** ��ͼ */
		canvas.drawBitmap(bitmap1, 0, 0, null);

	}

	public void setOnLogoEndLinener(OnLogoEndListener onLogoEndLinener) {
		this.onLogoEndLinener = onLogoEndLinener;
	}

	@Override
	public void run() {
		while (isrun) {

			try {
				Thread.sleep(TIME);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			isrun = false;
			if (onLogoEndLinener != null) {
				onLogoEndLinener.OnLogoEnd();
			}
		}

	}

}
