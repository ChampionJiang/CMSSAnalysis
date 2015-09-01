package com.cmss.storage;

import java.util.ArrayList;

public class TabularData {
	private ArrayList<DataUnit> units;
	IndexPool keyTablePool;
	
	public DataUnit createUnit(int type) {
		DataUnit unit = null;
		int index = units.size();
		
		switch (type) {
		case DataUnit.ATTRIBUTE:
			unit = new LookupTable();
			break;
		case DataUnit.METRICS:
			unit = new Metrics();
			break;
		default:
			break;
		}
		
		unit.setIndex(index);
		
		return unit;
	}
	
	public Cube createCube() {
		Cube cube = new Cube();	
		cube.setData(this);
		
		return cube;
	}
}
