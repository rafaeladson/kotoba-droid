package net.fiive.kotoba.activities;

import net.fiive.kotoba.R;
import net.fiive.kotoba.domain.Word;
import net.fiive.kotoba.domain.WordRepository;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class WordGameFragment extends Fragment {
	
	private static final String ANSWER_IS_SHOWN_KEY = "answerIsShown";
	private static final String CURRENT_QUESTION_KEY = "currentWord";
	private TextView questionLabel;
	private TextView answerLabel;
	private WordRepository wordRepository = new WordRepository();
	private Word currentWord;
	private boolean translationIsShown = false;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		if (savedInstanceState != null) {
			currentWord = (Word) savedInstanceState.getSerializable(CURRENT_QUESTION_KEY);
			questionLabel.setText(currentWord.getValue());
			if (savedInstanceState.getBoolean(ANSWER_IS_SHOWN_KEY)) {
				this.showTranslation();
			} else {
				emptyTranslation();
			}
		} else {
			showNextWord();
		}

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View wordGameView = inflater.inflate(R.layout.word_game, container);
		
		questionLabel = (TextView) wordGameView.findViewById(R.id.questionLabel);
		answerLabel = (TextView) wordGameView.findViewById(R.id.answerLabel);
		Button nextWordButton = (Button) wordGameView.findViewById(R.id.nextQuestionButton);
		nextWordButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showNextWord();
			}
		});
		Button showTranslationButton = (Button) wordGameView.findViewById(R.id.showAnswerButton);
		showTranslationButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showTranslation();
			}

		});
		
		
		return wordGameView;
	}
	
	
	public void showNextWord() {
		currentWord = wordRepository.getRandomWord();
		questionLabel.setText(currentWord.getValue());
		emptyTranslation();
	}

	public void showTranslation() {
		assert (currentWord != null);
		answerLabel.setText(currentWord.getTranslation());
		translationIsShown = true;

	}

	public void setWordRepository(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	public Word getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(Word currentWord) {
		this.currentWord = currentWord;
	}
	
	private void emptyTranslation() {
		answerLabel.setText("");
		translationIsShown = false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(CURRENT_QUESTION_KEY, currentWord);
		outState.putBoolean(ANSWER_IS_SHOWN_KEY, translationIsShown);
	}

	
	
}
