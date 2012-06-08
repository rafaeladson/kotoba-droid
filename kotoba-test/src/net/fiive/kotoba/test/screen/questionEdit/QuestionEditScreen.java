package net.fiive.kotoba.test.screen.questionEdit;

import com.google.common.base.Optional;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditFragment;
import net.fiive.kotoba.test.screen.base.BaseScreen;
import net.fiive.kotoba.test.screen.base.ScreenAutomator;
import net.fiive.kotoba.test.screen.base.SoloScreenAutomator;
import net.fiive.kotoba.test.screen.base.UnitTestScreenAutomator;

public class QuestionEditScreen extends BaseScreen<QuestionEditActivity, QuestionEditFragment> {

	static final int QUESTION_VALUE_ID = R.id.edit_question_value;
	static final int QUESTION_ANSWER_ID = R.id.edit_question_answer;
	private TestIds testIds;


	public static QuestionEditScreen screenForUnitTests(QuestionEditActivity activity) {
		QuestionEditFragment fragment = (QuestionEditFragment) activity.getSupportFragmentManager().findFragmentById(R.id.question_edit_fragment);
		UnitTestScreenAutomator<QuestionEditActivity, QuestionEditFragment> automator = new UnitTestScreenAutomator<QuestionEditActivity, QuestionEditFragment>(activity, fragment);
		TestIds ids = new TestIds.UnitTestIds();
		return new QuestionEditScreen(activity, fragment, automator, ids);
	}

	public static QuestionEditScreen screenForAcceptanceTests(QuestionEditActivity activity, Solo solo) {
		QuestionEditFragment fragment = (QuestionEditFragment) activity.getSupportFragmentManager().findFragmentById(R.id.question_edit_fragment);
		SoloScreenAutomator<QuestionEditActivity> automator = new SoloScreenAutomator<QuestionEditActivity>(activity, solo);
		TestIds ids = new TestIds.SoloTestIds();
		return new QuestionEditScreen(activity, fragment, automator, ids);
	}

	private QuestionEditScreen(QuestionEditActivity activity, QuestionEditFragment fragment, ScreenAutomator automator, TestIds ids) {
		super(activity, fragment, automator);
		this.testIds = ids;

	}


	public void fillQuestionAndAnswer(String value, String answer) {
		getAutomator().typeTextInEditText(QUESTION_VALUE_ID, value);
		getAutomator().typeTextInEditText(QUESTION_ANSWER_ID, answer);
	}

	public void selectSaveMenuItem() {
		getAutomator().selectMenuItem(testIds.getSaveMenuItemId());
	}

	public void selectSaveAndNewMenuItem() {
		getAutomator().selectMenuItem(testIds.getSaveAndNewButtonId());

	}

	public void removeCurrentQuestion() {
		getAutomator().selectRemoveMenuItem(testIds.getRemoveMenuItemId(), testIds.getAlertOkButtonId());

	}


	public void clickOnCancelButton() {
		getAutomator().clickOnButton(testIds.getCancelButtonId());
	}

	public void selectCancelMenuItem() {
		getAutomator().selectMenuItem(testIds.getCancelMenuItemId());
	}

	public String getValueText() {
		return this.getTextFromEditText(QUESTION_VALUE_ID);
	}

	public String getAnswerText() {
		return this.getTextFromEditText(QUESTION_ANSWER_ID);
	}

	public Optional<String> getValueTextValidationError() {
		return this.getErrorFromEditText(QUESTION_VALUE_ID);
	}
}


