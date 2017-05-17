package com.zrt.fragmentdemoone.yizhu;

import java.util.List;

import com.zrt.fragmentdemoone.GlobalInfoApplication;

public abstract class YiZhuStatusBasic {
	public String yizhu_type = "全部";
	
	public GlobalInfoApplication current_application;
	
	/**
	 * 
	 * @param yizhu_type 医嘱的类型
	 * @return 返回查询sqlite语句
	 */
	public abstract String getSQLiteType(String yizhu_type);
	
	public abstract <T> void onExecute(T object);
	
	/**
	 * 2017-05-15
	 * @param yizhu_type 医嘱类型
	 * @param isUpdate 是否刷新医嘱
	 */
	public abstract void setYiZhuType(String yizhu_type, boolean isUpdate);
	
	
	public <T> List<T> getData(){
		
		
		return null;
	}
	
	public void setCurrent_application(GlobalInfoApplication current_application) {
		this.current_application = current_application;
	}

}
