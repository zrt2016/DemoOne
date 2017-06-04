package com.zrt.fragmentdemoone.yizhu.tools;

import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;

public interface DialogExecute {
	/** 确定 */
	public void executeYiZhu(YiZhuInfo yiZhuInfo, String state, int insert_type);
	/** 其他 */
	public void otherOperation(YiZhuInfo yiZhuInfo, String state, int insert_type);
	/** 取消 */
	public void cancel(String state);
	/**
	 * 其他操作
	 * @param yiZhuInfo
	 * @param other
	 * @param insert_type
	 */
	public void executeOtherOperation(YiZhuInfo yiZhuInfo, String op_type, int insert_type);
}
