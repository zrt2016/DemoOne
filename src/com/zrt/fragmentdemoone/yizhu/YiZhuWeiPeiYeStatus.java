package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Log;

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
		Log.i(">>>>", "##"+object.toString()+"="+this.getClass().getName());
		
		
	}

	@Override
	public void setYiZhuType(String yizhu_type, boolean isUpdate) {
		this.yizhu_type = yizhu_type;
		// TODO  是否刷新医嘱界面
		if (isUpdate){
//			getSQLiteType(yizhu_type);
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
//		String history_id = UUID.randomUUID().toString();
//		String str2 = "UPDATE yizhu_info SET zhixing_state = '开始执行',state = 'update',operate_time=datetime('now', 'localtime'),kuatian = '否' WHERE zuhao = '" + yiZhuInfo.getZuhao() + "' ;";
//        db.execSQL(str2);
//        String str3 = "INSERT INTO yizhu_zhixing_history (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('"+history_id+"','" + current_application.current_patient_zhuyuan_id + "','" + op_type + "', '" + op_type + "','" + current_application.current_user_number + "','" + current_application.current_user_name + "','" + str1 + "',datetime('now', 'localtime'),'" + yizhu_type + "', datetime('now', 'localtime'),'"+op_type+"','"+hedui_cishu+"','"+dangqian_cishu+"',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','"+other_info+"','"+insert_type+"');";
//        db.execSQL(str3);
//        String str4 = "INSERT INTO yizhu_zhixing_history_lishi (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('"+history_id+"','" + current_application.current_patient_zhuyuan_id + "','" + op_type + "', '" + op_type + "','" + current_application.current_user_number + "','" + current_application.current_user_name + "','" + str1 + "',datetime('now', 'localtime'),'" + yizhu_type + "', datetime('now', 'localtime'),'"+op_type+"','"+hedui_cishu+"','"+dangqian_cishu+"',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','"+other_info+"','"+insert_type+"');";
//        db.execSQL(str4);

	}

}
