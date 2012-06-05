package net.fiive.kotoba.test.screen.questionGame;

import android.view.MenuItem;
import android.widget.Button;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;

class QuestionGameScreenUnitTestAutomator implements QuestionGameScreenAutomator {

	private MainActivity activity;
	private QuestionGameFragment fragment;

	public QuestionGameScreenUnitTestAutomator(MainActivity activity, QuestionGameFragment fragment) {
		this.activity = activity;
		this.fragment = fragment;
	}

	public void clickOnNextQuestionButton() {
		Button nextQuestionButton = (Button) activity.findViewById(R.id.nextQuestionButton);
		nextQuestionButton.performClick();
	}

	public void clickOnShowAnswerButton() {
		Button showAnswerButton = (Button) activity.findViewById(R.id.showAnswerButton);
		showAnswerButton.performClick();
	}

	public void clickOnAnswerView() {
		fragment.getAnswerLayout().performClick();
	}

	@Override
	public void selectMenuItem(int itemId) {
		MenuItem menuItem = new MenuItemStub((Integer) itemId);
		activity.onOptionsItemSelected(menuItem);
	}


}
