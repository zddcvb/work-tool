package com.jollyclass.json.utils;

import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {
	private static Gson gson;
	static {
		gson = new Gson();
	}

	/**
	 * object-->json
	 *
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		String json = gson.toJson(object);
		return json;
	}

	/**
	 * json-->object
	 *
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObject(String json, Class<T> clazz) {
		T entity = gson.fromJson(json, clazz);
		return entity;
	}

	/**
	 * list-->json
	 *
	 * @param jsonList
	 * @return
	 */
	public static <T> String listToJson(List<T> jsonList) {
		String json = gson.toJson(jsonList, new TypeToken<List<T>>() {
		}.getType());
		return json;
	}

	/**
	 * json-->list
	 *
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		List<T> jsonList = gson.fromJson(json, new TypeToken<List<T>>() {
		}.getType());
		return jsonList;
	}

	/**
	 * map-->json
	 *
	 * @param map
	 * @return
	 */
	public static <T> String mapToJson(Map<String, T> map) {
		String json = gson.toJson(map, new TypeToken<Map<String, T>>() {
		}.getType());
		return json;
	}

	/**
	 * json-->map
	 *
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> jsonToMap(String json, Class<T> clazz) {
		Map<String, T> map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}
}
