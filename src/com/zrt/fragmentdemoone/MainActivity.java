package com.zrt.fragmentdemoone;

import com.zrt.fragmentdemoone.yizhu.YiZhuManage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {
	
	// 待配液、已校对、开始执行、暂停执行、执行完毕
	public static final String state_daipeiye = "待配液";
	public static final String state_weizhixing = "已校对";
	public static final String state_kaishi = "开始执行";
	public static final String state_zanting = "暂停执行";
	public static final String state_wancheng = "执行完毕";
	
	private RadioGroup mainRG;
	/** 当前界面的所有fragment */
	private SparseArray<YiZhuStateChildFragment> childs;
	/** */
	private YiZhuStateChildFragment currentFragment;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		mainRG = (RadioGroup) findViewById(R.id.main_layout_rg);
	}
	
	private void initData() {
		childs = new SparseArray<YiZhuStateChildFragment>();
		childs.append(R.id.main_rb_daipeiye, YiZhuStateChildFragment.newInstance(state_daipeiye));
		childs.append(R.id.main_rb_yijiaodui, YiZhuStateChildFragment.newInstance(state_weizhixing));
		childs.append(R.id.main_rb_kaishizhixing, YiZhuStateChildFragment.newInstance(state_kaishi));
		childs.append(R.id.main_rb_zantingzhixing, YiZhuStateChildFragment.newInstance(state_zanting));
		childs.append(R.id.main_rb_zhixingwanbi, YiZhuStateChildFragment.newInstance(state_wancheng));
	}
	
	private void initListener() {
		mainRG.setOnCheckedChangeListener(onRadioGroupListener);
		mainRG.check(R.id.main_rb_yijiaodui);
	}
	
	private RadioGroup.OnCheckedChangeListener onRadioGroupListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			YiZhuManage manage = null;
			// TODO Auto-generated method stub
			switchFragment(childs.get(checkedId));
			switch (checkedId) {
				case R.id.main_rb_daipeiye:
					MyLogger.i("##待配液");
					manage = new YiZhuManage(state_daipeiye, "全部", (GlobalInfoApplication) MainActivity.this.getApplication());
					break;
				case R.id.main_rb_yijiaodui:
					MyLogger.i("##已校对");
					manage = new YiZhuManage(state_weizhixing, "全部", (GlobalInfoApplication) MainActivity.this.getApplication());
					break;
				case R.id.main_rb_kaishizhixing:
					MyLogger.i("##开始执行");
					manage = new YiZhuManage(state_kaishi, "全部", (GlobalInfoApplication) MainActivity.this.getApplication());
					break;
				case R.id.main_rb_zantingzhixing:
					MyLogger.i("##暂停执行");
					manage = new YiZhuManage(state_zanting, "全部", (GlobalInfoApplication) MainActivity.this.getApplication());
					break;
				case R.id.main_rb_zhixingwanbi:
					MyLogger.i("##执行完毕");
					manage = new YiZhuManage(state_wancheng, "全部", (GlobalInfoApplication) MainActivity.this.getApplication());
					break;
			}
		}
	};

	protected void switchFragment(YiZhuStateChildFragment yiZhuStateChildFragment) {
		if (currentFragment == yiZhuStateChildFragment){
			return;
		}
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (null != currentFragment){
			if (currentFragment.isAdded() && !currentFragment.isHidden()){
				transaction.hide(currentFragment);
			}
		}
		if (!yiZhuStateChildFragment.isAdded()){
			transaction.add(R.id.main_content, yiZhuStateChildFragment);
			transaction.show(yiZhuStateChildFragment);
		}else {
			transaction.show(yiZhuStateChildFragment);
		}
		transaction.commit();
		getSupportFragmentManager().executePendingTransactions();
		currentFragment = yiZhuStateChildFragment;
	}
	
	
}
