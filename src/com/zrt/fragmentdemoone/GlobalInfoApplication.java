package com.zrt.fragmentdemoone;

import android.app.Application;

public class GlobalInfoApplication extends Application{
	
	public String current_patient_zhuyuan_id = "";
	
	public long linshi_yizhu_xianshi = 32L;
	
	public String yizhu_peiye_hedui_types = "输液";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}

}
