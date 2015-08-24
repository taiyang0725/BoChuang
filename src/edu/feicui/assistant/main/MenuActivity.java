package edu.feicui.assistant.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.assistant.R;

/**
 * 这是一个菜单界面类
 * */
public class MenuActivity extends BaseOneActivity implements OnClickListener,
		OnLongClickListener {
	/**
	 * ImageView数组
	 **/
	private ImageView[] imageViews;
	/**
	 * 原图标数组
	 **/
	private int[] select;
	/**
	 * 发亮图标数组
	 **/
	private int[] unselect;
	/**
	 * 记忆器，记录上一次的图标id
	 **/
	private int lastIndex;
	/**
	 * TextaView字符串数组
	 **/
	private String[] title;
	private long sTime;
	private long nTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu);

		initView();

	}

	/**
	 * 初始化控件
	 * */
	private void initView() {

		imageViews = new ImageView[7];
		/**
		 * 软件管家
		 **/
		imageViews[0] = (ImageView) findViewById(R.id.a);
		imageViews[0].setOnClickListener(this);
		imageViews[0].setOnLongClickListener(this);
		/**
		 * 通讯录
		 **/
		imageViews[1] = (ImageView) findViewById(R.id.b);
		imageViews[1].setOnClickListener(this);
		imageViews[1].setOnLongClickListener(this);
		/**
		 * 照相机
		 **/
		imageViews[2] = (ImageView) findViewById(R.id.c);
		imageViews[2].setOnClickListener(this);
		imageViews[2].setOnLongClickListener(this);
		/**
		 * 电池管家
		 **/
		imageViews[3] = (ImageView) findViewById(R.id.d);
		imageViews[3].setOnClickListener(this);
		imageViews[3].setOnLongClickListener(this);
		/**
		 * 闹钟
		 **/
		imageViews[4] = (ImageView) findViewById(R.id.e);
		imageViews[4].setOnClickListener(this);
		imageViews[4].setOnLongClickListener(this);
		/**
		 * 系统加速
		 **/
		imageViews[5] = (ImageView) findViewById(R.id.f);
		imageViews[5].setOnClickListener(this);
		imageViews[5].setOnLongClickListener(this);

		/**
		 * 退出程序
		 **/
		imageViews[6] = (ImageView) findViewById(R.id.h);
		imageViews[6].setOnClickListener(this);
		imageViews[6].setOnLongClickListener(this);

		select = new int[] { R.drawable.menu_icon_1_0,// 软件管家
				R.drawable.menu_icon_0_0, // 通讯录
				R.drawable.menu_icon_5_0,// 照相机
				R.drawable.menu_icon_3_0,// 电池管家
				R.drawable.menu_icon_4_0,// 闹钟
				R.drawable.menu_icon_2_0 };// 系统加速

		unselect = new int[] { R.drawable.menu_icon_1_1,// 软件管家
				R.drawable.menu_icon_0_1, // 通讯录
				R.drawable.menu_icon_5_1,// 照相机
				R.drawable.menu_icon_3_1,// 电池管家
				R.drawable.menu_icon_4_1,// 闹钟
				R.drawable.menu_icon_2_1 };// 系统加速

	}

	/**
	 * 改变图标状态，文字的方法
	 * */
	private void changeIcon(int index) {

		if (lastIndex == index) {
			return;
		}

		/**
		 * 文字
		 **/
		TextView tv = (TextView) findViewById(R.id.tv);
		title = getResources().getStringArray(R.array.icon_title);
		tv.setText(title[index]);
		/**
		 * 图标
		 **/

		imageViews[index].setBackgroundResource(unselect[index]);
		imageViews[lastIndex].setBackgroundResource(select[lastIndex]);

		lastIndex = index;
	}

	/** 点击事件回调方法 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.a:
			changeIcon(0);
			break;
		case R.id.b:
			changeIcon(1);
			break;
		case R.id.c:
			changeIcon(2);
			break;
		case R.id.d:
			changeIcon(3);
			break;
		case R.id.e:
			changeIcon(4);
			break;
		case R.id.f:
			changeIcon(5);
			break;
		case R.id.h:

			finish();
			break;

		default:
			break;
		}

	}

	/** 长按事件回调方法 */
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.a:
			jump(SoftwareActivity.class);
			break;
		case R.id.b:
			jump(AddressListActivity.class);
			break;
		case R.id.c:
			jump(CameraActivity.class);
			break;
		case R.id.d:
			jump(TabHostBattery.class);
			break;
		case R.id.e:
			jump(ClockActivity.class);
			break;
		case R.id.f:
			jump(TabHostSpeed.class);
			break;

		default:
			break;
		}
		return true;
	}

	/**
	 * 跳转其他界面的方法
	 * */
	private void jump(Class<?> cls) {
		Intent it = new Intent(MenuActivity.this, cls);
		startActivity(it);

	}

	/**
	 * 监测手机返回键的方法
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		sTime = System.currentTimeMillis();
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			if (nTime > sTime - 100000) {

				return super.onKeyDown(keyCode, event);

			} else {

				Toast.makeText(this, R.string.exit_program, Toast.LENGTH_SHORT)
						.show();
				nTime = sTime;
				return true;
			}

		default:
			break;
		}
		return false;
	}

}
