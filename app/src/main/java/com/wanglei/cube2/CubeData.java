package com.wanglei.cube2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class CubeData {
	private static final String AUTHORITIES = CubeUtils.PACKAGE;
	public static final String DATABASE_NAME = "Cube.db";
	public static final String TABLE_CUBE = "cube";
	public static final String KEY_STATE = "state";
	public static final String KEY_ACTION = "action";
	public static final String KEY_ACTION_LENGTH = "action_length";
	public static final Uri CONTENT_URI_CUBE = Uri.parse("content://" + AUTHORITIES + "/" + TABLE_CUBE);

	public static Cursor query(Context context, byte[] state) {
		int length = state.length;
		int start = CubeState.BOARD_LENGTH - length;
		long state_min = 0;
		long state_max;
		for (int i = 0; i < length; i++) {
			state_min += state[i] * CubeState.POWER6[start + i];
		}
		state_max = state_min + CubeState.POWER6[start];
		return context.getContentResolver().query(CubeData.CONTENT_URI_CUBE, null,
				CubeData.KEY_STATE + " >= " + state_min + " and " + CubeData.KEY_STATE + " < " + state_max, null,
				CubeData.KEY_STATE + " desc");
	}
}