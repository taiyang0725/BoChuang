package edu.feicui.assistant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * �����û�ѡ���û�ͼ���������
 * */
public class SelPicAdapter extends BaseAdapter {
	/**
	 * �����Ķ���
	 * */
	private Context context;
	/**
	 * �û�ͼ������
	 * */
	private int[] selpic;
	/**
	 * �û�ͼ�������ܶ�
	 * */
	private final float mDensity;
	/**
	 * �û�ͼ���
	 * */
	private static final int ITEM_WIDTH = 30;
	/**
	 * �û�ͼ���
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
