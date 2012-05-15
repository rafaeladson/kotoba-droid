package net.fiive.kotoba.test.acceptance;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.activities.questionList.QuestionListFragment;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;


public class QuestionLifecycleTest extends ActivityInstrumentationTestCase2<MainActivity> {


	private Solo solo;

	public QuestionLifecycleTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testIteration() throws InterruptedException {
		if (Build.VERSION.SDK_INT < 11 ) {
			MainActivity mainActivity = getActivity();
			TextView questionTextView = (TextView) mainActivity.findViewById(R.id.questionLabel);
			TextView answerTextView = (TextView) mainActivity.findViewById(R.id.answerLabel);
			assertNotNull(questionTextView);
			assertNotNull(answerTextView);

			assertEquals("How do I use Kotoba?", questionTextView.getText().toString());
			assertAnswerIsHidden(answerTextView);

			solo.clickOnButton("Answer");
			assertTrue(answerTextView.getText().toString().contains("Click Answer to see the answer."));

			solo.clickOnButton("Next");
			assertEquals("How do I use Kotoba?", questionTextView.getText().toString());

			solo.clickOnMenuItem("Manage");
			QuestionListActivity listActivity = (QuestionListActivity) solo.getCurrentActivity();
			QuestionListFragment listFragment = (QuestionListFragment) listActivity.getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
			assertEquals(0, listFragment.getListView().getChildCount());

			addQuestionWithValueAndAnswer("Question 01", "Answer 01");
			listFragment = getListFragment();
			assertEquals(1, listFragment.getListView().getChildCount());
			TextView firstCell = (TextView) listFragment.getListView().getChildAt(0);
			assertEquals("Question 01", firstCell.getText());

			addQuestionWithValueAndAnswer("Question 02", "");
			listFragment = getListFragment();
			assertEquals(2, listFragment.getListView().getChildCount());
			TextView secondCell = (TextView) listFragment.getListView().getChildAt(1);
			assertEquals("Question 02", secondCell.getText());

			solo.clickOnText("Question 02");
			QuestionEditActivity editActivity = (QuestionEditActivity) solo.getCurrentActivity();
			EditText questionEdit = (EditText) editActivity.findViewById(R.id.edit_question_value);
			solo.clearEditText(questionEdit);
			solo.enterText(questionEdit, "Question 03");
			solo.clickOnMenuItem("Save");

			listFragment = getListFragment();
			assertEquals(2, listFragment.getListView().getChildCount());
			secondCell = (TextView) listFragment.getListView().getChildAt(1);
			assertEquals("Question 03", secondCell.getText());

			solo.goBack();
			mainActivity = (MainActivity) solo.getCurrentActivity();
			questionTextView = (TextView) mainActivity.findViewById(R.id.questionLabel);
			answerTextView = (TextView) mainActivity.findViewById(R.id.answerLabel);
			String firstText = questionTextView.getText().toString();
			assertTrue(firstText.equals("Question 01")|| firstText.equals("Question 03"));
			assertAnswerIsHidden(answerTextView);
			solo.clickOnButton("Answer");
			if ( firstText.equals("Question 01")) {
				assertEquals("Answer 01", answerTextView.getText().toString());
			} else {
				assertEquals("", answerTextView.getText().toString());
			}
			solo.clickOnButton("Next");
			String secondText = questionTextView.getText().toString();
			assertTrue(secondText.equals("Question 01") || secondText.equals("Question 03"));
			assertAnswerIsHidden(answerTextView);

			solo.clickOnMenuItem("Manage");
			removeQuestion("Question 01");
			listFragment = getListFragment();
			assertEquals(1, listFragment.getListView().getChildCount());
			firstCell = (TextView) listFragment.getListView().getChildAt(0);
			assertEquals("Question 03", firstCell.getText());

			removeQuestion("Question 03");
			listFragment = getListFragment();
			assertEquals(0, listFragment.getListView().getChildCount());

			solo.goBack();
			mainActivity = (MainActivity) solo.getCurrentActivity();
			questionTextView = (TextView) mainActivity.findViewById(R.id.questionLabel);
			assertEquals("How do I use Kotoba?", questionTextView.getText().toString());
		}
	}

	private void removeQuestion(String question) throws InterruptedException {
		solo.clickOnText(question);
		solo.clickOnMenuItem("Remove");
		solo.clickOnButton("OK");
		Thread.sleep(500L);
	}

	private void assertAnswerIsHidden(TextView answerTextView) {
		assertEquals("Click here to see the answer", answerTextView.getText().toString());
	}

	private QuestionListFragment getListFragment() {
		QuestionListActivity listActivity = (QuestionListActivity) solo.getCurrentActivity();
		return (QuestionListFragment) listActivity.getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
	}

	private void addQuestionWithValueAndAnswer(String value, String answer) {
		solo.clickOnMenuItem("Add");
		QuestionEditActivity editActivity = (QuestionEditActivity) solo.getCurrentActivity();
		EditText questionEdit = (EditText) editActivity.findViewById(R.id.edit_question_value);
		EditText answerEdit = (EditText) editActivity.findViewById(R.id.edit_question_answer);
		solo.enterText(questionEdit, value);
		solo.enterText(answerEdit, answer);
		solo.clickOnButton("Save");
	}


}
