package com.fbhaedware.library;

import android.content.Context;
import android.content.res.AssetManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;


/**
 * psot请求
 * 
 * @author Administrator
 *
 */
public class AsyncHttpManager {

	private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient(true,
			0, 8080);
	private static AssetManager mAssetManager;

	
	/**
	 * psot请求 获取json params可以为 null
	 * 
	 * @param url
	 * @param params
	 * @param callBack
	 */
	public static void postString(String url, RequestParams params,
								  StringHttpCallBack callBack) {
		// TODO Auto-generated method stub
		// RequestParams params = new RequestParams();
		if (params == null) {
			params = new RequestParams();
		}
		setHttpPostHead();
		asyncHttpClient.post(url, params, callBack);
	}


	public static void getString (String url, RequestParams params,
								  StringHttpCallBack callBack) {
		// TODO Auto-generated method stub
		// RequestParams params = new RequestParams();
		if (params == null) {
			params = new RequestParams();
		}
		setHttpGetHead();
		asyncHttpClient.get(url, params, callBack);
	}


	public static void get(String url, RequestParams params,
						   AsyncHttpResponseHandler callBack) {
		// TODO Auto-generated method stub
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setTimeout(10000);
		asyncHttpClient.get(url, params, callBack);
	}

	/**
	 * post请求 返回 byte
	 * 
	 * @param url
	 * @param params
	 * @param callBack
	 */
	public static void postByte(String url, RequestParams params,
								BaseHttpCallBack callBack) {
		// TODO Auto-generated method stub
		// RequestParams params = new RequestParams()
		if (params == null) {
			params = new RequestParams();
		}
		setHttpPostHead();
		asyncHttpClient.post(url, params, callBack);
	}

	public static void post(String url, RequestParams params,
							AsyncHttpResponseHandler callBack) {
		// TODO Auto-generated method stub
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setTimeout(10000);
		asyncHttpClient.post(url, params, callBack);
	}

	public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler callBack) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setTimeout(10000);
		asyncHttpClient.post(context, url, entity,contentType,callBack);
	}

	private static void setHttpPostHead() {
		asyncHttpClient.setTimeout(10000);
	}

	private static void setHttpGetHead(){
		asyncHttpClient.setTimeout(1000);
	}

}
