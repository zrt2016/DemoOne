package com.zrt.fragmentdemoone.yizhu;

import java.util.List;

public abstract class YiZhuStatusBasic {
	
	public abstract String getSQLiteType(String yizhu_type);
	
	public abstract <T> void onExecute(T object);
	
	public <T> List<T> getData(){
		
		
		return null;
	}

}
