package com.cmss.storage;

import java.util.ArrayList;
import java.util.UUID;

public class Metrics extends DataUnit{
	private ArrayList<UUID> ids;
	private ArrayList<MetricSlice> slices;
	public ArrayList<UUID> getIds() {
		return ids;
	}
	public void setIds(ArrayList<UUID> ids) {
		this.ids = ids;
	}
	public ArrayList<MetricSlice> getSlices() {
		return slices;
	}
	public void setSlices(ArrayList<MetricSlice> slices) {
		this.slices = slices;
	}
}
