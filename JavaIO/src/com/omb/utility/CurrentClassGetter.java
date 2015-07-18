package com.omb.utility;

public class CurrentClassGetter extends SecurityManager {
	public String getClassName() {
		return getClassContext()[1].getName();
	}
}
