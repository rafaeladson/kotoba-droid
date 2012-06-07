package net.fiive.kotoba.test.screen.questionGame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.screen.base.BaseScreen;

public class QuestionGameScreen extends BaseScreen<MainActivity, QuestionGameFragment> {

	static final int DEFAULT_WAIT_TIME = 100;
	private MainActivity activity;
	private TestIds testIds;


	public static QuestionGameScreen screenForUnitTest(MainActivity activity) {
		QuestionGameFragment fragment = getFragmentFromActivity(activity);
		return new QuestionGameScreen(activity, fragment, new QuestionGameScreenUnitTestScreenAutomator(activity, fragment), new TestIds.UnitTestIds());
	}

	public static QuestionGameScreen screenForAcceptanceTest(MainActivity activity, Solo solo) {
		QuestionGameFragment fragment = getFragmentFromActivity(activity);
		return new QuestionGameScreen(activity, fragment, new QuestionGameScreenSoloScreenAutomator(activity, solo), new TestIds.SoloTestIds());
	}

	private QuestionGameScreen(MainActivity activity, QuestionGameFragment fragment, QuestionGameScreenAutomator automator, TestIds testIds) {
		super(activity, fragment, automator);
		this.activity = activity;
		this.testIds = testIds;

	}

	public void clickOnNextQuestionButton() {
		getAutomator().clickOnButton(testIds.getNextQuestionButtonId());
	}

	public void clickOnShowAnswerButton() {
		getAutomator().clickOnButton(testIds.getShowAnswerButtonId());
		sleep(DEFAULT_WAIT_TIME);
	}

	public String getValueText() {
		TextView valueView = (TextView) activity.findViewById(R.id.questionLabel);
		return valueView.getText().toString();
	}

	public String getAnswerText() {
		if (isAnswerVisible()) {
			TextView answerView = (TextView) activity.findViewById(R.id.answer_label);
			return answerView.getText().toString();
		} else {
			throw new IllegalStateException("Error: You can only get answer text when answer is visible");
		}
	}

	public void clickOnAnswerView() {
		((QuestionGameScreenAutomator) getAutomator()).clickOnAnswerView();

	}

	public boolean isAnswerVisible() {
		return getFragment().isAnswerVisible();
	}


	public void setCurrentQuestion(Question question) {
		getFragment().setCurrentQuestion(question);
	}

	public boolean hasDefaultQuestionValue() {
		return activity.getResources().getText(R.string.how_do_i_use_kotoba_question).equals(getValueText());
	}

	public boolean hasDefaultAnswerValue() {
		return activity.getResources().getText(R.string.how_do_i_use_kotoba_answer).equals(getAnswerText());
	}

	@SuppressWarnings("unchecked")
	private static <F extends Fragment, A extends FragmentActivity> F getFragmentFromActivity(A activity) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		return (F) fragmentManager.findFragmentById(R.id.questionGameFragment);
	}

}
