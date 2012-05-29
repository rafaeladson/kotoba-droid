package net.fiive.kotoba.test.activities;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;

public class QuestionGameBeginsWithOneQuestionTest extends ActivityUnitTestCase<MainActivity> {

	private DataService dataService;
	private Question savedQuestion;
	private QuestionGameScreen screen;


	public QuestionGameBeginsWithOneQuestionTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		dataService = new DataService(getInstrumentation().getTargetContext());
		savedQuestion = new Question("foo", "bar");
		savedQuestion = dataService.saveOrUpdateQuestion(savedQuestion);

		startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null);
		MainActivity activity = this.getActivity();
		screen = new QuestionGameScreen(activity);
		screen.onActivityCreated(null);
		screen.clickOnShowAnswerButton();
	}

	public void testOnResumeWithSameItem() {
		assertEquals("bar", screen.getAnswerText());
	}

	public void testOnResumeWithZeroItems() {
		dataService.removeQuestion(savedQuestion);
		screen.onResume();
		assertTrue(screen.hasDefaultQuestionValue());
	}

	public void testOnResumeWithTwoItems() {
		Question anotherQuestion = new Question("testing", "question");
		dataService.saveOrUpdateQuestion(anotherQuestion);

		screen.onResume();
		assertFalse(screen.isAnswerVisible());
	}

	public void testOnResumeAfterUpdate() {
		screen.clickOnNextQuestionButton();
		savedQuestion.setValue("bar");
		dataService.saveOrUpdateQuestion(savedQuestion);

		screen.onResume();
		assertEquals("bar", screen.getValueText());
	}


}
