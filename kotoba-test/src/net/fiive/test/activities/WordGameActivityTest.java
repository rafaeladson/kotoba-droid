package net.fiive.test.activities;

import net.fiive.R;
import net.fiive.activities.WordGameActivity;
import net.fiive.domain.Word;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

public class WordGameActivityTest extends
		ActivityUnitTestCase<WordGameActivity> {

	private WordGameActivity activity;
	private TextView valueLabel;
	private TextView translationLabel;
	private Button nextWordButton;
	private Button showTranslationButton;

	public WordGameActivityTest() {
		super(WordGameActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startActivity(new Intent(getInstrumentation().getTargetContext(), WordGameActivity.class), null, null );
		activity = this.getActivity();
		valueLabel = (TextView) activity.findViewById(R.id.valueLabel);
		translationLabel = (TextView) activity
				.findViewById(R.id.translationLabel);
		nextWordButton = (Button) activity.findViewById(R.id.nextWordButton);
		showTranslationButton = (Button) activity
				.findViewById(R.id.showTranslationButton);
		
		activity.setWordRepository(new WordRepositoryStub());
	}


	public void testNormalInteration() throws Exception {
		final CharSequence firstWord = valueLabel.getText();
		assertNotNull(firstWord);
		
		nextWordButton.performClick();
		
		assertEquals("Word", valueLabel.getText());
		assertEquals("", translationLabel.getText());

		showTranslationButton.performClick();
		assertEquals("Wort", translationLabel.getText());
		nextWordButton.performClick();
		assertEquals("", translationLabel.getText());
		assertEquals( "Work", valueLabel.getText() );
		

	}
	
	
	@UiThreadTest
	public void testRestoreFromBundle() throws Exception {
		
		Bundle newActivityBundle = new Bundle();
		newActivityBundle.putSerializable("currentWord", new Word("das Auto", "car"));
		newActivityBundle.putBoolean("translationIsShown", true);
		
		activity.finish();
		Instrumentation instrumentation = this.getInstrumentation();
		instrumentation.callActivityOnDestroy(activity);
		instrumentation.callActivityOnCreate(activity, newActivityBundle);
		
		TextView newTranslationLabel = (TextView) activity.findViewById(R.id.translationLabel);
		assertEquals( "car", newTranslationLabel.getText());
		
		
		
	}

}
