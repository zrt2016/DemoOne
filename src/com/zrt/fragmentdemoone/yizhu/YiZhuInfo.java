package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.Map;

public class YiZhuInfo {
	
//	modify_time
	
	public int yizhu_id;

	public String zhuyuan_id = "";
	/**医嘱类型：长期或者临时*/
	public String yizhu_type = "";
	/** 医嘱内容 */
	public String content = "";
	/** 医嘱下达时间 */
	public String start_time = "";
	/** 医嘱停止时间 */
	public String stop_time = "";
	/** 医嘱用法 */
	public String yongfa = "";
	/** 医嘱用量 */
	public String yongliang = "";
	/** 医嘱用量单位 */
	public String shiyong_danwei = "";
	/** 医嘱使用频率 */
	public String pinlv = "";
	/** 医嘱组号 */
	public String zuhao = "";
	/** 医嘱执行状态 */
	public String zhixing_state = "";
	/** 医嘱每日需要完成次数 */
	public int meiri_cishu;
	/** 医嘱每日已完成次数 */
	public int wancheng_cishu;
	/**  */
	public String operate_time = "";
	/** 医嘱类型 */
	public String yongfa_type = "";
	/** 医嘱状态：开始执行或者停止执行或暂停执行 */
	public String state = "";
	/**  */
	public String beizhu = "";
	/**  */
	public String kuatian = "";
	/**  */
	public String xiangying_state = "";
	/**  */
	public String zhuangtai = "";
	/**  */
	public String yizhu_time = "";
	/**  */
	public String chushi_state = "";
	/**  */
	public String shouci_yongyao = "";
	/**  */
	public String yongyao_time = "";
	/**  */
	public String yichang_lock = "";
	/**  */
	public String unwork_time = "";
	/**  */
	public List<ContentInfo> yizhu_content_info;
	/**  */
	public List<Map<String, String>> yizhu_hedui_history;
	/**  */
	public List<Map<String, String>> yizhu_qita_history;
	/** 医嘱配液历史记录 */
	public String peiye_history = ""; //peiye_lishi_new
	/** 医嘱校对历史记录 */
	public String jiaodui_history = "";//jiaodui_lishi_new
	/** 医嘱开始执行历史记录 */
	public String kaishi_history = "";//kaishi_lishi_new
	/** 医嘱执行完毕历史记录 */
	public String wanbi_history = "";//wanbi_lishi_new
	/** 皮试医嘱执行完毕录入值 */
	public String pishi_value = "";//pishi_value
	
