package edu.feicui.assistant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 这是用户选择用户图标的适配器
 * */
public class SelPicAdapter extends BaseAdapter {
	/**
	 * 上下文对象
	 * */
	private Context context;
	/**
	 * 用户图标数组
	 * */
	private int[] selpic;
	/**
	 * 用户图标像素密度
	 * */
	private final float mDensity;
	/**
	 * 用户图标宽
	 * */
	private static final int ITEM_WIDTH = 30;
	/**
	 * 用户图标高
	 * */
	private static final int ITEM_HEIGHT = 30;

	// private final int mGalleryItemBackground;

	public SelPicAdapter(Context context, int[] selpic) {
		super();

		this.context = context;

		this.selpic = selpic;

		mDensity = context.getResources().getDisplayMetrics().density;
	}

	@Override
	public int getCount() {

		return selpic.length;
	}

	@Override
	public Object getItem(int position) {

		return selpic[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imageView;
		if (convertView == null) {
			convertView = new ImageView(context);

			imageView = (ImageView) convertView;
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					Gallery.LayoutParams.WRAP_CONTENT,
					Gallery.LayoutParams.WRAP_CONTENT));

		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(selpic[position]);

		return imageView;
	}
}
