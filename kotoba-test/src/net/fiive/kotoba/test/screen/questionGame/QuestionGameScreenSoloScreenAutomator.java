package net.fiive.kotoba.test.screen.questionGame;

import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.test.screen.base.SoloScreenAutomator;

public class QuestionGameScreenSoloScreenAutomator extends SoloScreenAutomator<MainActivity> implements QuestionGameScreenAutomator {


	public QuestionGameScreenSoloScreenAutomator(MainActivity activity, Solo solo) {
		super(activity, solo);
	}

	@Override
	public void clickOnAnswerView() {
		getSolo().clickOnText(getActivity().getResources().getString(R.string.click_answer_to_see_answer));
	}


}
