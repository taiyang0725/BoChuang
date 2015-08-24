package edu.feicui.assistant.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.feicui.assistant.R;
import edu.feicui.assistant.main.SoftwareActivity;
import edu.feicui.assistant.mode.AppEntity;

/**
 * ����һ������ܼ���������GridView
 * */
public class GridViewSoftwareAdapter extends BaseAdapter {
	/**
	 * �����Ķ���
	 * */
	private SoftwareActivity softwareActivity;
	/**
	 * ���ֽ�����
	 * */
	private LayoutInflater layoutInflater;
	/**
	 * ����Դ
	 * */
	private ArrayList<AppEntity> list;

	public GridViewSoftwareAdapter(SoftwareActivity softwareActivity,
			ArrayList<AppEntity> list) {

		this.softwareActivity = softwareActivity;

		this.list = list;

		this.layoutInflater = LayoutInflater.from(softwareActivity);
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = layoutInflater.inflate(R.layout.grditem, null);

			holder.img = (ImageView) convertView.findViewById(R.id.grd_icon);
			holder.txtlst = (TextView) convertView.findViewById(R.id.grd_txt);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.img.setBackgroundDrawable(list.get(position).getIcon());
		holder.txtlst.setText(list.get(position).getName());

		return convertView;
	}

	/**
	 * ����һ������view����
	 * */
	class ViewHolder {
		/**
		 * Ӧ�ó���ͼ��
		 * */
		private ImageView img;
		/**
		 * Ӧ�ó�������
		 * */
		private TextView txtlst;

	}
}
