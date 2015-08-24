package edu.feicui.assistant.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import edu.feicui.assistant.R;
import edu.feicui.assistant.mode.AppEntity;

/**
 * ����һ����ȡ�豸��Ϣ�Ĺ�����
 * */
public class AppEntityUtil {
	/**
	 * ��������
	 * */
	private PackageManager packageManager;
	/**
	 * ���弯�ϣ���Ż�ȡ������
	 * */
	private ArrayList<AppEntity> appList;
	/**
	 * �߼������
	 * */
	private BufferedReader bufferedReader;

	/**
	 * ��ȡϵͳ��װ�����б���Ϣ
	 * */
	public ArrayList<AppEntity> getSystemApp(Context context) {
		packageManager = context.getPackageManager();

		appList = new ArrayList<AppEntity>();

		List<PackageInfo> app1 = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);

		for (PackageInfo packageInfo : app1) {
			AppEntity appEntity = new AppEntity();
			/** ϵͳ������ж� */
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0
					| (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
				appEntity.setEditionName(packageInfo.versionName);// 1
				appEntity.setIcon(packageInfo.applicationInfo// 2
						.loadIcon(packageManager));
				appEntity.setName(packageInfo.applicationInfo// 3
						.loadLabel(packageManager));
				appEntity.setvCode(packageInfo.versionCode);// 4
				appEntity.setPackageName(packageInfo.packageName);// 5
				appList.add(appEntity);
			}
		}
		return appList;
	}

	/**
	 * ��ȡ�û���װ�����б���Ϣ
	 * */
	public ArrayList<AppEntity> getUserApp(Context context) {
		packageManager = context.getPackageManager();

		appList = new ArrayList<AppEntity>();

		List<PackageInfo> app1 = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);

		for (PackageInfo packageInfo : app1) {
			AppEntity appEntity = new AppEntity();
			/** �û�������ж� */
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
					& (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0
					& !packageInfo.packageName.equals("edu.feicui.assistant")) {
				appEntity.setEditionName(packageInfo.versionName);
				appEntity.setIcon(packageInfo.applicationInfo
						.loadIcon(packageManager));
				appEntity.setName(packageInfo.applicationInfo
						.loadLabel(packageManager));
				appEntity.setvCode(packageInfo.versionCode);
				appEntity.setPackageName(packageInfo.packageName);

				appList.add(appEntity);
			}
		}
		return appList;
	}

	/**
	 * ��ȡ���ڽ��̵���Ϣ
	 * */
	public ArrayList<AppEntity> getProgressCase(Context context) {

		packageManager = context.getPackageManager();

		appList = new ArrayList<AppEntity>();
		/**
		 * ��ȡ�Ѱ�װ�ĳ���
		 * */
		List<ApplicationInfo> aInfos = packageManager
				.getInstalledApplications(0);

		/**
		 * ��ȡActivity����
		 * */
		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		/**
		 * ��ȡ�������г�����̵���Ϣ
		 * */
		List<RunningAppProcessInfo> rInfos = aManager.getRunningAppProcesses();

		for (RunningAppProcessInfo runningAppProcessInfo : rInfos) {
			AppEntity appEntity = new AppEntity();
			/**
			 * �ڴ���Ϣ
			 * */
			android.os.Debug.MemoryInfo memoryInfo = aManager
					.getProcessMemoryInfo(new int[] { runningAppProcessInfo.pid })[0];
			/**
			 * ����ApplicationInfo��Ѱ��runningAppProcessInfo.processName
			 * */
			for (ApplicationInfo applicationInfo : aInfos) {

				if (runningAppProcessInfo.processName
						.equals(applicationInfo.processName)
						&& !applicationInfo.packageName
								.equals("edu.feicui.assistant")
						&& (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

					appEntity.setIcon(applicationInfo.loadIcon(packageManager));// ͼ��

					appEntity
							.setName(applicationInfo.loadLabel(packageManager));// ��������

					appEntity.setOneRAM(android.text.format.Formatter// ռ���ڴ�
							.formatFileSize(context,
									(memoryInfo.dalvikPrivateDirty/** ռ�õ��ڴ� */
									) * 1024));

					appEntity.setServeNum(getRunningAppServeNum(context,// ��������
							runningAppProcessInfo.pid));
					appEntity.setPackageName(applicationInfo.packageName);// ����
					appList.add(appEntity);
					break;
				}

			}

		}

		return appList;
	}

	/**
	 * ��ȡ��̨���г����������
	 * */
	public int getRunningAppServeNum(Context context, int pid) {
		/**
		 * �������������������
		 * */
		int count = 0;
		/**
		 * ��ȡActivity����
		 * */
		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		/**
		 * ��ȡ�����������
		 * */
		List<RunningServiceInfo> serviceInfos = aManager
				.getRunningServices(1000);

		for (RunningServiceInfo runningServiceInfo : serviceInfos) {
			if (runningServiceInfo.pid == pid) {
				count++;
			}
		}
		return count;

	}

	/**
	 * ��ȡ�ڴ��С
	 * */
	public long getRAM() {

		long inital_memory = 0;
		try {
			FileReader fileReader = new FileReader("/proc/meminfo");
			bufferedReader = new BufferedReader(fileReader, 1024);
			String str = bufferedReader.readLine();// meminfo��һ�У�ϵͳ���ڴ��С

			String[] ram = str.split("\\s+");

			inital_memory = Integer.valueOf(ram[1]).intValue() * 1024;
			bufferedReader.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return inital_memory;
	}

	/**
	 * ��ȡ�����ڴ��С
	 * */
	public long getAvailMemory(Context context) {

		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		MemoryInfo memoryInfo = new MemoryInfo();
		aManager.getMemoryInfo(memoryInfo);

		return memoryInfo.availMem;

	}

	/**
	 * ������Ϣ����
	 * */
	private String[] baseInfo;
	/**
	 * ��Ӫ������
	 * */
	private String name;

	/**
	 * �豸������Ϣ
	 * */
	public String[] getBaseInfo(Context context) {

		String model = android.os.Build.MODEL;// �豸�ͺ�

		String version = android.os.Build.VERSION.RELEASE;// ϵͳ�汾

		TelephonyManager tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String num = tManager.getDeviceId();// �ֻ�����

		String sim = tManager.getSimOperator();// ��Ӫ�̡�

		String[] simName = context.getResources().getStringArray(R.array.SIM);// ��Ӫ������
		if (sim.startsWith("460")) {// �ж��Ƿ�Chinese
			int simNum = Integer.parseInt(sim.substring(3));
			name = simName[simNum];

		}

		baseInfo = new String[] { model, version, num, name,
				isRoot() ? "YES" : "NO" };
		return baseInfo;

	}

	private Process process;// �������
	private DataOutputStream os;//

	/**
	 * �豸�Ƿ�root
	 * */
	public boolean isRoot() {

		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());

			os.writeBytes("ls /data/\n");

			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			if (process != null) {
				process.destroy();
			}
		}

		return true;
	}

	// private String[] cpu;// ���cpu��Ϣ����
	/** cpu���� */
	private String cpuModle;
	/** cpu������ */
	private String cpuHardware;

	/**
	 * ��ȡCPU��Ϣ
	 * */
	public String[] getCpuInfo(Context context) {

		/************************************** cpu�ͺ� ***************************************/

		try {
			FileReader fileReader = new FileReader("/proc/cpuinfo");
			bufferedReader = new BufferedReader(fileReader, 1024);
			String str = bufferedReader.readLine();
			String[] cpu = str.split("\\s+");
			cpuModle = cpu[2];
			String cpuHard = "";
			while ((cpuHard = bufferedReader.readLine()) != null) {
				if (cpuHard.startsWith("Hardware")) {
					String[] cpuH = cpuHard.split("\\s+");
					cpuHardware = cpu[2];
				}

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		/************************************** cpu���� ***************************************/
		File file = new File("/sys/devices/system/cpu/");
		String[] cpuNum = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (Pattern.matches("cpu[0-9]", filename)) {
					return true;
				}
				return false;
			}
		});

		String[] cpuInfo = { cpuHardware + ":" + cpuModle, cpuNum.length + "",
				getCpuFreq(0), getCpuFreq(1), getCpuFreq(2) };
		return cpuInfo;

	}

	/************************************** ��ȡcpuƵ�� ***************************************/
	public String getCpuFreq(int index) {

		String freqNum = "";// cpuƵ��
		String[] freq = { "cpuinfo_max_freq", "cpuinfo_min_freq",
				"scaling_cur_freq" };
		String path = "/system/bin/cat/"
				+ "/sys/devices/system/cpu/cpu0/cpufreq/ " + freq[1];

		try {
			Process process = new ProcessBuilder().command(path).start();
			InputStream stream = process.getInputStream();
			byte[] re = new byte[24];
			while (stream.read(re) != -1) {
				freqNum = freqNum + new String(re);

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return freqNum.trim();
	}

	/**
	 * �ڴ���Ϣ
	 * */
	public String[] getRamInfo(Context context) {
		File path = Environment.getExternalStorageDirectory();

		StatFs stat = new StatFs(path.getPath());

		/* ��ȡblock��SIZE */

		long blockSize = stat.getBlockSize();

		/* ������ */

		long availableBlocks = stat.getBlockCount();

		/* ����bit��С */

		String sdSize = Formatter.formatFileSize(context, availableBlocks
				* blockSize);
		String[] ramInfo = { Formatter.formatFileSize(context, getRAM()),
				sdSize, sdSize };
		return ramInfo;

	}

	/**
	 * window������
	 * */
	private WindowManager windowManager;

	/**
	 * ��ȡ�ֱ�����Ϣ
	 * */
	public String[] getDisplayMetricsInfo(Context context) {
		/******************************** ��Ļ�ֱ��� ********************************************/

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		String pixels = dm.widthPixels + " x " + dm.heightPixels;

		/********************************** ��Ƭ���ߴ� ********************************************/

		Display display = windowManager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		/************************************ ����� **********************************************/

		Camera camera = Camera.open();
		Parameters parameters = camera.getParameters();
		String flashMode = parameters.getFlashMode();
		camera.release();
		camera = null;

		String[] displayMetricsInfo = { pixels, width + "x" + height,
				flashMode != null ? "YES" : "NO" };
		return displayMetricsInfo;
	}

	/**
	 * ������Ϣ
	 * */
	public String[] getPixelInfo(Context context) {
		Parameters parameters = null;
		Camera camera = null;
		Size size = null;
		/************************************ �ֻ����� **********************************************/

		camera = Camera.open();
		parameters = camera.getParameters();
		size = parameters.getPictureSize();
		String pixel = (size.height * size.width) / 10000 + "px";
		camera.release();
		camera = null;

		/************************************ �����ܶ� **********************************************/

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		float den = dm.density;

		String[] pixelInfo = { pixel, den + "", isPointerCount() ? "YES" : "NO" };
		return pixelInfo;
	}

	/**
	 * ��㴥��
	 * */
	public boolean isPointerCount() {
		Method[] methods = MotionEvent.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("getPointerCount")) {
				return true;
			}

		}

		return false;
	}

	/**
	 * wifi��Ϣ
	 * */
	public String[] getWifiInfo(Context context) {

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifiManager.getConnectionInfo();

		String wifiName = info.getSSID();// WIFI����

		int wifiSpeed = info.getLinkSpeed();// ��ǰ�ٶ�

		String mac = info.getMacAddress();// mac��ַ

		int ip = info.getIpAddress();// iP��ַ ������1001001010100100100101010 32λ
		String ipAddress = Formatter.formatIpAddress(ip);

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		boolean isOpen = bluetoothAdapter.isEnabled();// �����Ƿ��

		String[] wifiInfo = { wifiName, ipAddress, wifiSpeed + "", mac,
				isOpen ? "YES" : "NO" };
		// String[] wifiInfo = { wifiName, ipAddress, wifiSpeed + "", mac,
		// "null" };
		return wifiInfo;

	}
}
