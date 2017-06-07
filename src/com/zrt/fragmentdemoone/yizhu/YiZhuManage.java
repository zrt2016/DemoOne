package com.zrt.fragmentdemoone.yizhu;

import com.zrt.fragmentdemoone.GlobalInfoApplication;

import android.content.Context;

public class YiZhuManage {
	// 待配液、已校对、开始执行、暂停执行、执行完毕
	public static final String status_daipeiye = "待配液";
	public static final String status_weizhixing = "已校对";
	public static final String status_kaishi = "开始执行";
	public static final String status_zanting = "暂停执行";
	public static final String status_wancheng = "执行完毕";
	
	
	public YiZhuStatusBasic statusBasic;
	
	public YiZhuManage(String status, String yizhu_type, Context context) {
		switch (status) {
			case status_daipeiye:
				statusBasic = new YiZhuWeiPeiYeStatus(yizhu_type);
				break;
			case status_weizhixing:
				statusBasic = new YiZhuWeiZhiXingStatus(yizhu_type);
				break;
			case status_kaishi:
				statusBasic = new YiZhuBeginStatus(yizhu_type);
				break;
			case status_zanting:
				statusBasic = new YiZhuPauseStatus(yizhu_type);
				break;
			case status_wancheng:
				statusBasic = new YiZhuCompletedStatus(yizhu_type);
				break;
			default://默认医嘱状态为已校对
				statusBasic = new YiZhuWeiZhiXingStatus(yizhu_type);
				break;
		}
		
		statusBasic.setContext(context);
		statusBasic.setCurrent_application((GlobalInfoApplication) context.getApplicationContext());
		//获取医嘱数据
//		statusBasic.getData(statusBasic.getSQLiteType(yizhu_type));
	}
	
	
}
