package com.wanglei.cube2explorer;

import com.wanglei.cube2.CubeUtils;
import com.wanglei.widget.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpinnerAdapter extends MyAdapter {
    private final LayoutInflater mLayoutInflater;

	public SpinnerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	protected void bindView(int position, View v, ViewGroup parent) {
		v.setBackgroundColor(CubeUtils.getColor((byte) (position - 1)));
	}

	@Override
	protected View newView(int position, ViewGroup parent) {
		return mLayoutInflater.inflate(R.layout.spinner_item, null);
	}

}