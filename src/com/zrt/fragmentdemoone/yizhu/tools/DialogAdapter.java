package com.zrt.fragmentdemoone.yizhu.tools;

import java.util.List;

import com.zrt.fragmentdemoone.R;
import com.zrt.fragmentdemoone.yizhu.YiZhuInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class DialogAdapter extends BaseAdapter{
	
	private List<YiZhuInfo> zhiXingList;
	private LayoutInflater inflater;
	
	public DialogAdapter(Context context, List<YiZhuInfo> zhiXingList) {
		this.zhiXingList = zhiXingList;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return this.zhiXingList == null ? 0 : this.zhiXingList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.zhiXingList == null ? null : this.zhiXingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null){
			convertView = this.inflater.inflate(R.layout.custom_alertdialog_lv_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		holder.initData();
		return convertView;
	}
	
	public class ViewHolder{
		public TextView tv_Title;
		public CheckBox cb_state;
		public int position;
		
		public void setPosition(int position){
			this.position = position;
		}
		
		public ViewHolder(View convertView) {
			tv_Title = (TextView) convertView.findViewById(R.id.custom_dialog_lv_item_tv);
			cb_state = (CheckBox) convertView.findViewById(R.id.custom_dialog_lv_item_cb);
		}
		
		public void initData(){
			tv_Title.setText(zhiXingList.get(position).getContent());
			cb_state.setChecked(DialogAdapter.this.zhiXingList.get(position).isOperation_state());
			cb_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					DialogAdapter.this.zhiXingList.get(position).setOperation_state(isChecked);
				}
			});
		}
	}
}
