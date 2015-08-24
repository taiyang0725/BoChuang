package edu.feicui.assistant.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.feicui.assistant.R;
import edu.feicui.assistant.mode.Person;

public class AddressListAdapter extends BaseAdapter {
	/**
	 * 上下文对象
	 * */
	private Context context;

	/**
	 * 联系人信息的集合
	 * */
	private ArrayList<Person> list;
	/**
	 * 布局解析器
	 * */
	private LayoutInflater layoutInflater;

	public AddressListAdapter(Context context, ArrayList<Person> list) {

		super();

		this.list = list;

		this.context = context;

		this.layoutInflater = LayoutInflater.from(context);
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

			convertView = layoutInflater.inflate(R.layout.address_book, null);

			holder.txtName = (TextView) convertView
					.findViewById(R.id.txt_name_friend);

			holder.txtTel = (TextView) convertView.findViewById(R.id.txt_tel);

			holder.txtIndex = (TextView) convertView
					.findViewById(R.id.txt_user_index);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtName.setText(list.get(position).getName());

		holder.txtTel.setText(list.get(position).getTelNum());

		holder.txtIndex.setText(position + 1 + ".");

		return convertView;
	}

	/**
	 * 管理view的类
	 * */
	class ViewHolder {
		/**
		 * 联系人姓名
		 * */
		private TextView txtName;
		/**
		 * 联系人电话
		 * */
		private TextView txtTel;
		/**
		 * 用户图标
		 * */
		private ImageView imgIcon;
		/**
		 * 用户编号
		 * */
		private TextView txtIndex;

	}

}
