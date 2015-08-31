package com.cmss.storage;

import java.util.ArrayList;

public class TabularData {
	private ArrayList<DataUnit> units;
	KeyTablePool keyTablePool;
	
	public Cube createCube() {
		Cube cube = new Cube();	
		cube.setData(this);
		
		return cube;
	}
}
