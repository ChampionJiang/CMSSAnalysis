package com.cmss.storage;

public class Tuple {

	Object [] data;
	public Tuple(int size) {
		// TODO Auto-generated constructor stub
		data = new Object[size];
	}

	public Object getKey(int idx)
	{
		return data[idx];
	}

	public void setKey(int idx, Object obj) {
		// TODO Auto-generated method stub
		data[idx] = obj;
	}

	public boolean equals(Object obj) { 
		if (!(obj instanceof Tuple))
			return false;
		
		Tuple tuple = (Tuple)obj;
		for (int i = 0; i < data.length; i++) {
			if (!data[i].equals(tuple.getKey(i))) {
				return false;
			}
		}
		
		return true;
	}  
}
