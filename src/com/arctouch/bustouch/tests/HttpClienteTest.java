package com.arctouch.bustouch.tests;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.arctouch.bustouch.json.common.CommonData;
import com.arctouch.bustouch.json.common.GenericHttpClient;

public class HttpClienteTest {

	@Test
	public void connectionSuccess() {
		String responseBody = GenericHttpClient.getInstance().sendRequestByGet(CommonData.SERVER_URL);
		
		assertThat(responseBody, not(nullValue()));
	}

}
