package net.fiive.kotoba.test.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;

public class QuestionGameScreen {

	private MainActivity activity;
	private QuestionGameFragment fragment;

	public QuestionGameScreen(MainActivity activity) {
		this.activity = activity;
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		fragment = (QuestionGameFragment) fragmentManager.findFragmentById(R.id.questionGameFragment);

	}

	public void clickOnNextQuestionButton() {
		Button nextQuestionButton = (Button) activity.findViewById(R.id.nextQuestionButton);
		nextQuestionButton.performClick();
	}

	public void clickOnShowAnswerButton() {
		Button showAnswerButton = (Button) activity.findViewById(R.id.showAnswerButton);
		showAnswerButton.performClick();
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
		fragment.getAnswerLayout().performClick();

	}

	public boolean isAnswerVisible() {
		return fragment.isAnswerVisible();
	}

	public void onActivityCreated(Bundle bundle) {
		this.fragment.onActivityCreated(bundle);
	}

	public void onSaveInstanceState(Bundle bundle) {
		fragment.onSaveInstanceState(bundle);
	}

	public void onResume() {
		fragment.onResume();
	}

	public void selectMenuItem(int itemId) {
		MenuItem menuItem = new MenuItemStub(itemId);
		activity.onOptionsItemSelected(menuItem);
	}

	public void setCurrentQuestion(Question question) {
		fragment.setCurrentQuestion(question);
	}

	public boolean hasDefaultQuestionValue() {
		return activity.getResources().getText(R.string.how_do_i_use_kotoba_question).equals(getValueText());
	}

	public boolean hasDefaultAnswerValue() {
		return activity.getResources().getText(R.string.how_do_i_use_kotoba_answer).equals(getAnswerText());
	}

}
