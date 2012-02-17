package net.fiive.kotoba.test.activities;

import junit.framework.Assert;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.QuestionGameFragment;
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
	private TextView translationLabel;
	private Button nextWordButton;
	private Button showTranslationButton;
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
		translationLabel = (TextView) activity
				.findViewById(R.id.answerLabel);
		nextWordButton = (Button) activity.findViewById(R.id.nextQuestionButton);
		showTranslationButton = (Button) activity
				.findViewById(R.id.showAnswerButton);
	
		QuestionGameFragment questionGameFragment = getFragment();
		
		
		questionGameFragment.setQuestionRepository(new QuestionRepositoryStub());
	}

	private QuestionGameFragment getFragment() {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		return (QuestionGameFragment)fragmentManager.findFragmentById(R.id.questionGameFragment);
	}


	public void testNormalInteration() throws Exception {
		final CharSequence firstWord = valueLabel.getText();
		assertNotNull(firstWord);
		
		nextWordButton.performClick();
		
		assertEquals("Question", valueLabel.getText());

		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, translationLabel.getText());

		showTranslationButton.performClick();
		assertEquals("Wort", translationLabel.getText());
		nextWordButton.performClick();
		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, translationLabel.getText());
		assertEquals( "Work", valueLabel.getText() );
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
		nextWordButton.performClick();
		showTranslationButton.performClick();
		
		Bundle fragmentBundle = new Bundle();
		QuestionGameFragment fragment = getFragment();
		fragment.onSaveInstanceState(fragmentBundle);
		
		Question question = (Question) fragmentBundle.get("currentQuestion");
		assertEquals( question.getValue(), "Question");
		
		Assert.assertTrue(fragmentBundle.getBoolean("answerIsShown"));
		
		
	}

}
