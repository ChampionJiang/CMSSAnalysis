package com.cmss.aggregation;

import java.util.List;

import com.cmss.storage.Column;

public class Aggregator {
	public enum Operator {
		SUM,
		COUNT,
		AVERAGE,
		MIN,
		MAX,
		PRODUCT
	}
	
	public Object aggregate(Column col, List<Integer> group, Aggregator.Operator operator) {
		return null;
	}
}
