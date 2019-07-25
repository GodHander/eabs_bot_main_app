package com.fbhaedware.library;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;


public abstract class BaseHttpCallBack extends AsyncHttpResponseHandler {
	
	Context context;

	/**
	 * 为了显示dialog
	 */
	public BaseHttpCallBack() {
		// TODO Auto-generated constructor stub
	}

	public void onSuccess(int statusCode, byte[] bytes) {

	};

	public void onFailure(int statusCode, Throwable throwable, String error) {

	};

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
	}

	@Override
	public void onFailure(int statusCode, Header[] arg1, byte[] error,
						  Throwable throwable) {
		// TODO Auto-generated method stub
		onFailure(statusCode, throwable, error.toString());
	}
	
	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
		// TODO Auto-generated method stub
		onSuccess(statusCode, bytes);
	}

	
	
}
