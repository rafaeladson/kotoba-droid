package net.fiive.kotoba.activities.questionEdit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.base.Constants;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;

import java.util.List;


public class QuestionEditFragment extends Fragment {

	private Long currentQuestionId;
	private boolean isEditing;
	private DataService dataService;
	private EditText valueText;
	private EditText answerText;


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
		dataService = new DataService(this.getActivity().getApplicationContext());

		Intent intent = getActivity().getIntent();
		initializeState(intent);


	}

	private void initializeState(Intent intent) {
		if (intent.getAction().equals(QuestionEditActivity.ADD_QUESTION_ACTION)) {
			currentQuestionId = null;
			isEditing = false;
			Log.i(Constants.LOG_TAG, "Editing a new question");
		} else if (intent.getAction().equals(QuestionEditActivity.EDIT_QUESTION_ACTION)) {
			isEditing = true;
			Uri data = intent.getData();
			List<String> pathSegments = data.getPathSegments();
			currentQuestionId = Long.parseLong(pathSegments.get(pathSegments.size() - 1));
		} else {
			throw new IllegalArgumentException("Error: activity was started with incompatible intent");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View editQuestionView = inflater.inflate(R.layout.question_edit, container);
		Button cancelButton = (Button) editQuestionView.findViewById(R.id.cancel_edit_question);
		cancelButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				goBack();
			}
		});

		Button saveButton = (Button) editQuestionView.findViewById(R.id.save_question);
		saveButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				saveCurrentQuestion();
			}
		});

		valueText = (EditText) editQuestionView.findViewById(R.id.edit_question_value);
		answerText = (EditText) editQuestionView.findViewById(R.id.edit_question_answer);

		return editQuestionView;
	}

	private void saveCurrentQuestion() {
		String questionValue = valueText.getText().toString();
		String answer = answerText.getText().toString();
		Question question;
		if (isEditing) {
			question = dataService.findQuestionById(currentQuestionId);
			if ( question != null ) {
			question.setValue(questionValue);
			question.setAnswer(answer);
			} else {
				throw new IllegalStateException("Error: could not find current question on database");
			}
		} else {
			question = new Question(questionValue, answer);
		}

		dataService.saveOrUpdateQuestion(question);
		goBack();
	}


	private void goBack() {
		Intent intent = new Intent(this.getActivity(), QuestionListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
