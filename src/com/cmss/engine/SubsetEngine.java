package com.cmss.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cmss.Import.ExcelImport;
import com.cmss.aggregation.Aggregator;
import com.cmss.storage.Column;
import com.cmss.storage.RawTable;
import com.cmss.storage.ObjectAlreadyInitializedException;
import com.cmss.storage.Tuple;

public class SubsetEngine {
	public RawTable subset(RawTable iTable, Integer[] attrs, Integer[] metrics){
		
		Map<Tuple, List<Integer>> grouping = groupColumns(iTable, attrs);
		Iterator<Tuple> iterator = grouping.keySet().iterator();
		
		RawTable result = new RawTable();
		int resultRow = grouping.size();
		try {
			result.init(resultRow);
			
			for (int i = 0; i < attrs.length+metrics.length; i++) {
				Column origin = iTable.getColumn(i<attrs.length? attrs[i] : metrics[i-attrs.length]);
				Column col = result.AddColumn(origin.getDataType());
				col.setName(origin.getName());
			}
						
		} catch (ObjectAlreadyInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int iRow = 0;
		while (iterator.hasNext()) {
			Tuple tuple = iterator.next();
			
			for (int c = 0; c < attrs.length; c++) {
				result.getColumn(c).setData(iRow, tuple.getKey(c));
			}
			
			List<Integer> group = grouping.get(tuple);
			
			Aggregator aggregator = new Aggregator();
			for (int m = 0; m < metrics.length; m++) {
				result.getColumn(attrs.length+m).setData(iRow, 
						aggregator.aggregate(iTable.getColumn(metrics[m]), group, 
								Aggregator.Operator.SUM));
			}
			
			iRow++;
		}
		
		return result;
	}
	
	private Map<Tuple, List<Integer>> groupColumns(RawTable iTable, Integer[] attrs) {
		
		int row = iTable.getNumOfRows();
		
		List<Column> cols = new ArrayList<Column>();
		for (int attr: attrs) {
			cols.add(iTable.getColumn(attr));
		}
		
		Map<Tuple, List<Integer>> result = new TreeMap<Tuple, List<Integer>>();
		for (int i = 0; i < row; i++) {
			Tuple tuple = new Tuple(cols.size());
			for (int j = 0; j < cols.size(); j++) {
				tuple.setKey(j, cols.get(j).getData(i));
			}
			
			List<Integer> group = result.get(tuple);
			
			if (group == null) {
				group = new LinkedList<Integer>();
				result.put(tuple, group);
			}
			
			group.add(i);
		}
		
		return result;
	}
	
	public static void main(String[] args) throws ObjectAlreadyInitializedException, IOException {
		ExcelImport excelimport = new ExcelImport("test.xlsx");
		
		RawTable t1 = excelimport.Transform();
		SubsetEngine se = new SubsetEngine();
		Integer attrs[] = {0,1};
		Integer metrics[] = {4};
		RawTable t2 = se.subset(t1, attrs, metrics);
		
		System.out.println("Start");
		System.out.println(t1.toJSON());
		System.out.println("---------------");
		System.out.println(t2.toJSON());
		System.out.println("finished");
	}
}
