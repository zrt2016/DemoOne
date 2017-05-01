package com.zrt.fragmentdemoone;

public class YiZhuStateChildFragment extends FragmentBasic{
	
	private String state;
	
	public YiZhuStateChildFragment(String state){
		this.state = state;
	}
	
	public static YiZhuStateChildFragment newInstance (String state){
		YiZhuStateChildFragment childFragment = new YiZhuStateChildFragment(state);
		return childFragment;
	}
	
	
	
}
