package net.fiive.test;

import net.fiive.R;
import net.fiive.activities.WordGameActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

public class WordGameActivityTest extends
		ActivityInstrumentationTestCase2<WordGameActivity> {

	private WordGameActivity activity;
	private TextView valueLabel;
	private TextView translationLabel;
	private Button nextWordButton;
	private Button showTranslationButton;

	public WordGameActivityTest() {
		super("net.fiive.activities", WordGameActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = this.getActivity();
		valueLabel = (TextView) activity.findViewById(R.id.valueLabel);
		translationLabel = (TextView) activity
				.findViewById(R.id.translationLabel);
		nextWordButton = (Button) activity.findViewById(R.id.nextWordButton);
		showTranslationButton = (Button) activity
				.findViewById(R.id.showTranslationButton);
	}

	@UiThreadTest
	public void testNormalInteration() throws Exception {
		final CharSequence firstWord = valueLabel.getText();
		assertNotNull(valueLabel.getText());
		assertEquals("", translationLabel.getText());

		showTranslationButton.performClick();
		assertFalse("".equals(translationLabel.getText()));
		nextWordButton.performClick();
		assertEquals("", translationLabel.getText());
		assertFalse(firstWord.equals(valueLabel.getText()));

	}

}
