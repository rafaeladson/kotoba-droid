package net.fiive.kotoba.test.screen.questionGame;

import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.test.screen.base.UnitTestScreenAutomator;

class QuestionGameScreenUnitTestScreenAutomator extends UnitTestScreenAutomator<MainActivity, QuestionGameFragment> implements QuestionGameScreenAutomator {

	private QuestionGameFragment fragment;


	public QuestionGameScreenUnitTestScreenAutomator(MainActivity activity, QuestionGameFragment fragment) {
		super(activity, fragment);

	}

	public void clickOnAnswerView() {
		getFragment().getAnswerLayout().performClick();
	}


}
