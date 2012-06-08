package net.fiive.kotoba.test.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.test.ActivityUnitTestCase;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;
import net.fiive.kotoba.test.screen.questionEdit.QuestionEditScreen;


public class QuestionEditNewFragmentTest extends ActivityUnitTestCase<QuestionEditActivity> {

	private QuestionEditActivity activity;
	private DataService dataService;

	private QuestionEditScreen screen;


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

		screen = QuestionEditScreen.screenForUnitTests(activity);
		screen.onActivityCreated(null);
	}




	public void testUserClicksCancelButton() {
		screen.clickOnCancelButton();
		Intent backIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", backIntent.getComponent().getShortClassName());
	}

	public void testUserClicksCancelMenu() {
		screen.selectMenuItem(R.id.cancel_edit_question_menu);

		Intent backIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", backIntent.getComponent().getShortClassName());
	}

	public void testUserSaveNewQuestion() {
		screen.fillQuestionAndAnswer("foo", "bar");
		screen.clickOnSaveButton();


		Question questionFromDb = dataService.findQuestionById(dataService.findAllQuestionIds().get(0));
		assertEquals("foo", questionFromDb.getValue());
		assertEquals("bar", questionFromDb.getAnswer());
	}

	public void testUserClicksOnSaveAndNew() {
		screen.fillQuestionAndAnswer("foo", "bar");
		screen.selectSaveAndNewMenuItem();

		Question questionFromDb = dataService.findQuestionById(dataService.findAllQuestionIds().get(0));
		assertEquals("foo", questionFromDb.getValue());
		assertEquals("", screen.getValueText());
		assertEquals("", screen.getAnswerText());

		screen.fillQuestionAndAnswer("foo2", "bar2");
		screen.selectSaveAndNewMenuItem();
		assertEquals(2, dataService.findAllQuestionIds().size());
	}


}
