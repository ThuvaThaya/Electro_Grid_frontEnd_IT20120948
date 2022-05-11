package com.main.user.extras;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

public class JsonArray {
	private ArrayList<HashMap<String, String>> parentList;
	private HashMap<String, String> child;
	
	public JsonArray() {
		super();
		this.parentList = new ArrayList<HashMap<String, String>>();
		this.child = new HashMap<String, String>();
	}
	
	public void addJsonObject(String key, String value) {
		child.put(key, value);
	}
	
	public String getJsonObject() {
		return new Gson().toJson(this.child);
	}
	
	public String addAllToArray() {
		parentList.add(child);
		String json = new Gson().toJson(parentList);
		return json;
	}
	
	public String getJsonArray() {
		return new Gson().toJson(this.parentList);
	}
}
