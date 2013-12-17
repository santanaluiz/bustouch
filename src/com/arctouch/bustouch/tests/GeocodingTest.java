package com.arctouch.bustouch.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import com.arctouch.bustouch.json.common.GeocodingHttpClient;

public class GeocodingTest {

	private static final String COORDENADAS_LAURO_LINHARES = "-27.597912,-48.520224";
	
	@Test
	public void test() {
		String streetName = GeocodingHttpClient.getStreet(COORDENADAS_LAURO_LINHARES);
		
		assertThat(streetName, is(notNullValue()));
		assertThat(streetName, is("Rua Lauro Linhares"));
	}

}
