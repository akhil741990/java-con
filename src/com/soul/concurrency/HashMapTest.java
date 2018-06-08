package com.soul.concurrency;

import java.util.HashMap;

public class HashMapTest {

	public static void main(String args[]) {
		HashMap<String, String>  map = new HashMap<>();
		map.put("1", "one");
		
		System.out.println("Map : "+map.toString());
	}
}
