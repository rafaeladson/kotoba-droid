package net.fiive.kotoba.test.activities;

import android.content.Intent;
import android.net.Uri;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.test.data.dao.DatabaseCleaner;

public class QuestionEditExistingFragmentTest extends ActivityUnitTestCase<QuestionEditActivity> {


	private QuestionEditActivity activity;
	private DataService dataService;
	private Long currentQuestionId;



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
	}

	public void testEditQuestion() {
		EditText questionValueText = (EditText)activity.findViewById(R.id.edit_question_value);
		EditText questionAnswerText = (EditText)activity.findViewById(R.id.edit_question_answer);
		questionValueText.setText("hello");
		questionAnswerText.setText("world");

		Button saveQuestionButton = (Button) activity.findViewById(R.id.save_question);
		saveQuestionButton.performClick();

		Question questionFromDatabase = dataService.findQuestionById(currentQuestionId);
		assertEquals("hello", questionFromDatabase.getValue());
		assertEquals("world", questionFromDatabase.getAnswer());

		Intent goBackIntent = getStartedActivityIntent();
		assertEquals( ".activities.questionList.QuestionListActivity", goBackIntent.getComponent().getShortClassName());
	}

}
