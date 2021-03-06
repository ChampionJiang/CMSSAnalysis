package com.cmss.storage;

public class TabIndex {
	
	private int[] keyTable;
	private int nUnit;
	private int[] units;
	private int row;

	public TabIndex(int[] units, int row, int[] keyTable) {
		nUnit = units.length;
		this.units = units;
		this.keyTable = keyTable;
		this.row = row;
	}
	
	public void getRow(int r, int[] key) {
		int start = r*nUnit;
		
		for (int i = 0; i < nUnit; i++) {
			key[units[i]] = keyTable[start++];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
