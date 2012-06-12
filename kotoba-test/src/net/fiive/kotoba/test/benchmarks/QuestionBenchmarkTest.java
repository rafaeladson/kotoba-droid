package net.fiive.kotoba.test.benchmarks;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;
import net.fiive.kotoba.test.screen.questionEdit.QuestionEditScreen;
import net.fiive.kotoba.test.screen.questionGame.QuestionGameScreen;
import net.fiive.kotoba.test.screen.questionList.QuestionListScreen;
import net.fiive.kotoba.test.utils.RandomUtils;

public class QuestionBenchmarkTest extends ActivityInstrumentationTestCase2<MainActivity> {

	static final int NUMBER_OF_QUESTIONS = 1000;
	static final int NUMBER_OF_SOLO_ADD_QUESTIONS = 2;

	private Solo solo;
	private DataService dataService;

	public QuestionBenchmarkTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
		solo = new Solo(this.getInstrumentation(), this.getActivity());
		dataService = new DataService(getInstrumentation().getTargetContext());
	}

	public void tearDown() throws Exception {
		super.tearDown();
		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
	}

	public void testBenchmarkQuestionGame() {
		if (Build.VERSION.SDK_INT < 11) {
			QuestionGameScreen gameScreen = QuestionGameScreen.screenForAcceptanceTest((MainActivity) solo.getCurrentActivity(), solo);
			gameScreen.selectMenuItem(R.string.manage_questions);
			for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
				addRandomQuestionOnDb();
			}

			long timeBefore = System.currentTimeMillis();
			for (int i = 0; i < NUMBER_OF_SOLO_ADD_QUESTIONS; i++) {
				addRandomQuestionBySolo();
			}
			long timeAfter = System.currentTimeMillis();
			long averageTimeAddingQuestions = (timeAfter - timeBefore) / (1000 * NUMBER_OF_SOLO_ADD_QUESTIONS);

			timeBefore = System.currentTimeMillis();
			solo.goBack();
			gameScreen = QuestionGameScreen.screenForAcceptanceTest((MainActivity) solo.getCurrentActivity(), solo);
			gameScreen.clickOnNextQuestionButton();
			timeAfter = System.currentTimeMillis();
			long timeReloadingQuestions = (timeAfter - timeBefore) / 1000;


			String tag = "Kotoba.Benchmarks";
			Log.w(tag, "Benchmarks results:");
			Log.w(tag, String.format("Average time adding a question by solo: %d seconds ", averageTimeAddingQuestions));
			Log.w(tag, String.format("Total time to go back to main screen and show the next question: %d seconds", timeReloadingQuestions));
		}
	}

	private void addRandomQuestionOnDb() {
		Question newRandomQuestion = new Question(RandomUtils.getRandomQuestionValue(), RandomUtils.getRandomQuestionValue());
		dataService.saveOrUpdateQuestion(newRandomQuestion);
	}

	private void addRandomQuestionBySolo() {
		QuestionListScreen listScreen = QuestionListScreen.screenForAcceptanceTests((QuestionListActivity) solo.getCurrentActivity(), solo);
		listScreen.selectNewQuestionMenu();
		QuestionEditScreen editScreen = QuestionEditScreen.screenForAcceptanceTests((QuestionEditActivity) solo.getCurrentActivity(), solo);
		editScreen.fillQuestionAndAnswer(RandomUtils.getRandomQuestionValue(), RandomUtils.getRandomQuestionValue());
		editScreen.selectSaveMenuItem();
	}
}
