package edu.feicui.assistant.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import edu.feicui.assistant.R;
import edu.feicui.assistant.interf.OnLogoEndListener;

/**
 * 这是一个logo资源类
 * */
public class LogoView extends View implements Runnable {
	/**
	 * 线程开关
	 * */
	private boolean isrun;
	/**
	 * 线程休眠时间
	 * */
	private static final long TIME = 2000l;
	/**
	 * 原图片
	 * */
	private Bitmap bitmap;
	/**
	 * 处理后的图片图片
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
		/** 画图 */
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
