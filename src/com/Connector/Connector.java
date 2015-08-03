package com.Connector;

import com.Storage.MyTable;
import com.Storage.ObjectAlreadyInitializedException;

public interface Connector {
	public void Transform(MyTable table) throws ObjectAlreadyInitializedException;
}
