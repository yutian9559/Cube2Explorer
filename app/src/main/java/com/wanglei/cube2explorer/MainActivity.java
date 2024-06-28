package com.wanglei.cube2explorer;

import com.wanglei.cube2.CubeAdapter;
import com.wanglei.cube2.CubeData;
import com.wanglei.cube2.CubeState;
import com.wanglei.cube2.CubeUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends ListActivity implements View.OnClickListener, DialogInterface.OnClickListener {
	private static final String TAG = "MainActivity";
	private static final String RESOURCE_NAME_PREFIX = "com.wanglei.cube2explorer:id/grid";
	private static final int RESOURCE_NAME_PREFIX_LENGTH = RESOURCE_NAME_PREFIX.length();
	private static final int DIALOG = 0;
	private final String[] mWhereList = new String[CubeState.BOARD_LENGTH];
	private int mIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Resources resources = getResources();
		final String packageName = getPackageName();
		for (int i = 0; i < CubeState.BOARD_LENGTH; i++) {
			int id = resources.getIdentifier("grid" + i, "id", packageName);
			View view = findViewById(id);
			view.setOnClickListener(this);
		}
		updateCursor();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CubeAdapter adapter = (CubeAdapter) getListAdapter();
		Cursor cursor = adapter.getCursor();
		cursor.close();
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		final Resources resources = getResources();
		String resourceName = resources.getResourceName(viewId);
		mIndex = Integer.parseInt(resourceName.substring(RESOURCE_NAME_PREFIX_LENGTH));
		showDialog(DIALOG);
		Log.i(TAG, "WL_DEBUG onItemSelected resourceName = " + resourceName);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog result = null;
        if (id == DIALOG) {
            result = new AlertDialog.Builder(this).setAdapter(new SpinnerAdapter(this), this).create();
        }
		return result;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which >= 0 && which <= 6) {
			String where = null;
			byte state = (byte) (which - 1);

			if (state != CubeState.STATE_INVALID) {
                where = CubeData.KEY_STATE +
                        "/" +
                        CubeState.POWER6[mIndex] +
                        "%6=" +
                        state;
			}

			if ((mWhereList[mIndex] != null && !mWhereList[mIndex].equals(where))
					|| (mWhereList[mIndex] == null && where != null)) {
				mWhereList[mIndex] = where;
				final Resources resources = getResources();
				final String packageName = getPackageName();
				int id = resources.getIdentifier("grid" + mIndex, "id", packageName);
				View view = findViewById(id);
				view.setBackgroundColor(CubeUtils.getColor(state));
				updateCursor();
			}

		}
	}

	private void updateCursor() {
		StringBuilder sb = new StringBuilder();

		for (String where : mWhereList) {
			if (where != null) {
				if (sb.length() != 0) {
					sb.append(" and ");
				}
				sb.append(where);
			}
		}
		Cursor cursor = this.getContentResolver().query(CubeData.CONTENT_URI_CUBE, null, sb.toString(), null, null);
		CubeAdapter adapter = (CubeAdapter) getListAdapter();
		if (adapter == null) {
			adapter = new CubeAdapter(this, cursor);
			setListAdapter(adapter);
		} else {
			adapter.changeCursor(cursor);
		}
	}
}
