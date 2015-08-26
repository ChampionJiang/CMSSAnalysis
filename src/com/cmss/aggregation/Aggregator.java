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
		switch (operator){
		case SUM: {
			double dVal = 0;
			for (int r: group) {
				Object val = col.getData(r);
				if (val instanceof Integer)
					dVal += (int) val;
				else if (val instanceof Double)
					dVal += (double) val;
			}
			return dVal;
		}
			//break;
		default:
			break;
		}
		
		return null;
	}
}
