package com.zrt.fragmentdemoone.yizhu.tools;

import android.app.AlertDialog;

public class AlertDialogTools {
	
	public static AlertDialogTools dialogTools;
	
	public boolean isShow = false;

	public AlertDialog alertDialog;
	
	public AlertDialogTools() {
		// TODO Auto-generated constructor stub
	}
	
	public static AlertDialogTools getInstance(){
		if (null == dialogTools){
			dialogTools = new AlertDialogTools();
		}
		return dialogTools;
	}
	
	public void showAlertDialog(AlertDialog.Builder dialog){
		isShow = true;
		alertDialog = dialog.show();
	}
	
	public void dismisAlertDialog(AlertDialog alertDialog){
		isShow = false;
		if (null != alertDialog){
			alertDialog.dismiss();
			alertDialog = null;
		}
	}
}
