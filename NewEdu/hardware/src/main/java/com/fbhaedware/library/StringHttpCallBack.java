package com.fbhaedware.library;

import android.util.Log;

import org.apache.http.Header;


public abstract class StringHttpCallBack extends BaseHttpCallBack {

	public StringHttpCallBack() {
		// TODO Auto-generated constructor stub
	}



	public abstract void onSuccess(int statusCode, String str);

	@Override
	public abstract void onFailure(int statusCode, Throwable throwable,
								   String error);

	@Override
	public void onFailure(int statusCode, Header[] arg1, byte[] error,
						  Throwable throwable) {
		// TODO Auto-generated method stub
		try {
			String errors = InputStreamUtils.byteTOString(error);
			Log.e("onFailure", errors);
			onFailure(statusCode, throwable, errors);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
		// TODO Auto-generated method stub
		try {
			String json = InputStreamUtils.byteTOString(bytes);
			Log.d("onSuccess", json);
			onSuccess(statusCode,json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
