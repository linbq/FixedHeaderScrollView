package com.maneater.fixedheader;

import android.app.Activity;
import android.os.Bundle;

public class DemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		((FixedHeaderScrollView) (findViewById(R.id.fixedScrollView)))
				.setHeaderView(findViewById(R.id.headerView));
	}

}
