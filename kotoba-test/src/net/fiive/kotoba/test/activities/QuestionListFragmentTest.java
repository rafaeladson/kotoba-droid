package net.fiive.kotoba.test.activities;


import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.test.ActivityUnitTestCase;
import android.view.MenuItem;
import android.view.View;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.info.InfoActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.activities.questionList.QuestionListFragment;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;

public class QuestionListFragmentTest extends ActivityUnitTestCase<QuestionListActivity> {

	private QuestionListActivity activity;


	public QuestionListFragmentTest() {
		super(QuestionListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.startActivity(new Intent(getInstrumentation().getTargetContext(), QuestionListActivity.class), null, null);
		activity = this.getActivity();
	}

	public void testShouldDispatchIntentWhenUserClicksOnAItem() {
		ListFragment fragment = this.getFragment();
		fragment.onListItemClick(fragment.getListView(), null, 0, 0);
		Intent editQuestionIntent = this.getStartedActivityIntent();
		assertEquals(QuestionEditActivity.EDIT_QUESTION_ACTION, editQuestionIntent.getAction());
		assertEquals("kotoba://kotoba.fiive.net/question/0", editQuestionIntent.getDataString());
	}


	public void testShouldDispatchIntentWhenUserClicksOnNewQuestionMenu() {
		MenuItem item = new MenuItemStub(R.id.add_question_menu);
		activity.onOptionsItemSelected(item);
		assertAddNewQuestionIntentWasDispatched();
	}

	public void testSeeInfo() {
		MenuItem item = new MenuItemStub(R.id.info_menu);
		activity.onOptionsItemSelected(item);

		Intent infoIntent = this.getStartedActivityIntent();
		assertEquals(InfoActivity.VIEW_INFO_ACTION, infoIntent.getAction());
	}

	public void testShouldDispatchNewQuestionIntentWhenUserClicksOnAddNewQuestionOnTheTable() {
		if (Build.VERSION.SDK_INT < 11) {
			View clickToAddView = this.getActivity().findViewById(R.id.question_add_new_link);
			clickToAddView.performClick();
			assertAddNewQuestionIntentWasDispatched();
		}
	}

	private QuestionListFragment getFragment() {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		return (QuestionListFragment) fragmentManager.findFragmentById(R.id.question_list_fragment);
	}

	private void assertAddNewQuestionIntentWasDispatched() {
		Intent addQuestionIntent = this.getStartedActivityIntent();
		assertEquals(QuestionEditActivity.ADD_QUESTION_ACTION, addQuestionIntent.getAction());
		assertEquals("kotoba://kotoba.fiive.net/question/new", addQuestionIntent.getDataString());
	}
}
