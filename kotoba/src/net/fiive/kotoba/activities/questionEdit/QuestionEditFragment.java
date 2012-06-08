package net.fiive.kotoba.activities.questionEdit;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import com.google.common.base.Preconditions;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.intern.android.view.alerts.RemoveAlertInfo;
import net.fiive.intern.android.view.validation.TextValidator;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;

import java.util.List;


public class QuestionEditFragment extends Fragment {

	public static final String QUESTION_BUNDLE_KEY = "currentQuestion";
	public static final String IS_EDITING_BUNDLE_KEY = "isEditing";


	private Question currentQuestion;
	private boolean isEditing;
	private DataService dataService;
	private EditText valueText;
	private EditText answerText;

	private TextValidator validator;
	private AlertHelper alertHelper = new AlertHelper();


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		dataService = new DataService(this.getActivity().getApplicationContext());
		this.validator = new TextValidator();

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
				saveCurrentQuestion(PostSaveAction.GO_BACK);
			}
		});

		valueText = (EditText) editQuestionView.findViewById(R.id.edit_question_value);
		answerText = (EditText) editQuestionView.findViewById(R.id.edit_question_answer);
		valueText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					answerText.requestFocus();
					return true;
				}
				return false;
			}
		});


		return editQuestionView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(QUESTION_BUNDLE_KEY) && savedInstanceState.containsKey(IS_EDITING_BUNDLE_KEY)) {
			currentQuestion = (Question) savedInstanceState.getSerializable(QUESTION_BUNDLE_KEY);
			isEditing = savedInstanceState.getBoolean(IS_EDITING_BUNDLE_KEY);
		} else {
			initializeState(getActivity().getIntent());
		}

		valueText.setText(currentQuestion.getValue());
		answerText.setText(currentQuestion.getAnswer());
		this.setHasOptionsMenu(true);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (dataService == null) {
			dataService = new DataService(this.getActivity().getApplicationContext());
			validator = new TextValidator();
			alertHelper = new AlertHelper();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.question_edit, menu);
		menu.findItem(R.id.remove_question_menu).setEnabled(isEditing);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.save_question_menu:
				saveCurrentQuestion(PostSaveAction.GO_BACK);
				return true;
			case R.id.question_save_and_new_menu:
				saveCurrentQuestion(PostSaveAction.CREATE_NEW);
				return true;
			case R.id.remove_question_menu:
				removeCurrentQuestion();
				return true;
			case R.id.cancel_edit_question_menu:
				goBack();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(QUESTION_BUNDLE_KEY, currentQuestion);
		outState.putBoolean(IS_EDITING_BUNDLE_KEY, isEditing);
	}

	public void mockAlertHelper(AlertHelper helper) {
		this.alertHelper = helper;
	}


	private void removeCurrentQuestion() {
		if (isEditing) {
			Resources resources = getResources();
			String alertTitle = resources.getString(R.string.alert_title);
			String confirmationMessage = resources.getString(R.string.are_you_sure_you_want_to_remove_question);
			String okButtonLabel = resources.getString(R.string.ok);
			String cancelButtonLabel = resources.getString(R.string.cancel);

			RemoveAlertInfo removeAlertInfo = new RemoveAlertInfo(alertTitle, confirmationMessage, okButtonLabel, cancelButtonLabel);
			alertHelper.showRemoveAlert(this.getActivity(), removeAlertInfo, new AlertHelper.Callback() {

				@Override
				public void onCallback() {
					dataService.removeQuestion(currentQuestion);
					goBack();
				}
			}, null);
		}
	}

	private void saveCurrentQuestion(PostSaveAction postSaveAction) {

		String answer = answerText.getText().toString();

		Resources resources = getResources();
		int maxQuestionLength = resources.getInteger(R.integer.max_question_length);
		String questionValue = valueText.getText().toString().replace("\n", " ");
		String mustTypeQuestionMessage = resources.getString(R.string.must_type_question);
		String maxQuestionLengthMessage = String.format(resources.getString(R.string.max_question_length_message), maxQuestionLength);

		if (validator.validateTextNotEmpty(valueText, mustTypeQuestionMessage) && validator.validateTextMaxLength(valueText, maxQuestionLength, maxQuestionLengthMessage)) {
			currentQuestion.setValue(questionValue);
			currentQuestion.setAnswer(answer);
			dataService.saveOrUpdateQuestion(currentQuestion);
			executePostSaveAction(postSaveAction);
		}
	}


	private void goBack() {
		Intent intent = new Intent(this.getActivity(), QuestionListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private void initializeState(Intent intent) {
		if (intent.getAction().equals(QuestionEditActivity.ADD_QUESTION_ACTION)) {
			currentQuestion = new Question();
			isEditing = false;
		} else if (intent.getAction().equals(QuestionEditActivity.EDIT_QUESTION_ACTION)) {
			isEditing = true;
			Uri data = intent.getData();
			List<String> pathSegments = data.getPathSegments();
			Long currentQuestionId = Long.parseLong(pathSegments.get(pathSegments.size() - 1));
			currentQuestion = dataService.findQuestionById(currentQuestionId);
			if (currentQuestion == null) {
				throw new IllegalStateException("Error: Current question is null");
			}
		} else {
			throw new IllegalArgumentException("Error: activity was started with incompatible intent");
		}
	}

	private void executePostSaveAction(PostSaveAction action) {
		Preconditions.checkNotNull(action);
		if (PostSaveAction.GO_BACK.equals(action)) {
			goBack();
		} else if (PostSaveAction.CREATE_NEW.equals(action)) {
			isEditing = false;
			currentQuestion = new Question();
			valueText.setText("");
			answerText.setText("");
			valueText.requestFocus();

		} else {
			throw new IllegalArgumentException("Error: unsupported PostSaveAction.");
		}
	}

}
