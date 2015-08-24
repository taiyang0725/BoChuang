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
 * 这是一个软件管家适配器：ListView
 * */
public class ListViewSoftwareAdapter extends BaseAdapter {

	/**
	 * 上下文对象
	 * */
	private SoftwareActivity softwareActivity;
	/**
	 * 布局解析器
	 * */
	private LayoutInflater layoutInflater;
	/**
	 * 数据源
	 * */

	private ArrayList<AppEntity> list;

	public ListViewSoftwareAdapter(SoftwareActivity softwareActivity,
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

			convertView = layoutInflater.inflate(R.layout.lstitem, null);

			holder.img = (ImageView) convertView.findViewById(R.id.lst_icon);
			holder.txtlst = (TextView) convertView.findViewById(R.id.lst_txt);
			holder.txtpackage = (TextView) convertView
					.findViewById(R.id.lst_package);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.img.setBackgroundDrawable(list.get(position).getIcon());
		holder.txtlst.setText(list.get(position).getName());
		holder.txtpackage.setText(list.get(position).getPackageName());

		return convertView;
	}

	/**
	 * 这是一个管理view的类
	 * */
	class ViewHolder {
		/**
		 * 应用程序图标
		 * */
		private ImageView img;
		/**
		 * 应用程序名称
		 * */
		private TextView txtlst;
		/**
		 * 应用程序包名
		 * */
		private TextView txtpackage;
	}

}
