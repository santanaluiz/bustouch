package com.arctouch.bustouch.json.services;

import java.lang.reflect.Type;

public interface Service {
	/**
	 * Retorna o Collection Type para fazer o parser
	 * @return
	 * 	Collection Type
	 */
	Type getCollectionType();
}
