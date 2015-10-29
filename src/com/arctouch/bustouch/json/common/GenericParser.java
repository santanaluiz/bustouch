package com.arctouch.bustouch.json.common;

import java.lang.reflect.Type;
import java.util.List;

import com.arctouch.bustouch.json.model.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenericParser<T extends BaseEntity> {
	
	private static Gson GSON;
	private T type;
	
	/**
	 * Default constructor
	 */
	public GenericParser() {
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		gb = gb.serializeNulls();
		
		GSON = gb.create();
	}
	
	/**
	 * Method to get the entity class dynamic
	 * 
	 * @return
	 * 	Generic Object class 
	 */
	private Class getEntityClass() {
		return type.getClass();
	}
	
	/**
	 * Parse entity
	 * 
	 * @param json
	 * @return
	 * 	entity
	 */
	public T getEntityFromJson(String json) {
		return (T) GSON.fromJson(json, this.getEntityClass());
	}

	/**
	 * Parse entity
	 * 
	 * @param json
	 * @param classe
	 * @return
	 * 	entity
	 */
	public T getEntityFromJson(String json, Class classe) {
		return (T) GSON.fromJson(json, classe);
	}
	
	/**
	 * Parse entity list
	 * 
	 * @param json
	 * @param collectionType
	 * @return
	 * 	list entity
	 */
	public List<T> getEntityListFromJson(String json, Type collectionType) {
		return GSON.fromJson(json, collectionType);
	}
	
	/**
	 * Parse entity to json
	 * 
	 * @param entity
	 * @return
	 * 	JSON
	 */
	public String getJsonFromEntity(T entity) {
		return GSON.toJson(entity);
	}
}
