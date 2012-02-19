package net.fiive.kotoba.activities.questionEdit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import net.fiive.kotoba.R;


public class QuestionEditActivity extends FragmentActivity {
	public static final String EDIT_QUESTION_ACTION = "net.fiive.kotoba.EDIT_QUESTION";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_edit_activity);
	}
}
