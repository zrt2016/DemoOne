package com.zrt.fragmentdemoone.yizhu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zrt.fragmentdemoone.GlobalInfoApplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class YiZhuStatusBasic {
	public String yizhu_type = "全部";
	
	public SQLiteDatabase db;
	
	public GlobalInfoApplication current_application;
	/** 护士扫描双签名：每一次进入医嘱执行界面，都需要扫描另一个护士的胸牌，进行签名后才能执行医嘱 */
	public Map<String,String> mapScan = new HashMap<String,String>();
	
	/**
	 * 
	 * @param yizhu_type 医嘱的类型
	 * @return 返回查询sqlite语句
	 */
	public abstract String getSQLiteType(String yizhu_type);
	
	/**
	 * 医嘱执行操作
	 * @param object
	 */
	public abstract <T> void onExecute(T object);
	
	/**
	 * 2017-05-15
	 * @param yizhu_type 医嘱类型
	 * @param isUpdate 是否刷新医嘱
	 */
	public abstract void setYiZhuType(String yizhu_type, boolean isUpdate);
	
	public void setCurrent_application(GlobalInfoApplication current_application) {
		this.current_application = current_application;
	}
	
	
	/**
	 * 根据组号和状态获取到当前医嘱的执行记录
	 * @return 
	 */
	public abstract void getYiZhuStateRecord(YiZhuInfo yiZhuInfo);
	
	public List<YiZhuInfo> getData(String yizhu_sqlite){
		List<YiZhuInfo> list = new ArrayList<>();
		Cursor zuhaoCursor = null;
		try {
			zuhaoCursor =  db.rawQuery(yizhu_sqlite, new String[0]);
			while (zuhaoCursor.moveToNext()) {
				YiZhuInfo yiZhuInfo = new YiZhuInfo();
//				String zuhao = zuhaoCursor.getString(zuhaoCursor.getColumnIndex("zuhao"));
//				yiZhuInfo.setZuhao(zuhao);
				//获取医嘱数据
				getYiZhuData(yiZhuInfo, zuhaoCursor);
				//获取医嘱内容
				yiZhuInfo.setYizhu_content_info(getYiZhuContent(yiZhuInfo.getZuhao()));
				//获取操作历史 
				getYiZhuStateRecord(yiZhuInfo);
				//获取配前核对历史
				if (current_application.yizhu_peiye_hedui_type.contains(yiZhuInfo.getYongfa_type())){
					yiZhuInfo.setYizhu_hedui_history(getPeiQianHeDuiHistory(yiZhuInfo.getZuhao()));
				}
				//查询该医嘱的所有操作历史
				yiZhuInfo.setYizhu_qita_history(getZhiXingDetailed(yiZhuInfo.getZuhao()));
				
				list.add(yiZhuInfo);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			releaseCursor(zuhaoCursor);
		}
		return list;
	}
	

	/**
	 * 获取值
	 * @param yiZhuInfo
	 * @param zuhaoCursor
	 */
	public void getYiZhuData(YiZhuInfo yiZhuInfo, Cursor zuhaoCursor) {
		// TODO Auto-generated method stub
//		zuhaoCursor.getString(zuhaoCursor.getColumnIndex(""))
		yiZhuInfo.setYizhu_id(zuhaoCursor.getInt(zuhaoCursor.getColumnIndex("yizhu_id")));
		yiZhuInfo.setZhuyuan_id(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("zhuyuan_id")));
		yiZhuInfo.setYizhu_type(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yizhu_type")));
		yiZhuInfo.setStart_time(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("start_time")));
		yiZhuInfo.setStop_time(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("stop_time")));
		yiZhuInfo.setYongfa(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yongfa")));
		yiZhuInfo.setYongliang(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yongliang")));
		yiZhuInfo.setShiyong_danwei(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("shiyong_danwei")));
		yiZhuInfo.setPinlv(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("pinlv")));
		yiZhuInfo.setZuhao(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("zuhao")));
		yiZhuInfo.setZhixing_state(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("zhixing_state")));
		yiZhuInfo.setMeiri_cishu(zuhaoCursor.getInt(zuhaoCursor.getColumnIndex("meiri_cishu")));
		yiZhuInfo.setWancheng_cishu(zuhaoCursor.getInt(zuhaoCursor.getColumnIndex("wancheng_cishu")));
		yiZhuInfo.setOperate_time(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("operate_time")));
		yiZhuInfo.setYongfa_type(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yongfa_type")));
		yiZhuInfo.setState(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("state")));
		yiZhuInfo.setBeizhu(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("beizhu")));
		yiZhuInfo.setKuatian(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("kuatian")));
		yiZhuInfo.setXiangying_state(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("xiangying_state")));
		yiZhuInfo.setZhuangtai(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("zhuangtai")));
		yiZhuInfo.setYizhu_time(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yizhu_time")));
		yiZhuInfo.setChushi_state(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("chushi_state")));
		yiZhuInfo.setShouci_yongyao(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("shouci_yongyao")));
		yiZhuInfo.setYongyao_time(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yongyao_time")));
		yiZhuInfo.setYichang_lock(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("yichang_lock")));
		yiZhuInfo.setUnwork_time(zuhaoCursor.getString(zuhaoCursor.getColumnIndex("unwork_time")));
		
	}

	/**
	 * 根据组号获取到当前医嘱的详细content
	 * @param yiZhuInfo
	 * @return 
	 */
	private List<YiZhuInfo.ContentInfo> getYiZhuContent(String zuhao) {
		List<YiZhuInfo.ContentInfo> list = new ArrayList<>();
		StringBuilder contentSQLite = new StringBuilder();
		contentSQLite.append("select * from yizhu_info where zuhao = '")
					 .append(zuhao)
					 .append("' order by yizhu_id asc");
//		String contentSQLite = "select * from yizhu_info where zuhao = '" + zuhao + "' order by yizhu_id asc";
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(contentSQLite.toString(), new String[0]);
			int count = cursor.getCount();
			while (cursor.moveToNext()) {
				YiZhuInfo.ContentInfo contentInfo = new YiZhuInfo().new ContentInfo();
				String content = cursor.getString(cursor.getColumnIndex("content"));
				String yongliang = cursor.getString(cursor.getColumnIndex("yongliang"));
				String shiyong_danwei = cursor.getString(cursor.getColumnIndex("shiyong_danwei"));
				contentInfo.setYongliang(yongliang);
				contentInfo.setShiyong_danwei(shiyong_danwei);
				if (count <= 1){
					contentInfo.setContent(content);
					list.add(contentInfo);
					continue;
				}
				if (cursor.isFirst()){
					contentInfo.setContent("┏"+content);
					list.add(contentInfo);
					continue;
				}
				if ( cursor.isLast()){
					contentInfo.setContent("┗"+content);
					list.add(contentInfo);
					continue;
				}
				contentInfo.setContent("┃"+content);
				list.add(contentInfo);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			releaseCursor(cursor);
		}
		return list;
	}
	
	/**
	 * 获取配前核对操作历史
	 * @param yiZhuInfo
	 */
	private List<YiZhuInfo.OperationHistoryInfo> getPeiQianHeDuiHistory(String zuhao) {
		// TODO Auto-generated method stub
		StringBuilder peiqian_sqlite = new StringBuilder();
		peiqian_sqlite.append("SELECT * FROM yizhu_zhixing_history_lishi WHERE zhixing_zuhao = '")
		  .append(zuhao)
		  .append("' and op_type = '配前核对' and case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-")
		  .append(current_application.linshi_yizhu_xianshi)
		  .append(" Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end ORDER BY hedui_cishu ASC ");
		Cursor pqhdCursor = db.rawQuery(peiqian_sqlite.toString(), new String[0]);
		List<YiZhuInfo.OperationHistoryInfo> localHeduiLishi = new ArrayList<YiZhuInfo.OperationHistoryInfo>();
		if (pqhdCursor.getCount() > 0) {
			while (pqhdCursor.moveToNext()) {
//		    	HashMap<String, String> localMap = new HashMap<String,String>();
				YiZhuInfo.OperationHistoryInfo heDuiInfo = new YiZhuInfo().new OperationHistoryInfo();
				heDuiInfo.setZhixing_hushi_name(pqhdCursor.getString(pqhdCursor.getColumnIndex("zhixing_hushi_name")));
				heDuiInfo.setZhixing_time(pqhdCursor.getString(pqhdCursor.getColumnIndex("zhixing_time")));
				heDuiInfo.setHedui_cishu(pqhdCursor.getString(pqhdCursor.getColumnIndex("hedui_cishu")));
				heDuiInfo.setOp_type(pqhdCursor.getString(pqhdCursor.getColumnIndex("op_type")));
				heDuiInfo.setDangqian_cishu(pqhdCursor.getString(pqhdCursor.getColumnIndex("dangqian_cishu")));
		  	  	localHeduiLishi.add(heDuiInfo);
		    }
		}
		releaseCursor(pqhdCursor);
		return localHeduiLishi;
	}
	
	/**
	 * 查询该医嘱的所有操作历史
	 * 显示在Dialog里面
	 * @param yiZhuInfo
	 */
	private List<YiZhuInfo.OperationHistoryInfo> getZhiXingDetailed(String zuhao) {
		// 所有执行明细数据列表
		StringBuilder Operation_sqlite = new StringBuilder();
		Operation_sqlite.append("SELECT * FROM yizhu_zhixing_history_lishi WHERE zhixing_zuhao = '")
						.append(zuhao)
						.append("' and case when type = '临时' then zhixing_time >= strftime('%Y-%m-%d %H:%M:%S','now', '-")
						.append(current_application.linshi_yizhu_xianshi)
						.append(" Hour', 'localtime') else yizhu_time = strftime('%Y-%m-%d', 'now', 'localtime') end ORDER BY real_time, hedui_cishu ASC ");
	    Cursor mxCursor = db.rawQuery(Operation_sqlite.toString(), new String[0]);
	    List<YiZhuInfo.OperationHistoryInfo> localQitaLishi = new ArrayList<YiZhuInfo.OperationHistoryInfo>();
	    if (mxCursor.getCount() > 0) {
	    	while (mxCursor.moveToNext()) {
	    		YiZhuInfo.OperationHistoryInfo heDuiInfo = new YiZhuInfo().new OperationHistoryInfo();
				heDuiInfo.setZhixing_hushi_name(mxCursor.getString(mxCursor.getColumnIndex("zhixing_hushi_name")));
				heDuiInfo.setZhixing_time(mxCursor.getString(mxCursor.getColumnIndex("zhixing_time")));
				heDuiInfo.setHedui_cishu(mxCursor.getString(mxCursor.getColumnIndex("hedui_cishu")));
				heDuiInfo.setOp_type(mxCursor.getString(mxCursor.getColumnIndex("op_type")));
				heDuiInfo.setDangqian_cishu(mxCursor.getString(mxCursor.getColumnIndex("dangqian_cishu")));
				heDuiInfo.setBeizhu(mxCursor.getString(mxCursor.getColumnIndex("beizhu")));
	      	  	localQitaLishi.add(heDuiInfo);
	        }
	    }
	    releaseCursor(mxCursor);
	    return localQitaLishi;
    }
	
	/**
	 * 开始执行：修改yizhu_info表数据
	 * @param yiZhuInfo
	 * @return
	 */
	public String getUpdateBeginSQLite(YiZhuInfo yiZhuInfo){
		StringBuilder updateSQLite = new StringBuilder();
		updateSQLite.append("UPDATE yizhu_info SET zhixing_state = '开始执行',state = 'update',operate_time=datetime('now', 'localtime'),kuatian = '否' WHERE zuhao = '")
					.append(yiZhuInfo.getZuhao())
					.append("' ");
		return updateSQLite.toString();
	}
	
	/**
	 * 执行完毕：修改yizhu_info表数据
	 * @param yiZhuInfo
	 * @return
	 */
	public String getUpdateCompletedSQLite(YiZhuInfo yiZhuInfo){
		StringBuilder updateSQLite = new StringBuilder();
		updateSQLite.append("UPDATE yizhu_info SET zhixing_state = case when chushi_state = '待配液' or chushi_state = '已校对' then chushi_state else '待配液' end ,state = 'update',wancheng_cishu = wancheng_cishu+1,operate_time=datetime('now', 'localtime') ,kuatian = '否' WHERE zuhao = '")
					.append(yiZhuInfo.getZuhao())
					.append("' ");
		return updateSQLite.toString();
	}
	
	/**
	 * 医嘱执行：插入临时数据
	 * @param yiZhuInfo
	 * @param dangqian_zhixing_state 
	 * @param history_id
	 * @param dangqian_cishu
	 * @param hedui_cishu
	 * @param other_info
	 * @param insert_type
	 * @return
	 */
	public String getInsertHistorySQLite(YiZhuInfo yiZhuInfo, String dangqian_zhixing_state,String history_id, int dangqian_cishu, int hedui_cishu ,String other_info, int insert_type){
		StringBuilder insertSQLite = new StringBuilder();
        insertSQLite.append("INSERT INTO yizhu_zhixing_history (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('")
        			.append(history_id).append("','")
        			.append(current_application.current_patient_zhuyuan_id).append("','")
        			.append(dangqian_zhixing_state).append("','")
        			.append(dangqian_zhixing_state).append("','")
        			.append(current_application.current_user_number).append("','")
        			.append(current_application.current_user_name).append("','")
        			.append(yiZhuInfo.getZuhao()).append("',datetime('now', 'localtime'),'")
        			.append(yiZhuInfo.getYizhu_type()).append("', datetime('now', 'localtime'),'")
        			.append(dangqian_zhixing_state).append("','")
        			.append(hedui_cishu).append("','")
        			.append(dangqian_cishu).append("',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','")
        			.append(other_info).append("','")
        			.append(insert_type).append("' ");
		return insertSQLite.toString();
	}
	
	/**
	 * 医嘱执行：插入最终数据
	 * @param yiZhuInfo
	 * @param dangqian_zhixing_state
	 * @param history_id
	 * @param dangqian_cishu
	 * @param hedui_cishu
	 * @param other_info
	 * @param insert_type
	 * @return
	 */
	public String getInsertHistoryLiShiSQLite(YiZhuInfo yiZhuInfo, String dangqian_zhixing_state, String history_id, int dangqian_cishu, int hedui_cishu, String other_info, int insert_type){
		StringBuilder insertSQLite = new StringBuilder();
		insertSQLite.append("INSERT INTO yizhu_zhixing_history_lishi (history_id,zhuyuan_id,zhixing_state,zhixing_type,zhixing_hushi_id,zhixing_hushi_name,zhixing_zuhao,zhixing_time,type,real_time,op_type,hedui_cishu,dangqian_cishu,yizhu_time,yizhu_shuxing,other_info,changqi_yizhu_id) VALUES ('")
					.append(history_id).append("','")
					.append(current_application.current_patient_zhuyuan_id).append("','")
					.append(dangqian_zhixing_state).append("','")
					.append(dangqian_zhixing_state).append("','")
					.append(current_application.current_user_number).append("','")
					.append(current_application.current_user_name).append("','")
					.append(yiZhuInfo.getZuhao()).append("',datetime('now', 'localtime'),'")
					.append(yiZhuInfo.getYizhu_type()).append("', datetime('now', 'localtime'),'")
					.append(dangqian_zhixing_state).append("','")
					.append(hedui_cishu).append("','")
					.append(dangqian_cishu).append("',date(strftime('%Y-%m-%d', 'now', 'localtime')),'0','")
					.append(other_info).append("','")
					.append(insert_type).append("' ");
		return insertSQLite.toString();
	}
	
	public void executeDB(String insertSQLite){
		this.db.execSQL(insertSQLite);
	}
	
	/**
	 * 结束sqlite查询
	 * @param cursor
	 */
	public void releaseCursor(Cursor cursor){
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}
	
	/**
	   * 计算当前核对次数
	   * @param zuhao 组号
	   * @param op_type 操作类型
	   * @param unwork_time 历史医嘱未完成日期
	   * @param dangqian_cishu 当前流程次数
	   * @return
	   */
	public int getCalcHeduiCishu(String zuhao,String op_type,int dangqian_cishu){
		int hedui_cishu = 0;
		String str1 = "";
//		if("".equals(StringHelper.notEmpty(unwork_time))){
		str1 = "select zhixing_zuhao,hedui_cishu,op_type from yizhu_zhixing_history_lishi where zhixing_zuhao = '"+zuhao
				+"' and zhixing_time >= strftime('%Y-%m-%d', 'now', 'localtime') and dangqian_cishu = '"+dangqian_cishu+"' and op_type = '"+op_type+"' group by zhixing_zuhao,hedui_cishu ";
//		}else{
//			  str1 = "select zhixing_zuhao,hedui_cishu,op_type from yizhu_zhixing_history_lishi where zhixing_zuhao = '"+zuhao
//					  +"' and yizhu_time = '"+unwork_time+"' and dangqian_cishu = '"+dangqian_cishu+"' and op_type = '"+op_type+"' group by zhixing_zuhao,hedui_cishu ";
//		}
		Cursor localCursor1 = this.db.rawQuery(str1, new String[0]);
		hedui_cishu = localCursor1.getCount();
		localCursor1.close();
		return hedui_cishu + 1;
	}
	
	/**
	 * 获取当前医嘱当前完成次数
	 * @param wancheng_cishu
	 * @param meiri_cishu
	 * @param tab_state
	 * @return
	 */
	public int getDangqianWanchengCishu(int wancheng_cishu,int meiri_cishu,String tab_state) {
		int dangqian_cishu = 0;
//		if (!"".equals(StringHelper.notEmpty(wancheng_cishu)) & wancheng_cishu.matches("[0-9]+")) {
		dangqian_cishu = wancheng_cishu;
//		}
		if(wancheng_cishu == meiri_cishu){
			return dangqian_cishu;
		}
		if("执行完毕".equals(tab_state)){
			return dangqian_cishu;
		}
		return dangqian_cishu + 1;
	}
	

}
