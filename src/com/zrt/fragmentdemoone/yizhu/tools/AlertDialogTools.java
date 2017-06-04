package com.zrt.fragmentdemoone.yizhu.tools;

import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogTools {
	
	public static AlertDialogTools dialogTools;
	
	public boolean isShow = false;

	public AlertDialog alertDialog;
	
	public Context context;
	
	public AlertDialogTools(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public static AlertDialogTools getInstance(Context context){
		if (null == dialogTools){
			dialogTools = new AlertDialogTools(context);
		}
		return dialogTools;
	}
	
	public void contentDialogThree(final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo,final String... selectTag){
		if (selectTag.length == 3){
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setPositiveButton(selectTag[0], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.executeYiZhu(yiZhuInfo, selectTag[0]);
				}
			});
			dialog.setNeutralButton(selectTag[1], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.otherOperation(yiZhuInfo, selectTag[1]);
				}
			});
			dialog.setNegativeButton(selectTag[2], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.cancel(selectTag[2]);
				}
			});
			showAlertDialog(dialog);
			return;
		}
	}
	
	public void showAlertDialog(AlertDialog.Builder dialog){
		isShow = true;
		alertDialog = dialog.show();
	}
	
	public void dismisAlertDialog(AlertDialog alertDialog){
		isShow = false;
		if (null != alertDialog && alertDialog.isShowing()){
			alertDialog.dismiss();
			alertDialog = null;
		}
	}
}
