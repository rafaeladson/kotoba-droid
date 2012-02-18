package net.fiive.kotoba.activities.game;


import net.fiive.intern.random.CircularItemCursor;
import net.fiive.intern.random.RandomIterator;
import net.fiive.kotoba.R;
import net.fiive.kotoba.dao.QuestionDAO;
import net.fiive.kotoba.domain.Question;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class QuestionGameFragment extends Fragment {
	
	private static final String ANSWER_IS_SHOWN_KEY = "answerIsShown";
	private static final String CURRENT_QUESTION_KEY = "currentQuestion";
	private TextView questionLabel;
	private TextView answerLabel;

	private boolean answerIsShown = false;

	private CircularItemCursor<Question> cursor;
	private Question currentQuestion;

	public QuestionGameFragment() {
		super();
		cursor = new CircularItemCursor<Question>(new RandomIterator.Builder<Question>(new QuestionDAO()));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		if (savedInstanceState != null) {
			currentQuestion = (Question) savedInstanceState.getSerializable(CURRENT_QUESTION_KEY);
			questionLabel.setText(currentQuestion.getValue());
			if (savedInstanceState.getBoolean(ANSWER_IS_SHOWN_KEY)) {
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
		
		questionLabel = (TextView) wordGameView.findViewById(R.id.questionLabel);
		answerLabel = (TextView) wordGameView.findViewById(R.id.answerLabel);
		answerLabel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showAnswer();
			}
		});
		
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
	
	
	public void showNextQuestion() {
		cursor.goToNext();
		currentQuestion = cursor.getCurrent();
		questionLabel.setText(currentQuestion.getValue());
		clearAnswer();
	}



	public void showAnswer() {
		assert (currentQuestion != null);
		answerLabel.setText(currentQuestion.getAnswer());
		answerIsShown = true;

	}

	private void clearAnswer() {
		answerLabel.setText(R.string.click_here_to_see_answer);
		answerIsShown = false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(CURRENT_QUESTION_KEY, currentQuestion);
		outState.putBoolean(ANSWER_IS_SHOWN_KEY, answerIsShown);
	}


}
