package net.fiive.kotoba.activities.questionList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;


public class QuestionListActivity extends FragmentActivity {

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
		switch( item.getItemId()) {
			case R.id.add_question_menu:
				addNewQuestion();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void addNewQuestion() {
		Intent addQuestionIntent = new Intent(QuestionEditActivity.ADD_QUESTION_ACTION, Uri.parse("kotoba://kotoba.fiive.net/question/new"));
		startActivity(addQuestionIntent);
	}


}
