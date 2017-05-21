package com.zrt.fragmentdemoone.yizhu;

import java.util.List;

import android.database.Cursor;
import android.util.Log;

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
		Log.i(">>>>", "##"+object.toString()+"="+this.getClass().getName());
	}

	@Override
	public void setYiZhuType(String yizhu_type, boolean isUpdate) {
		this.yizhu_type = yizhu_type;
		// TODO  是否刷新医嘱界面
		if (isUpdate){
			
		}
	}


	@Override
	public void getYiZhuStateRecord(YiZhuInfo yiZhuInfo) {
		// TODO Auto-generated method stub
		StringBuilder peiye_lishi_new = new StringBuilder();
		StringBuilder jiaodui_lishi_new = new StringBuilder();
		StringBuilder kaishi_lishi_new = new StringBuilder();
		StringBuilder wanbi_lishi_new = new StringBuilder();
		String pishi_value = "";
		
		StringBuilder sb_sqlite = new StringBuilder();
		sb_sqlite.append("SELECT * FROM yizhu_zhixing_history_lishi WHERE zhixing_zuhao = '")
				 .append(yiZhuInfo.getZuhao())
				 .append("' AND zhixing_state in ('已配液','已校对','开始执行','暂停执行','执行完毕') AND case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-")
				 .append(current_application.linshi_yizhu_xianshi)
				 .append(" Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end ORDER BY real_time DESC ");
//		String yizhu_zhuangtai = yizhuData.getString(yizhuData.getColumnIndex("zhixing_state"));
		Cursor lishiCursor = db.rawQuery(sb_sqlite.toString(), new String[] {});
		if (lishiCursor.getCount() > 0) {
			int temp_peiye_number = 0;
			int temp_jiaodui_number = 0;
			int temp_kaishi_number = 0;
			int temp_wanbi_number = 0;
			if (yiZhuInfo.getZhixing_state().equals(yiZhuInfo.getChushi_state()) || yiZhuInfo.getZhixing_state().equals("执行完毕") || yiZhuInfo.getZhixing_state().equals("待配液")) {
				while (lishiCursor.moveToNext()) {
					if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("已配液")&& temp_peiye_number == 0) {
//						peiye_lishi_new += lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")) + " "+ lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time"));
						peiye_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
									   .append(" ")
									   .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						temp_peiye_number++;
						continue;
					} 
					if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("已校对")&& temp_jiaodui_number == 0) {
						jiaodui_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
										 .append(" ")
										 .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						temp_jiaodui_number++;
						continue;
					} 
					if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("开始执行")&& temp_kaishi_number == 0) {
						kaishi_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
						   				.append(" ")
						   				.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						temp_kaishi_number++;
						continue;
					} 
					if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("执行完毕")&& temp_wanbi_number == 0) {
						wanbi_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
									   .append(" ")
									   .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						temp_wanbi_number++;
						if (yiZhuInfo.getYongfa_type().equals("皮试")){
							pishi_value = lishiCursor.getString(lishiCursor.getColumnIndex("beizhu"));
							pishi_value = pishi_value == null ? "" : pishi_value;
						}
						continue;
					}
				}
			} else {
				String zhixing_wanbi_time = "";
				while (lishiCursor.moveToNext()) {
					if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("执行完毕")&& temp_wanbi_number == 0) {
						wanbi_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
									   .append(" ")
									   .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						zhixing_wanbi_time = lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time"));
						temp_wanbi_number++;
						if (yiZhuInfo.getYongfa_type().equals("皮试")){
							pishi_value = lishiCursor.getString(lishiCursor.getColumnIndex("beizhu"));
							pishi_value = pishi_value == null ? "" : pishi_value;
						}
						break;
					}
				}
				StringBuilder duoci_lishi_sql = new StringBuilder();
				//根据组号，长期查询当天，临时的查设定时间
				duoci_lishi_sql.append("SELECT * FROM yizhu_zhixing_history_lishi WHERE zhixing_zuhao = '")
							   .append(yiZhuInfo.getZuhao())
							   .append("' AND zhixing_state in ('已配液','已校对','开始执行','暂停执行') AND zhixing_time < '")
							   .append(zhixing_wanbi_time)
							   .append("' AND case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-")
							   .append(current_application.linshi_yizhu_xianshi)
							   .append(" Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end ORDER BY zhixing_time DESC");
				Cursor duociLishiCursor = db.rawQuery(duoci_lishi_sql.toString(), new String[] {});
				while (duociLishiCursor.moveToNext()) {
					if (duociLishiCursor.getString(duociLishiCursor.getColumnIndex("zhixing_state")).equals("已配液")) {
						if (temp_peiye_number == 0) {
							peiye_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
										   .append(" ")
										   .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						}
						temp_peiye_number++;
						continue;
					} 
					if (duociLishiCursor.getString(duociLishiCursor.getColumnIndex("zhixing_state")).equals("已校对")) {
						if (temp_jiaodui_number == 0) {
							jiaodui_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
											 .append(" ")
											 .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						}
						temp_jiaodui_number++;
						continue;
					} 
					if (duociLishiCursor.getString(duociLishiCursor.getColumnIndex("zhixing_state")).equals("开始执行")) {
						if (temp_kaishi_number == 0) {
							kaishi_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
											.append(" ")
											.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
						}
						temp_kaishi_number++;
						continue;
					} 
				}
				duociLishiCursor.close();
				duociLishiCursor = null;
			}
		}
		lishiCursor.close();
		lishiCursor = null;
	
	}

}
