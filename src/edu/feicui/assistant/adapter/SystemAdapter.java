package edu.feicui.assistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import edu.feicui.assistant.R;

/**
 * ����һ��ϵͳ����������
 * */
public class SystemAdapter extends BaseExpandableListAdapter {
	/**
	 * 
	 * �����Ķ���
	 */
	private Context context;
	/**
	 * ������Ϣ
	 * */
	String[] groupView;
	/**
	 * ������Ϣ
	 * */
	String[][] childView;
	/**
	 * ��������
	 * */
	String[][] childInfo;
	/**
	 * ���ֽ�����
	 * */
	private LayoutInflater layoutInflater;

	public SystemAdapter(Context context, String[] groupView,
			String[][] childView, String[][] childInfo) {
		super();

		this.context = context;

		this.groupView = groupView;

		this.childView = childView;

		this.childInfo = childInfo;

		this.layoutInflater = LayoutInflater.from(context);

	}

	@Override
	public int getGroupCount() {

		return groupView.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return childView[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return groupView[groupPosition];
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return childView[groupPosition][childPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childView[groupPosition][childPosition].hashCode();
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = layoutInflater.inflate(R.layout.sys_group, null);

			holder.txtGroup = (TextView) convertView
					.findViewById(R.id.txt_group);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtGroup.setText(groupView[groupPosition]);
		holder.txtGroup.setTextSize(20);
		holder.txtGroup.setTextColor(Color.parseColor("#8B0000"));

		return convertView;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = layoutInflater.inflate(R.layout.sys_child, null);

			holder.txtName = (TextView) convertView.findViewById(R.id.txt_base);
			holder.txtInfo = (TextView) convertView
					.findViewById(R.id.txt_base_info);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtName.setText(childView[groupPosition][childPosition]);
		holder.txtName.setTextSize(15);

		holder.txtInfo.setText(childInfo[groupPosition][childPosition]);
		holder.txtInfo.setTextSize(15);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return false;
	}

	/**
	 * ����һ������view����
	 * */
	class ViewHolder {

		/**
		 * ��������
		 * */
		private TextView txtName;
		/**
		 * ��������
		 * */
		private TextView txtInfo;
		/**
		 * ��������
		 * */
		private TextView txtGroup;

	}
}
