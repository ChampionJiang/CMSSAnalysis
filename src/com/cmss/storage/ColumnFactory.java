package com.cmss.storage;

public class ColumnFactory {
	public static Column CreateColumn(SimpleColumn.DataType dt, int rowCount)
	{
		Column result = new SimpleColumn(rowCount, dt);
		
//		switch(dt)
//		{
//		case INTEGER:
//			result = new MyColumn<Integer>(rowCount, dt);
//			break;
//		case DOUBLE:
//			result = new MyColumn<Double>(rowCount, dt);
//			break;
//		case FLOAT:
//			result = new MyColumn<Float>(rowCount, dt);
//			break;
//		case STRING:
//			result = new MyColumn<String>(rowCount, dt);
//			break;
//		default:
//			break;
//		}
		
		return result;
	}
}
