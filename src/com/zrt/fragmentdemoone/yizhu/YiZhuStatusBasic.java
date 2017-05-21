package com.zrt.fragmentdemoone.yizhu;

import java.util.ArrayList;
import java.util.List;

import com.zrt.fragmentdemoone.GlobalInfoApplication;
import com.zrt.fragmentdemoone.yizhu.YiZhuInfo.ContentInfo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class YiZhuStatusBasic {
	public String yizhu_type = "全部";
	
	public SQLiteDatabase db;
	public GlobalInfoApplication current_application;
	
	/**
	 * 
	 * @param yizhu_type 医嘱的类型
	 * @return 返回查询sqlite语句
	 */
	public abstract String getSQLiteType(String yizhu_type);
	
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
				String zuhao = zuhaoCursor.getString(zuhaoCursor.getColumnIndex("zuhao"));
				yiZhuInfo.setZuhao(zuhao);
				//获取医嘱内容
				yiZhuInfo.setYizhu_content_info(getYiZhuContent(zuhao));
				//获取操作历史 
				getYiZhuStateRecord(yiZhuInfo);
				
				
				list.add(yiZhuInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			releaseCursor(zuhaoCursor);
		}
		
		
		
		return null;
	}
	
	/**
	 * 根据组号获取到当前医嘱的详细content
	 * @param zuhao
	 * @return 
	 */
	private List<YiZhuInfo.ContentInfo> getYiZhuContent(String zuhao) {
		List<YiZhuInfo.ContentInfo> list = new ArrayList<>();
		String contentSQLite = "select * from yizhu_info where zuhao = '" + zuhao + "' order by yizhu_id asc";
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(contentSQLite, new String[0]);
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

	public void releaseCursor(Cursor cursor){
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}
	

}
