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
 * 这是一个获取设备信息的工具类
 * */
public class AppEntityUtil {
	/**
	 * 包管理器
	 * */
	private PackageManager packageManager;
	/**
	 * 定义集合，存放获取的数据
	 * */
	private ArrayList<AppEntity> appList;
	/**
	 * 高级输出流
	 * */
	private BufferedReader bufferedReader;

	/**
	 * 获取系统安装程序列表信息
	 * */
	public ArrayList<AppEntity> getSystemApp(Context context) {
		packageManager = context.getPackageManager();

		appList = new ArrayList<AppEntity>();

		List<PackageInfo> app1 = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);

		for (PackageInfo packageInfo : app1) {
			AppEntity appEntity = new AppEntity();
			/** 系统程序的判断 */
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
	 * 获取用户安装程序列表信息
	 * */
	public ArrayList<AppEntity> getUserApp(Context context) {
		packageManager = context.getPackageManager();

		appList = new ArrayList<AppEntity>();

		List<PackageInfo> app1 = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);

		for (PackageInfo packageInfo : app1) {
			AppEntity appEntity = new AppEntity();
			/** 用户程序的判断 */
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
	 * 获取关于进程的信息
	 * */
	public ArrayList<AppEntity> getProgressCase(Context context) {

		packageManager = context.getPackageManager();

		appList = new ArrayList<AppEntity>();
		/**
		 * 获取已安装的程序
		 * */
		List<ApplicationInfo> aInfos = packageManager
				.getInstalledApplications(0);

		/**
		 * 获取Activity服务
		 * */
		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		/**
		 * 获取正在运行程序进程的信息
		 * */
		List<RunningAppProcessInfo> rInfos = aManager.getRunningAppProcesses();

		for (RunningAppProcessInfo runningAppProcessInfo : rInfos) {
			AppEntity appEntity = new AppEntity();
			/**
			 * 内存信息
			 * */
			android.os.Debug.MemoryInfo memoryInfo = aManager
					.getProcessMemoryInfo(new int[] { runningAppProcessInfo.pid })[0];
			/**
			 * 遍历ApplicationInfo，寻找runningAppProcessInfo.processName
			 * */
			for (ApplicationInfo applicationInfo : aInfos) {

				if (runningAppProcessInfo.processName
						.equals(applicationInfo.processName)
						&& !applicationInfo.packageName
								.equals("edu.feicui.assistant")
						&& (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

					appEntity.setIcon(applicationInfo.loadIcon(packageManager));// 图标

					appEntity
							.setName(applicationInfo.loadLabel(packageManager));// 程序名称

					appEntity.setOneRAM(android.text.format.Formatter// 占用内存
							.formatFileSize(context,
									(memoryInfo.dalvikPrivateDirty/** 占用的内存 */
									) * 1024));

					appEntity.setServeNum(getRunningAppServeNum(context,// 服务数量
							runningAppProcessInfo.pid));
					appEntity.setPackageName(applicationInfo.packageName);// 包名
					appList.add(appEntity);
					break;
				}

			}

		}

		return appList;
	}

	/**
	 * 获取后台运行程序服务数量
	 * */
	public int getRunningAppServeNum(Context context, int pid) {
		/**
		 * 计数器，计算服务数量
		 * */
		int count = 0;
		/**
		 * 获取Activity服务
		 * */
		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		/**
		 * 获取服务的总数量
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
	 * 获取内存大小
	 * */
	public long getRAM() {

		long inital_memory = 0;
		try {
			FileReader fileReader = new FileReader("/proc/meminfo");
			bufferedReader = new BufferedReader(fileReader, 1024);
			String str = bufferedReader.readLine();// meminfo第一行，系统总内存大小

			String[] ram = str.split("\\s+");

			inital_memory = Integer.valueOf(ram[1]).intValue() * 1024;
			bufferedReader.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return inital_memory;
	}

	/**
	 * 获取可用内存大小
	 * */
	public long getAvailMemory(Context context) {

		ActivityManager aManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		MemoryInfo memoryInfo = new MemoryInfo();
		aManager.getMemoryInfo(memoryInfo);

		return memoryInfo.availMem;

	}

	/**
	 * 基本信息数组
	 * */
	private String[] baseInfo;
	/**
	 * 运营商名称
	 * */
	private String name;

	/**
	 * 设备基本信息
	 * */
	public String[] getBaseInfo(Context context) {

		String model = android.os.Build.MODEL;// 设备型号

		String version = android.os.Build.VERSION.RELEASE;// 系统版本

		TelephonyManager tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String num = tManager.getDeviceId();// 手机串号

		String sim = tManager.getSimOperator();// 运营商　

		String[] simName = context.getResources().getStringArray(R.array.SIM);// 运营商名称
		if (sim.startsWith("460")) {// 判断是否Chinese
			int simNum = Integer.parseInt(sim.substring(3));
			name = simName[simNum];

		}

		baseInfo = new String[] { model, version, num, name,
				isRoot() ? "YES" : "NO" };
		return baseInfo;

	}

	private Process process;// 定义进程
	private DataOutputStream os;//

	/**
	 * 设备是否root
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

	// private String[] cpu;// 拆分cpu信息数组
	/** cpu核数 */
	private String cpuModle;
	/** cpu制造商 */
	private String cpuHardware;

	/**
	 * 获取CPU信息
	 * */
	public String[] getCpuInfo(Context context) {

		/************************************** cpu型号 ***************************************/

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

		/************************************** cpu核数 ***************************************/
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

	/************************************** 获取cpu频率 ***************************************/
	public String getCpuFreq(int index) {

		String freqNum = "";// cpu频率
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
	 * 内存信息
	 * */
	public String[] getRamInfo(Context context) {
		File path = Environment.getExternalStorageDirectory();

		StatFs stat = new StatFs(path.getPath());

		/* 获取block的SIZE */

		long blockSize = stat.getBlockSize();

		/* 块数量 */

		long availableBlocks = stat.getBlockCount();

		/* 返回bit大小 */

		String sdSize = Formatter.formatFileSize(context, availableBlocks
				* blockSize);
		String[] ramInfo = { Formatter.formatFileSize(context, getRAM()),
				sdSize, sdSize };
		return ramInfo;

	}

	/**
	 * window管理者
	 * */
	private WindowManager windowManager;

	/**
	 * 获取分辨率信息
	 * */
	public String[] getDisplayMetricsInfo(Context context) {
		/******************************** 屏幕分辨率 ********************************************/

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		String pixels = dm.widthPixels + " x " + dm.heightPixels;

		/********************************** 照片最大尺寸 ********************************************/

		Display display = windowManager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		/************************************ 闪光灯 **********************************************/

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
	 * 像素信息
	 * */
	public String[] getPixelInfo(Context context) {
		Parameters parameters = null;
		Camera camera = null;
		Size size = null;
		/************************************ 手机像素 **********************************************/

		camera = Camera.open();
		parameters = camera.getParameters();
		size = parameters.getPictureSize();
		String pixel = (size.height * size.width) / 10000 + "px";
		camera.release();
		camera = null;

		/************************************ 像素密度 **********************************************/

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		float den = dm.density;

		String[] pixelInfo = { pixel, den + "", isPointerCount() ? "YES" : "NO" };
		return pixelInfo;
	}

	/**
	 * 多点触控
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
	 * wifi信息
	 * */
	public String[] getWifiInfo(Context context) {

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifiManager.getConnectionInfo();

		String wifiName = info.getSSID();// WIFI名字

		int wifiSpeed = info.getLinkSpeed();// 当前速度

		String mac = info.getMacAddress();// mac地址

		int ip = info.getIpAddress();// iP地址 二进制1001001010100100100101010 32位
		String ipAddress = Formatter.formatIpAddress(ip);

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		boolean isOpen = bluetoothAdapter.isEnabled();// 蓝牙是否打开

		String[] wifiInfo = { wifiName, ipAddress, wifiSpeed + "", mac,
				isOpen ? "YES" : "NO" };
		// String[] wifiInfo = { wifiName, ipAddress, wifiSpeed + "", mac,
		// "null" };
		return wifiInfo;

	}
}
