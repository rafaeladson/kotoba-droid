package net.fiive.kotoba.test.screen.questionList;

import android.os.Build;
import com.google.common.base.Preconditions;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.activities.questionList.QuestionListFragment;
import net.fiive.kotoba.test.screen.base.BaseListScreen;
import net.fiive.kotoba.test.screen.base.ListScreenAutomator;
import net.fiive.kotoba.test.screen.base.SoloListScreenAutomator;
import net.fiive.kotoba.test.screen.base.UnitTestListScreenAutomator;

public class QuestionListScreen extends BaseListScreen<QuestionListActivity, QuestionListFragment> {

	private TestIds testIds;

	public static QuestionListScreen screenForUnitTests(QuestionListActivity activity) {
		QuestionListFragment fragment = (QuestionListFragment) activity.getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
		ListScreenAutomator automator = new UnitTestListScreenAutomator<QuestionListActivity, QuestionListFragment>(activity, fragment);
		TestIds testIds = new UnitTestIds();
		return new QuestionListScreen(activity, fragment, automator, testIds);

	}

	public static QuestionListScreen screenForAcceptanceTests(QuestionListActivity activity, Solo solo) {
		QuestionListFragment fragment = (QuestionListFragment) activity.getSupportFragmentManager().findFragmentById(R.id.question_list_fragment);
		ListScreenAutomator automator = new SoloListScreenAutomator<QuestionListActivity>(activity, solo);
		TestIds testIds = new SoloTestIds();
		return new QuestionListScreen(activity, fragment, automator, testIds);
	}

	private QuestionListScreen(QuestionListActivity activity, QuestionListFragment fragment, ListScreenAutomator automator, TestIds testIds) {
		super(activity, fragment, automator);
		this.testIds = testIds;
	}

	public <T> void clickOnQuestion(T itemId) {
		this.clickOnListItem(itemId);
	}

	public void selectNewQuestionMenu() {
		this.selectMenuItem(testIds.getNewQuestionMenuId());
	}

	public void selectInfoMenu() {
		this.selectMenuItem(testIds.getInfoMenuId());
	}

	public void clickOnAddNewQuestion() {
		Preconditions.checkState(Build.VERSION.SDK_INT < 11, "Error: Method can only be run on android gingerbread or older");
		this.clickOnView(testIds.getAddNewQuestionItemId());
	}


}
