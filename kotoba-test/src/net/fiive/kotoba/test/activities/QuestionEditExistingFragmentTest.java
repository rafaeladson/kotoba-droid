package net.fiive.kotoba.test.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditFragment;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.activities.stub.AlertHelperMock;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;

public class QuestionEditExistingFragmentTest extends ActivityUnitTestCase<QuestionEditActivity> {


	private QuestionEditActivity activity;
	private DataService dataService;
	private Long currentQuestionId;
	private QuestionEditFragment fragment;
	private EditText questionValueText;
	private EditText questionAnswerText;
	private Button saveQuestionButton;



	public QuestionEditExistingFragmentTest() {
		super(QuestionEditActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		dataService = new DataService(getInstrumentation().getTargetContext());

		Question questionInDatabase = new Question("foo", "bar");
		questionInDatabase = dataService.saveOrUpdateQuestion(questionInDatabase);
		currentQuestionId = questionInDatabase.getId();


		this.startActivity(new Intent(QuestionEditActivity.EDIT_QUESTION_ACTION, Uri.parse("kotoba://kotoba.fiive.net/question/" + questionInDatabase.getId())), null, null);
		activity = getActivity();
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		fragment = (QuestionEditFragment)fragmentManager.findFragmentById(R.id.question_edit_fragment);

		questionValueText = (EditText) activity.findViewById(R.id.edit_question_value);
		questionAnswerText = (EditText) activity.findViewById(R.id.edit_question_answer);
		saveQuestionButton = (Button) activity.findViewById(R.id.save_question);

		fragment.onActivityCreated(null);

	}

	public void testEditQuestion() {
		questionValueText.setText("hello");
		questionAnswerText.setText("world");

		saveQuestionButton.performClick();

		Question questionFromDatabase = dataService.findQuestionById(currentQuestionId);
		assertEquals("hello", questionFromDatabase.getValue());
		assertEquals("world", questionFromDatabase.getAnswer());

		Intent goBackIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", goBackIntent.getComponent().getShortClassName());
	}

	public void testRestoreFromBundle() {
		Bundle bundle = new Bundle();
		bundle.putSerializable(QuestionEditFragment.QUESTION_BUNDLE_KEY, new Question("fromBundleValue", "fromBundleAnswer"));
		bundle.putBoolean(QuestionEditFragment.IS_EDITING_BUNDLE_KEY, true);
		fragment.onActivityCreated(bundle);


		assertEquals("fromBundleValue", questionValueText.getText().toString());
		assertEquals( "fromBundleAnswer", questionAnswerText.getText().toString());
	}

	public void testSaveBundleState() {
		fragment.onActivityCreated(null);

		Bundle bundle = new Bundle();
		fragment.onSaveInstanceState(bundle);

		assertTrue( bundle.containsKey(QuestionEditFragment.QUESTION_BUNDLE_KEY));
		Question question = (Question) bundle.getSerializable(QuestionEditFragment.QUESTION_BUNDLE_KEY);
		assertEquals( "foo", question.getValue());
		assertEquals( "bar", question.getAnswer());

		assertTrue( bundle.getBoolean(QuestionEditFragment.IS_EDITING_BUNDLE_KEY));
	}

	public void testUserUpdateQuestionWithNoValue() {
		AlertHelperMock alertHelperMock = new AlertHelperMock();
		fragment.mockAlertHelper(alertHelperMock);

		questionValueText.setText("");
		questionAnswerText.setText("question_with_no_value");
		saveQuestionButton.performClick();

		assertTrue( alertHelperMock.showErrorAlertWasCalled());
		Question questionFromDb = dataService.findQuestionById(currentQuestionId);
		assertFalse(questionFromDb.getAnswer().equals("question_with_no_value"));
	}

	public void testUserUpdateQuestionWithNoAnswer() {
		AlertHelperMock alertHelperMock = new AlertHelperMock();
		fragment.mockAlertHelper(alertHelperMock);

		questionValueText.setText("question_with_no_answer");
		questionAnswerText.setText("");
		saveQuestionButton.performClick();

		assertTrue( alertHelperMock.showErrorAlertWasCalled());
		Question questionFromDb = dataService.findQuestionById(currentQuestionId);
		assertFalse( questionFromDb.getValue().equals("question_with_no_answer"));



	}

}
