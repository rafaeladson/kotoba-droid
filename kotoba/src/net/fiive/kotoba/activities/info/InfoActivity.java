package net.fiive.kotoba.activities.info;

import net.fiive.kotoba.R;
import android.app.Activity;
import android.os.Bundle;


public class InfoActivity extends Activity {

	public static final String VIEW_INFO_ACTION = "net.fiive.kotoba.VIEW_INFO";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	}


}
