package net.fiive.kotoba.activities.questionEdit;


import net.fiive.kotoba.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class QuestionEditActivity extends FragmentActivity {
	public static final String EDIT_QUESTION_ACTION = "net.fiive.kotoba.EDIT_QUESTION";
	public static final String ADD_QUESTION_ACTION = "net.fiive.kotoba.ADD_QUESTION";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_edit_activity);
	}

}
