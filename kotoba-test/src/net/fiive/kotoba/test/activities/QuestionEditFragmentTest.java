package net.fiive.kotoba.test.activities;


import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.view.MenuItem;
import android.widget.Button;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditFragment;


public class QuestionEditFragmentTest extends ActivityUnitTestCase<QuestionEditActivity> {

	private QuestionEditActivity activity;
	private QuestionEditFragment fragment;


	public QuestionEditFragmentTest() {
		super(QuestionEditActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.startActivity(new Intent(getInstrumentation().getContext(), QuestionEditActivity.class), null, null);
		activity = getActivity();

		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		this.fragment = (QuestionEditFragment)fragmentManager.findFragmentById(R.id.question_edit_fragment);
	}

	public void testShouldGoToHomeWhenUserClicksHomeButton() {
		if (Build.VERSION.SDK_INT >= 11 ) {
			MenuItem menuItem = new MenuItemStub(android.R.id.home);
			activity.onOptionsItemSelected(menuItem);
			Intent goToHomeIntent = getStartedActivityIntent();
			assertEquals(".activities.MainActivity", goToHomeIntent.getComponent().getShortClassName());
		}
	}

	public void testUserClicksCancelButton() {
		Button cancelButton = (Button)this.getActivity().findViewById(R.id.cancel_edit_question);
		cancelButton.performClick();
		Intent backIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", backIntent.getComponent().getShortClassName());
	}

}
