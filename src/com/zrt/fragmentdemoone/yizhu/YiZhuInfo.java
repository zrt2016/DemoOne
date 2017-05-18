package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.Map;

public class YiZhuInfo {
	
//	yizhu_id integer primary key,
//	zhuyuan_id
//	yizhu_type
//	content
//	start_time
//	stop_time
//	yongfa
//	yongliang
//	shiyong_danwei
//	pinlv
//	zuhao
//	zhixing_state
//	meiri_cishu integer
//	wancheng_cishu integer
//	operate_time
//	yongfa_type
//	state,beizhu
//	kuatian
//	xiangying_state
//	zhuangtai
//	yizhu_time
//	chushi_state
//	modify_time
//	shouci_yongyao
//	yongyao_time
//	yichang_lock
	
	public int yizhu_id;

	public String zhuyuan_id;
	/**医嘱类型：长期或者临时*/
	public String yizhu_type;
	/** 医嘱内容 */
	public String content;
	/** 医嘱下达时间 */
	public String start_time;
	/** 医嘱停止时间 */
	public String stop_time;
	/** 医嘱用法 */
	public String yongfa;
	/** 医嘱用量 */
	public String yongliang;
	/** 医嘱用量单位 */
	public String shiyong_danwei;
	/** 医嘱使用频率 */
	public String pinlv;
	/** 医嘱组号 */
	public String zuhao;
	/** 医嘱执行状态 */
	public String zhixing_state;
	/** 医嘱每日需要完成次数 */
	public int meiri_cishu;
	/** 医嘱每日已完成次数 */
	public int wancheng_cishu;
	/**  */
	public String operate_time;
	/** 医嘱类型 */
	public String yongfa_type;
	/** 医嘱状态：开始执行或者停止执行或暂停执行 */
	public String state;
	/**  */
	public String beizhu;
	/**  */
	public String kuatian;
	/**  */
	public String xiangying_state;
	/**  */
	public String zhuangtai;
	/**  */
	public String yizhu_time;
	/**  */
	public String chushi_state;
	/**  */
	public String shouci_yongyao;
	/**  */
	public String yongyao_time;
	/**  */
	public String yichang_lock;
	/**  */
	public String unwork_time;
	/**  */
	public List<Map<String, String>> yizhu_content_info;
	/**  */
	public List<Map<String, String>> yizhu_hedui_history;
	/**  */
	public List<Map<String, String>> yizhu_qitq_history;
	/** 医嘱配液历史记录 */
	public String peiye_lishi = ""; 
	/** 医嘱校对历史记录 */
	public String jiaodui_lishi = "";
	/** 医嘱开始执行历史记录 */
	public String kaishi_lishi = "";
	/** 医嘱执行完毕历史记录 */
	public String wanbi_lishi = "";
	/** 皮试医嘱执行完毕录入值 */
	public String pishi_value = "";
	
	
//	localHashMap2.put("yizhu_content_info", yizhuContentData);
//    localHashMap2.put("peiqian_hedui_lishi", localHeduiLishi);
//    localHashMap2.put("yizhu_qita_lishi", localQitaLishi);
	
//    localHashMap2.put("peiye_lishi", peiye_lishi_new);		// peiye_lishi
//    localHashMap2.put("jiaodui_lishi", jiaodui_lishi_new);	// jiaodui_lishi
//    localHashMap2.put("zhixing_lishi", kaishi_lishi_new);	// zhixing_lishi
//    localHashMap2.put("wancheng_lishi", wanbi_lishi_new);	// wancheng_lishi
//	String peiye_lishi_new = "";		// 配液
//	String jiaodui_lishi_new = "";		// 校对
//	String kaishi_lishi_new = "";		// 开始执行
//	String wanbi_lishi_new = "";		// 执行完毕
//	String pishi_value = "";	
	
}
