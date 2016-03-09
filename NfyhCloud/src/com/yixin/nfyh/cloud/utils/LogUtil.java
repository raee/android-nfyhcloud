package com.yixin.nfyh.cloud.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.apache.http.Header;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import cn.rui.framework.utils.AppInfo;

import com.rae.core.http.async.AsyncHttpResponseHandler;
import com.rae.core.http.async.RequestParams;
import com.rae.core.http.async.SyncHttpClient;

/**
 * 日志记录
 * 
 * @author ChenRui
 * 
 */
public class LogUtil implements ILog {

	private static LogUtil INSTENCE = null;

	public static boolean EnableDebug = true; // 是否启用调试

	public static ILog getLog() {
		if (INSTENCE == null) {
			INSTENCE = new LogUtil();
		}
		return INSTENCE;
	}

	private String fileNamePath = "android.log";

	private String fileDirPath = "com.yixin.nfyh.cloud";

	private LogUtil() {
	}

	@Override
	public void info(String tag, Object msg) {
		if (!EnableDebug || msg == null)
			return;
		Log.i(tag, msg.toString());
		this.write("I", tag, msg);
	}

	@Override
	public void verbose(String tag, Object msg) {
		if (!EnableDebug || msg == null)
			return;
		Log.v(tag, msg.toString());
		// this.write("V", tag, msg);
	}

	@Override
	public void error(String tag, Object msg) {
		if (!EnableDebug || msg == null)
			return;
		Log.e(tag, msg.toString());
		this.write("E", tag, msg);
	}

	@Override
	public void debug(String tag, Object msg) {
		if (!EnableDebug || msg == null)
			return;
		Log.d(tag, msg.toString());
		// this.write("D", tag, msg);
	}

	@Override
	public void warn(String tag, Object msg) {
		if (!EnableDebug || msg == null)
			return;
		Log.w(tag, msg.toString());
		this.write("W", tag, msg);
	}

	@Override
	public void setExcetion(String tag, Exception e) {
		if (!EnableDebug || e == null)
			return;
		String stace = Log.getStackTraceString(e); // 堆栈信息
		this.write("EXCEPTION", tag, stace);
		e.printStackTrace();
	}

	/**
	 * 创建文件，如果不存在则创建，发生异常返回临时文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	private File getFile() throws IOException {
		String path = FileUtil.createSdCardDirs(fileDirPath);
		if (path != null) {
			path = path + "/" + fileNamePath;
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 写入日志文件
	 * 
	 * @param level
	 * @param tag
	 * @param msg
	 */
	private void write(String level, String tag, Object msg) {
		if (!EnableDebug)
			return;
		try {
			File file = getFile();
			if (file == null)
				return; // 没有可用的存储
			Calendar cal = Calendar.getInstance();
			String content = msg.toString();
			StringBuilder sb = new StringBuilder();
			sb.append("\r\n");
			sb.append("[" + level + "]：" + tag);
			sb.append("\r\n");
			sb.append("[TIME]:" + DateFormat.format("MM-dd-yyyy hh:mm:ss", cal));
			sb.append("\r\n");
			sb.append("[MSG]\r\n");
			sb.append(content);
			sb.append("\r\n");
			sb.append("----------------");
			content = sb.toString();
			FileWriter out = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(out);
			bw.append(content);
			bw.flush();
			bw.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commit(final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				runCommit(context);
			}
		}).start();
	}

	private void runCommit(Context context) {
		try {
			final File file = getFile(); // Log日志文件
			if (file == null || !file.exists()) { // 获取日记文件路径错误

				return;
			}
			if (file.length() < 2)
				return; // 空文件
			this.write("D", "设备信息", getMoblieInfo(context)); // 写入设备信息
			SyncHttpClient client = new SyncHttpClient();
			client.setTimeout(3000);
			String url = "http://nfyh.smu.edu.cn/postlog.aspx";
			RequestParams params = new RequestParams();
			params.put("file", file);
			client.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// 删除文件
					try {
						Thread.sleep(3000);
						file.delete();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] body,
						Throwable e) {
					if (e != null)
						error("log commit", e.getMessage());
				}
			});
		} catch (IOException e) {
			setExcetion("log commit", e);
		}
	}

	/**
	 * 获取手机的信息
	 * 
	 * @return
	 */
	private String getMoblieInfo(Context context) {
		String result = "";
		AppInfo info = new AppInfo(context);
		result = info.toString();
		info = null;
		return result;
	}
}
