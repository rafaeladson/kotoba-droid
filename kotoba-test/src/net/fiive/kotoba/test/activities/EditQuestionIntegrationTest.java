package net.fiive.kotoba.test.activities;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.activities.questionList.QuestionListFragment;

public class EditQuestionIntegrationTest extends ActivityInstrumentationTestCase2<QuestionListActivity> {


	private Solo solo;

	public EditQuestionIntegrationTest() {
		super("net.fiive.kotoba.activities", QuestionListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testShouldClickOnAItemOnTheListAndGoToEditTheQuestion() {
		QuestionListActivity activity = getActivity();
		QuestionListFragment fragment = (QuestionListFragment)activity.getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
		assertNotNull(fragment);
		solo.clickInList(0);
		solo.assertCurrentActivity("should have gone to edit the question", QuestionEditActivity.class);
	}
}
