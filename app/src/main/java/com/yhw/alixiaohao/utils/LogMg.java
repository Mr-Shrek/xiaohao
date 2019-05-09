package com.yhw.alixiaohao.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogMg {
	public static final String TAG							= "LogMg";

	public static boolean		mShowLog					= true;

	public static boolean		mshowTcardlog				= true/* false */;

	private static int			SDCARD_LOG_FILE_SAVE_DAYS	= 2;

	private static void writeLogInFileSystem(String content) {
		synchronized (LogMg.class) {
			String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			String dirPath = sdcardPath + "/LogMg/";
			File file = null;
			RandomAccessFile randomAccessFile = null;

			if (!mshowTcardlog) {
				return;
			}
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HH");

			Date nowtime = new Date(System.currentTimeMillis());
			content = sFormat.format(nowtime) + "  " + content;

			String needDelFiel = logfile.format(getDateBefore());
			// delFile(dirPath + "/log"+ needDelFiel);
			try {
				file = new File(dirPath);
				if (!file.exists()) {
					file.mkdir();
				}

				file = new File(dirPath + "/log" + logfile.format(nowtime));
				if (!file.exists()) {
					file.mkdir();
				}
				String filePath = dirPath + "/log" + logfile.format(nowtime) + "/log" + timeFormat.format(nowtime)
						+ ".txt";

				file = new File(filePath);
				long currSize = file.length();

				randomAccessFile = new RandomAccessFile(filePath, "rwd");
				randomAccessFile.seek(currSize);

				byte[] buffer = content.getBytes();
				randomAccessFile.write(buffer);
				randomAccessFile.write('\n');
			} catch (Exception e) {

				e.printStackTrace();
			}

			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void writeTestLog(String content) {
		synchronized (LogMg.class) {
			File file = null;
			RandomAccessFile randomAccessFile = null;

			if (!mshowTcardlog) {
				return;
			}

			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date nowtime = new Date(System.currentTimeMillis());
			content = sFormat.format(nowtime) + "  " + content;

			try {
				String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KXD.txt";
				file = new File(filePath);
				long currSize = file.length();

				randomAccessFile = new RandomAccessFile(filePath, "rwd");
				randomAccessFile.seek(currSize);

				byte[] buffer = content.getBytes();
				randomAccessFile.write(buffer);
				randomAccessFile.write('\n');
			} catch (Exception e) {

				e.printStackTrace();
			}

			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void delFile(String filePath) {

		File file = new File(filePath);
		deleteDir(file);
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();

			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	private static Date getDateBefore() {
		Date nowtime = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowtime);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
		return now.getTime();
	}

	public static void writeImageInFileSystem(byte[] content) {
		String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		String dirPath = sdcardPath + "/Log/";
		File file = null;
		RandomAccessFile randomAccessFile = null;

		if (!mshowTcardlog) {
			return;
		}

		try {
			file = new File(dirPath);

			if (!file.exists()) {
				file.mkdir();
			}

			String filePath = dirPath + "/image";

			file = new File(filePath);
			long currSize = file.length();

			randomAccessFile = new RandomAccessFile(filePath, "rwd");
			randomAccessFile.seek(currSize);

			randomAccessFile.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void v(String tag, String msg) {
		if (mShowLog)
			Log.v(TAG, tag + ": " + msg);

		if (mshowTcardlog)
			writeLogInFileSystem(tag + ": " + msg);
	}

	public static void i(String tag, String msg) {
		if (mShowLog)
			Log.i(TAG, tag + ": " + msg);

		if (mshowTcardlog)
			writeLogInFileSystem(tag + ": " + msg);
	}

	public static void e(String tag, String msg) {
		if (mShowLog)
			Log.e(TAG, tag + ": " + msg);

		if (mshowTcardlog)
			writeLogInFileSystem(tag + ": " + msg);
	}

	public static void d(String tag, String msg) {
		if (mShowLog)
			Log.d(TAG, tag + ": " + msg);

		if (mshowTcardlog)
			writeLogInFileSystem(tag + ": " + msg);
	}

	public static void f(String msg) {
		writeTestLog(msg);
	}

}
