package com.zrt.fragmentdemoone.yizhu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.AlertDialog;
import android.content.Context;
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
	
	public final String dangqian_zhixing_state = "开始执行";
	public String yongfa_type = "全部";
	public List<YiZhuInfo> zhiXingList = new ArrayList<YiZhuInfo>();
	public Context context;

	public YiZhuWeiZhiXingStatus(String yizhu_type,Context context) {
		this.yongfa_type = yizhu_type;
		this.context = context;
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
		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), dangqian_zhixing_state, dangqian_cishu);
		String other_info = mapScan.get(yiZhuInfo.getZuhao());
		executeDB(getUpdateBeginSQLite(yiZhuInfo));
		executeDB(getInsertHistorySQLite(yiZhuInfo, dangqian_zhixing_state, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type));
		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, dangqian_zhixing_state, history_id, dangqian_cishu, hedui_cishu, other_info, insert_type));
	}
	
	/**
	 * 查询是否存在正在执行的输液医嘱
	 * @param yiZhuInfo
	 * @return
	 */
	public boolean checkDuoDaiShuYeQuery(YiZhuInfo yiZhuInfo){
		if (!current_application.yizhu_duodai_shuye_tixing){
			return false;
		}
		
		zhiXingList.clear();
		StringBuilder zuHaoSQLite = new StringBuilder();
//		strOther = "SELECT * FROM yizhu_info WHERE zhuyuan_id = '" + this.current_application.current_patient_zhuyuan_id + 
//				"' and zuhao != '" + zuhao + "' and (zhixing_state = '开始执行' or zhixing_state = '暂停执行') and yongfa_type = '输液' GROUP BY zuhao ";
		zuHaoSQLite.append("SELECT * FROM yizhu_info WHERE zhuyuan_id = '")
			  .append(current_application.current_patient_zhuyuan_id)
			  .append("' and zuhao != '").append(yiZhuInfo.getZuhao())
			  .append("' and zhixing_state in ('开始执行','暂停执行') and yongfa_type = '输液' ");
		//TODO 配置
		zuHaoSQLite.append(" GROUP BY zuhao ");
		Cursor zuHaoCursor = db.rawQuery(zuHaoSQLite.toString(), new String[0]);
		if (zuHaoCursor.getCount() == 0){
			releaseCursor(zuHaoCursor);
			return false;
		}
		while (zuHaoCursor.moveToNext()) {
			YiZhuInfo yizhu = new YiZhuInfo();
			getYiZhuData(yizhu, zuHaoCursor);
			yizhu.setOperation_state(false);
			StringBuilder contentSQLite = new StringBuilder();
			contentSQLite.append("select * from yizhu_info where zuhao = '")
						 .append(yizhu.getZuhao())
						 .append("' order by yizhu_id asc");
			Cursor contentCursor = db.rawQuery(contentSQLite.toString(), new String[0]);
			StringBuilder content = new StringBuilder();
			
			while (contentCursor.moveToNext()){
				String contentItem = contentCursor.getString(contentCursor.getColumnIndex("content"));
				if (contentCursor.getCount() <= 1){
					content.append(contentItem);
					continue;
				}
				if (contentCursor.isFirst()){
					content.append("┏").append(contentItem).append("\n");
					continue;
				}
				if ( contentCursor.isLast()){
					content.append("┗").append(contentItem);
					continue;
				}
				content.append("┃").append(contentItem).append("\n");
			}
			releaseCursor(contentCursor);
			yizhu.setContent(content.toString());
			zhiXingList.add(yizhu);
		}
		releaseCursor(zuHaoCursor);
		duoDaiShuYeExecute(yiZhuInfo);
		return true;
	}
	
	/**
	 * 选择需要结束的正在执行的输液医嘱
	 * @param yiZhuInfo
	 */
	private void duoDaiShuYeExecute(YiZhuInfo yiZhuInfo) {
		// TODO Auto-generated method stub
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.context);
//		dialog.setMultiChoiceItems(items, checkedItems, listener)
		dialog.show();
	}
	
	/**
	 * 结束已选择的正在执行的医嘱
	 * @param insert_type
	 */
	private void yiZhuDuoDaiFinish(int insert_type){
		for (int x=0; x<zhiXingList.size(); x++){
			YiZhuInfo yiZhuInfo = zhiXingList.get(x);
			if (yiZhuInfo.isOperation_state()){
				int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
				int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), dangqian_zhixing_state, dangqian_cishu);
				String history_id = UUID.randomUUID().toString();
				executeDB(getUpdateCompletedSQLite(yiZhuInfo));
				executeDB(getInsertHistorySQLite(yiZhuInfo, dangqian_zhixing_state, history_id, dangqian_cishu, hedui_cishu, "", insert_type));
				executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, dangqian_zhixing_state, history_id, dangqian_cishu, hedui_cishu, "", insert_type));
			}
		}
	}
	
	
	public void yiZhuItemExecute(YiZhuInfo yiZhuInfo, int insert_type){
		int dangqian_cishu = getDangqianWanchengCishu(yiZhuInfo.getWancheng_cishu(), yiZhuInfo.getMeiri_cishu(), "");
		int hedui_cishu = getCalcHeduiCishu(yiZhuInfo.getZuhao(), dangqian_zhixing_state, dangqian_cishu);
		String other_info = mapScan.get(yiZhuInfo.getZuhao());
		
		String history_id_one = UUID.randomUUID().toString();
		executeDB(getUpdateBeginSQLite(yiZhuInfo));
		executeDB(getInsertHistorySQLite(yiZhuInfo, dangqian_zhixing_state, history_id_one, dangqian_cishu, hedui_cishu, other_info, insert_type));
		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, dangqian_zhixing_state, history_id_one, dangqian_cishu, hedui_cishu, other_info, insert_type));
		String history_id_two = UUID.randomUUID().toString();
		executeDB(getUpdateCompletedSQLite(yiZhuInfo));
		executeDB(getInsertHistorySQLite(yiZhuInfo, dangqian_zhixing_state, history_id_two, dangqian_cishu, hedui_cishu, "", insert_type));
		executeDB(getInsertHistoryLiShiSQLite(yiZhuInfo, dangqian_zhixing_state, history_id_two, dangqian_cishu, hedui_cishu, "", insert_type));
	}

}
