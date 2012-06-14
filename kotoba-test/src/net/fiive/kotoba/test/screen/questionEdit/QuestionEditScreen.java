package net.fiive.kotoba.test.screen.questionEdit;

import android.app.Instrumentation;
import com.google.common.base.Optional;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditFragment;
import net.fiive.kotoba.test.screen.base.BaseScreen;
import net.fiive.kotoba.test.screen.base.ScreenMechanize;
import net.fiive.kotoba.test.screen.base.SoloScreenMechanize;
import net.fiive.kotoba.test.screen.base.UnitTestScreenMechanize;

public class QuestionEditScreen extends BaseScreen<QuestionEditActivity, QuestionEditFragment> {

	static final int QUESTION_VALUE_ID = R.id.edit_question_value;
	static final int QUESTION_ANSWER_ID = R.id.edit_question_answer;
	private TestIds testIds;


	public static QuestionEditScreen screenForUnitTests(QuestionEditActivity activity) {
		UnitTestScreenMechanize<QuestionEditActivity, QuestionEditFragment> automator = new UnitTestScreenMechanize<QuestionEditActivity, QuestionEditFragment>(activity);
		TestIds ids = new TestIds.UnitTestIds();
		return new QuestionEditScreen(activity, automator, ids);
	}

	public static QuestionEditScreen screenForAcceptanceTests(Instrumentation instrumentation, Solo solo) {
		SoloScreenMechanize<QuestionEditActivity> automator = new SoloScreenMechanize<QuestionEditActivity>(instrumentation, solo);
		QuestionEditActivity activity = (QuestionEditActivity) solo.getCurrentActivity();
		TestIds ids = new TestIds.SoloTestIds();
		return new QuestionEditScreen(activity, automator, ids);
	}

	private QuestionEditScreen(QuestionEditActivity activity, ScreenMechanize mechanize, TestIds ids) {
		super(activity, mechanize);
		this.testIds = ids;

	}


	public void fillQuestionAndAnswer(String value, String answer) {
		getMechanize().typeTextInEditText(QUESTION_VALUE_ID, value);
		getMechanize().typeTextInEditText(QUESTION_ANSWER_ID, answer);
	}

	public void selectSaveMenuItem() {
		getMechanize().selectMenuItem(testIds.getSaveMenuItemId());
	}

	public void selectSaveAndNewMenuItem() {
		getMechanize().selectMenuItem(testIds.getSaveAndNewButtonId());

	}

	public void removeCurrentQuestion() {
		getMechanize().selectRemoveMenuItem(testIds.getRemoveMenuItemId(), testIds.getAlertOkButtonId());

	}


	public void clickOnCancelButton() {
		getMechanize().clickOnButton(testIds.getCancelButtonId());
	}

	public void selectCancelMenuItem() {
		getMechanize().selectMenuItem(testIds.getCancelMenuItemId());
	}

	public String getValueText() {
		return this.getEditTextContents(QUESTION_VALUE_ID);
	}

	public String getAnswerText() {
		return this.getEditTextContents(QUESTION_ANSWER_ID);
	}

	public Optional<String> getValueTextValidationError() {
		return this.getErrorFromEditText(QUESTION_VALUE_ID);
	}
}


