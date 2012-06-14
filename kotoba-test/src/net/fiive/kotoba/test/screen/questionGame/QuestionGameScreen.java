package net.fiive.kotoba.test.screen.questionGame;

import android.app.Instrumentation;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.screen.base.BaseScreen;
import net.fiive.kotoba.test.screen.base.ScreenMechanize;
import net.fiive.kotoba.test.screen.base.SoloScreenMechanize;
import net.fiive.kotoba.test.screen.base.UnitTestScreenMechanize;

public class QuestionGameScreen extends BaseScreen<MainActivity, QuestionGameFragment> {

	static final int DEFAULT_WAIT_TIME = 100;
	private MainActivity activity;
	private TestIds testIds;


	public static QuestionGameScreen screenForUnitTest(MainActivity activity) {
		return new QuestionGameScreen(activity, new UnitTestScreenMechanize<MainActivity, QuestionGameFragment>(activity), new TestIds.UnitTestIds());
	}

	public static QuestionGameScreen screenForAcceptanceTest(Instrumentation instrumentation, Solo solo) {
		MainActivity activity = (MainActivity) solo.getCurrentActivity();
		return new QuestionGameScreen(activity, new SoloScreenMechanize<MainActivity>(instrumentation, solo), new TestIds.SoloTestIds());
	}

	private QuestionGameScreen(MainActivity activity, ScreenMechanize mechanize, TestIds testIds) {
		super(activity, mechanize);
		this.activity = activity;
		this.testIds = testIds;

	}

	public void clickOnNextQuestionButton() {
		getMechanize().clickOnButton(testIds.getNextQuestionButtonId());
	}

	public void clickOnShowAnswerButton() {
		getMechanize().clickOnButton(testIds.getShowAnswerButtonId());
		sleep(DEFAULT_WAIT_TIME);
	}

	public void selectManageQuestionsMenu() {
		getMechanize().selectMenuItem(testIds.getManageQuestionsMenuId());
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
		getMechanize().clickOnView(R.id.answer_frame_layout);

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


}
