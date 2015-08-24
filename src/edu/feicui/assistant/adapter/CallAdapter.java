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
import edu.feicui.assistant.mode.AppEntity;

/**
 * 这是手机加速的适配器
 * */
public class CallAdapter extends BaseAdapter {
	/**
	 * 
	 * 上下文对象
	 */
	private Context context;
	/**
	 * 正在运行的程序的集合
	 * */
	private ArrayList<AppEntity> listP;
	/**
	 * 布局解析器
	 * */
	private LayoutInflater layoutInflater;

	public CallAdapter(Context context, ArrayList<AppEntity> listP) {
		super();

		this.context = context;

		this.listP = listP;

		this.layoutInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {

		return listP.size();
	}

	@Override
	public Object getItem(int position) {

		return listP.get(position);
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

			convertView = layoutInflater.inflate(R.layout.calllistitem, null);

			holder.imgIcon = (ImageView) convertView
					.findViewById(R.id.img_call);

			holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);

			holder.txtNumServe = (TextView) convertView
					.findViewById(R.id.txt_numServe);

			holder.txtRam = (TextView) convertView.findViewById(R.id.txt_ram);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imgIcon.setBackgroundDrawable(listP.get(position).getIcon());

		holder.txtName.setText(listP.get(position).getName());

		holder.txtRam.setText(listP.get(position).getOneRAM());

		holder.txtNumServe.setText(listP.get(position).getServeNum() + "");

		return convertView;
	}

	/**
	 * 这是一个管理view的类
	 * */
	class ViewHolder {

		/**
		 * 服务的量
		 * */
		private TextView txtNumServe;
		/**
		 * 内存
		 * */
		private TextView txtRam;
		/**
		 * 程序名称
		 * */
		private TextView txtName;
		/**
		 * 程序图标
		 * */
		private ImageView imgIcon;

	}
}
