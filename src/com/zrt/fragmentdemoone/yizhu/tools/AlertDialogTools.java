package com.zrt.fragmentdemoone.yizhu.tools;

import java.lang.reflect.Field;

import com.zrt.fragmentdemoone.R;
import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AlertDialogTools {
	
	public static AlertDialogTools dialogTools;
	
	public boolean isShow = false;

	public AlertDialog alertDialog;
	
	public Context context;
	
	/** 其他操作中记录选择项 */
	public String selectOtherItem;
	
	public boolean switchDialog = true;
	
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
	
	public void contentDialogThree(final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final int insert_type,final String... selectTag){
		if (selectTag.length == 3){
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("医嘱执行");
			dialog.setPositiveButton(selectTag[0], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.executeYiZhu(yiZhuInfo, selectTag[0], insert_type);
					dismisAlertDialog();
				}
			});
			dialog.setNeutralButton(selectTag[1], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.otherOperation(yiZhuInfo, selectTag[1], insert_type);
					dismisAlertDialog();
				}
			});
			dialog.setNegativeButton(selectTag[2], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.cancel(selectTag[2]);
					dismisAlertDialog();
				}
			});
			showAlertDialog(dialog);
			return;
		}
	}
	
	/**
	 * 其他操作
	 * @param dialogExecute
	 * @param yiZhuInfo
	 * @param insert_type
	 * @param itemOperation
	 */
	public void otherOperationDialog(final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final int insert_type, final String[] itemOperation){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("医嘱执行");
		selectOtherItem = itemOperation[0];
		dialog.setSingleChoiceItems(itemOperation, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
//				 YizhuZhixingListActivity.this.choose_operate_type = arrayOfString[paramAnonymous2Int];
				selectOtherItem = itemOperation[paramAnonymous2Int];
			}
		});
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogExecute.executeOtherOperation(yiZhuInfo, selectOtherItem, insert_type);
				dismisAlertDialog();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogExecute.cancel("其他操作");
				dismisAlertDialog();
			}
		});
		showAlertDialog(dialog);
		
	}
	
	public void otherInputDiSu(final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final String op_type, final int insert_type){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		View inputDiSuViwe = LayoutInflater.from(context).inflate(R.layout.yizhu_luru_disu_dialog, null);
		TextView tv_Title = (TextView) inputDiSuViwe.findViewById(R.id.dialog_title);
		tv_Title.setText(op_type);
		TextView tv_luRuDanWei = (TextView) inputDiSuViwe.findViewById(R.id.luru_disu_danwei_id);
		tv_luRuDanWei.setVisibility(View.GONE);
		final EditText disu_text = (EditText) inputDiSuViwe.findViewById(R.id.luru_disu_text);
		disu_text.setInputType(InputType.TYPE_CLASS_TEXT);
		disu_text.setGravity(View.FOCUS_DOWN);
		dialog.setView(inputDiSuViwe);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String disu_value = disu_text.getEditableText().toString();
				if ("".equals(disu_value)){
					switchAlertDialog(dialog, false);
					return;
				}
				if (!switchDialog){
					switchAlertDialog(dialog, true);
				}
				dialogExecute.executeInputOtherValue(yiZhuInfo, op_type, insert_type, disu_value);
				dismisAlertDialog();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (!switchDialog){
					switchAlertDialog(dialog, true);
				}
				dialogExecute.cancel("其他操作");
				dismisAlertDialog();
			}
		});
		showAlertDialog(dialog);
	}
	
	
	private void showAlertDialog(AlertDialog.Builder dialog){
		isShow = true;
		switchDialog = true;
		alertDialog = dialog.show();
	}
	
	public void dismisAlertDialog(){
		isShow = false;
		if (null != alertDialog && alertDialog.isShowing()){
			alertDialog.dismiss();
			alertDialog = null;
		}
	}
	
	/**
	 * 是否关闭dialog框
	 * @param paramAnonymous2DialogInterface
	 * @param state true 可关闭； false 不可关闭
	 */
	public void switchAlertDialog(DialogInterface dialogInterface, boolean state){
		switchDialog = state;
		// 条件不成立不能关闭 AlertDialog 窗口
		try 
        {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, state); // false - 使之不能关闭(此为机关所在，其它语句相同)
        } 
        catch (Exception e) 
        {
            Log.e("", e.getMessage());
            e.printStackTrace();
        }
	}
}
