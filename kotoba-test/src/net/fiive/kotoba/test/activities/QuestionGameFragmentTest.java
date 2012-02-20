package net.fiive.kotoba.test.activities;

import android.view.MenuItem;
import junit.framework.Assert;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.domain.Question;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.TextView;

public class QuestionGameFragmentTest extends
		ActivityUnitTestCase<MainActivity> {

	private MainActivity activity;
	private TextView valueLabel;
	private TextView answerLabel;
	private Button nextQuestionButton;
	private Button showAnswerButton;
	private static final String CLICK_HERE_TO_SEE_ANSWER_TEXT = "Click here to see answer";

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
	}

	private QuestionGameFragment getFragment() {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		return (QuestionGameFragment)fragmentManager.findFragmentById(R.id.questionGameFragment);
	}


	public void testNormalInteration() throws Exception {
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
		
		TextView newTranslationLabel = (TextView) activity.findViewById(R.id.answerLabel);
		assertEquals( "car", newTranslationLabel.getText());
		
	}
	
	public void testShouldSaveBundle() throws Exception {
		nextQuestionButton.performClick();
		showAnswerButton.performClick();
		
		Bundle fragmentBundle = new Bundle();
		QuestionGameFragment fragment = getFragment();
		fragment.onSaveInstanceState(fragmentBundle);
		
		Question question = (Question) fragmentBundle.get("currentQuestion");
		assertNotNull(question);
		assertNotNull(question.getValue());
		
		Assert.assertTrue(fragmentBundle.getBoolean("answerIsShown"));
	}

	public void testShouldDispatchIntentToListWhenIClickOnMenu() throws Exception {
		MenuItem menuItem = new MenuItemStub(R.id.manage_questions_menu);
		activity.onOptionsItemSelected(menuItem);
		Intent manageQuestionsIntent = getStartedActivityIntent();
		Assert.assertEquals(QuestionListActivity.MANAGE_QUESTIONS_ACTION, manageQuestionsIntent.getAction());
	}

}
