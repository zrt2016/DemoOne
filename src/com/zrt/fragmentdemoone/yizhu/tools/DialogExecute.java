package com.zrt.fragmentdemoone.yizhu.tools;

import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;

public interface DialogExecute {
	/** 确定 */
	public void executeYiZhu(YiZhuInfo yiZhuInfo, String state);
	/** 其他 */
	public void otherOperation(YiZhuInfo yiZhuInfo, String state);
	/** 取消 */
	public void cancel(String state);
}
