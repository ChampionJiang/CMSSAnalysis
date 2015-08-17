package com.storage;

import java.io.Serializable;
import java.util.Random;

public class SingleColumn implements Serializable {
	private int rowCount;
	private double value[];
	
	public SingleColumn(int rc, double vals[])
	{
		rowCount = rc;
		value = vals;
	}
	
	public void Print()
	{
		for (double v: value)
			System.out.print(v+" ");
		System.out.println();
	}
	
	public static void main(String args[])
	{
		double val[] = new double[10];
		Random random = new Random();
		
		for (int i = 0; i < 10; i++ )
		{
			val[i] = random.nextDouble();
		}
		
		SingleColumn sc = new SingleColumn(10, val);
		sc.Print();
	}
}
