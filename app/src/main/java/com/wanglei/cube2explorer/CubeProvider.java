package com.wanglei.cube2explorer;

import com.wanglei.cube2.CubeData;
import com.wanglei.widget.ReadOnlyDatabaseProvider;

public class CubeProvider extends ReadOnlyDatabaseProvider {

	@Override
	protected String getDatabaseName() {
		return CubeData.DATABASE_NAME;
	}
}