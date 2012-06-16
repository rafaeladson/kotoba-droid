package net.fiive.kotoba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import net.fiive.intern.android.activity.BaseActivity;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;

public class MainActivity extends BaseActivity {


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		this.getMenuInflater().inflate(R.menu.question_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.manage_questions_menu:
				manageQuestions();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}


	}

	private void manageQuestions() {
		Intent manageQuestionsIntent = new Intent(QuestionListActivity.MANAGE_QUESTIONS_ACTION);
		startActivity(manageQuestionsIntent);
	}


	@Override
	@SuppressWarnings("unchecked")
	public QuestionGameFragment getFragment() {
		return (QuestionGameFragment) getSupportFragmentManager().findFragmentById(R.id.questionGameFragment);
	}
}
