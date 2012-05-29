package net.fiive.kotoba.test.screen.questionGame;

import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;

public class QuestionGameScreenSoloAutomator implements QuestionGameScreenAutomator {

	private MainActivity activity;
	private Solo solo;

	public QuestionGameScreenSoloAutomator(MainActivity activity, Solo solo) {
		this.activity = activity;
		this.solo = solo;
	}

	@Override
	public void clickOnNextQuestionButton() {
		solo.clickOnButton(activity.getResources().getString(R.string.next_question));
	}

	@Override
	public void clickOnShowAnswerButton() {
		solo.clickOnButton(activity.getResources().getString(R.string.show_answer));
	}

	@Override
	public void clickOnAnswerView() {
		solo.clickOnText(activity.getResources().getString(R.string.click_answer_to_see_answer));
	}

	@Override
	public void selectMenuItem(int itemId) {
		solo.clickOnMenuItem(activity.getResources().getString(itemId));
	}
}
