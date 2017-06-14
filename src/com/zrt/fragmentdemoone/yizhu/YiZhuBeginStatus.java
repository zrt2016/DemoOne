package com.zrt.fragmentdemoone.yizhu;

import java.util.List;
import java.util.UUID;

import com.zrt.fragmentdemoone.yizhu.tools.AlertDialogTools;
import com.zrt.fragmentdemoone.yizhu.tools.DialogExecute;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * 开始执行模块
 * @author zrt
 *
 */
public class YiZhuBeginStatus extends YiZhuStatusBasic implements DialogExecute{
	public final String dangqian_zhixing_state = "执行完毕";
	public final String zanting_zhixing = "暂停执行";
	public final String other_operation = "其他操作";
	public String yongfa_type = "全部";
	public YiZhuBeginStatus(String yizhu_type) {
		this.yongfa_type = yizhu_type;
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
	public <T> void onExecute(T object, int insert_type) {
		// TODO Auto-generated method stub
		Log.i(">>>>", "##"+object.toString()+"="+this.getClass().getName());
		YiZhuInfo yiZhuInfo = (YiZhuInfo) object;
		AlertDialogTools.contentDialogThree(context, this, yiZhuInfo, insert_type, dangqian_zhixing_state, other_operation, cancel);
	}
	
	@Override
	public void scanYiZhuExecute(YiZhuInfo yiZhuInfo, int insert_type) {
		// TODO 扫描医嘱执行
		if (null != AlertDialogTools.alertDialog && AlertDialogTools.alertDialog.isShowing()){
			yiZhuCompleteExecute(yiZhuInfo, dangqian_zhixing_state, insert_type);
			return;
		}
		AlertDialogTools.contentDialogThree(context, this, yiZhuInfo, insert_type, dangqian_zhixing_state, other_operation, cancel);
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
	
	/**
	 * 状态：其他操作
	 * @param yiZhuInfo
	 * @param insert_type
	 */
	public void yiZhuQiTaExecute(YiZhuInfo yiZhuInfo, int insert_type){
		//TODO 其他操作
		String[] itemOperation = current_application.yizhu_qita_caozuo_xuanzhe.split("\\|");
		if(null == itemOperation || itemOperation.length ==0){
			itemOperation = new String[]{ "暂停", "穿刺", "录入滴速", "药物反应", "巡视", "拔针", "接液" ,"其它"};
		}
		AlertDialogTools.otherOperationDialog(context, this, yiZhuInfo, insert_type, itemOperation); 
	}
	

	@Override
	public void executeYiZhu(YiZhuInfo yiZhuInfo, String state, int insert_type) {
		// TODO 执行完毕
//		AlertDialogTools.dismisAlertDialog();
		if (state.equals(dangqian_zhixing_state)){
			yiZhuCompleteExecute(yiZhuInfo, dangqian_zhixing_state, insert_type);
			return;
		}
		
	}

	@Override
	public void otherOperation(YiZhuInfo yiZhuInfo, String state, int insert_type) {
		// TODO 其他操作
//		AlertDialogTools.getInstance(current_application).dismisAlertDialog();
		if (state.equals(other_operation)){
			yiZhuQiTaExecute(yiZhuInfo, insert_type);
			return;
		}
	}

	@Override
	public void cancel(String state) {
		// TODO 取消
//		AlertDialogTools.getInstance(current_application).dismisAlertDialog();
		if (state.equals(cancel)){
			
			return;
		}
		if (state.equals(other_operation)){
			
			return;
		}
	}

	@Override
	public void executeOtherOperation(YiZhuInfo yiZhuInfo, String op_type, int insert_type) {
		// TODO Auto-generated method stub
		if (op_type.equals("录入滴速")){
			AlertDialogTools.otherInputDiSuDialog(context, this, yiZhuInfo, op_type, insert_type);
			return;
		}
		if (op_type.equals("药物反应")){
			 
			return;
		}
		if (op_type.equals("其它")){
			
			return;
		}
		insertOtherOpteration(yiZhuInfo, op_type, insert_type, "");
	}
	
	@Override
	public void executeInputOtherValue(YiZhuInfo yiZhuInfo, String op_type, int insert_type, String beizhu) {
		// TODO Auto-generated method stub
		insertOtherOpteration(yiZhuInfo, op_type, insert_type, beizhu);
	}
	
	@Override
	public boolean executeExistsStartYiZhu(List<YiZhuInfo> zhiXingList, int insert_type) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 其他操作：数据存储
	 * @param yiZhuInfo
	 * @param op_type 
	 * @param insert_type
	 * @param beizhu
	 */
	public void insertOtherOpteration(YiZhuInfo yiZhuInfo, String op_type, int insert_type, String beizhu){
		String history_id = UUID.randomUUID().toString();
		String other_info = mapScan.get(yiZhuInfo.getZuhao());
		int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), op_type, dangqian_cishu);
		String zhixing_state = "开始执行";
		if (op_type.contains("暂停")){
			zhixing_state = "暂停执行";
			executeDB(getUpdateBeginSQLite(yiZhuInfo, zhixing_state));
		}
		executeDB(getInsertHistorySQLite(yiZhuInfo, zhixing_state, op_type, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type, beizhu));
		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, zhixing_state, op_type, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type, beizhu));
		if("开始执行".equals(zhixing_state) && "药物反应".equals(op_type)){
			executeDB(insertShouCiYongYaoHistory(yiZhuInfo));
			executeDB("delete from yizhu_shouci_yongyao where zuhao = '"+yiZhuInfo.getZuhao()+"' ");
		}
		
	}
	
	/**
	 * 首次用药记录数据
	 * @param yiZhuInfo
	 * @return
	 */
	public String insertShouCiYongYaoHistory(YiZhuInfo yiZhuInfo){
		StringBuilder insertSQLite = new StringBuilder();
		insertSQLite.append("INSERT INTO shouciyongyao_duqu_lishi (zuhao,duqu_time,zhuyuan_id,zhixing_hushi_id,zhixing_hushi_name,type)  VALUES ('")
					.append(yiZhuInfo.getZuhao()).append("',datetime('now', 'localtime'),'")
					.append(current_application.current_patient_zhuyuan_id).append("', '")
					.append(current_application.current_user_number).append("', '")
					.append(current_application.current_user_name).append("', '")
					.append(yiZhuInfo.getYizhu_type()).append("')");
		return insertSQLite.toString();
	}

}
