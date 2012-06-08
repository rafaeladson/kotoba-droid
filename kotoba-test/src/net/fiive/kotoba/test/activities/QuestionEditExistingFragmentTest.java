package net.fiive.kotoba.test.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditFragment;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;
import net.fiive.kotoba.test.screen.questionEdit.QuestionEditScreen;

public class QuestionEditExistingFragmentTest extends ActivityUnitTestCase<QuestionEditActivity> {


	private DataService dataService;
	private Long currentQuestionId;
	private QuestionEditScreen screen;


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
		QuestionEditActivity activity = getActivity();
		screen = QuestionEditScreen.screenForUnitTests(activity);
		screen.onActivityCreated(null);

	}

	public void testEditQuestion() {
		screen.fillQuestionAndAnswer("hello", "world");
		screen.selectSaveMenuItem();

		Question questionFromDatabase = dataService.findQuestionById(currentQuestionId);
		assertEquals("hello", questionFromDatabase.getValue());
		assertEquals("world", questionFromDatabase.getAnswer());

		Intent goBackIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", goBackIntent.getComponent().getShortClassName());
	}

	public void testUpdateQuestionByMenu() {
		screen.fillQuestionAndAnswer("menu", "");
		screen.selectMenuItem(R.id.save_question_menu);


		Question questionFromDatabase = dataService.findQuestionById(currentQuestionId);
		assertEquals("menu", questionFromDatabase.getValue());

		Intent goBackIntent = getStartedActivityIntent();
		assertEquals(".activities.questionList.QuestionListActivity", goBackIntent.getComponent().getShortClassName());
	}

	public void testRestoreFromBundle() {
		Bundle bundle = new Bundle();
		bundle.putSerializable(QuestionEditFragment.QUESTION_BUNDLE_KEY, new Question("fromBundleValue", "fromBundleAnswer"));
		bundle.putBoolean(QuestionEditFragment.IS_EDITING_BUNDLE_KEY, true);
		screen.onActivityCreated(bundle);

		assertEquals("fromBundleValue", screen.getValueText());
		assertEquals("fromBundleAnswer", screen.getAnswerText());
	}

	public void testSaveBundleState() {
		screen.onActivityCreated(null);

		Bundle bundle = new Bundle();
		screen.onSaveInstanceState(bundle);

		assertTrue(bundle.containsKey(QuestionEditFragment.QUESTION_BUNDLE_KEY));
		Question question = (Question) bundle.getSerializable(QuestionEditFragment.QUESTION_BUNDLE_KEY);
		assertEquals("foo", question.getValue());
		assertEquals("bar", question.getAnswer());

		assertTrue(bundle.getBoolean(QuestionEditFragment.IS_EDITING_BUNDLE_KEY));
	}

	public void testUserUpdateQuestionWithNoValue() {
		screen.fillQuestionAndAnswer("", "question_with_no_value");
		screen.selectSaveMenuItem();

		assertEquals("Please type a question.", screen.getValueTextValidationError().get());
		Question questionFromDb = dataService.findQuestionById(currentQuestionId);
		assertFalse(questionFromDb.getAnswer().equals("question_with_no_value"));
	}

	public void testUserUpdateQuestionWithValueTooLong() {
		int maxLength = getActivity().getResources().getInteger(R.integer.max_question_length);

		String text = "I. THE BURIAL OF THE DEAD\n" +
				      "APRIL is the cruellest month, breeding\t \n" +
				      "Lilacs out of the dead land, mixing\t \n" +
				      "Memory and desire, stirring\t \n" +
				      "Dull roots with spring rain.\t \n" +
				      "Winter kept us warm, covering\t         5\n" +
				      "Earth in forgetful snow, feeding\t \n" +
				      "A little life with dried tubers.\t \n" +
				      "Summer surprised us, coming over the Starnbergersee\t \n" +
				      "With a shower of rain; we stopped in the colonnade,\t \n" +
				      "And went on in sunlight, into the Hofgarten,\t  10\n" +
				      "And drank coffee, and talked for an hour.\t \n" +
				      "Bin gar keine Russin, stamm’ aus Litauen, echt deutsch.\t \n" +
				      "And when we were children, staying at the archduke’s,\t \n" +
				      "My cousin’s, he took me out on a sled,\t \n" +
				      "And I was frightened. He said, Marie,";
		String textWithoutNewLines = text.replace("\n", " ");
		String textTruncatedAtLimit = textWithoutNewLines.substring(0, maxLength);

		screen.fillQuestionAndAnswer(text, "");
		screen.selectSaveMenuItem();
		Question questionFromDb = dataService.findQuestionById(currentQuestionId);
		assertEquals(textTruncatedAtLimit, questionFromDb.getValue());
	}

	public void testUserUpdateQuestionWithNewlines() {
		screen.fillQuestionAndAnswer("foo\nbar", "");
		screen.selectSaveMenuItem();
		Question questionFromDb = dataService.findQuestionById(currentQuestionId);
		assertEquals("foo bar", questionFromDb.getValue());
	}


	public void testUserRemovedQuestion() {
		screen.removeCurrentQuestion();
		assertNull(dataService.findQuestionById(currentQuestionId));
	}


}
