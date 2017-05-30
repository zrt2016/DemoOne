package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Log;

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
		
		StringBuilder sb_sqlite = new StringBuilder();
		sb_sqlite.append("SELECT * FROM yizhu_zhixing_history_lishi WHERE zhixing_zuhao = '")
		  .append(yiZhuInfo.getZuhao())
		  .append("' AND zhixing_state in ('已配液','已校对') AND case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-")
		  .append(current_application.linshi_yizhu_xianshi)
		  .append(" Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end ORDER BY real_time DESC");
		Cursor lishiCursor = db.rawQuery(sb_sqlite.toString(), new String[] {});
		if (lishiCursor.getCount() > 0) {
			int temp_peiye_number = 0;
			int temp_jiaodui_number = 0;
			while (lishiCursor.moveToNext()) {
				if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("已配液")&& temp_peiye_number == 0) {
					peiye_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
								   .append(" ")
								   .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));
					temp_peiye_number++;
					continue;
				} 
				if (lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_state")).equals("已校对")&& temp_jiaodui_number == 0) {
					jiaodui_lishi_new.append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_hushi_name")))
									 .append(" ")
									 .append(lishiCursor.getString(lishiCursor.getColumnIndex("zhixing_time")));;
					temp_jiaodui_number++;
					continue;
				}
			}
		}
		releaseCursor(lishiCursor);
		yiZhuInfo.setPeiye_history(peiye_lishi_new.toString());
		yiZhuInfo.setJiaodui_history(jiaodui_lishi_new.toString());
	}
	
	/**
	 * 开始执行
	 * @param yiZhuInfo 
	 * @param insert_type
	 */
	private void yiZhuBeginExecute(YiZhuInfo yiZhuInfo, int insert_type) {
		// TODO Auto-generated method stub
		String history_id = UUID.randomUUID().toString();
		int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
		
		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), "开始执行", dangqian_cishu);
		StringBuilder update_yizhu = new StringBuilder();
		update_yizhu.append("UPDATE yizhu_info SET zhixing_state = '开始执行',state = 'update',operate_time=datetime('now', 'localtime'),kuatian = '否' WHERE zuhao = '")
					.append(yiZhuInfo.getZuhao())
					.append("' ");
        db.execSQL(update_yizhu.toString());
//        StringBuilder insert_yizhu = new StringBuilder();
//        insert_yizhu.append("INSERT INTO yizhu_zhixing_history (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('")
//        			.append(history_id).append("','")
//        			.append(current_application.current_patient_zhuyuan_id).append("','开始执行','开始执行','")
//        			.append("current_application.current_user_number").append("','")
//        			.append(current_application.current_user_name).append("','")
//        			.append(yiZhuInfo.getZuhao()).append("',datetime('now', 'localtime'),'")
//        			.append(yiZhuInfo.getYizhu_type()).append("','")
//        			.append(hedui_cishu).append("','")
//        			.append(dangqian_cishu).append("',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','")
//        			.append("+other_info+").append("','")//TODO 未完成
//        			.append(insert_type).append("' ");
//        db.execSQL(insert_yizhu.toString());
//        StringBuilder insert_yizhu_history = new StringBuilder();
//        insert_yizhu_history.append("INSERT INTO yizhu_zhixing_history_lishi (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('")
//        					.append(history_id).append("','")
//        					.append(current_application.current_patient_zhuyuan_id).append("','开始执行','开始执行','")
//                			.append("current_application.current_user_number").append("','")
//                			.append(current_application.current_user_name).append("','");
                			
//        String str3 = "INSERT INTO yizhu_zhixing_history ("
//        		+ "history_id,"
//        		+ "zhuyuan_id,zhixing_state,zhixing_type,"
//        		+ "zhixing_hushi_id,"
//        		+ "zhixing_hushi_name,"
//        		+ "zhixing_zuhao,zhixing_time,type,"
//        		+ "real_time,op_type,"
//        		+ "hedui_cishu,"
//        		+ "dangqian_cishu,yizhu_time,yizhu_shuxing"
//        		+ ",other_info,"
//        		+ "changqi_yizhu_id) VALUES ('"
//        		+history_id+"','" 
//        		+ current_application.current_patient_zhuyuan_id + "','" + op_type + "', '" + op_type + "','"
//        		+ current_application.current_user_number + "','" 
//        		+ current_application.current_user_name + "','" 
//        		+ str1 + "',datetime('now', 'localtime'),'" + yizhu_type 
//        		+ "', datetime('now', 'localtime'),'"+op_type+"','"+hedui_cishu+"','"+dangqian_cishu+"',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','"+other_info+"','"+insert_type+"');";
//        db.execSQL(str3);
//        String str4 = "INSERT INTO yizhu_zhixing_history_lishi (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('"+history_id+"','" + current_application.current_patient_zhuyuan_id + "','" + op_type + "', '" + op_type + "','" + current_application.current_user_number + "','" + current_application.current_user_name + "','" + str1 + "',datetime('now', 'localtime'),'" + yizhu_type + "', datetime('now', 'localtime'),'"+op_type+"','"+hedui_cishu+"','"+dangqian_cishu+"',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','"+other_info+"','"+insert_type+"');";
//        db.execSQL(str4);    			
//        String str3 = ""+history_id+"','" + current_application.current_patient_zhuyuan_id + "','开始执行', '开始执行','" + current_application.current_user_number + "','" + current_application.current_user_name + "','"
//        + str1 + "',datetime('now', 'localtime'),'" + yizhu_type + "', datetime('now', 'localtime'),'"
//        		+op_type+"','"+hedui_cishu+"','"+dangqian_cishu+"',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','"+other_info+"','"+insert_type+"');";
//        db.execSQL(str3);
//        String str4 = ""+history_id+"','" + current_application.current_patient_zhuyuan_id + "','" + op_type + "', '" + op_type + "','" + current_application.current_user_number + "','" + current_application.current_user_name + "','"
//        		+ str1 + "',datetime('now', 'localtime'),'" + yizhu_type + "', datetime('now', 'localtime'),'"+op_type+"','"+hedui_cishu+"','"+dangqian_cishu+"',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','"+other_info+"','"+insert_type+"');";
//        db.execSQL(str4);

	}
	

}
