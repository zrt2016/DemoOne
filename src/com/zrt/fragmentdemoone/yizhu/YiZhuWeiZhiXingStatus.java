package com.zrt.fragmentdemoone.yizhu;

/**
 * 已校对模块
 * 存在状态为待配液的非输液医嘱
 * 和状态为已校对的输液医嘱
 * （其中输液类型不一定单指输液类型医嘱，该类型可为配置）
 * @author zrt
 *
 */
public class YiZhuWeiZhiXingStatus extends YiZhuStatusBasic{

	public YiZhuWeiZhiXingStatus(String yizhu_type) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSQLiteType(String yizhu_type) {
		StringBuilder sb = new StringBuilder();
		if (yizhu_type.equals("全部")){
			/**
			 * 如果当前yizhu_type为全部，
			 * 则显示所有状态为待配液的非输液医嘱
			 * 和状态为已校对的输液医嘱
			 */
			sb.append("SELECT * FROM yizhu_info a WHERE a.zhuyuan_id = '")
			  .append(this.current_application.current_patient_zhuyuan_id)
			  .append("' and ((zhixing_state = '待配液' and yongfa_type not in (")
			  .append(this.current_application.yizhu_peiye_hedui_types)
			  .append(")) or (zhixing_state = '已校对' and yongfa_type in (")
			  .append(this.current_application.yizhu_peiye_hedui_types)
			  .append("))) and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
			return sb.toString();
		}
		if (this.current_application.yizhu_peiye_hedui_types.contains(yizhu_type)){
			sb.append("SELECT * FROM yizhu_info WHERE zhuyuan_id = '")
			  .append(this.current_application.current_patient_zhuyuan_id)
			  .append("' and yongfa_type = '")
			  .append(yizhu_type)
			  .append("' and zhixing_state = '已校对' and wancheng_cishu < meiri_cishu and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
			  .append(this.current_application.linshi_yizhu_xianshi)
			  .append(" Hour', 'localtime'))) GROUP BY zuhao ORDER BY yizhu_id ASC ");
			return sb.toString();
		}
		sb.append("SELECT * FROM yizhu_info WHERE zhuyuan_id = '")
		  .append(this.current_application.current_patient_zhuyuan_id)
		  .append("' and yongfa_type = '")
		  .append(yizhu_type)
		  .append("' and zhixing_state = '待配液' and wancheng_cishu < meiri_cishu and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
		  .append(this.current_application.linshi_yizhu_xianshi)
		  .append(" Hour', 'localtime'))) GROUP BY zuhao ORDER BY yizhu_id ASC ");
		return sb.toString();
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
