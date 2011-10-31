package com.geishatokyo.aws.ec2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AmazonEC2MetaData {
	private static AmazonEC2MetaData instance = new AmazonEC2MetaData();
	private String baseURL = "http://169.254.169.254";

	private AmazonEC2MetaData() {
	}

	public static AmazonEC2MetaData getInstance() {
		return instance;
	}

	public static String get(String key) {
		return get(key, null);
	}

	public static String get(String key, String defaultValue) {
		return getInstance().doGet(key, defaultValue);
	}

	private String doGet(String key, String defaultValue) {
		URL url = getURL(key);
		String value = getContent(url);
		if (value != null) {
			return value;
		} else {
			return defaultValue;
		}
	}

	private URL getURL(String key) {
		String sUrl = baseURL + key; // TODO: must validate key
		URL url = null;
		try {
			url = new URL(sUrl);
		} catch (MalformedURLException e) {
			//
		}
		return url;
	}

	private String getContent(URL url) {
		if (url == null) {
			return null;
		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
		} catch (IOException e) {
			//
		}
		if (connection == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		try {
			if (connection.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while (true) {
					String s = br.readLine();
					if (s == null) {
						break;
					} else {
						if (buf == null) {
							buf = new StringBuilder();
						}
						buf.append(s); // TODO: must care newlines
					}
				}
			}
		} catch (IOException e) {
			//
		} finally {
			connection.disconnect();
		}
		return 0 < buf.length() ? buf.toString() : null;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public static void main(String... args) {
		for(String key: args) {
			System.out.println(key + "=" + get(key, "nil"));
		}
	}
}
