package net.fiive.kotoba.test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;

public class QuestionGameFragmentTest extends
		ActivityUnitTestCase<MainActivity> {

	private MainActivity activity;
	private TextView valueLabel;
	private TextView answerLabel;
	private Button nextQuestionButton;
	private Button showAnswerButton;
	private static final String CLICK_HERE_TO_SEE_ANSWER_TEXT = "Click here to see answer";
	private DataService dataService;

	public QuestionGameFragmentTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null );
		activity = this.getActivity();
		valueLabel = (TextView) activity.findViewById(R.id.questionLabel);
		answerLabel = (TextView) activity
				.findViewById(R.id.answerLabel);
		nextQuestionButton = (Button) activity.findViewById(R.id.nextQuestionButton);
		showAnswerButton = (Button) activity
				.findViewById(R.id.showAnswerButton);

		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		dataService = new DataService(getInstrumentation().getTargetContext());
	}

	private QuestionGameFragment getFragment() {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		return (QuestionGameFragment)fragmentManager.findFragmentById(R.id.questionGameFragment);
	}


	public void testNormalInteration() throws Exception {
		Question questionA = new Question( "foo", "bar");
		dataService.saveOrUpdateQuestion(questionA);
		Question questionB = new Question("bar", "foo");
		dataService.saveOrUpdateQuestion(questionB);

		getFragment().onActivityCreated(null);
		nextQuestionButton.performClick();

		final CharSequence firstWord = valueLabel.getText();
		assertNotNull(firstWord);
		
		nextQuestionButton.performClick();
		CharSequence secondWord = valueLabel.getText();
		assertNotNull(firstWord);
		assertFalse(secondWord.equals(firstWord));
		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, answerLabel.getText());

		showAnswerButton.performClick();
		assertFalse(CLICK_HERE_TO_SEE_ANSWER_TEXT.equals(answerLabel.getText()));
		nextQuestionButton.performClick();
		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, answerLabel.getText());
		assertNotNull(valueLabel.getText());
	}

	public void testNormalIteration_databaseWithoutQuestions() throws Exception {
		getFragment().onActivityCreated(null);

		final CharSequence firstWord = valueLabel.getText();
		assertEquals( activity.getResources().getText(R.string.how_do_i_use_kotoba_question), firstWord);
		assertEquals( CLICK_HERE_TO_SEE_ANSWER_TEXT, answerLabel.getText());

		showAnswerButton.performClick();
		assertEquals( activity.getResources().getText(R.string.how_do_i_use_kotoba_answer), answerLabel.getText());

		nextQuestionButton.performClick();
		assertEquals( firstWord, valueLabel.getText());
	}

	public void testClickOnAnswerTestViewDoesShowAnswer() throws Exception {
		nextQuestionButton.performClick();
		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, answerLabel.getText());
		answerLabel.performClick();
		assertFalse(CLICK_HERE_TO_SEE_ANSWER_TEXT.equals(answerLabel.getText()));
	}
	
	
	public void testRestoreFromBundle() throws Exception {
		
		Bundle newActivityBundle = new Bundle();
		newActivityBundle.putSerializable("currentQuestion", new Question("das Auto", "car"));
		newActivityBundle.putBoolean("answerIsShown", true);
		
		QuestionGameFragment fragment = getFragment();
		fragment.onActivityCreated(newActivityBundle);
		
		TextView newAnswerLabel = (TextView) activity.findViewById(R.id.answerLabel);
		assertEquals("car", newAnswerLabel.getText());
		
	}
	
	public void testShouldSaveBundle() throws Exception {
		Question question = new Question("foo", "bar");
		getFragment().setCurrentQuestion(question);
		showAnswerButton.performClick();

		Bundle fragmentBundle = new Bundle();
		QuestionGameFragment fragment = getFragment();
		fragment.onSaveInstanceState(fragmentBundle);
		
		Question questionFromBundle = (Question) fragmentBundle.get("currentQuestion");
		assertNotNull(questionFromBundle);
		assertEquals( question, questionFromBundle);
		
		assertTrue(fragmentBundle.getBoolean("answerIsShown"));
	}

	public void testShouldDispatchIntentToListWhenIClickOnMenu() throws Exception {
		MenuItem menuItem = new MenuItemStub(R.id.manage_questions_menu);
		activity.onOptionsItemSelected(menuItem);
		Intent manageQuestionsIntent = getStartedActivityIntent();
		assertEquals(QuestionListActivity.MANAGE_QUESTIONS_ACTION, manageQuestionsIntent.getAction());
	}

}