	public int getYizhu_id() {
		return yizhu_id;
	}
	public void setYizhu_id(int yizhu_id) {
		this.yizhu_id = yizhu_id;
	}
	public String getZhuyuan_id() {
		return zhuyuan_id;
	}
	public void setZhuyuan_id(String zhuyuan_id) {
		this.zhuyuan_id = zhuyuan_id;
	}
	public String getYizhu_type() {
		return yizhu_type;
	}
	public void setYizhu_type(String yizhu_type) {
		this.yizhu_type = yizhu_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getStop_time() {
		return stop_time;
	}
	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
	}
	public String getYongfa() {
		return yongfa;
	}
	public void setYongfa(String yongfa) {
		this.yongfa = yongfa;
	}
	public String getYongliang() {
		return yongliang;
	}
	public void setYongliang(String yongliang) {
		this.yongliang = yongliang;
	}
	public String getShiyong_danwei() {
		return shiyong_danwei;
	}
	public void setShiyong_danwei(String shiyong_danwei) {
		this.shiyong_danwei = shiyong_danwei;
	}
	public String getPinlv() {
		return pinlv;
	}
	public void setPinlv(String pinlv) {
		this.pinlv = pinlv;
	}
	public String getZuhao() {
		return zuhao;
	}
	public void setZuhao(String zuhao) {
		this.zuhao = zuhao;
	}
	public String getZhixing_state() {
		return zhixing_state;
	}
	public void setZhixing_state(String zhixing_state) {
		this.zhixing_state = zhixing_state;
	}
	public int getMeiri_cishu() {
		return meiri_cishu;
	}
	public void setMeiri_cishu(int meiri_cishu) {
		this.meiri_cishu = meiri_cishu;
	}
	public int getWancheng_cishu() {
		return wancheng_cishu;
	}
	public void setWancheng_cishu(int wancheng_cishu) {
		this.wancheng_cishu = wancheng_cishu;
	}
	public String getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	public String getYongfa_type() {
		return yongfa_type;
	}
	public void setYongfa_type(String yongfa_type) {
		this.yongfa_type = yongfa_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public String getKuatian() {
		return kuatian;
	}
	public void setKuatian(String kuatian) {
		this.kuatian = kuatian;
	}
	public String getXiangying_state() {
		return xiangying_state;
	}
	public void setXiangying_state(String xiangying_state) {
		this.xiangying_state = xiangying_state;
	}
	public String getZhuangtai() {
		return zhuangtai;
	}
	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}
	public String getYizhu_time() {
		return yizhu_time;
	}
	public void setYizhu_time(String yizhu_time) {
		this.yizhu_time = yizhu_time;
	}
	public String getChushi_state() {
		return chushi_state;
	}
	public void setChushi_state(String chushi_state) {
		this.chushi_state = chushi_state;
	}
	public String getShouci_yongyao() {
		return shouci_yongyao;
	}
	public void setShouci_yongyao(String shouci_yongyao) {
		this.shouci_yongyao = shouci_yongyao;
	}
	public String getYongyao_time() {
		return yongyao_time;
	}
	public void setYongyao_time(String yongyao_time) {
		this.yongyao_time = yongyao_time;
	}
	public String getYichang_lock() {
		return yichang_lock;
	}
	public void setYichang_lock(String yichang_lock) {
		this.yichang_lock = yichang_lock;
	}
	public String getUnwork_time() {
		return unwork_time;
	}
	public void setUnwork_time(String unwork_time) {
		this.unwork_time = unwork_time;
	}
	public List<ContentInfo> getYizhu_content_info() {
		return yizhu_content_info;
	}
	public void setYizhu_content_info(List<ContentInfo> yizhu_content_info) {
		this.yizhu_content_info = yizhu_content_info;
	}
	public List<Map<String, String>> getYizhu_hedui_history() {
		return yizhu_hedui_history;
	}
	public void setYizhu_hedui_history(List<Map<String, String>> yizhu_hedui_history) {
		this.yizhu_hedui_history = yizhu_hedui_history;
	}
	public List<Map<String, String>> getYizhu_qita_history() {
		return yizhu_qita_history;
	}
	public void setYizhu_qita_history(List<Map<String, String>> yizhu_qita_history) {
		this.yizhu_qita_history = yizhu_qita_history;
	}
	public String getPeiye_history() {
		return peiye_history;
	}
	public void setPeiye_history(String peiye_history) {
		this.peiye_history = peiye_history;
	}
	public String getJiaodui_history() {
		return jiaodui_history;
	}
	public void setJiaodui_history(String jiaodui_history) {
		this.jiaodui_history = jiaodui_history;
	}
	public String getKaishi_history() {
		return kaishi_history;
	}
	public void setKaishi_history(String kaishi_history) {
		this.kaishi_history = kaishi_history;
	}
	public String getWanbi_history() {
		return wanbi_history;
	}
	public void setWanbi_history(String wanbi_history) {
		this.wanbi_history = wanbi_history;
	}
	public String getPishi_value() {
		return pishi_value;
	}
	public void setPishi_value(String pishi_value) {
		this.pishi_value = pishi_value;
	}
	
	public class ContentInfo{
		public String content = "";
		public String yongliang = "";
		public String shiyong_danwei = "";
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getYongliang() {
			return yongliang;
		}
		public void setYongliang(String yongliang) {
			this.yongliang = yongliang;
		}
		public String getShiyong_danwei() {
			return shiyong_danwei;
		}
		public void setShiyong_danwei(String shiyong_danwei) {
			this.shiyong_danwei = shiyong_danwei;
		}
	}
	
}
