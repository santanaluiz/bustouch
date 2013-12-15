package com.arctouch.bustouch.json.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;

import com.arctouch.bustouch.json.exception.InternetConnectionException;
import static com.arctouch.bustouch.util.ValidationUtil.*;


public class GenericHttpClient {

	private static GenericHttpClient instance;
	private static HttpClient client;
	
	public static GenericHttpClient getInstance() {
		if (isNull(instance)) {
			instance = new GenericHttpClient();
		}
		
		return instance;
	}
	
	public void checkInternetConnection(Activity activity) throws InternetConnectionException {
		// se estiver offline, lan??a exception
		if (!CommonData.isOnline(activity)) {
			throw new InternetConnectionException("Sem conectividade com a internet.");
		}
	}
	
	public String sendRequestByGet(String url) {
		HttpGet get = new HttpGet(url);
		openClientInstance();
		
		return executeRequestAndGetResponseBody(get);
	}

	public String sendRequestByPost(String url) {
		HttpPost post = new HttpPost(url);
		openClientInstance();
		
		return executeRequestAndGetResponseBody(post);
	}
	
	public String sendRequestByPost(String url, Map<String, String> headers, String requestContent) {
		HttpPost post = new HttpPost(url);
		
		setRequestHeaders(post, headers);
		openClientInstance();
		setRequestContentBody(post, requestContent);
		
		return executeRequestAndGetResponseBody(post);
	}
	
	private void setRequestHeaders(HttpEntityEnclosingRequest request, Map<String, String> headers) {
		for (String key : headers.keySet()) {
			request.addHeader(key, headers.get(key));
		}
	}
	
	private void openClientInstance() {
		client = new DefaultHttpClient();
	}
	
	private void setRequestContentBody(HttpEntityEnclosingRequest request, String requestContent) {
		try {
			StringEntity requestBody;
			requestBody = new StringEntity(requestContent);
			request.setEntity(requestBody);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private String executeRequestAndGetResponseBody(HttpUriRequest request) {
		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			final String responseString = EntityUtils.toString(entity, "UTF-8");
			
			return responseString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
}
