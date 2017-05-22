package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.Map;

public class YiZhuInfo {
	
//	modify_time
	
	private int yizhu_id;

	private String zhuyuan_id = "";
	/**医嘱类型：长期或者临时*/
	private String yizhu_type = "";
	/** 医嘱内容 */
	private String content = "";
	/** 医嘱下达时间 */
	private String start_time = "";
	/** 医嘱停止时间 */
	private String stop_time = "";
	/** 医嘱用法 */
	private String yongfa = "";
	/** 医嘱用量 */
	private String yongliang = "";
	/** 医嘱用量单位 */
	private String shiyong_danwei = "";
	/** 医嘱使用频率 */
	private String pinlv = "";
	/** 医嘱组号 */
	private String zuhao = "";
	/** 医嘱执行状态 */
	private String zhixing_state = "";
	/** 医嘱每日需要完成次数 */
	private int meiri_cishu;
	/** 医嘱每日已完成次数 */
	private int wancheng_cishu;
	/**  */
	private String operate_time = "";
	/** 医嘱类型 */
	private String yongfa_type = "";
	/** 医嘱状态：开始执行或者停止执行或暂停执行 */
	private String state = "";
	/**  */
	private String beizhu = "";
	/**  */
	private String kuatian = "";
	/**  */
	private String xiangying_state = "";
	/**  */
	private String zhuangtai = "";
	/**  */
	private String yizhu_time = "";
	/**  */
	private String chushi_state = "";
	/**  */
	private String shouci_yongyao = "";
	/**  */
	private String yongyao_time = "";
	/**  */
	private String yichang_lock = "";
	/**  */
	private String unwork_time = "";
	/**  */
	private List<ContentInfo> yizhu_content_info;
	/**  */
	private List<OperationHistoryInfo> yizhu_hedui_history;
	/**  */
	private List<OperationHistoryInfo> yizhu_qita_history;
	/** 医嘱配液历史记录 */
	private String peiye_history = ""; //peiye_lishi_new
	/** 医嘱校对历史记录 */
	private String jiaodui_history = "";//jiaodui_lishi_new
	/** 医嘱开始执行历史记录 */
	private String kaishi_history = "";//kaishi_lishi_new
	/** 医嘱执行完毕历史记录 */
	private String wanbi_history = "";//wanbi_lishi_new
	/** 皮试医嘱执行完毕录入值 */
	private String pishi_value = "";//pishi_value
	
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
	public List<OperationHistoryInfo> getYizhu_hedui_history() {
		return yizhu_hedui_history;
	}
	public void setYizhu_hedui_history(List<OperationHistoryInfo> yizhu_hedui_history) {
		this.yizhu_hedui_history = yizhu_hedui_history;
	}
	public List<OperationHistoryInfo> getYizhu_qita_history() {
		return yizhu_qita_history;
	}
	public void setYizhu_qita_history(List<OperationHistoryInfo> yizhu_qita_history) {
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
	
	public class ContentInfo {
		private String content = "";
		private String yongliang = "";
		private String shiyong_danwei = "";
		
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
	
	
	public class OperationHistoryInfo {
		private String zhixing_hushi_name = "";
		private String zhixing_time = "";
		private String hedui_cishu = "";
		private String op_type = "";
		private String dangqian_cishu = "";
		private String beizhu = "";
		
		public String getZhixing_hushi_name() {
			return zhixing_hushi_name;
		}
		public void setZhixing_hushi_name(String zhixing_hushi_name) {
			this.zhixing_hushi_name = zhixing_hushi_name;
		}
		public String getZhixing_time() {
			return zhixing_time;
		}
		public void setZhixing_time(String zhixing_time) {
			this.zhixing_time = zhixing_time;
		}
		public String getHedui_cishu() {
			return hedui_cishu;
		}
		public void setHedui_cishu(String hedui_cishu) {
			this.hedui_cishu = hedui_cishu;
		}
		public String getOp_type() {
			return op_type;
		}
		public void setOp_type(String op_type) {
			this.op_type = op_type;
		}
		public String getDangqian_cishu() {
			return dangqian_cishu;
		}
		public void setDangqian_cishu(String dangqian_cishu) {
			this.dangqian_cishu = dangqian_cishu;
		}
		public String getBeizhu() {
			return beizhu;
		}
		public void setBeizhu(String beizhu) {
			this.beizhu = beizhu;
		}
	}
	
}
