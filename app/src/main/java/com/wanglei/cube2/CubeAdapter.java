package com.wanglei.cube2;

import java.util.ArrayList;

import com.wanglei.cube2explorer.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CubeAdapter extends CursorAdapter {
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private final int INDEX_STATE;
	private final int INDEX_ACTION;
	private final int INDEX_ACTION_LENGTH;

	public CubeAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		INDEX_STATE = cursor.getColumnIndex(CubeData.KEY_STATE);
		INDEX_ACTION = cursor.getColumnIndex(CubeData.KEY_ACTION);
		INDEX_ACTION_LENGTH = cursor.getColumnIndex(CubeData.KEY_ACTION_LENGTH);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mLayoutInflater.inflate(R.layout.list_item, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		long stateData = cursor.getLong(INDEX_STATE);
		long actionData = cursor.getLong(INDEX_ACTION);
		int actionLength = cursor.getInt(INDEX_ACTION_LENGTH);
		CubeState cubeState = new CubeState(stateData);
		ArrayList<Byte> actions = CubeUtils.getActions(actionData, actionLength);
		String actionString = mContext.getString(R.string.actions) + CubeUtils.getActionString(actions);
		String actionStringRevert = mContext.getString(R.string.revert) + CubeUtils.getActionStringRevert(actions);
		CubeView cubeView = view.findViewById(R.id.cube);
		TextView tvActionString = view.findViewById(R.id.actions);
		TextView tvActionStringRevert = view.findViewById(R.id.revert);
		cubeView.setCubeState(cubeState);
		tvActionString.setText(actionString);
		tvActionStringRevert.setText(actionStringRevert);
	}
}