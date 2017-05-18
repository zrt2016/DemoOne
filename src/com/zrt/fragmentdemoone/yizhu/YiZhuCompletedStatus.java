package com.zrt.fragmentdemoone.yizhu;


public class YiZhuCompletedStatus extends YiZhuStatusBasic{

	public YiZhuCompletedStatus(String yizhu_type) {
		// TODO Auto-generated constructor stub
	}
	
	
//		String zhixing_wanbi_zuhao = "";
//		String zhixing_wanbi_sql = "SELECT * FROM yizhu_zhixing_history_lishi WHERE zhuyuan_id = '" + current_application.current_patient_zhuyuan_id + "' "
//				+ " and zhixing_state = '执行完毕' and case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-" + current_application.linshi_yizhu_xianshi + " Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end GROUP BY zhixing_zuhao ORDER BY zhixing_time ASC ";
//		Cursor zhixingWanbiData = localDatabaseHelper1.rawQuery(zhixing_wanbi_sql, new String[] {});
//		while (zhixingWanbiData.moveToNext()) {
//			zhixing_wanbi_zuhao += "'" + zhixingWanbiData.getString(zhixingWanbiData .getColumnIndex("zhixing_zuhao")) + "'";
//			if (!zhixingWanbiData.isLast()) {
//				zhixing_wanbi_zuhao += ",";
//			}
//		}
//		zhixingWanbiData.close();
//		zhixingWanbiData = null;
//		str1 = "SELECT * FROM yizhu_info WHERE zhuyuan_id = '" + current_application.current_patient_zhuyuan_id + "' and zuhao in (" + zhixing_wanbi_zuhao + ") "
//				+ " and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-"
//				+ current_application.linshi_yizhu_xianshi + " Hour', 'localtime'))) GROUP BY zuhao ORDER BY yizhu_id ASC ";

	@Override
	public String getSQLiteType(String yizhu_type) {
		StringBuilder sb = new StringBuilder();
		if (yizhu_type.equals("全部")){
			sb.append("SELECT * FROM yizhu_info a WHERE a.zhuyuan_id = '")
			  .append(current_application.current_patient_zhuyuan_id)
			  .append("' and wancheng_cishu > 0 and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
			  .append(current_application.linshi_yizhu_xianshi)
			  .append(" Hour', 'localtime'))) GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
			return sb.toString();
		}
		sb.append("SELECT * FROM yizhu_info a WHERE a.zhuyuan_id = '")
		  .append(current_application.current_patient_zhuyuan_id)
		  .append("' yongfa_type = '")
		  .append(yizhu_type)
		  .append("' and wancheng_cishu > 0 and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
		  .append(current_application.linshi_yizhu_xianshi)
		  .append(" Hour', 'localtime'))) GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
		
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
