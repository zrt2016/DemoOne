package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.UUID;

import com.zrt.fragmentdemoone.yizhu.tools.AlertDialogTools;
import com.zrt.fragmentdemoone.yizhu.tools.DialogExecute;

import android.database.Cursor;
import android.util.Log;

/**
 * 暂停执行模块
 * @author zrt
 *
 */
public class YiZhuPauseStatus extends YiZhuStatusBasic implements DialogExecute{
	public final String zhixing_state_completed = "执行完毕";
	public final String zhixing_state_begin = "开始执行";

	public YiZhuPauseStatus(String yizhu_type) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSQLiteType(String yizhu_type) {
		StringBuilder sb = new StringBuilder();
		if (yizhu_type.equals("全部")){
			sb.append("select * from yizhu_info a where a.zhuyuan_id='")
			  .append(this.current_application.current_patient_zhuyuan_id)
			  .append("' and a.zhixing_state = '暂停执行' and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
			  .append(current_application.linshi_yizhu_xianshi)
			  .append(" Hour', 'localtime'))) and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
			return sb.toString();
		}
		sb.append("select * from yizhu_info a where a.zhuyuan_id='")
		  .append(current_application.current_patient_zhuyuan_id)
		  .append("' and a.zhixing_state = '暂停执行' a.and yongfa_type='")
		  .append(yizhu_type)
		  .append("' and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-")
		  .append(current_application.linshi_yizhu_xianshi)
		  .append(" Hour', 'localtime'))) and wancheng_cishu < meiri_cishu GROUP BY a.zuhao ORDER BY a.yizhu_id ASC ");
		return sb.toString();
	}

	@Override
	public <T> void onExecute(T object, int insert_type) {
		// TODO Auto-generated method stub
		Log.i(">>>>", "##"+object.toString()+"="+this.getClass().getName());
	}
	
	@Override
	public void scanYiZhuExecute(YiZhuInfo yiZhuInfo, int insert_type) {
		// TODO 扫描医嘱执行
		if (null != AlertDialogTools.alertDialog && AlertDialogTools.alertDialog.isShowing()){
			executeYiZhu(yiZhuInfo, zhixing_state_completed, insert_type);
			return;
		}
		AlertDialogTools.contentDialogThree(context, this, yiZhuInfo, insert_type, zhixing_state_completed, zhixing_state_begin, cancel);
		
	}

	@Override
	public void setYiZhuType(String yizhu_type) {
		this.yongfa_type = yizhu_type;
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

	@Override
	public void executeYiZhu(YiZhuInfo yiZhuInfo, String state, int insert_type) {
		// TODO Auto-generated method stub
		if (state.equals(zhixing_state_begin)){
			yiZhuBeginExecute(yiZhuInfo, zhixing_state_begin, insert_type);
			return;
		}
		if (state.equals(zhixing_state_completed)){
			yiZhuCompleteExecute(yiZhuInfo, zhixing_state_completed, insert_type);
		}
		
	}

	@Override
	public void otherOperation(YiZhuInfo yiZhuInfo, String state, int insert_type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(String state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeOtherOperation(YiZhuInfo yiZhuInfo, String op_type, int insert_type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeInputOtherValue(YiZhuInfo yiZhuInfo, String op_type, int insert_type, String beizhu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean executeExistsStartYiZhu(List<YiZhuInfo> zhiXingList, int insert_type) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	/**
//	 * 开始执行
//	 * @param yiZhuInfo 
//	 * @param insert_type
//	 */
//	private void yiZhuBeginExecute(YiZhuInfo yiZhuInfo, int insert_type) {
//		// TODO Auto-generated method stub
//		String history_id = UUID.randomUUID().toString();
//		int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
//		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), zhixing_state_begin, dangqian_cishu);
//		String other_info = mapScan.get(yiZhuInfo.getZuhao());
//		executeDB(getUpdateBeginSQLite(yiZhuInfo, zhixing_state_begin));
//		executeDB(getInsertHistorySQLite(yiZhuInfo, zhixing_state_begin, zhixing_state_begin, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type, ""));
//		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, zhixing_state_begin, zhixing_state_begin, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type, ""));
//	}
	
//	/**
//	 * 执行完毕
//	 * @param yiZhuInfo
//	 * @param insert_type
//	 */
//	private void yiZhuCompleteExecute(YiZhuInfo yiZhuInfo, int insert_type) {
//		String other_info = "";
//		int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
//		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), zhixing_state_completed, dangqian_cishu);
//		String history_id = UUID.randomUUID().toString();
//		executeDB(getUpdateCompletedSQLite(yiZhuInfo));
//		executeDB(getInsertHistorySQLite(yiZhuInfo, zhixing_state_completed, zhixing_state_completed, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type, ""));
//		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, zhixing_state_completed, zhixing_state_completed, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type, ""));
//	}
	
}
