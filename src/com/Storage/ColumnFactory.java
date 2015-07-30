package com.Storage;

public class ColumnFactory {
	public static Column CreateColumn(MyColumn.DataType dt, int rowCount)
	{
		Column result = new MyColumn(rowCount, dt);
		
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
