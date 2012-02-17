package net.fiive.kotoba.activities;

import net.fiive.kotoba.R;
import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.domain.QuestionRepository;
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
	private QuestionRepository questionRepository = new QuestionRepository();
	private Question currentQuestion;
	private boolean answerIsShown = false;


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
			showNextWord();
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
		
		Button nextWordButton = (Button) wordGameView.findViewById(R.id.nextQuestionButton);
		nextWordButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showNextWord();
			}
		});
		Button showTranslationButton = (Button) wordGameView.findViewById(R.id.showAnswerButton);
		showTranslationButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showAnswer();
			}

		});
		
		
		return wordGameView;
	}
	
	
	public void showNextWord() {
		currentQuestion = questionRepository.getRandomWord();
		questionLabel.setText(currentQuestion.getValue());
		clearAnswer();
	}

	public void showAnswer() {
		assert (currentQuestion != null);
		answerLabel.setText(currentQuestion.getTranslation());
		answerIsShown = true;

	}

	public void setQuestionRepository(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
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
