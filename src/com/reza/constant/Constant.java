package com.reza.constant;

import java.util.List;
import java.util.Map;

public final class Constant {

	private static Map<String, String> constantValues;// values are string
	private static Map<String, List<String>> constantCollections;// values are list

	public static String get(String key) {
		return constantValues.get(key).trim();
	}
	public static List<String> getCollection(String key) {
		return constantCollections.get(key);
	}

	public void setConstantCollections(
			Map<String, List<String>> constantCollectionz) {
		constantCollections = constantCollectionz;
	}

	public void setConstantValues(Map<String, String> constantValuz) {
		constantValues = constantValuz;
	}
}
