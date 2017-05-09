package com.zrt.fragmentdemoone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class FragmentBasic extends Fragment{
	
	public View contentView;
	private TextView tv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == contentView){
			contentView = inflater.inflate(R.layout.fragment_yizhu_state_child, container, false);
		}
		tv = (TextView) contentView.findViewById(R.id.fragment_state_a);
		tv.setText(configTabChild());
		return contentView;
	}
	
	public abstract String configTabChild();
	
}
