package net.fiive.kotoba.test.screen.questionGame;

import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.test.screen.base.BaseScreenSoloAutomator;

public class QuestionGameScreenSoloAutomator extends BaseScreenSoloAutomator<MainActivity> implements QuestionGameScreenAutomator {


	public QuestionGameScreenSoloAutomator(MainActivity activity, Solo solo) {
		super(activity, solo);
	}

	@Override
	public void clickOnNextQuestionButton() {
		getSolo().clickOnButton(getActivity().getResources().getString(R.string.next_question));
	}

	@Override
	public void clickOnShowAnswerButton() {
		getSolo().clickOnButton(getActivity().getResources().getString(R.string.show_answer));
	}

	@Override
	public void clickOnAnswerView() {
		getSolo().clickOnText(getActivity().getResources().getString(R.string.click_answer_to_see_answer));
	}


}
