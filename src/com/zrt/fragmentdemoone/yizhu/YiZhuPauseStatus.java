package com.zrt.fragmentdemoone.yizhu;

public class YiZhuPauseStatus extends YiZhuStatusBasic{

	public YiZhuPauseStatus(String yizhu_type) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSQLiteType(String yizhu_type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void onExecute(T object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYiZhuType(String yizhu_type, boolean isUpdate) {
		this.yizhu_type = yizhu_type;
		// TODO  是否刷新医嘱界面
		if (isUpdate){
			
		}
	}

}
