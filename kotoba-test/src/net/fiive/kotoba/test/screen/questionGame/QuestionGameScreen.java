package net.fiive.kotoba.test.screen.questionGame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.domain.Question;

public class QuestionGameScreen {

	private MainActivity activity;
	private QuestionGameFragment fragment;
	private QuestionGameScreenAutomator automator;


	public static QuestionGameScreen screenForUnitTest(MainActivity activity) {
		QuestionGameFragment fragment = getFragmentFromActivity(activity);
		return new QuestionGameScreen(activity, fragment, new QuestionGameScreenUnitTestAutomator(activity, fragment));
	}

	public static QuestionGameScreen screenForAcceptanceTest(MainActivity activity, Solo solo) {
		QuestionGameFragment fragment = getFragmentFromActivity(activity);
		return new QuestionGameScreen(activity, fragment, new QuestionGameScreenSoloAutomator(activity, solo));
	}

	private QuestionGameScreen(MainActivity activity, QuestionGameFragment fragment, QuestionGameScreenAutomator automator) {
		this.activity = activity;
		this.fragment = fragment;
		this.automator = automator;
	}

	public void clickOnNextQuestionButton() {
		automator.clickOnNextQuestionButton();
	}

	public void clickOnShowAnswerButton() {
		automator.clickOnShowAnswerButton();
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
		automator.clickOnAnswerView();

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
		automator.selectMenuItem(itemId);
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

	@SuppressWarnings("unchecked")
	private static <F extends Fragment, A extends FragmentActivity> F getFragmentFromActivity(A activity) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		return (F) fragmentManager.findFragmentById(R.id.questionGameFragment);
	}

}
