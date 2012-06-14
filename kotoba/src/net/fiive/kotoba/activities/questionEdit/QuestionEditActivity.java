package net.fiive.kotoba.activities.questionEdit;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.base.BaseActivity;


public class QuestionEditActivity extends BaseActivity {
	public static final String EDIT_QUESTION_ACTION = "net.fiive.kotoba.EDIT_QUESTION";
	public static final String ADD_QUESTION_ACTION = "net.fiive.kotoba.ADD_QUESTION";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_edit_activity);
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

	@Override
	@SuppressWarnings("unchecked")
	public QuestionEditFragment getFragment() {
		return (QuestionEditFragment) getSupportFragmentManager().findFragmentById(R.id.question_edit_fragment);
	}
}
