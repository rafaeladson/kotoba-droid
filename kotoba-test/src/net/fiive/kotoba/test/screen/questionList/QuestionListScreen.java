package net.fiive.kotoba.test.screen.questionList;

import android.app.Instrumentation;
import android.os.Build;
import com.google.common.base.Preconditions;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.activities.questionList.QuestionListFragment;
import net.fiive.kotoba.test.screen.base.BaseListScreen;
import net.fiive.kotoba.test.screen.base.ListScreenMechanize;
import net.fiive.kotoba.test.screen.base.SoloListScreenMechanize;
import net.fiive.kotoba.test.screen.base.UnitTestListScreenMechanize;

public class QuestionListScreen extends BaseListScreen<QuestionListActivity, QuestionListFragment> {

	private TestIds testIds;

	public static QuestionListScreen screenForUnitTests(QuestionListActivity activity) {
		ListScreenMechanize automator = new UnitTestListScreenMechanize<QuestionListActivity, QuestionListFragment>(activity);
		TestIds testIds = new UnitTestIds();
		return new QuestionListScreen(activity, automator, testIds);

	}

	public static QuestionListScreen screenForAcceptanceTests(Instrumentation instrumentation, Solo solo) {
		QuestionListActivity activity = (QuestionListActivity) solo.getCurrentActivity();
		ListScreenMechanize automator = new SoloListScreenMechanize<QuestionListActivity>(instrumentation, solo);
		TestIds testIds = new SoloTestIds();
		return new QuestionListScreen(activity, automator, testIds);
	}

	private QuestionListScreen(QuestionListActivity activity, ListScreenMechanize mechanize, TestIds testIds) {
		super(activity, mechanize);
		this.testIds = testIds;
	}

	public void selectNewQuestionMenu() {
		getMechanize().selectMenuItem(testIds.getNewQuestionMenuId());
	}

	public void selectInfoMenu() {
		getMechanize().selectMenuItem(testIds.getInfoMenuId());
	}

	public void clickOnAddNewQuestion() {
		Preconditions.checkState(Build.VERSION.SDK_INT < 11, "Error: Method can only be run on android gingerbread or older");
		this.clickOnView(testIds.getAddNewQuestionItemId());
	}


}
