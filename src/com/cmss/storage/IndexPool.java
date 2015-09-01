package com.cmss.storage;

import java.util.Map;

public class IndexPool {

	private Map<int[], TabIndex> indices;
	
	public TabIndex getIndex(int [] units) {
		if (indices.containsKey(units)) {
			return indices.get(units);
		}
		
		TabIndex result = null;
		// find base index
		
		// generate index from the base index
		
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
