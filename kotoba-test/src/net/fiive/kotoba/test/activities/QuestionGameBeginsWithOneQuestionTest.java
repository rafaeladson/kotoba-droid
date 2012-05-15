package net.fiive.kotoba.test.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.TextView;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;

public class QuestionGameBeginsWithOneQuestionTest extends ActivityUnitTestCase<MainActivity> {

	private QuestionGameFragment fragment;
	private DataService dataService;
	private Question savedQuestion;
	private TextView valueTextView;
	private TextView answerTextView;
	private MainActivity activity;

	private static final String CLICK_ANSWER_TO_SEE_ANSWER_TEXT = "Click Answer to see the answer";
	private static final String CLICK_SHOW_ANSWER_TO_SEE_ANSWER_TEXT = "Click Show Answer to see the answer";


	public QuestionGameBeginsWithOneQuestionTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		dataService = new DataService(getInstrumentation().getTargetContext());
		savedQuestion = new Question("foo", "bar");
		savedQuestion = dataService.saveOrUpdateQuestion(savedQuestion);

		startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null);
		activity = this.getActivity();
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		fragment = (QuestionGameFragment) fragmentManager.findFragmentById(R.id.questionGameFragment);

		fragment.onActivityCreated(null);

		Button showAnswerButton = (Button) activity.findViewById(R.id.showAnswerButton);
		showAnswerButton.performClick();

		valueTextView = (TextView) activity.findViewById(R.id.questionLabel);
		answerTextView = (TextView) activity.findViewById(R.id.answerLabel);


	}

	public void testOnResumeWithSameItem() {
		fragment.onResume();
		assertEquals("bar", answerTextView.getText().toString());
	}

	public void testOnResumeWithZeroItems() {
		dataService.removeQuestion(savedQuestion);
		fragment.onResume();
		String howDoIUseKotobaQuestionString = activity.getResources().getString(R.string.how_do_i_use_kotoba_question);
		assertEquals(howDoIUseKotobaQuestionString, valueTextView.getText().toString());
	}

	public void testOnResumeWithTwoItems() {
		Question anotherQuestion = new Question("testing", "question");
		dataService.saveOrUpdateQuestion(anotherQuestion);

		fragment.onResume();
		assertAnswerIsHidden(answerTextView);
	}

	public void testOnResumeAfterUpdate() {
		fragment.showNextQuestion();
		savedQuestion.setValue("bar");
		dataService.saveOrUpdateQuestion(savedQuestion);

		fragment.onResume();
		assertEquals("bar", valueTextView.getText().toString());
	}

	private void assertAnswerIsHidden(TextView answerTextView) {
		assertTrue(CLICK_ANSWER_TO_SEE_ANSWER_TEXT.equals(answerTextView.getText()) || CLICK_SHOW_ANSWER_TO_SEE_ANSWER_TEXT.equals(answerTextView.getText()));
	}

}
