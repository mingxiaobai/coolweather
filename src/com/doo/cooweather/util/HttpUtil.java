package com.doo.cooweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	public static void sendHttpRequest(final String address,
			final HttpCallbackListener httpCallbackListener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine())!=null) {
						response.append(line);
					}
					if (httpCallbackListener != null) {
						// 回调onFinish()方法
						httpCallbackListener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (httpCallbackListener!=null) {
						// 回调onError()方法
						httpCallbackListener.onError(e);
					}
				}
				
			}
		}).start();

	}
}
