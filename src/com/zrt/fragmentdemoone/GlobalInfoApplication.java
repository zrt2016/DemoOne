package com.zrt.fragmentdemoone;

import android.app.Application;

public class GlobalInfoApplication extends Application{
	
	public String current_patient_zhuyuan_id = "";
	public String current_user_number = "";
	public String current_user_name = "";
	
	
	public long linshi_yizhu_xianshi = 32L;
	
	 public String yizhu_peiye_hedui_type = "输液";
	public String yizhu_peiye_hedui_types = "'输液'";
	public String yizhu_qita_caozuo_xuanzhe = "暂停|穿刺|录入滴速|药物反应|巡视|拔针|接液|其它";
	
//	public String DUODAI_SHUYE_ZHUSHE_TIPS = "";
	public boolean yizhu_duodai_shuye_tixing = true;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}

}
