package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Log;

/**
 * 开始执行模块
 * @author zrt
 *
 */
public class YiZhuBeginStatus extends YiZhuStatusBasic{
	public final String dangqian_zhixing_state = "执行完毕";
	public final String zanting_zhixing = "暂停执行";

	public YiZhuBeginStatus(String yizhu_type) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSQLiteType(String yizhu_type) {
		StringBuilder sb = new StringBuilder();
		if (yizhu_type.equals("全部")){
			sb.append("select * from yizhu_info a where a.zhuyuan_id='")
			  .append(this.current_application.current_patient_zhuyuan_id)
			  .append("' and a.zhixing_state = '开始执行' and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
			  .append(current_application.linshi_yizhu_xianshi)
			  .append(" Hour', 'localtime'))) and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
			return sb.toString();
		}
		sb.append("select * from yizhu_info a where a.zhuyuan_id='")
		  .append(current_application.current_patient_zhuyuan_id)
		  .append("' and a.zhixing_state = '开始执行' a.and yongfa_type='")
		  .append(yizhu_type)
		  .append("' and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
		  .append(current_application.linshi_yizhu_xianshi)
		  .append(" Hour', 'localtime'))) and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
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
		
		StringBuilder sb_sqlite = new StringBuilder();
		sb_sqlite.append("SELECT * FROM yizhu_zhixing_history_lishi WHERE zhixing_zuhao = '")
				 .append(yiZhuInfo.getZuhao())
				 .append("' AND zhixing_state in ('已配液','已校对','开始执行','暂停执行') AND case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-")
				 .append(current_application.linshi_yizhu_xianshi)
				 .append(" Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end ORDER BY real_time DESC");
		
		Cursor lishiCursor = db.rawQuery(sb_sqlite.toString(), new String[] {});
		int temp_peiye_number = 0;
		int temp_jiaodui_number = 0;
		int temp_kaishi_number = 0;
		while (lishiCursor.moveToNext()) {
			if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("已配液")&& temp_peiye_number == 0) {
				peiye_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
							   .append(" ")
							   .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
				temp_peiye_number++;
				continue;
			}
			if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("已校对")&& temp_jiaodui_number == 0) {
				jiaodui_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")) )
								 .append(" ")
								 .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
				temp_jiaodui_number++;
				continue;
			}
			if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("开始执行")&& temp_kaishi_number == 0) {
				kaishi_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
				 				.append(" ")
				 				.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));;
				temp_kaishi_number++;
				continue;
			}
		}
		releaseCursor(lishiCursor);
		yiZhuInfo.setPeiye_history(peiye_lishi_new.toString());
		yiZhuInfo.setJiaodui_history(jiaodui_lishi_new.toString());
		yiZhuInfo.setKaishi_history(kaishi_lishi_new.toString());
		
	}
	
	/**
	 * 执行完毕
	 * @param yiZhuInfo
	 * @param insert_type
	 */
	private void yiZhuCompleteExecute(YiZhuInfo yiZhuInfo, int insert_type) {
		int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), dangqian_zhixing_state, dangqian_cishu);
		String history_id = UUID.randomUUID().toString();
		executeDB(getUpdateCompletedSQLite(yiZhuInfo));
		executeDB(getInsertHistorySQLite(yiZhuInfo, dangqian_zhixing_state, history_id, dangqian_cishu, hedui_cishu, "", insert_type));
		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, dangqian_zhixing_state, history_id, dangqian_cishu, hedui_cishu, "", insert_type));
	}
	
//	public String getUpdateCompletedSQLite(YiZhuInfo yiZhuInfo){
//		StringBuilder updateSQLite = new StringBuilder();
//		updateSQLite.append("UPDATE yizhu_info SET zhixing_state = case when chushi_state = '待配液' or chushi_state = '已校对' then chushi_state else '待配液' end ,state = 'update',wancheng_cishu = wancheng_cishu+1,operate_time=datetime('now', 'localtime') ,kuatian = '否' WHERE zuhao = '")
//					.append(yiZhuInfo.getZuhao())
//					.append("' ");
//		return updateSQLite.toString();
//	}
	
//	public String getInsertHistorySQLite(YiZhuInfo yiZhuInfo, String dangqian_zhixing_state,String history_id, int dangqian_cishu, int hedui_cishu, int insert_type){
//		StringBuilder insertSQLite = new StringBuilder();
//        insertSQLite.append("INSERT INTO yizhu_zhixing_history (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,changqi_yizhu_id) VALUES ('")
//        			.append(history_id).append("','")
//        			.append(current_application.current_patient_zhuyuan_id).append("','")
//        			.append(dangqian_zhixing_state).append("','")
//        			.append(dangqian_zhixing_state).append("','")
//        			.append(current_application.current_user_number).append("','")
//        			.append(current_application.current_user_name).append("','")
//        			.append(yiZhuInfo.getZuhao()).append("',datetime('now', 'localtime'),'")
//        			.append(yiZhuInfo.getYizhu_type()).append("', datetime('now', 'localtime'),'")
//        			.append(dangqian_zhixing_state).append("','")
//        			.append(hedui_cishu).append("','")
//        			.append(dangqian_cishu).append("',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','")
//        			.append(insert_type).append("' ");
//		return insertSQLite.toString();
//	}
//	
//	public String getInsertHistoryLiShiSQLite(YiZhuInfo yiZhuInfo, String dangqian_zhixing_state, String history_id, int dangqian_cishu, int hedui_cishu, int insert_type){
//		StringBuilder insertSQLite = new StringBuilder();
//		insertSQLite.append("INSERT INTO yizhu_zhixing_history_lishi (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,changqi_yizhu_id) VALUES ('")
//					.append(history_id).append("','")
//					.append(current_application.current_patient_zhuyuan_id).append("','")
//					.append(dangqian_zhixing_state).append("','")
//					.append(dangqian_zhixing_state).append("','")
//					.append(current_application.current_user_number).append("','")
//					.append(current_application.current_user_name).append("','")
//					.append(yiZhuInfo.getZuhao()).append("',datetime('now', 'localtime'),'")
//					.append(yiZhuInfo.getYizhu_type()).append("', datetime('now', 'localtime'),'")
//					.append(dangqian_zhixing_state).append("','")
//					.append(hedui_cishu).append("','")
//					.append(dangqian_cishu).append("',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','")
//					.append(insert_type).append("' ");
//		return insertSQLite.toString();
//	}
	
	/**
	 * 其他操作
	 * @param yiZhuInfo
	 * @param insert_type
	 */
	public void yiZhuQiTaExecute(YiZhuInfo yiZhuInfo, int insert_type){
		
	}


}
