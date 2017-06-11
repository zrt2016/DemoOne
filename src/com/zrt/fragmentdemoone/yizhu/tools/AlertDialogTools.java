package com.zrt.fragmentdemoone.yizhu.tools;

import java.lang.reflect.Field;
import java.util.List;

import com.zrt.fragmentdemoone.R;
import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;
import com.zrt.fragmentdemoone.yizhu.tools.DialogAdapter.ViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlertDialogTools {
	
	public static AlertDialogTools dialogTools;
	
	private static boolean isShow = false;

	public static AlertDialog alertDialog;
	
//	public Context context;
	
	/** 其他操作中记录选择项 */
	public static String selectOtherItem;
	
	/** 是否可关闭dialog */
	public static boolean switchDialog = true;
	
//	/**
//	 * 多袋输液全选按钮点击记录
//	 */
//	private boolean duoDaiShuYeQuanXuan;
	
//	public AlertDialogTools(Context context) {
//		// TODO Auto-generated constructor stub
//		this.context = context;
//	}
	
//	public static AlertDialogTools getInstance(Context context){
//		if (null == dialogTools){
//			dialogTools = new AlertDialogTools(context);
//		}
//		return dialogTools;
//	}
	
	public static void contentDialogTwo(Context context, final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final int insert_type,final String... selectTag){
		if (selectTag.length == 2){
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			//TODO 医嘱内容
			dialog.setTitle("医嘱执行");
			dialog.setPositiveButton(selectTag[0], new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialogExecute.executeYiZhu(yiZhuInfo, selectTag[0], insert_type);
					dismisAlertDialog();
				}
			});
			dialog.setNegativeButton(selectTag[1], new DialogInterface.OnClickListener() {
				
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
	
	public static void contentDialogThree(Context context, final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final int insert_type,final String... selectTag){
		if (selectTag.length == 3){
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			//TODO 医嘱内容
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
	public static void otherOperationDialog(Context context, final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final int insert_type, final String[] itemOperation){
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
	
	/**
	 * 滴速录入
	 * @param dialogExecute
	 * @param yiZhuInfo
	 * @param op_type
	 * @param insert_type
	 */
	public static void otherInputDiSuDialog(final Context context, final DialogExecute dialogExecute, final YiZhuInfo yiZhuInfo, final String op_type, final int insert_type){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		View inputDiSuViwe = LayoutInflater.from(context).inflate(R.layout.yizhu_luru_disu_dialog, null);
		TextView tv_Title = (TextView) inputDiSuViwe.findViewById(R.id.dialog_title);
		tv_Title.setText(op_type);
		final EditText disu_text = (EditText) inputDiSuViwe.findViewById(R.id.luru_disu_text);
		if (!op_type.equals("录入滴速")){
			TextView tv_luRuDanWei = (TextView) inputDiSuViwe.findViewById(R.id.luru_disu_danwei_id);
			tv_luRuDanWei.setVisibility(View.GONE);
			disu_text.setInputType(InputType.TYPE_CLASS_TEXT);
			disu_text.setGravity(View.FOCUS_DOWN);
		}
		dialog.setView(inputDiSuViwe);
		
		final String lurudisu_danwei = context.getResources().getString(R.string.luru_disu_danwei);

		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String disu_value = disu_text.getText().toString().trim();
				if ("".equals(disu_value)){
					switchAlertDialog(dialog, false);
					Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!switchDialog){
					switchAlertDialog(dialog, true);
				}
				if (op_type.equals("录入滴速")){
					disu_value = disu_value + lurudisu_danwei;
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
	
	/**
	 * 多袋输液医嘱选择
	 * @param context
	 * @param dialogExecute
	 * @param zhiXingList
	 * @param yiZhuInfo
	 * @param insert_type
	 */
	public static void existsStartShuYeDialog(Context context, final DialogExecute dialogExecute,final List<YiZhuInfo> zhiXingList, final YiZhuInfo yiZhuInfo,final int insert_type){
		final AlertDialog.Builder existsDialog = new AlertDialog.Builder(context);
//		adb.setIcon(R.drawable.ic_logo);
//		adb.setTitle("存在"+this.zhiXingList.size()+"条医嘱未执行完毕\n请选择要结束的医嘱");
		View existView = LayoutInflater.from(context).inflate(R.layout.custom_alertdialog_listview, null);
		((TextView) existView.findViewById(R.id.custom_dialog_title)).setText("存在"+zhiXingList.size()+"条医嘱未执行完毕\n请选择要结束的医嘱");
		final CheckBox cb = (CheckBox) existView.findViewById(R.id.custom_dialog_quanxuan);
		final ListView lv = (ListView) existView.findViewById(R.id.custom_dialog_lv);
		final DialogAdapter adapter = new DialogAdapter(context, zhiXingList);
		lv.setAdapter(adapter);
		existsDialog.setView(existView);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DialogAdapter.ViewHolder holder = (ViewHolder) view.getTag();
				holder.cb_state.toggle();
				if (!holder.cb_state.isChecked()){
					cb.setChecked(false);
				}else{
					boolean flag = false;
					for (int x=0; x<zhiXingList.size(); x++){
						if (!zhiXingList.get(x).isOperation_state())
							flag = true;
					}
					if (!flag){
						cb.setChecked(true);
					}
				}
			}
		});
//		cb.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
////				duoDaiShuYeQuanXuan = true;
//				return false;
//			}
//		});
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if (duoDaiShuYeQuanXuan){
					for (int x=0; x<zhiXingList.size(); x++){
						zhiXingList.get(x).setOperation_state(isChecked);
					}
					adapter.notifyDataSetChanged();
//					duoDaiShuYeQuanXuan = false;
//				}
			}
		});
		existsDialog.setPositiveButton("选择执行", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (dialogExecute.executeExistsStartYiZhu(zhiXingList, insert_type)){
					if (!switchDialog){
						switchAlertDialog(dialog, true);
					}
					dialogExecute.executeYiZhu(yiZhuInfo, "执行完毕", insert_type);
					return;
				}
				switchAlertDialog(dialog, false);
				
			}
		});
		existsDialog.setNeutralButton("全部执行", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int x=0; x<zhiXingList.size(); x++){
					zhiXingList.get(x).setOperation_state(true);
				}
				dialogExecute.executeExistsStartYiZhu(zhiXingList, insert_type);
				dialogExecute.executeYiZhu(yiZhuInfo, "执行完毕", insert_type);
				if (!switchDialog){
					switchAlertDialog(dialog, true);
				}
			}
		});
		existsDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (!switchDialog){
					switchAlertDialog(dialog, true);
				}
			}
		});
		existsDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            	
              return false;
            }
        });
		showAlertDialog(existsDialog);
	}
	
	/**
	 * 警告提醒框
	 * @param title 标题
	 * @param message 提示内容
	 * @param icon 图片
	 */
	public static void promptWarningDialog(Context context, String title, String message, int icon){
		AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(context);
        localBuilder2.setTitle(title);
        localBuilder2.setMessage(message);
        localBuilder2.setIcon(icon);
        localBuilder2.setOnKeyListener(new OnKeyListener() {
              @Override
              public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
              	if(isShow){
              		if((keyCode == KeyEvent.KEYCODE_F11 || keyCode == KeyEvent.KEYCODE_F12|| keyCode == 140) && event.getRepeatCount()==0)
	                    {
	                        dismisAlertDialog();
	                    }
              	}
                  return false;
              }
          });
       showAlertDialog(localBuilder2);
	}
	
	private static void showAlertDialog(AlertDialog.Builder dialog){
		isShow = true;
		switchDialog = true;
		alertDialog = dialog.show();
	}
	
	public static  void dismisAlertDialog(){
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
	public static void switchAlertDialog(DialogInterface dialogInterface, boolean state){
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
	
	public boolean isShow(){
		return isShow;
	}
}
