package net.fiive.kotoba.test.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.game.QuestionGameFragment;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditFragment;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.activities.stub.AlertHelperMock;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;


public class QuestionEditNewFragmentTest extends ActivityUnitTestCase<QuestionEditActivity> {

	private QuestionEditActivity activity;
	private DataService dataService;

	private EditText valueText;
	private EditText answerText;

	QuestionEditFragment fragment;


	public QuestionEditNewFragmentTest() {
		super(QuestionEditActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.startActivity(new Intent(QuestionEditActivity.ADD_QUESTION_ACTION, Uri.parse("kotoba://kotoba.fiive.net/question/new")), null, null);
		activity = getActivity();
		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		dataService = new DataService(getInstrumentation().getTargetContext());
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		 fragment = (QuestionEditFragment) fragmentManager.findFragmentById(R.id.question_edit_fragment);
		fragment.onActivityCreated(null);
		valueText = (EditText) this.getActivity().findViewById(R.id.edit_question_value);
		answerText = (EditText) this.getActivity().findViewById(R.id.edit_question_answer);
	}

	public void testUserClicksCancelButton() {
		Button cancelButton = (Button)this.getActivity().findViewById(R.id.cancel_edit_question);
		cancelButton.performClick();
		Intent backIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", backIntent.getComponent().getShortClassName());
	}

	public void testUserClicksCancelMenu() {
		MenuItem menuItem = new MenuItemStub(R.id.cancel_edit_question_menu);
		fragment.onOptionsItemSelected(menuItem);

		Intent backIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", backIntent.getComponent().getShortClassName());
	}

	public void testUserSaveNewQuestion() {

		valueText.setText("foo");

		answerText.setText("bar");

		Button saveButton = (Button) this.getActivity().findViewById(R.id.save_question);
		saveButton.performClick();

		Question questionFromDb = dataService.findQuestionById(dataService.findAllQuestionIds().get(0));
		assertEquals("foo", questionFromDb.getValue());
		assertEquals("bar", questionFromDb.getAnswer());
	}


}
