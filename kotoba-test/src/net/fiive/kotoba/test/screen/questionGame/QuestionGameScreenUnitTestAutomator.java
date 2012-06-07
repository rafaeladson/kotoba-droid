package net.fiive.kotoba.test.screen.questionGame;

import android.widget.Button;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.test.screen.base.BaseScreenUnitTestAutomator;

class QuestionGameScreenUnitTestAutomator extends BaseScreenUnitTestAutomator<MainActivity> implements QuestionGameScreenAutomator {

	private QuestionGameFragment fragment;


	public QuestionGameScreenUnitTestAutomator(MainActivity activity, QuestionGameFragment fragment) {
		super(activity);
		this.fragment = fragment;
	}

	public void clickOnNextQuestionButton() {
		Button nextQuestionButton = (Button) getActivity().findViewById(R.id.nextQuestionButton);
		nextQuestionButton.performClick();
	}

	public void clickOnShowAnswerButton() {
		Button showAnswerButton = (Button) getActivity().findViewById(R.id.showAnswerButton);
		showAnswerButton.performClick();
	}

	public void clickOnAnswerView() {
		fragment.getAnswerLayout().performClick();
	}


}
