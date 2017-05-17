package com.zrt.fragmentdemoone.yizhu;

/**
 * 未配液模块
 * @author zrt
 *
 */
public class YiZhuWeiPeiYeStatus extends YiZhuStatusBasic{
	
	

	public YiZhuWeiPeiYeStatus(String yizhu_type) {
		
	}

	@Override
	public String getSQLiteType(String yizhu_type) {
		StringBuilder sb = new StringBuilder();
		if (yizhu_type.equals("全部")){
			sb.append("select * from yizhu_info a where a.zhuyuan_id = '")
			  .append(this.current_application.current_patient_zhuyuan_id)
			  .append("' and a.zhixing_state in ('待配液','已配液')  and a.yongfa_type in(")
			  .append(this.current_application.yizhu_peiye_hedui_types)
			  .append(") and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
			return sb.toString();
		}
		if (this.current_application.yizhu_peiye_hedui_types.contains(yizhu_type)){
			sb.append("select * from yizhu_info a where a.zhuyuan_id = '")
			  .append(this.current_application.current_patient_zhuyuan_id)
			  .append("' and a.zhixing_state in ('待配液','已配液')  and a.yongfa_type = '")
			  .append(yizhu_type)
			  .append("' and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
			return sb.toString();
		}
		if (sb.toString().equals("")){
			sb.append("select 1=1");
		}
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
