package net.fiive.kotoba.test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;
import net.fiive.kotoba.test.screen.questionGame.QuestionGameScreen;

public class QuestionGameBeginsWithZeroQuestionsTest extends
	ActivityUnitTestCase<MainActivity> {

	private QuestionGameScreen screen;
	private DataService dataService;

	public QuestionGameBeginsWithZeroQuestionsTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null);
		MainActivity activity = this.getActivity();
		screen = QuestionGameScreen.screenForUnitTest(activity);

		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		dataService = new DataService(getInstrumentation().getTargetContext());
	}

	public void testNormalInteration() throws Exception {
		Question questionA = new Question("foo", "bar");
		dataService.saveOrUpdateQuestion(questionA);
		Question questionB = new Question("bar", "foo");
		dataService.saveOrUpdateQuestion(questionB);

		screen.onActivityCreated(null);
		screen.clickOnNextQuestionButton();

		final CharSequence firstWord = screen.getValueText();
		assertNotNull(firstWord);

		screen.clickOnNextQuestionButton();
		CharSequence secondWord = screen.getValueText();
		assertNotNull(secondWord);
		assertFalse(secondWord.equals(firstWord));
		assertFalse(screen.isAnswerVisible());

		screen.clickOnShowAnswerButton();
		assertTrue(screen.isAnswerVisible());
		screen.clickOnNextQuestionButton();
		assertFalse(screen.isAnswerVisible());
		assertNotNull(screen.getValueText());
	}

	public void testNormalIteration_databaseWithoutQuestions() throws Exception {
		screen.onActivityCreated(null);

		assertTrue(screen.hasDefaultQuestionValue());
		assertFalse(screen.isAnswerVisible());

		screen.clickOnShowAnswerButton();
		assertTrue(screen.hasDefaultAnswerValue());

		screen.clickOnNextQuestionButton();
		assertTrue(screen.hasDefaultQuestionValue());
	}


	public void testClickOnAnswerTestViewDoesShowAnswer() throws Exception {
		screen.clickOnNextQuestionButton();
		assertFalse(screen.isAnswerVisible());

		screen.clickOnAnswerView();
		assertTrue(screen.isAnswerVisible());
	}


	public void testRestoreFromBundle() throws Exception {

		Bundle newActivityBundle = new Bundle();
		newActivityBundle.putSerializable("currentQuestion", new Question("das Auto", "car"));
		newActivityBundle.putBoolean("answerVisible", true);

		screen.onActivityCreated(newActivityBundle);

		assertEquals("car", screen.getAnswerText());

	}

	public void testShouldSaveBundle() throws Exception {
		Question question = new Question("foo", "bar");
		screen.setCurrentQuestion(question);
		screen.clickOnShowAnswerButton();

		Bundle fragmentBundle = new Bundle();
		screen.onSaveInstanceState(fragmentBundle);

		Question questionFromBundle = (Question) fragmentBundle.get("currentQuestion");
		assertNotNull(questionFromBundle);
		assertEquals(question, questionFromBundle);

		assertTrue(fragmentBundle.getBoolean("answerVisible"));
	}

	public void testShouldDispatchIntentToListWhenIClickOnMenu() throws Exception {
		screen.selectMenuItem(R.id.manage_questions_menu);
		Intent manageQuestionsIntent = getStartedActivityIntent();
		assertEquals(QuestionListActivity.MANAGE_QUESTIONS_ACTION, manageQuestionsIntent.getAction());
	}


}
