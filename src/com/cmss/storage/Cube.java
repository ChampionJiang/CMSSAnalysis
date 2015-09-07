package com.cmss.storage;

import java.util.ArrayList;
import java.util.List;

public class Cube {
	private TabularData data;
	private IndexPool indexPool;
	private List<CubeSlice> slices;

	public Cube() {
		slices = new ArrayList<CubeSlice>();
	}
	
	public TabularData getData() {
		return data;
	}
	
	public void setData(TabularData data) {
		this.data = data;
		indexPool = this.data.getIndexPool();
	}

	public CubeSlice getSlice(int sliceID) {
		return slices.get(sliceID);
	}
	
	public int AddSlice(CubeSlice slice) {
		int sliceID = slices.size();
		
		slices.add(slice);
		return sliceID;
	}
	
	public CubeSlice createSlice(int[] units, Column.DataType dt) {
		CubeSlice slice = new MetricSlice();
		
		return slice;
	}
}
