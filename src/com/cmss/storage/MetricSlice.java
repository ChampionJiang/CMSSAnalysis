package com.cmss.storage;

public class MetricSlice extends CubeSlice {
	private TabIndex index;
	private Column column;

	public TabIndex getKeyTable() {
		return index;
	}


	public void setKeyTable(TabIndex index) {
		this.index = index;
	}


	public Column getColumn() {
		return column;
	}


	public void setColumn(Column column) {
		this.column = column;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
