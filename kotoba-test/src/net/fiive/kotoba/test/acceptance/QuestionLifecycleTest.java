package net.fiive.kotoba.test.acceptance;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.activities.questionList.QuestionListFragment;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;
import net.fiive.kotoba.test.screen.questionEdit.QuestionEditScreen;
import net.fiive.kotoba.test.screen.questionGame.QuestionGameScreen;


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
		if (Build.VERSION.SDK_INT < 11) {
			MainActivity mainActivity = getActivity();
			QuestionGameScreen screen = QuestionGameScreen.screenForAcceptanceTest(mainActivity, solo);
			assertTrue(screen.hasDefaultQuestionValue());
			assertFalse(screen.isAnswerVisible());

			screen.clickOnShowAnswerButton();
			assertTrue(screen.isAnswerVisible());
			assertTrue(screen.hasDefaultAnswerValue());

			solo.clickOnButton("Next");
			assertFalse(screen.isAnswerVisible());
			assertTrue(screen.hasDefaultQuestionValue());

			screen.selectMenuItem(R.string.manage_questions);
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
			QuestionEditScreen editScreen = QuestionEditScreen.screenForAcceptanceTests(editActivity, solo);
			editScreen.fillQuestionAndAnswer("Question 03", editScreen.getAnswerText());
			editScreen.selectSaveMenuItem();

			listFragment = getListFragment();
			assertEquals(2, listFragment.getListView().getChildCount());
			secondCell = (TextView) listFragment.getListView().getChildAt(1);
			assertEquals("Question 03", secondCell.getText());

			solo.goBack();
			mainActivity = (MainActivity) solo.getCurrentActivity();
			screen = QuestionGameScreen.screenForAcceptanceTest(mainActivity, solo);
			String firstText = screen.getValueText();
			assertTrue(firstText.equals("Question 01") || firstText.equals("Question 03"));
			assertFalse(screen.isAnswerVisible());
			screen.clickOnShowAnswerButton();
			if (firstText.equals("Question 01")) {
				assertEquals("Answer 01", screen.getAnswerText());
			} else {
				assertEquals("", screen.getAnswerText());
			}
			screen.clickOnNextQuestionButton();
			String secondText = screen.getValueText();
			assertTrue(secondText.equals("Question 01") || secondText.equals("Question 03"));
			assertFalse(screen.isAnswerVisible());

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
			screen = QuestionGameScreen.screenForAcceptanceTest(mainActivity, solo);
			assertTrue(screen.hasDefaultQuestionValue());
		}
	}

	private void removeQuestion(String question) throws InterruptedException {
		solo.clickOnText(question);
		QuestionEditActivity editActivity = (QuestionEditActivity) solo.getCurrentActivity();
		QuestionEditScreen editScreen = QuestionEditScreen.screenForAcceptanceTests(editActivity, solo);
		editScreen.removeCurrentQuestion();
	}

	private QuestionListFragment getListFragment() {
		QuestionListActivity listActivity = (QuestionListActivity) solo.getCurrentActivity();
		return (QuestionListFragment) listActivity.getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
	}

	private void addQuestionWithValueAndAnswer(String value, String answer) {
		solo.clickOnMenuItem("Add");
		QuestionEditActivity editActivity = (QuestionEditActivity) solo.getCurrentActivity();
		QuestionEditScreen screen = QuestionEditScreen.screenForAcceptanceTests(editActivity, solo);
		screen.fillQuestionAndAnswer(value, answer);
		screen.selectSaveMenuItem();
	}


}
