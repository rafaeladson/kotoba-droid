package net.fiive.kotoba.activities.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import net.fiive.intern.random.CircularItemCursor;
import net.fiive.kotoba.R;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;

import java.util.List;

public class QuestionGameFragment extends Fragment {

	private static final String ANSWER_VISIBLE_KEY = "answerVisible";
	private static final String CURRENT_QUESTION_KEY = "currentQuestion";
	private TextView questionLabel;
	private FrameLayout answerLayout;

	private boolean answerVisible = false;

	private CircularItemCursor<Long> cursor;
	private Question currentQuestion;
	private DataService dataService;
	private List<Long> questionIds;

	public QuestionGameFragment() {
		super();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		dataService = new DataService(this.getActivity().getApplicationContext());

		questionIds = dataService.findAllQuestionIds();
		if (questionIds.size() > 0) {
			cursor = new CircularItemCursor<Long>(questionIds);
		}


		if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_QUESTION_KEY) && savedInstanceState.containsKey(ANSWER_VISIBLE_KEY)) {
			currentQuestion = (Question) savedInstanceState.getSerializable(CURRENT_QUESTION_KEY);
			questionLabel.setText(currentQuestion.getValue());
			if (savedInstanceState.getBoolean(ANSWER_VISIBLE_KEY)) {
				this.showAnswer();
			} else {
				clearAnswer();
			}
		} else {
			showNextQuestion();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
				 Bundle savedInstanceState) {
		View wordGameView = inflater.inflate(R.layout.question_game, container);
		answerLayout = (FrameLayout) wordGameView.findViewById(R.id.answer_frame_layout);


		questionLabel = (TextView) wordGameView.findViewById(R.id.questionLabel);


		Button nextQuestionButton = (Button) wordGameView.findViewById(R.id.nextQuestionButton);
		nextQuestionButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showNextQuestion();
			}
		});
		Button showAnswerButton = (Button) wordGameView.findViewById(R.id.showAnswerButton);
		showAnswerButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showAnswer();
			}

		});


		return wordGameView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (dataService == null) {
			dataService = new DataService(this.getActivity().getApplicationContext());
		}
		List<Long> questionIds = dataService.findAllQuestionIds();
		Boolean databaseChanged = !questionIds.equals(this.questionIds);
		if (currentQuestion != null) {
			Question questionFromDb = dataService.findQuestionById(currentQuestion.getId());
			databaseChanged = databaseChanged || questionFromDb == null || !questionFromDb.equals(currentQuestion);
		}
		if (databaseChanged) {
			if (questionIds.size() > 0) {
				cursor = new CircularItemCursor<Long>(questionIds);
			} else {
				cursor = null;
			}
			showNextQuestion();
		}
	}

	public void showNextQuestion() {
		if (cursor != null) {
			cursor.goToNext();
			currentQuestion = dataService.findQuestionById(cursor.getCurrent());
			questionLabel.setText(currentQuestion.getValue());
		} else {
			currentQuestion = null;
			questionLabel.setText(getString(R.string.how_do_i_use_kotoba_question));
		}
		clearAnswer();
	}


	public void showAnswer() {
		showAnswerView();
		TextView answerView = (TextView) answerLayout.findViewById(R.id.answer_label);
		if (currentQuestion != null) {
			answerView.setText(currentQuestion.getAnswer());
		} else {
			answerView.setText(getResources().getText(R.string.how_do_i_use_kotoba_answer));
		}
		answerVisible = true;
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(CURRENT_QUESTION_KEY, currentQuestion);
		outState.putBoolean(ANSWER_VISIBLE_KEY, answerVisible);
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.question_game, menu);
	}

	/**
	 * DO NOT USE!! Only for use in tests!
	 *
	 * @param currentQuestion a new value for current question
	 */
	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	/**
	 * DO NOT USE!! Only for use in tests!!
	 *
	 * @param cursor a new value for the cursor.
	 */
	public void setCursor(CircularItemCursor<Long> cursor) {
		this.cursor = cursor;
	}

	public TextView getQuestionLabel() {
		return questionLabel;
	}

	public FrameLayout getAnswerLayout() {
		return answerLayout;
	}

	public boolean isAnswerVisible() {
		return answerVisible;
	}

	private void showAnswerView() {
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		answerLayout.removeAllViews();
		inflater.inflate(R.layout.question_game_answer_shown, answerLayout);
		answerLayout.setOnClickListener(null);
	}

	private void clearAnswer() {
		hideAnswerView();
		answerVisible = false;
	}

	private void hideAnswerView() {
		answerLayout.removeAllViews();
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		answerLayout = (FrameLayout) inflater.inflate(R.layout.question_game_answer_hidden, answerLayout);
		answerLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showAnswer();
			}
		});
	}


}
