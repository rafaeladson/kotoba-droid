package net.fiive.kotoba.activities.questionList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import net.fiive.kotoba.R;


public class QuestionListActivity extends FragmentActivity {

	public static final String MANAGE_QUESTIONS_ACTION = "net.fiive.kotoba.MANAGE_QUESTIONS";


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_list_activity);
	}


}
