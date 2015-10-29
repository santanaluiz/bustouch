package com.arctouch.bustouch.json.services;

import static com.arctouch.bustouch.util.ValidationUtil.isNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.arctouch.bustouch.json.common.GenericHttpClient;

public abstract class AbstractService implements Service {
	private GenericHttpClient client;
	private Map<String, String> headers;
	
	@Override
	public abstract Type getCollectionType();

	protected void initializeClient() {
		if (isNull(client)) {
			this.client = GenericHttpClient.getInstance();
		}
	}
	
	protected void initializeHeaders() {
		if (isNull(headers)) {
			this.headers = new HashMap<String, String>();
			this.headers.put("Content-Type", "application/json");
			this.headers.put("Authorization", "Basic V0tENE43WU1BMXVpTThWOkR0ZFR0ek1MUWxBMGhrMkMxWWk1cEx5VklsQVE2OA==");
			this.headers.put("X-AppGlu-Environment", "staging");
		}
	}
	
	protected String executePost(String url, String requestBody) {
		return client.sendRequestByPost(
				url, 
				this.headers, 
				requestBody);
	}
	
	protected String extractRowsFromJSON(String responseJSON) {
		return responseJSON.substring(responseJSON.indexOf("["), (responseJSON.lastIndexOf("]") + 1));
	}
}
