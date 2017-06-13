package com.zrt.fragmentdemoone.yizhu;

import com.zrt.fragmentdemoone.GlobalInfoApplication;

import android.content.Context;

public class YiZhuManage {
	// 待配液、已校对、开始执行、暂停执行、执行完毕
	public static final String status_daiPeiYe = "待配液";
	public static final String status_yiJiaoDui = "已校对";
	public static final String status_kaiShi = "开始执行";
	public static final String status_zanTing = "暂停执行";
	public static final String status_wanCheng = "执行完毕";
	
	public final int onTouch = 1;
	public final int onScanBar = 1;
	
	
	public YiZhuStatusBasic statusBasic;
	private YiZhuStatusBasic status_daiPeiYe_basic;
	private YiZhuStatusBasic status_yiJiaoDui_Basic;
	private YiZhuStatusBasic status_kaiShi_Basic;
	private YiZhuStatusBasic status_zanTing_Basic;
	private YiZhuStatusBasic status_wanCheng_Basic;
	
	public YiZhuManage(String status, String yizhu_type, Context context) {
		switch (status) {
			case status_daiPeiYe:
				if (null == status_daiPeiYe_basic){
					status_daiPeiYe_basic = new YiZhuWeiPeiYeStatus(yizhu_type);
				}
				statusBasic = status_daiPeiYe_basic;
				break;
			case status_yiJiaoDui:
				if (null == status_yiJiaoDui_Basic){
					status_yiJiaoDui_Basic = new YiZhuWeiZhiXingStatus(yizhu_type);
				}
				statusBasic = status_yiJiaoDui_Basic;
				break;
			case status_kaiShi:
				if (null == status_kaiShi_Basic){
					status_kaiShi_Basic = new YiZhuBeginStatus(yizhu_type);
				}
				statusBasic = status_kaiShi_Basic;
				break;
			case status_zanTing:
				if (null == status_zanTing_Basic){
					status_zanTing_Basic = new YiZhuPauseStatus(yizhu_type);
				}
				statusBasic = status_zanTing_Basic;
				break;
			case status_wanCheng:
				if (null == status_wanCheng_Basic){
					status_wanCheng_Basic = new YiZhuCompletedStatus(yizhu_type);
				}
				statusBasic = status_wanCheng_Basic;
				break;
			default://默认医嘱状态为已校对
				if (null == status_yiJiaoDui_Basic){
					status_yiJiaoDui_Basic = new YiZhuWeiZhiXingStatus(yizhu_type);
				}
				statusBasic = status_yiJiaoDui_Basic;
				break;
		}
		
		statusBasic.setContext(context);
		statusBasic.setCurrent_application((GlobalInfoApplication) context.getApplicationContext());
		statusBasic.setYiZhuType(yizhu_type);
		//获取医嘱数据
//		statusBasic.getData(statusBasic.getSQLiteType(yizhu_type));
	}
	
	public void yiZhuDialog(String zuhao){
		if (null == statusBasic){
			statusBasic = new YiZhuWeiPeiYeStatus();
		}
		YiZhuInfo yiZhuInfo = statusBasic.checkYiZhu(zuhao);
		if (null == yiZhuInfo){
			return;
		}
		if (yiZhuInfo.getZhixing_state().equals(status_yiJiaoDui)){
			if (null == status_yiJiaoDui_Basic){
				status_yiJiaoDui_Basic = new YiZhuWeiZhiXingStatus(yiZhuInfo.getYongfa_type());
			}
			statusBasic = status_yiJiaoDui_Basic;
		}else if (yiZhuInfo.getZhixing_state().equals(status_kaiShi)){
			if (null == status_kaiShi_Basic){
				status_kaiShi_Basic = new YiZhuBeginStatus(yiZhuInfo.getYongfa_type());
			}
			statusBasic = status_kaiShi_Basic;
		}else if (yiZhuInfo.getZhixing_state().equals(status_zanTing)){
			if (null == status_zanTing_Basic){
				status_zanTing_Basic = new YiZhuPauseStatus(yiZhuInfo.getYongfa_type());
			}
			statusBasic = status_zanTing_Basic;
		}
		statusBasic.yiZhuOpreationHistory(yiZhuInfo);
		statusBasic.scanYiZhuExecute(yiZhuInfo, onScanBar);
	}
	
	/**
	 * 扫描
	 * @param param
	 */
	public void sucessScanBar(String param){
		
	}
	
}
