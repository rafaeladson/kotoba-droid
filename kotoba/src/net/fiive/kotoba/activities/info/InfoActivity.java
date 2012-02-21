package net.fiive.kotoba.activities.info;

import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


public class InfoActivity extends Activity {

	public static final String VIEW_INFO_ACTION = "net.fiive.kotoba.VIEW_INFO";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}
}
