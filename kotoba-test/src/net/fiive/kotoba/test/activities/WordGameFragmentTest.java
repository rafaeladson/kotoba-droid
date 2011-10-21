package net.fiive.kotoba.test.activities;

import junit.framework.Assert;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.WordGameFragment;
import net.fiive.kotoba.domain.Word;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.TextView;

public class WordGameFragmentTest extends
		ActivityUnitTestCase<MainActivity> {

	private MainActivity activity;
	private TextView valueLabel;
	private TextView translationLabel;
	private Button nextWordButton;
	private Button showTranslationButton;
	private static final String CLICK_HERE_TO_SEE_ANSWER_TEXT = "Click here to see answer";

	public WordGameFragmentTest() {
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
	
		WordGameFragment wordGameFragment = getFragment();
		
		
		wordGameFragment.setWordRepository(new WordRepositoryStub());
	}

	private WordGameFragment getFragment() {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		WordGameFragment wordGameFragment = (WordGameFragment)fragmentManager.findFragmentById(R.id.wordGameFragment);
		return wordGameFragment;
	}


	public void testNormalInteration() throws Exception {
		final CharSequence firstWord = valueLabel.getText();
		assertNotNull(firstWord);
		
		nextWordButton.performClick();
		
		assertEquals("Word", valueLabel.getText());

		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, translationLabel.getText());

		showTranslationButton.performClick();
		assertEquals("Wort", translationLabel.getText());
		nextWordButton.performClick();
		assertEquals(CLICK_HERE_TO_SEE_ANSWER_TEXT, translationLabel.getText());
		assertEquals( "Work", valueLabel.getText() );
	}
	
	
	public void testRestoreFromBundle() throws Exception {
		
		Bundle newActivityBundle = new Bundle();
		newActivityBundle.putSerializable("currentQuestion", new Word("das Auto", "car"));
		newActivityBundle.putBoolean("answerIsShown", true);
		
		WordGameFragment fragment = getFragment();
		fragment.onActivityCreated(newActivityBundle);
		
		TextView newTranslationLabel = (TextView) activity.findViewById(R.id.answerLabel);
		assertEquals( "car", newTranslationLabel.getText());
		
	}
	
	public void testShouldSaveBundle() throws Exception {
		nextWordButton.performClick();
		showTranslationButton.performClick();
		
		Bundle fragmentBundle = new Bundle();
		WordGameFragment fragment = getFragment();
		fragment.onSaveInstanceState(fragmentBundle);
		
		Word word = (Word) fragmentBundle.get("currentQuestion");
		assertEquals( word.getValue(), "Word");
		
		Assert.assertTrue(fragmentBundle.getBoolean("answerIsShown"));
		
		
	}

}
