package com.zrt.fragmentdemoone.yizhu.tools;

import java.util.List;

import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;

public interface DialogExecute {
	/** 医嘱执行 */
	public void executeYiZhu(YiZhuInfo yiZhuInfo, String state, int insert_type);
	/** 其他 */
	public void otherOperation(YiZhuInfo yiZhuInfo, String state, int insert_type);
	/** 取消 */
	public void cancel(String state);
	/**
	 * 其他操作：根据选择条件进行对应操作
	 * @param yiZhuInfo
	 * @param other
	 * @param insert_type
	 */
	public void executeOtherOperation(YiZhuInfo yiZhuInfo, String op_type, int insert_type);
	
	/**
	 * 其他操作：插入备注值
	 * @param yiZhuInfo
	 * @param op_type
	 * @param insert_type
	 * @param beizhu
	 */
	public void executeInputOtherValue(YiZhuInfo yiZhuInfo, String op_type, int insert_type, String beizhu);
	
	
	public boolean executeExistsStartYiZhu(List<YiZhuInfo> zhiXingList, int insert_type);
	
}
