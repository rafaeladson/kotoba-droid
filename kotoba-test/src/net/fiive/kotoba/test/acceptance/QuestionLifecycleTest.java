package net.fiive.kotoba.test.acceptance;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;
import net.fiive.kotoba.test.screen.questionEdit.QuestionEditScreen;
import net.fiive.kotoba.test.screen.questionGame.QuestionGameScreen;
import net.fiive.kotoba.test.screen.questionList.QuestionListScreen;


public class QuestionLifecycleTest extends ActivityInstrumentationTestCase2<MainActivity> {


	private Solo solo;

	public QuestionLifecycleTest() {
		super(MainActivity.class);
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testIteration() throws InterruptedException {

		if (Build.VERSION.SDK_INT < 11) {

			QuestionGameScreen screen = QuestionGameScreen.screenForAcceptanceTest(getInstrumentation(), solo);
			assertTrue(screen.hasDefaultQuestionValue());
			assertFalse(screen.isAnswerVisible());

			screen.clickOnShowAnswerButton();
			assertTrue(screen.isAnswerVisible());
			assertTrue(screen.hasDefaultAnswerValue());

			screen.clickOnNextQuestionButton();
			assertFalse(screen.isAnswerVisible());
			assertTrue(screen.hasDefaultQuestionValue());

			screen.selectManageQuestionsMenu();

			QuestionListScreen listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			assertTrue(listScreen.isEmpty());

			addQuestionWithValueAndAnswer("Question 01", "Answer 01");
			listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			assertEquals(1, listScreen.getListSize());
			assertEquals("Question 01", listScreen.getTextAtLine(0));

			addQuestionWithValueAndAnswer("Question 02", "");
			listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			assertEquals(2, listScreen.getListSize());
			assertEquals("Question 02", listScreen.getTextAtLine(1));


			solo.clickOnText("Question 02");
			QuestionEditScreen editScreen = QuestionEditScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			editScreen.fillQuestionAndAnswer("Question 03", editScreen.getAnswerText());
			editScreen.selectSaveMenuItem();

			listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			assertEquals(2, listScreen.getListSize());
			assertEquals("Question 03", listScreen.getTextAtLine(1));

			solo.goBack();
			screen = QuestionGameScreen.screenForAcceptanceTest(getInstrumentation(), solo);
			String firstText = screen.getValueText();
			assertTrue(firstText.equals("Question 01") || firstText.equals("Question 03"));
			assertFalse(screen.isAnswerVisible());
			screen.clickOnShowAnswerButton();
			if (firstText.equals("Question 01")) {
				assertEquals("Answer 01", screen.getAnswerText());
			} else {
				assertEquals("", screen.getAnswerText());
			}
			screen.clickOnNextQuestionButton();
			String secondText = screen.getValueText();
			assertTrue(secondText.equals("Question 01") || secondText.equals("Question 03"));
			assertFalse(screen.isAnswerVisible());

			screen.selectManageQuestionsMenu();
			removeQuestion("Question 01");
			listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			assertEquals(1, listScreen.getListSize());
			assertEquals("Question 03", listScreen.getTextAtLine(0));


			removeQuestion("Question 03");
			listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
			assertTrue(listScreen.isEmpty());

			solo.goBack();
			screen = QuestionGameScreen.screenForAcceptanceTest(getInstrumentation(), solo);
			assertTrue(screen.hasDefaultQuestionValue());
		}

	}

	private void removeQuestion(String question) throws InterruptedException {
		solo.clickOnText(question);
		QuestionEditScreen editScreen = QuestionEditScreen.screenForAcceptanceTests(getInstrumentation(), solo);
		editScreen.removeCurrentQuestion();
	}

	private void addQuestionWithValueAndAnswer(String value, String answer) {
		QuestionListScreen listScreen = QuestionListScreen.screenForAcceptanceTests(getInstrumentation(), solo);
		listScreen.selectNewQuestionMenu();

		QuestionEditScreen editScreen = QuestionEditScreen.screenForAcceptanceTests(getInstrumentation(), solo);
		editScreen.fillQuestionAndAnswer(value, answer);
		editScreen.selectSaveMenuItem();
	}


}
