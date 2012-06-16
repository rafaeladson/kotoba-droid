package net.fiive.kotoba.activities.questionList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import net.fiive.intern.android.activity.BaseActivity;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.info.InfoActivity;


public class QuestionListActivity extends BaseActivity {

	public static final String MANAGE_QUESTIONS_ACTION = "net.fiive.kotoba.MANAGE_QUESTIONS";


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_list_activity);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.question_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_question_menu:
				getFragment().addNewQuestion();
				return true;
			case android.R.id.home:
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case R.id.info_menu:
				showInfo();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void showInfo() {
		Intent infoIntent = new Intent(InfoActivity.VIEW_INFO_ACTION);
		startActivity(infoIntent);
	}

	@Override
	@SuppressWarnings("unchecked")
	public QuestionListFragment getFragment() {
		return (QuestionListFragment) getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
	}
}
