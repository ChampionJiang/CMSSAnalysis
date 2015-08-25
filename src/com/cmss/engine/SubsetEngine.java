package com.cmss.engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cmss.Import.CSVImport;
import com.cmss.aggregation.Aggregator;
import com.cmss.storage.Column;
import com.cmss.storage.MyTable;
import com.cmss.storage.ObjectAlreadyInitializedException;

public class SubsetEngine {
	public MyTable subset(MyTable iTable, int[] attrs, int[] metrics){
		Map<Object[], List<Integer>> grouping = groupColumns(iTable, attrs);
		Iterator<Object[]> iterator = grouping.keySet().iterator();
		
		MyTable result = new MyTable();
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
			Object[] key = iterator.next();
			
			for (int c = 0; c < attrs.length; c++) {
				result.getColumn(c).setData(iRow, key[c]);
			}
			
			List<Integer> group = grouping.get(key);
			
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
	
	private Map<Object[], List<Integer>> groupColumns(MyTable iTable, int[] attrs) {
		
		int row = iTable.getNumOfRows();
		
		List<Column> cols = new ArrayList<Column>();
		for (int attr: attrs) {
			cols.add(iTable.getColumn(attr));
		}
		
		Map<Object[], List<Integer>> result = new HashMap<Object[], List<Integer>>();
		for (int i = 0; i < row; i++) {
			Object[] tuple = new Object[cols.size()];
			for (int j = 0; j < cols.size(); j++) {
				tuple[j] = cols.get(j).getData(i);
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
	
	public static void main(String[] args) throws FileNotFoundException, ObjectAlreadyInitializedException {
		CSVImport csvimport = new CSVImport("test.csv");
		
		MyTable t1 = csvimport.Transform();
		SubsetEngine se = new SubsetEngine();
		int attrs[] = {0};
		int metrics[] = {2};
		MyTable t2 = se.subset(t1, attrs, metrics);
		
		System.out.println("Start");
		System.out.println(t1.toJSON());
		System.out.println("---------------");
		System.out.println(t2.toJSON());
		System.out.println("finished");
	}
}
