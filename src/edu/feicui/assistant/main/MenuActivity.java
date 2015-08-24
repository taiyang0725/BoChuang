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
 * ����һ���˵�������
 * */
public class MenuActivity extends BaseOneActivity implements OnClickListener,
		OnLongClickListener {
	/**
	 * ImageView����
	 **/
	private ImageView[] imageViews;
	/**
	 * ԭͼ������
	 **/
	private int[] select;
	/**
	 * ����ͼ������
	 **/
	private int[] unselect;
	/**
	 * ����������¼��һ�ε�ͼ��id
	 **/
	private int lastIndex;
	/**
	 * TextaView�ַ�������
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
	 * ��ʼ���ؼ�
	 * */
	private void initView() {

		imageViews = new ImageView[7];
		/**
		 * ����ܼ�
		 **/
		imageViews[0] = (ImageView) findViewById(R.id.a);
		imageViews[0].setOnClickListener(this);
		imageViews[0].setOnLongClickListener(this);
		/**
		 * ͨѶ¼
		 **/
		imageViews[1] = (ImageView) findViewById(R.id.b);
		imageViews[1].setOnClickListener(this);
		imageViews[1].setOnLongClickListener(this);
		/**
		 * �����
		 **/
		imageViews[2] = (ImageView) findViewById(R.id.c);
		imageViews[2].setOnClickListener(this);
		imageViews[2].setOnLongClickListener(this);
		/**
		 * ��عܼ�
		 **/
		imageViews[3] = (ImageView) findViewById(R.id.d);
		imageViews[3].setOnClickListener(this);
		imageViews[3].setOnLongClickListener(this);
		/**
		 * ����
		 **/
		imageViews[4] = (ImageView) findViewById(R.id.e);
		imageViews[4].setOnClickListener(this);
		imageViews[4].setOnLongClickListener(this);
		/**
		 * ϵͳ����
		 **/
		imageViews[5] = (ImageView) findViewById(R.id.f);
		imageViews[5].setOnClickListener(this);
		imageViews[5].setOnLongClickListener(this);

		/**
		 * �˳�����
		 **/
		imageViews[6] = (ImageView) findViewById(R.id.h);
		imageViews[6].setOnClickListener(this);
		imageViews[6].setOnLongClickListener(this);

		select = new int[] { R.drawable.menu_icon_1_0,// ����ܼ�
				R.drawable.menu_icon_0_0, // ͨѶ¼
				R.drawable.menu_icon_5_0,// �����
				R.drawable.menu_icon_3_0,// ��عܼ�
				R.drawable.menu_icon_4_0,// ����
				R.drawable.menu_icon_2_0 };// ϵͳ����

		unselect = new int[] { R.drawable.menu_icon_1_1,// ����ܼ�
				R.drawable.menu_icon_0_1, // ͨѶ¼
				R.drawable.menu_icon_5_1,// �����
				R.drawable.menu_icon_3_1,// ��عܼ�
				R.drawable.menu_icon_4_1,// ����
				R.drawable.menu_icon_2_1 };// ϵͳ����

	}

	/**
	 * �ı�ͼ��״̬�����ֵķ���
	 * */
	private void changeIcon(int index) {

		if (lastIndex == index) {
			return;
		}

		/**
		 * ����
		 **/
		TextView tv = (TextView) findViewById(R.id.tv);
		title = getResources().getStringArray(R.array.icon_title);
		tv.setText(title[index]);
		/**
		 * ͼ��
		 **/

		imageViews[index].setBackgroundResource(unselect[index]);
		imageViews[lastIndex].setBackgroundResource(select[lastIndex]);

		lastIndex = index;
	}

	/** ����¼��ص����� */
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

	/** �����¼��ص����� */
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
	 * ��ת��������ķ���
	 * */
	private void jump(Class<?> cls) {
		Intent it = new Intent(MenuActivity.this, cls);
		startActivity(it);

	}

	/**
	 * ����ֻ����ؼ��ķ���
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
